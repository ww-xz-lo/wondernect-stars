package com.wondernect.stars.rbac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.rdb.base.model.BaseStringModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * Copyright (C), 2017-2018, wondernect.com
 * FileName: Role
 * Author: chenxun
 * Date: 2018/11/5 10:24
 * Description: 角色
 */
@Entity
@Table(
        name = "role",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})},
        indexes = {
                @Index(columnList = "roleTypeId")
        }
)
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "角色")
public class Role extends BaseStringModel implements Serializable {

    private static final long serialVersionUID = -420047983890095875L;

    @JsonProperty("name")
    @ApiModelProperty(notes = "名称")
    private String name;

    @JsonProperty("description")
    @ApiModelProperty(notes = "描述")
    private String description;

    @JsonProperty("editable")
    @ApiModelProperty(notes = "是否可编辑")
    private Boolean editable;

    @JsonProperty("deletable")
    @ApiModelProperty(notes = "是否可删除")
    private Boolean deletable;

    @JsonProperty("weight")
    @ApiModelProperty(notes = "权重")
    private Integer weight;

    @JsonProperty("role_type_id")
    @ApiModelProperty(notes = "角色类型id")
    private String roleTypeId;

    public Role() {
    }

    public Role(String name, String description, String roleTypeId) {
        this.name = name;
        this.description = description;
        this.editable = true;
        this.deletable = true;
        this.weight = 0;
        this.roleTypeId = roleTypeId;
    }

    public Role(String name, String description, Boolean editable, Boolean deletable, Integer weight, String roleTypeId) {
        this.name = name;
        this.description = description;
        this.editable = ESObjectUtils.isNotNull(editable) ? editable : true;
        this.deletable = ESObjectUtils.isNotNull(deletable) ? deletable : true;
        this.weight = weight;
        this.roleTypeId = roleTypeId;
    }
}
