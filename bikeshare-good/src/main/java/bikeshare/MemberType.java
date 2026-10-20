package bikeshare;

import java.util.Locale;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int ratePerMinute;

    MemberType(int ratePerMinute) {
        this.ratePerMinute = ratePerMinute;
    }

    public int feeFor(int minutes) {
        return ratePerMinute * minutes;
    }

    public static MemberType parse(String text) {
        try {
            return valueOf(text.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Member type must be student or staff: " + text);
        }
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ROOT);
    }
}
