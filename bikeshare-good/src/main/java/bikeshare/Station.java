package bikeshare;

import java.util.Locale;

public enum Station {
    LIBRARY,
    CANTEEN,
    DORM;

    public static Station parse(String text) {
        try {
            return valueOf(text.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Station must be library, canteen or dorm: " + text);
        }
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
