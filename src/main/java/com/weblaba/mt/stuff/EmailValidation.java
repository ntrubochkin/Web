package com.weblaba.mt.stuff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailValidation {
    private EmailValidation() { }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String email) {
        Matcher m = EMAIL_PATTERN.matcher(email);
        return m.find();
    }
}