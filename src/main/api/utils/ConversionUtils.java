package main.api.utils;

/** Utils for converting objects to other types. */
public class ConversionUtils {

    /**
     * Converts the enum value to a proper string, i.e. with first char capitalized
     * and the following chars lower case. For example, input "Example.VAL" returns
     * the output "Val".
     *
     * @param e the enum value
     * @return the proper string representation of the enum
     */
    public static <E extends Enum<?>> String enumToProperStr(E e) {
        String t = e.name().toLowerCase();
        return t.substring(0, 1).toUpperCase() + t.substring(1);
    }

}
