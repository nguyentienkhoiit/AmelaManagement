package com.khoinguyen.amela.util;

public class StringUtil {
    public static String formatString(String request, boolean isUpperCaseFirst) {
        String[] words = request.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                String firstLetter = isUpperCaseFirst ? word.substring(0, 1).toUpperCase() : word.substring(0, 1);
                String restLetters = word.substring(1).toLowerCase();
                result.append(firstLetter).append(restLetters).append(" ");
            }
        }
        if (!result.isEmpty()) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString().trim().replaceAll("\\s+", " ");
    }
}
