package com.wondernect.stars.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondernect.elements.rdb.request.PageRequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: SaveRoleRequestDTO
 * Author: chenxun
 * Date: 2019/6/3 14:02
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户分页请求对象")
public class PageUserRequestDTO {

    @JsonProperty("value")
    @ApiModelProperty(notes = "搜索内容")
    private String value;

    @NotNull(message = "分页请求参数不能为空")
    @JsonProperty("page_request_data")
    @ApiModelProperty(notes = "分页参数")
    private PageRequestData pageRequestData;
}