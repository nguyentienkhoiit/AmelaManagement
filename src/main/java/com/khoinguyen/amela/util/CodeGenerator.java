package com.khoinguyen.amela.util;

public class CodeGenerator {
    public static String generateCode() {
        return Constant.PREFIX + System.currentTimeMillis();
    }
}
