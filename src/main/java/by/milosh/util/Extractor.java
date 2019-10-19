package by.milosh.util;

public class Extractor {

    public static String onlyDigits(String value) {
        return value.replaceAll("\\D+", "");
    }
}
