package main.utils;

public class Globals {

    public static final String DASHED_DELIM = "---------------------------------------------------------------";

    public static int parseIntOrDefault(String s, int d) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return d;
        }
    }

    public static boolean equalsAny(int x, int... any) {
        for (int i : any) {
            if (x == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    public static boolean isNumericInput(String s) {
        if (s.isEmpty()) {
            return false;
        }
        String[] tokens = s.split("\\s+");
        if (tokens.length != 1) {
            return false;
        }
        String t = tokens[0].replaceAll("\\s+", "");
        for (int i = 0; i < t.length(); i++) {
            if (!Character.isDigit(t.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
