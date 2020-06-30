package com.wondernect.stars.rbac.service.userrole;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.rdb.base.service.BaseStringService;
import com.wondernect.elements.rdb.criteria.Criteria;
import com.wondernect.elements.rdb.criteria.Restrictions;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.rbac.dto.userrole.ListUserRoleRequestDTO;
import com.wondernect.stars.rbac.dto.userrole.PageUserRoleRequestDTO;
import com.wondernect.stars.rbac.dto.userrole.UserRoleRequestDTO;
import com.wondernect.stars.rbac.dto.userrole.UserRoleResponseDTO;
import com.wondernect.stars.rbac.manager.RoleManager;
import com.wondernect.stars.rbac.manager.UserRoleManager;
import com.wondernect.stars.rbac.model.Role;
import com.wondernect.stars.rbac.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色服务抽象实现类
 *
 * @author chenxun 2020-06-28 21:46:02
 **/
public abstract class UserRoleAbstractService extends BaseStringService<UserRoleResponseDTO, UserRole> implements UserRoleInterface {

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private RoleManager roleManager;

    @Transactional
    @Override
    public void add(UserRoleRequestDTO userRoleRequestDTO) {
        Role role = roleManager.findById(userRoleRequestDTO.getRoleId());
        if (ESObjectUtils.isNull(role)) {
            throw new BusinessException("角色不存在");
        }
        UserRole userRole = userRoleManager.findByUserIdAndRoleId(userRoleRequestDTO.getUserId(), userRoleRequestDTO.getRoleId());
        if (ESObjectUtils.isNotNull(userRole)) {
            throw new BusinessException("用户角色已存在");
        }
        super.saveEntity(new UserRole(userRoleRequestDTO.getUserId(), userRoleRequestDTO.getRoleId()));
    }

    @Transactional
    @Override
    public void delete(UserRoleRequestDTO userRoleRequestDTO) {
        UserRole userRole = userRoleManager.findByUserIdAndRoleId(userRoleRequestDTO.getUserId(), userRoleRequestDTO.getRoleId());
        if (ESObjectUtils.isNotNull(userRole)) {
            super.deleteById(userRole.getId());
        }
    }

    @Override
    public UserRoleResponseDTO findByUserIdAndRoleId(String userId, String roleId) {
        UserRole userRole = userRoleManager.findByUserIdAndRoleId(userId, roleId);
        if (ESObjectUtils.isNull(userRole)) {
            return null;
        }
        return generate(userRole);
    }

    @Override
    public List<UserRoleResponseDTO> list(ListUserRoleRequestDTO listUserRoleRequestDTO) {
        Criteria<UserRole> userRoleCriteria = new Criteria<>();
        userRoleCriteria.add(Restrictions.eq("userId", listUserRoleRequestDTO.getUserId()));
        return super.findAll(userRoleCriteria, listUserRoleRequestDTO.getSortDataList());
    }

    @Override
    public PageResponseData<UserRoleResponseDTO> page(PageUserRoleRequestDTO pageUserRoleRequestDTO) {
        Criteria<UserRole> userRoleCriteria = new Criteria<>();
        userRoleCriteria.add(Restrictions.eq("userId", pageUserRoleRequestDTO.getUserId()));
        return super.findAll(userRoleCriteria, pageUserRoleRequestDTO.getPageRequestData());
    }

    @Override
    public UserRoleResponseDTO generate(UserRole userRole) {
        Role role = roleManager.findById(userRole.getRoleId());
        return new UserRoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }
}