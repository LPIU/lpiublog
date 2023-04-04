package com.lxs.domain.enums;

public enum AppHttpCodeEnum {
    CONTENT_NOT_NULL(506, "内容不能为空"),
    EMAIL_EXIST(503, "邮箱已存在"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传PNG或JPG"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    NEED_LOGIN(401, "需要登录后操作") // 登录
    ,
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    NIKENAME_EXIST(512, "昵称已存在"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    PARENT_MENU_ERROR(500, "父菜单不能是自身"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    SUCCESS(200, "操作成功") // 成功
    ,
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    USERNAME_NOT_NULL(508, "用户名不能为空");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
