package bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class BikeShare {
    private static final int BIKES_PER_STATION = 2;
    private static final Pattern MEMBER_ID = Pattern.compile("[A-Za-z0-9_-]+");

    private record Rental(MemberType type, Bike bike, Station origin) {
    }

    private final Map<Station, Deque<Bike>> docks = new EnumMap<>(Station.class);
    private final Map<String, Rental> rentals = new HashMap<>();
    private final RideRepository rides;

    public BikeShare(RideRepository rides) {
        this.rides = rides;
        int number = 1;
        for (Station station : Station.values()) {
            Deque<Bike> dock = new ArrayDeque<>();
            for (int i = 0; i < BIKES_PER_STATION; i++) {
                dock.add(new Bike("B" + number++));
            }
            docks.put(station, dock);
        }
    }

    public Bike rent(String memberId, MemberType type, Station station) {
        if (!MEMBER_ID.matcher(memberId).matches()) {
            throw new IllegalArgumentException("Member ID may only contain letters, digits, - and _");
        }
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException("Member " + memberId + " already has a bike");
        }
        Deque<Bike> dock = docks.get(station);
        if (dock.isEmpty()) {
            throw new IllegalStateException("No bike available at " + station);
        }
        Bike bike = dock.poll();
        rentals.put(memberId, new Rental(type, bike, station));
        return bike;
    }

    public Ride giveBack(String memberId, Station station, int minutes) {
        Rental rental = rentals.get(memberId);
        if (rental == null) {
            throw new IllegalStateException("Member " + memberId + " has no rented bike");
        }
        if (minutes <= 0) {
            throw new IllegalArgumentException("Minutes must be greater than 0");
        }
        Ride ride = new Ride(memberId, rental.type(), rental.bike().id(), rental.origin(),
                station, minutes, rental.type().feeFor(minutes));
        rides.save(ride);
        rentals.remove(memberId);
        docks.get(station).add(rental.bike());
        return ride;
    }
}
