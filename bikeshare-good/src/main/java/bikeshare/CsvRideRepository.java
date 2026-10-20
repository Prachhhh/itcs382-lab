package bikeshare;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvRideRepository implements RideRepository {
    private static final String HEADER = "memberId,memberType,bike,from,to,minutes,fee";

    private final Path file;

    public CsvRideRepository(Path file) {
        this.file = file;
    }

    @Override
    public void save(Ride ride) {
        String row = String.join(",", ride.memberId(), ride.memberType().toString(), ride.bikeId(),
                ride.from().toString(), ride.to().toString(),
                String.valueOf(ride.minutes()), String.valueOf(ride.fee()));
        try {
            if (Files.notExists(file)) {
                Files.writeString(file, HEADER + System.lineSeparator());
            }
            Files.writeString(file, row + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write " + file, e);
        }
    }
}
