package com.anntly.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    FOODS_NOT_FOUND(404,"菜品信息未找到"),
    Menus_NOT_FOUND(404,"菜单信息未找到"),
    Desks_NOT_FOUND(404,"餐桌信息未找到"),
    COUPONS_NOT_FOUND(404,"优惠券信息未找到"),
    Permission_NOT_FOUND(404,"权限信息未找到"),
    Roles_NOT_FOUND(404,"角色信息未找到"),
    Employees_NOT_FOUND(404,"员工信息未找到"),
    NOT_FOUND(404,"未找到信息"),
    PARAMETER_ERROR(400,"参数不合法"),
    INVALID_FILE_TYPE(400,"文件类型错误"),
    COUPONS_EXPIRE(400,"优惠券已失效"),
    SAVE_FAILED(500,"数据添加失败"),
    UPDATE_FAILED(500,"数据更新失败"),
    DELETE_FAILED(500,"数据删除失败"),
    FILE_UPLOAD_ERROR(500,"文件上传失败"),
    USERNAME_OR_PASSWORD_ERROR(401,"用户名或密码错误"),
    PASSWORD_ERROR(500,"密码不匹配"),
    LOGIN_FAILED(500,"登录失败"),
    LOGOUT_FAILED(500,"退出失败"),
    STOCK_ERROR(500,"库存不足"),
    User_EXIST(500,"用户已存在"),
    REFRESH_FAILED(3000,"token刷新失败"),
    TOKEN_NOT_FOUND(3001,"token不存在"),
    ;

    private int code;

    private String msg;
}
