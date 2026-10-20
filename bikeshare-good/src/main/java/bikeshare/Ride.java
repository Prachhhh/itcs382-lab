package bikeshare;

public record Ride(String memberId, MemberType memberType, String bikeId,
                   Station from, Station to, int minutes, int fee) {
}
