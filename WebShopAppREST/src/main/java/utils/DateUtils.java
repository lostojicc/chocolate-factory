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
        
        // Set the calendars to the given dates
        cal1.setTime(date1);
        cal2.setTime(date2);
        
        // Ensure cal1 is before cal2
        if (cal1.after(cal2)) {
            Calendar temp = cal1;
            cal1 = cal2;
            cal2 = temp;
        }
        
        // Add one month to cal1
        cal1.add(Calendar.MONTH, 1);
        
        // Check if cal1 after adding one month is still before or equal to cal2
        return cal1.after(cal2);
    }
}
