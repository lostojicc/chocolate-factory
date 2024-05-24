package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {
	private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd/MM/yyyy"));

    public static Date parse(String dateString) throws ParseException {
        return DATE_FORMATTER.get().parse(dateString);
    }

    public static String format(Date date) {
        return DATE_FORMATTER.get().format(date);
    }
}
