package in.seleption.Utility;

import java.util.List;

/**
 * Created by Lokesh on 08-12-2015.
 */
public class Utility {

    public static boolean isValidText(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean isValidList(List<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

}
