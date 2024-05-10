package com.khoinguyen.amela.util;

public class CodeGenerator {
    public static String generateNextUserCode(String currentCode) {
        currentCode = currentCode.replaceAll(Constant.PREFIX, "");
        int userCount = Integer.parseInt(currentCode);
        userCount++;
        return String.format("%s%06d", Constant.PREFIX, userCount);
    }
}
