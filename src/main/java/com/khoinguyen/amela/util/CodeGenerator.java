package com.khoinguyen.amela.util;

import java.util.Random;

public class CodeGenerator {
    public static String generateCode() {
        return Constant.PREFIX + System.currentTimeMillis();
    }

    private static final String PREFIX = "A";

    public static void main(String[] args) {
        System.out.println(generateEmployeeCode());
    }

    private static String generateEmployeeCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0 đến 9
        }
        return sb.toString();
    }
}
