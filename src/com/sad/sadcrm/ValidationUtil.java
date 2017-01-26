package com.sad.sadcrm;

import java.util.Map;

public class ValidationUtil {
    public static String validateNotNull(Map<String, String> fields) {
        for (String s : fields.keySet()) {
            if (s.equals("")) {
                return fields.get(s);
            }
        }

        return null;
    }

    public static boolean validatePassword(String password) {
        return 4 <= password.length() || password.length() <= 10;
    }

    public static boolean validateNotEqual(String value, String value2) {
        return value.equalsIgnoreCase(value2);
    }

    public static boolean validatePostalCode(String postalCode) {
        return postalCode.matches("[0-9]{2}-[0-9]{3}");
    }

    private final static int[] PESEL_WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    public static boolean validatePesel(String pesel) {
        if (pesel.length() != 11) {
            return false;
        }

        int controlSum = 0;
        for (int i = 0; i < 10; i++) {
            controlSum += Integer.parseInt(pesel.charAt(i) + "") * PESEL_WEIGHTS[i];
        }

        int controlDigit = Integer.parseInt(pesel.charAt(10) + "");
        controlSum = (10 - (controlSum % 10)) % 10;

        return controlSum == controlDigit;
    }
}
