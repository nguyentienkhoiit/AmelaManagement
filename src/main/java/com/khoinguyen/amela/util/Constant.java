package com.khoinguyen.amela.util;

import java.util.Collection;
import java.util.List;

public class Constant {
    public static final Long EMAIL_WAITING_EXPIRATION = 3600L;
    public static final Long PAGE_INDEX = 1L;
    public static final Long PAGE_SIZE = 6L;
    public static final String PASSWORD_DEFAULT = "123456";
    public static final String PREFIX = "A";
    public static final String HOST = "http://localhost:8080/";
    public static String[] LIST_PERMIT_ALL = new String[]{
            "/forgot-password",
            "/new-password",
            "/user-new-password",
            "/register/**",
            "/login",
            "/css/**",
            "/image/**",
            "js/**"};
    public static Collection<String> LIST_ATTRIBUTE_NAME = List.of(
            "{{name}}", "{{age}}", "{{address}}",
            "{{code}}", "{{email}}", "{{phone}}",
            "{{username}}", "{{username}}", "{{username}}",
            "{{department}}", "{{position}}"
    );
    public static String ADMIN_NAME = "ADMIN";
    public static String USER_NAME = "USER";
}
