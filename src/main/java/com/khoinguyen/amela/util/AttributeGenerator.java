package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.User;

import java.text.Normalizer;
import java.util.regex.Pattern;

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
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFKD);

        Pattern pattern = Pattern.compile("[^\\w\\s.-]");
        String sanitized = pattern.matcher(normalized).replaceAll("_");

        sanitized = sanitized.replaceAll("\\s+", "_");
        sanitized = sanitized.replaceAll("_+", "_");

        return sanitized;
    }
}
