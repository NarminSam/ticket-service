package com.io.ticket.util;

public class StringReformUtil {

    public static String reformatDate(String date) {
        StringBuilder newDate = new StringBuilder()
                .append(date, 0, 4).append("/")  // Append year and slash
                .append(date, 4, 6).append("/")  // Append month and slash
                .append(date, 6, 8);             // Append day

        return newDate.toString();
    }

    public static String reformString(String text) {
        return text.replaceAll("[\\[\\]\"]", "");
    }

}
