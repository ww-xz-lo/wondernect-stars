package com.wondernect.stars.session.dto.captcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondernect.elements.rdb.request.SortData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: ListPrivilegeRequestDTO
 * Author: chenxun
 * Date: 2019/6/3 14:02
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "验证码列表请求对象")
public class ListCaptchaRequestDTO {

    @JsonProperty("value")
    @ApiModelProperty(notes = "输入(代码或名称)")
    private String value;

    @JsonProperty("sort_data_list")
    @ApiModelProperty(notes = "列表排序参数")
    private List<SortData> sortDataList;
}
