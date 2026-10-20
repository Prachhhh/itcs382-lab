package bikeshare;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.Scanner;

public class Cli {
    private static final String USAGE = """
            Commands:
              rent <memberId> <student|staff> <library|canteen|dorm>
              return <memberId> <library|canteen|dorm> <minutes>
              quit""";

    private final BikeShare share;
    private final Scanner in;
    private final PrintStream out;

    public Cli(BikeShare share, InputStream in, PrintStream out) {
        this.share = share;
        this.in = new Scanner(in);
        this.out = out;
    }

    public void run() {
        out.println(USAGE);
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) {
                break;
            }
            if (!line.isEmpty()) {
                handle(line.split("\\s+"));
            }
        }
    }

    private void handle(String[] words) {
        try {
            switch (words[0].toLowerCase(Locale.ROOT)) {
                case "rent" -> rent(words);
                case "return" -> giveBack(words);
                default -> out.println(USAGE);
            }
        } catch (IllegalArgumentException | IllegalStateException | UncheckedIOException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    private void rent(String[] words) {
        requireWords(words, 4);
        Station station = Station.parse(words[3]);
        Bike bike = share.rent(words[1], MemberType.parse(words[2]), station);
        out.println("Bike " + bike.id() + " rented at " + station);
    }

    private void giveBack(String[] words) {
        requireWords(words, 4);
        Ride ride = share.giveBack(words[1], Station.parse(words[2]), parseMinutes(words[3]));
        out.println(ReceiptFormatter.format(ride));
    }

    private static void requireWords(String[] words, int expected) {
        if (words.length != expected) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
    }

    private static int parseMinutes(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Minutes must be a whole number: " + text);
        }
    }
}
