package bikeshare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BikeShare {
    public static final int STUDENT = 0;
    public static final int STAFF = 1;
    public static final int[] RATE = {2, 3};
    public static final String[] STATIONS = {"Library", "Canteen", "Dorm"};
    public static int[] bikeStation = {0, 0, 1, 1, 2, 2};
    public static String[] bikeMember = new String[6];
    public static int[] bikeMemberType = new int[6];

    static int stationOf(String name) {
        for (int i = 0; i < STATIONS.length; i++) {
            if (STATIONS[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    static void handle(String[] p, boolean rent) {
        if (p.length != 4) {
            System.out.println(rent ? "Usage: rent <id> <student|staff> <station>"
                                    : "Usage: return <id> <station> <minutes>");
            return;
        }
        if (rent) {
            int type = p[2].equalsIgnoreCase("student") ? STUDENT : p[2].equalsIgnoreCase("staff") ? STAFF : -1;
            int st = stationOf(p[3]);
            if (type < 0 || st < 0) {
                System.out.println("Unknown member type or station.");
                return;
            }
            for (int i = 0; i < bikeStation.length; i++) {
                if (bikeStation[i] < 0 && p[1].equals(bikeMember[i])) {
                    System.out.println("Member " + p[1] + " already has bike " + (i + 1) + ".");
                    return;
                }
            }
            for (int i = 0; i < bikeStation.length; i++) {
                if (bikeStation[i] == st) {
                    bikeStation[i] = -1;
                    bikeMember[i] = p[1];
                    bikeMemberType[i] = type;
                    System.out.println("Member " + p[1] + " rented bike " + (i + 1) + " at " + STATIONS[st] + ".");
                    return;
                }
            }
            System.out.println("No bike available at " + STATIONS[st] + ".");
        } else {
            int st = stationOf(p[2]);
            int minutes;
            try {
                minutes = Integer.parseInt(p[3]);
            } catch (NumberFormatException e) {
                minutes = -1;
            }
            if (st < 0 || minutes < 0) {
                System.out.println("Unknown station or invalid minutes.");
                return;
            }
            for (int i = 0; i < bikeStation.length; i++) {
                if (bikeStation[i] < 0 && p[1].equals(bikeMember[i])) {
                    int fee = minutes * RATE[bikeMemberType[i]];
                    String type = bikeMemberType[i] == STAFF ? "staff" : "student";
                    System.out.println("Receipt: bike " + (i + 1) + ", " + minutes + " min, " + fee + " baht");
                    try (BufferedWriter w = new BufferedWriter(new FileWriter("rides.csv", true))) {
                        w.write((i + 1) + "," + p[1] + "," + type + "," + STATIONS[st] + "," + minutes + "," + fee);
                        w.newLine();
                    } catch (IOException e) {
                        System.out.println("Could not save ride: " + e.getMessage());
                    }
                    bikeStation[i] = st;
                    bikeMember[i] = null;
                    return;
                }
            }
            System.out.println("Member " + p[1] + " has no rented bike.");
        }
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                String[] p = line.split("\\s+");
                if (p[0].equalsIgnoreCase("rent")) {
                    handle(p, true);
                } else if (p[0].equalsIgnoreCase("return")) {
                    handle(p, false);
                } else {
                    System.out.println("Commands: rent, return, quit");
                }
            }
        }
    }
}
