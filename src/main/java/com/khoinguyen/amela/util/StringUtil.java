package com.khoinguyen.amela.util;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.khoinguyen.amela.util.Constant.LIST_ATTRIBUTE_NAME;

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

    public static Set<String> extractAttributeName(String input) {
        Pattern pattern = Pattern.compile("\\{\\{([^{}]+)}}");
        Matcher matcher = pattern.matcher(input);

        Set<String> result = new HashSet<>();
        while (matcher.find()) {
            String extractedText = matcher.group(0);
            result.add(extractedText);
        }
        return result;
    }

    public static Set<String> extractAttributeNameInvalid(String input) {
        Set<String> attributes = extractAttributeName(input);
        Set<String> sources = new HashSet<>(LIST_ATTRIBUTE_NAME);

        Set<String> listErrors = new HashSet<>();
        for (String item : attributes) {
            if (!sources.contains(item)) {
                listErrors.add(item);
            }
        }
        return listErrors;
    }
}
