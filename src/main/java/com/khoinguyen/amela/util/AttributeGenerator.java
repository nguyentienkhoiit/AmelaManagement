package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.User;

public class AttributeGenerator {
    public static String generateNextUserCode(String currentCode) {
        currentCode = currentCode.replaceAll(Constant.PREFIX, "");
        int userCount = Integer.parseInt(currentCode);
        userCount++;
        return String.format("%s%06d", Constant.PREFIX, userCount);
    }

    public static String generatorUsername(User user, Long id) {
        return user.getEmail().split("@")[0] + id;
    }
}
