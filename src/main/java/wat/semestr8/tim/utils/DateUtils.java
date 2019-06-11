package wat.semestr8.tim.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";
    private static final TimeZone utc = TimeZone.getTimeZone("UTC");
    private static final SimpleDateFormat concertDateIsoFormatter = new SimpleDateFormat(ISO_FORMAT);
    static { concertDateIsoFormatter.setTimeZone(utc); }

    public static Date parseDate(String dateStr) throws ParseException
    {
        return formatter.parse(dateStr);
    }

    public static String formatDate(Date theDate)
    {
        String result = null;
        if (theDate != null) {
            result = formatter.format(theDate);
        }
        return result;
    }

    public static String toUtcString(Date date)
    {
        if(date == null) return null;
        return concertDateIsoFormatter.format(date);
    }

    public static Date utcToDate(String utc) throws ParseException {
        return concertDateIsoFormatter.parse(utc);
    }

    public static long minutesBetweenDates(Date before, Date after){
        return (after.getTime() - before.getTime()) / (60 * 1000);
    }
}
