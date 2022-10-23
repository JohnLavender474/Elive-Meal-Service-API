package main.api.domain;

import java.util.List;

public class ErrorMessages {

    public static final String ERR_MSG_HEAD = "Unable to process: ";
    public static final String TOO_MUCH_ORDER_ERR = "%s cannot be ordered more than once";
    public static final String MISSING_ERR = "%s is missing";

    public static String buildErrMsg(List<String> errors) {
        StringBuilder sb = new StringBuilder();
        sb.append(ERR_MSG_HEAD);
        for (int i = 0; i < errors.size(); i++) {
            if (i == 0) {
                String s = errors.get(0);
                String head = s.substring(0, 1).toUpperCase();
                String tail = s.substring(1).toLowerCase();
                sb.append(head).append(tail);
            } else {
                sb.append(errors.get(i).toLowerCase());
            }
            if (i < errors.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
