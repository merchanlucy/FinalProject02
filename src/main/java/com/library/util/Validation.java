package com.library.util;

public class Validation {
    public static boolean isValidISBN(String isbn) {
        return isbn != null && isbn.matches("\\d{13}");
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
}
