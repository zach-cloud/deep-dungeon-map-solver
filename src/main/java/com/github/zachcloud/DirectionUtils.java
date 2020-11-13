package com.github.zachcloud;

public class DirectionUtils {

    public static void verifyDirection(String originalDirection) {
        originalDirection = originalDirection.toLowerCase();
        if (!originalDirection.equalsIgnoreCase("n") && !originalDirection.equalsIgnoreCase("w") &&
                !originalDirection.equalsIgnoreCase("e") && !originalDirection.equalsIgnoreCase("s") &&
                !originalDirection.equalsIgnoreCase("d") && !originalDirection.equalsIgnoreCase("u")) {
            throw new RuntimeException("Invalid direction: " + originalDirection);
        }
    }

    public static String reverseDirecrtion(String originalDirection) {
        originalDirection = originalDirection.toLowerCase();
        if(originalDirection.equals("s")) {
            return "n";
        } else if(originalDirection.equals("n")) {
            return "s";
        } else if(originalDirection.equals("w")) {
            return "e";
        } else if(originalDirection.equals("e")) {
            return "w";
        }
        throw new RuntimeException("Invalid direction (irreversible): " + originalDirection);
    }
}
