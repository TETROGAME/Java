import java.text.DateFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class I18n {
    private static ResourceBundle bundle;
    private static Locale locale = Locale.UK;

    private I18n() {}

    public static void init(Locale l) {
        if (l != null) {
            locale = l;
        }
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static String tr(String key, Object... args) {
        if (bundle == null) init(locale);
        String pattern;
        try {
            pattern = bundle.getString(key);
        } catch (MissingResourceException e) {
            pattern = "??" + key + "??";
        }
        return MessageFormat.format(pattern, args);
    }

    public static String fmt(LocalDate date) {
        if (date == null) return "";
        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale);
        return date.format(fmt);
    }

    public static String fmt(Date date) {
        if (date == null) return "";
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
        return df.format(date);
    }

    public static boolean isYes(String input) {
        if (input == null) return false;
        String s = input.trim().toLowerCase(locale);
        String lang = locale.getLanguage();
        if ("ru".equals(lang)) {
            return s.equals("y") || s.equals("yes") || s.equals("д") || s.equals("да");
        } else if ("be".equals(lang)) {
            return s.equals("y") || s.equals("yes") || s.equals("т") || s.equals("так");
        } else {
            return s.equals("y") || s.equals("yes");
        }
    }
}