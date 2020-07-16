package ooc.squishtable.utils;

import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Collection;

public class WebUtils {
    public static String toString(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append("UserName:").append(user.getUsername());

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            sb.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    sb.append(", ").append(a.getAuthority());
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }
    public static boolean isValidDateRange(String startDate, String endDate) {
        // false if either value is null
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");

        if (startDate == null || endDate == null) { return false; }
        Date start = (Date) formatter.parse(startDate);
        Date end = (Date) formatter.parse(endDate);

        // true if endDate after startDate
        if (end.after(start)) { return true; }

        return false;
    }
}
