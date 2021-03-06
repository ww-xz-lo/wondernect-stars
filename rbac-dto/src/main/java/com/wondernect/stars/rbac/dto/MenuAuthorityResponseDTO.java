package com.wondernect.stars.rbac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: MenuAuthorityResponseDTO
 * Author: chenxun
 * Date: 2019/7/3 11:36
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "菜单权限响应对象")
public class MenuAuthorityResponseDTO {

    @JsonProperty("code")
    @ApiModelProperty(notes = "代码")
    private String code;

    @JsonProperty("name")
    @ApiModelProperty(notes = "名称")
    private String name;

    @JsonProperty("limitable")
    @ApiModelProperty(notes = "是否限制可见时间")
    private Boolean limitable;

    @JsonProperty("start_time")
    @ApiModelProperty(notes = "可见开始时间")
    private Long startTime;

    @JsonProperty("end_time")
    @ApiModelProperty(notes = "可见结束时间")
    private Long endTime;

    @JsonProperty("operation_list")
    @ApiModelProperty(notes = "操作列表")
    private List<OperationAuthorityResponseDTO> operationList;
}
