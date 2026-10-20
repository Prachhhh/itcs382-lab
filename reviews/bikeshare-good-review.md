1. Files read (from your upload; I did not access the `<itcs382-lab>` path): Bike.java, BikeShare.java, Cli.java, CsvRideRepository.java, Main.java, MemberType.java, ReceiptFormatter.java, Ride.java, RideRepository.java, Station.java.

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---|---|---|---|---|---|
| 1 | Code quality | MemberType.java:16 | `ratePerMinute * minutes` is int multiplication. Cli.java:72 accepts any positive int, so a large value overflows and produces a negative fee. | Should fix | Use `Math.multiplyExact` or an upper limit on minutes. |
| 2 | Code quality | Cli.java:22 | The `Scanner` is never closed. It wraps `System.in`, so closing it would also be wrong in this design. | Note | Document that the caller owns the stream, or let the caller pass in the `Scanner`. |
| 3 | Code quality | MemberType.java:19-25, 28-30 and Station.java:10-16, 19-21 | `parse` and `toString` are duplicated almost line for line across the two enums. | Should fix | Move the shared logic into one helper. |
| 4 | Code quality | CsvRideRepository.java:24-27 | The code checks whether the file exists and then writes. The file can change between the check and the write, and there are two writes where one would do. | Note | Use one append call that writes the header only when the file is empty or new. |
| 5 | Code quality | Main.java:10 | The file name `"rides.csv"` is hard-coded and relative to the working directory. | Note | Read the path from `args` with this value as the default. |
| 6 | Cohesion | MemberType.java:15-30 | The enum handles pricing (`feeFor`), text parsing (`parse`) and display (`toString`). `parse` and `toString` do not use `ratePerMinute`, which is weak class cohesion. | Note | Move parsing and display into the CLI layer. |
| 7 | Cohesion | Station.java:10-21 | The enum holds the domain value plus input parsing and display text, which is weak class cohesion. | Note | Move `parse` and the display text into the CLI layer. |
| 8 | Coupling | ReceiptFormatter.java:7, called at Cli.java:61 | The formatter receives the whole `Ride` but uses only 3 of its 7 fields. This is stamp coupling. | Note | Pass `bikeId`, `minutes` and `fee` instead. |
| 9 | Coupling | BikeShare.java:57-58 | The code calls `rental.bike().id()` and `rental.type().feeFor(...)`, reaching through the `Rental` object to its parts. | Note | Add small methods on `Rental` such as `bikeId()` and `feeFor(minutes)`. |
| 10 | Coupling | Cli.java:51-62, 46 | `Cli` uses BikeShare, Bike, Station, MemberType, Ride and ReceiptFormatter, and it catches `UncheckedIOException`, which is a persistence detail. This is strong coupling. | Note | Have `BikeShare` return a small result type and translate storage errors into its own exception. |
| 11 | Connascence | BikeShare.java:12, 34 and CsvRideRepository.java:20-22 | Algorithm: the CSV stays valid only because `MEMBER_ID` rejects commas, and the writer does no escaping of its own. | Should fix | Escape fields in the writer, or document the rule on `RideRepository`. |
| 12 | Connascence | CsvRideRepository.java:10 and 20-22 | Position: the column order in `HEADER` must match the order of the values in the row. | Note | Build both from one ordered list of columns. |
| 13 | Connascence | CsvRideRepository.java:20-21 | Convention or meaning: the file stores `toString()` display text of the enums, so changing the display text changes the file format. | Should fix | Write `name()` instead. |
| 14 | Connascence | Cli.java:12-14, 30, 42-43 | Convention: the command words `rent`, `return` and `quit` appear in both the usage text and the code that handles them. | Note | Define each command once, for example in an enum. |
| 15 | Connascence | Cli.java:12-13, Station.java:14, MemberType.java:23 | Convention: the station and member-type names are written out three separate times and must match the enum constants. | Should fix | Build the text from `values()`. |
| 16 | Connascence | Cli.java:53-54, 60 | Position: arguments are read as `words[1]`, `words[2]` and `words[3]`, so the meaning depends on index. | Note | Name the indexes with constants, or parse into a small command object. |
| 17 | Connascence | Ride.java:3-4, built at BikeShare.java:57-58 | Position: a seven-argument constructor with adjacent same-type parameters (`from`/`to`, `minutes`/`fee`) that can be swapped without a compile error. | Should fix | Add a factory method or builder that names each argument. |

Not found (checked, nothing to report):
- Method cohesion at procedural level or weaker: every method is functional or sequential.
- Content, common or control coupling.
- Non-private fields, and methods that return an internal collection.
- Unused imports, fields or parameters.
- Empty blocks.
- Objects compared with `==`.
- String literals repeated many times.
- Methods that are too long or too complex (the longest is about 15 lines).

3. Number of findings
- Check 1 (code quality): 5
- Check 2 (cohesion, coupling, connascence): 12