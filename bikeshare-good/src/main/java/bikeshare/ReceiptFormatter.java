package bikeshare;

public final class ReceiptFormatter {
    private ReceiptFormatter() {
    }

    public static String format(Ride ride) {
        return """
                ----- Receipt -----
                Bike:    %s
                Minutes: %d
                Fee:     %d baht
                -------------------""".formatted(ride.bikeId(), ride.minutes(), ride.fee());
    }
}
