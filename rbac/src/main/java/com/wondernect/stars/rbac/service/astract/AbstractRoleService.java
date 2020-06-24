package com.wondernect.stars.rbac.service.astract;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.rdb.criteria.Criteria;
import com.wondernect.elements.rdb.criteria.Restrictions;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.rbac.dto.role.ListRoleRequestDTO;
import com.wondernect.stars.rbac.dto.role.PageRoleRequestDTO;
import com.wondernect.stars.rbac.dto.role.RoleResponseDTO;
import com.wondernect.stars.rbac.dto.role.SaveRoleRequestDTO;
import com.wondernect.stars.rbac.manager.RoleManager;
import com.wondernect.stars.rbac.manager.RoleMenuManager;
import com.wondernect.stars.rbac.manager.RoleMenuOperationManager;
import com.wondernect.stars.rbac.manager.RoleTypeManager;
import com.wondernect.stars.rbac.model.Role;
import com.wondernect.stars.rbac.model.RoleType;
import com.wondernect.stars.rbac.service.InitRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: RoleService
 * Author: chenxun
 * Date: 2020-02-21 13:56
 * Description:
 */
public abstract class AbstractRoleService implements InitRoleService {

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private RoleTypeManager roleTypeManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private RoleMenuOperationManager roleMenuOperationManager;

    @Transactional
    public RoleResponseDTO create(SaveRoleRequestDTO saveRoleRequestDTO) {
        RoleType roleType = roleTypeManager.findByCode(saveRoleRequestDTO.getRoleType());
        if (ESObjectUtils.isNull(roleType)) {
            throw new BusinessException("角色类型不存在");
        }
        Role role = roleManager.findByCode(saveRoleRequestDTO.getCode());
        if (ESObjectUtils.isNotNull(role)) {
            throw new BusinessException("角色已存在");
        }
        if (ESObjectUtils.isNull(saveRoleRequestDTO.getWeight())) {
            saveRoleRequestDTO.setWeight(0);
        }
        role = roleManager.save(new Role(saveRoleRequestDTO.getCode(), saveRoleRequestDTO.getName(), saveRoleRequestDTO.getDescription(), saveRoleRequestDTO.getEditable(), saveRoleRequestDTO.getDeletable(), saveRoleRequestDTO.getWeight(), saveRoleRequestDTO.getRoleType()));
        return generate(role);
    }

    @Transactional
    public RoleResponseDTO update(String id, SaveRoleRequestDTO saveRoleRequestDTO) {
        RoleType roleType = roleTypeManager.findByCode(saveRoleRequestDTO.getRoleType());
        if (ESObjectUtils.isNull(roleType)) {
            throw new BusinessException("角色类型不存在");
        }
        Role role = roleManager.findById(id);
        if (ESObjectUtils.isNull(role)) {
            throw new BusinessException("角色不存在");
        }
        if (!role.getEditable()) {
            throw new BusinessException("角色不可编辑");
        }
        role.setName(saveRoleRequestDTO.getName());
        role.setDescription(saveRoleRequestDTO.getDescription());
        role.setEditable(saveRoleRequestDTO.getEditable());
        role.setDeletable(saveRoleRequestDTO.getDeletable());
        if (ESObjectUtils.isNotNull(saveRoleRequestDTO.getWeight())) {
            role.setWeight(saveRoleRequestDTO.getWeight());
        }
        role.setRoleType(saveRoleRequestDTO.getRoleType());
        role = roleManager.save(role);
        return generate(role);
    }

    @Transactional
    public void delete(String id) {
        Role role = roleManager.findById(id);
        if (ESObjectUtils.isNull(role)) {
            throw new BusinessException("角色不存在");
        }
        if (!role.getDeletable()) {
            throw new BusinessException("角色不可删除");
        }
        roleMenuManager.deleteAllByRoleCode(role.getCode());
        roleMenuOperationManager.deleteAllByRoleCode(role.getCode());
        roleManager.deleteById(id);
    }

    public RoleResponseDTO getByCode(String code) {
        Role role = roleManager.findByCode(code);
        if (ESObjectUtils.isNull(role)) {
            return null;
        }
        return generate(role);
    }

    public RoleResponseDTO getById(String id) {
        Role role = roleManager.findById(id);
        if (ESObjectUtils.isNull(role)) {
            return null;
        }
        return generate(role);
    }

    public List<RoleResponseDTO> list(ListRoleRequestDTO listRoleRequestDTO) {
        Criteria<Role> roleCriteria = new Criteria<>();
        roleCriteria.add(
                Restrictions.and(
                        Restrictions.eq("roleType", listRoleRequestDTO.getRoleType()),
                        Restrictions.or(
                                Restrictions.like("code", listRoleRequestDTO.getValue(), MatchMode.ANYWHERE),
                                Restrictions.like("name", listRoleRequestDTO.getValue(), MatchMode.ANYWHERE)
                        )
                )
        );
        List<Role> roleList = roleManager.findAll(roleCriteria, listRoleRequestDTO.getSortDataList());
        List<RoleResponseDTO> roleResponseDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (Role role : roleList) {
                roleResponseDTOList.add(generate(role));
            }
        }
        return roleResponseDTOList;
    }

    public PageResponseData<RoleResponseDTO> page(PageRoleRequestDTO pageRoleRequestDTO) {
        Criteria<Role> roleCriteria = new Criteria<>();
        roleCriteria.add(
                Restrictions.and(
                        Restrictions.eq("roleType", pageRoleRequestDTO.getRoleType()),
                        Restrictions.or(
                                Restrictions.like("code", pageRoleRequestDTO.getValue(), MatchMode.ANYWHERE),
                                Restrictions.like("name", pageRoleRequestDTO.getValue(), MatchMode.ANYWHERE)
                        )
                )
        );
        PageResponseData<Role> rolePageResponseData = roleManager.findAll(roleCriteria, pageRoleRequestDTO.getPageRequestData());
        List<RoleResponseDTO> roleResponseDTOList = new ArrayList<>();
        List<Role> roleList = rolePageResponseData.getDataList();
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (Role role : roleList) {
                roleResponseDTOList.add(generate(role));
            }
        }
        return new PageResponseData<>(
                rolePageResponseData.getPage(),
                rolePageResponseData.getSize(),
                rolePageResponseData.getTotalPages(),
                rolePageResponseData.getTotalElements(),
                roleResponseDTOList
        );
    }

    public RoleResponseDTO generate(Role role) {
        RoleType roleType = roleTypeManager.findByCode(role.getRoleType());
        return new RoleResponseDTO(
                role.getId(),
                role.getCode(),
                role.getName(),
                role.getDescription(),
                role.getEditable(),
                role.getDeletable(),
                role.getWeight(),
                role.getRoleType(),
                ESObjectUtils.isNotNull(roleType) ? roleType.getName() : null
        );
    }
}