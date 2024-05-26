package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.User;

import java.text.Normalizer;

public class AttributeGenerator {
    public static String generateNextUserCode(String currentCode) {
        currentCode = currentCode.replaceAll(Constant.PREFIX, "");
        int userCount = Integer.parseInt(currentCode);
        userCount++;
        return String.format("%s%06d", Constant.PREFIX, userCount);
    }

    public static String generatorUsername(User user, Long id) {
        String name = user.getEmail().split("@")[0] + id;
        return sanitizeFileName(name);
    }

    public static String sanitizeFileName(String fileName) {
        final String DEFAULT_REPLACEMENT = "_";
        final String FILENAME_REGEX = "[^\\w\\s.-]+";

        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFKD);
        String sanitized = normalized.replaceAll(FILENAME_REGEX, DEFAULT_REPLACEMENT);
        sanitized = sanitized.replaceAll("[\\s_]+", DEFAULT_REPLACEMENT);

        return sanitized;
    }
}
