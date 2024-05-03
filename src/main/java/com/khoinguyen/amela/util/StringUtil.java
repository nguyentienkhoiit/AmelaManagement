package com.khoinguyen.amela.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
    public static String formatString(String request, boolean isUpperCaseFirst) {
        if (request == null) return "";
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

    public static String replaceSpecialChars(String input) {
        String regex = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`”“-]";
        return input.replaceAll(regex, " ")
                .replaceAll("\\s+", " ")
                .toLowerCase().trim();
    }

    public static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("")
                .replace("Đ", "D").replace("đ", "d");
        return replaceSpecialChars(result);
    }

}
