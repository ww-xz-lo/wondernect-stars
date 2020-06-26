package com.wondernect.stars.user.controller;

import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.stars.user.dto.auth.third.AuthUserThirdAuthRequestDTO;
import com.wondernect.stars.user.dto.auth.third.SaveUserThirdAuthRequestDTO;
import com.wondernect.stars.user.dto.auth.third.UserThirdAuthResponseDTO;
import com.wondernect.stars.user.em.AppType;
import com.wondernect.stars.user.service.astract.dfault.UserThirdAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: UserThirdAuthController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户第三方认证
 */
@Api(tags = "用户第三方认证", description = "用户第三方认证")
@Validated
@RestController
@RequestMapping(value = "/v1/{application}/user_third_auth")
public class UserThirdAuthController {

    @Autowired
    private UserThirdAuthService userThirdAuthService;

    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/{user_id}/create")
    public BusinessData<UserThirdAuthResponseDTO> create(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "user_id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveUserThirdAuthRequestDTO saveUserThirdAuthRequestDTO
    ) {
        return new BusinessData<>(userThirdAuthService.create(userId, saveUserThirdAuthRequestDTO));
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{user_id}/update")
    public BusinessData<UserThirdAuthResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "user_id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveUserThirdAuthRequestDTO saveUserThirdAuthRequestDTO
    ) {
        return new BusinessData<>(userThirdAuthService.update(userId, saveUserThirdAuthRequestDTO));
    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{user_id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "user_id", required = false) String userId,
            @ApiParam(required = true) @NotBlank(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType
    ) {
        userThirdAuthService.deleteByUserIdAndAppType(userId, appType);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/{user_id}/detail")
    public BusinessData<UserThirdAuthResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "user_id", required = false) String userId,
            @ApiParam(required = true) @NotBlank(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType
    ) {
        return new BusinessData<>(userThirdAuthService.findByUserIdAndAppType(userId, appType));
    }

    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<UserThirdAuthResponseDTO> detailByAppTypeAndAppUserId(
            @ApiParam(required = true) @NotBlank(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType,
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @RequestParam(value = "app_user_id", required = false) String appUserId
    ) {
        return new BusinessData<>(userThirdAuthService.findByAppTypeAndAppUserId(appType, appUserId));
    }

    @ApiOperation(value = "认证", httpMethod = "POST")
    @PostMapping(value = "/auth")
    public BusinessData<UserThirdAuthResponseDTO> list(
            @ApiParam(required = true) @NotNull(message = "认证请求参数不能为空") @Validated @RequestBody AuthUserThirdAuthRequestDTO authUserThirdAuthRequestDTO
    ) {
        return new BusinessData<>(userThirdAuthService.auth(authUserThirdAuthRequestDTO));
    }
}
