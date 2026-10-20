package parcelpoint;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ParcelService service = new ParcelService();
        try (Scanner in = new Scanner(System.in)) {
            LOGGER.info("Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit");
            while (in.hasNextLine()) {
                String[] words = in.nextLine().trim().split(" ");
                if (words[0].equals("store")) {
                    Parcel parcel = new Parcel(words[1], words[2], Integer.parseInt(words[3]));
                    Locker locker = service.storeParcel(parcel);
                    LOGGER.info(() -> locker == null ? "No free locker" : "Locker " + locker.getNumber());
                } else if (words[0].equals("pickup")) {
                    LOGGER.log(Level.INFO, "Fee: {0}", service.pickUp(words[1], Integer.parseInt(words[2])));
                } else if (words[0].equals("list")) {
                    service.printParcels(false);
                } else if (words[0].equals("staff")) {
                    service.printParcels(true);
                        LOGGER.log(Level.INFO, "Free lockers: {0}",
                            service.getLockers().stream().filter(Locker::isFree).count());
                } else if (words[0].equals("quit")) {
                    break;
                }
            }
        }
    }
}
