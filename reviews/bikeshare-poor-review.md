**1. Files read:** `BikeShare.java` (the only file provided, 105 lines). I could not browse `<itcs382-lab>/bikeshare-poor/src/main/java`, so other files in that folder were not reviewed.

**2. Findings**

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---|---|---|---|---|---|
| 1 | 1 Code quality | BikeShare.java:26-85 | `handle` is about 60 lines with many nested branches (rent path, return path, validation, fee, file write). Hard to read and test. | Should fix | Split into `rent(...)` and `returnBike(...)` methods. |
| 2 | 1 Code quality | BikeShare.java:47, 61, 40, 68, 20, 23 | `-1` is used with different meanings (bike is rented, invalid minutes, station/name not found) without a named constant. | Should fix | Introduce named constants such as `RENTED` and `NOT_FOUND`. |
| 3 | 1 Code quality | BikeShare.java:69 | `minutes * RATE[...]` is `int` arithmetic. A very large valid `minutes` value overflows and gives a negative or wrong fee. | Note | Use `long` for the fee, or cap `minutes`. |
| 4 | 1 Code quality | BikeShare.java:72-80 | If saving to `rides.csv` fails, the receipt is already printed and the bike is returned. The ride is lost with only a console message. | Note | Report the failure to the caller, or retry before releasing the bike. |
| 5 | 1 Code quality | BikeShare.java:13-15 | The bike count 6 is hard-coded in three places. The arrays can drift apart if one is changed. | Note | Define one `BIKE_COUNT` constant and use it for all three. |
| 6 | 2 Cohesion | BikeShare.java:26-85 (`handle`) | Logical cohesion: the `rent` flag selects between two unrelated operations. The return branch is also sequential (fee, print, persist, update state). | Should fix | Split into one method per operation. |
| 7 | 2 Cohesion | BikeShare.java:87-104 (`main`) | Procedural cohesion: it handles input reading, trimming, parsing, dispatch and help text in one loop. | Note | Move command parsing and dispatch into a separate method. |
| 8 | 2 Cohesion | BikeShare.java:8-105 | Weak class cohesion: one class models bikes, members, stations, pricing, file persistence and the command-line UI. | Should fix | Extract at least a `Bike` class and a ride-log writer. |
| 9 | 2 Coupling | BikeShare.java:13-15 (used at 40, 46-49, 68, 78-79) | Common coupling: the global, public, mutable static arrays are shared state that any code can read and write. | Should fix | Make the state private and instance-based. |
| 10 | 2 Coupling | BikeShare.java:26, 96, 98 | Control coupling: `main` passes the boolean `rent` to tell `handle` which behaviour to run. | Should fix | Call two separate methods instead of passing a flag. |
| 11 | 2 Connascence | BikeShare.java:33, 34, 40, 56, 59, 68 | Connascence of position: the meaning of `p[1]`, `p[2]`, `p[3]` depends on the argument order, which differs between rent and return. | Note | Parse the arguments once into named values or a small command object. |
| 12 | 2 Connascence | BikeShare.java:13-15, 47-49, 78-79 | Connascence of position: three parallel arrays must be updated at the same index. | Should fix | Replace them with one `Bike` object holding station, member and type. |
| 13 | 2 Connascence | BikeShare.java:40, 47, 68, 78 | Connascence of convention: `bikeStation[i] < 0` means "rented", and every place must know this. | Should fix | Add an `isRented()` method or a named constant. |
| 14 | 2 Connascence | BikeShare.java:33 and 70 | Connascence of algorithm: the mapping from "student"/"staff" text to the int type (line 33) must match the reverse mapping (line 70). | Note | Use an `enum MemberType` with a parse method. |
| 15 | 2 Connascence | BikeShare.java:11, 69 | Connascence of position: `RATE` is indexed by the `STUDENT`/`STAFF` constants, so their order must match. | Note | Put the rate inside the `MemberType` enum. |
| 16 | 2 Encapsulation | BikeShare.java:9-15 | All fields are `public static`. `RATE`, `STATIONS`, `bikeStation`, `bikeMember` and `bikeMemberType` are mutable arrays that any caller can change directly. | Must fix | Make the fields private and expose only the operations needed. |

Not found:
- Resources never closed: the `Scanner` (line 88) and the writer (line 72) are both in try-with-resources.
- Objects compared with `==`: not found.
- Unused imports, fields or parameters: not found.
- Empty blocks: not found.
- Operations that return an internal collection: not found. There are no getters, but the arrays are directly public (finding 16).
- Interactions between classes tighter than data coupling: not found. There is only one user class, and the coupling is inside it (findings 9 and 10).
- Classes that depend on many other classes or reach through objects: not found. It uses only JDK classes.
- Content coupling: not found.
- Methods with procedural or weaker cohesion: `stationOf` (line 17) is functional, so it is not reported.

**3. Number of findings per check**
- Check 1, Code quality: 5
- Check 2, Cohesion, coupling and connascence: 11