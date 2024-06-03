package com.khoinguyen.amela.util;

import java.util.Collection;
import java.util.List;

public class Constant {
    public static final Long EMAIL_WAITING_EXPIRATION = 3600L;
    public static final Long PAGE_INDEX = 1L;
    public static final Long PAGE_SIZE = 6L;
    public static final Long PAGE_SIZE_MESSAGE_USER = 6L;
    public static final String PASSWORD_DEFAULT = "123456";
    public static final String PREFIX = "A";
    public static final String FIRST_CODE = "000001";
    public static final String[] LIST_PERMIT_ALL = new String[] {
        "/forgot-password",
        "/new-password",
        "/user-new-password",
        "/register/**",
        "/login",
        "/css/**",
        "/image/**",
        "js/**",
        "/badRequest",
        "/notFound",
        "/forbidden",
        "/methodNotAllowed",
        "/internalServerError",
        "/badGateway",
        "/tooManyRequests",
        "/requestsTimeout",
        "/unauthorized"
    };
    public static final Collection<String> LIST_ATTRIBUTE_NAME = List.of(
            "{{name}}",
            "{{age}}",
            "{{address}}",
            "{{code}}",
            "{{email}}",
            "{{phone}}",
            "{{username}}",
            "{{username}}",
            "{{username}}",
            "{{department}}",
            "{{position}}");
    public static final String ADMIN_NAME = "ADMIN";
    public static final String USER_NAME = "USER";
    public static final List<String> ALLOWED_EXTENSIONS = List.of("jpeg", "png", "jpg");
    public static final String UPLOAD_DIR = "./upload";
    public static final String DEFAULT_AVATAR = "avatar.jpg";
    public static final int IN_DAY_EDITED = 3;
}
