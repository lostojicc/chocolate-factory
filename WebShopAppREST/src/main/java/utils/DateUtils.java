package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd/MM/yyyy"));

    public static Date parse(String dateString) throws ParseException {
        return DATE_FORMATTER.get().parse(dateString);
    }

    public static String format(Date date) {
        return DATE_FORMATTER.get().format(date);
    }
    
    public static boolean isWithinMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        
        cal1.setTime(date1);
        cal2.setTime(date2);
        
        if (cal1.after(cal2)) {
            Calendar temp = cal1;
            cal1 = cal2;
            cal2 = temp;
        }
        
        cal1.add(Calendar.MONTH, 1);
        
        return cal1.after(cal2);
    }
}
