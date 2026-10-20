**1. Files read:** Locker.java, Main.java, Parcel.java, ParcelService.java (all in package `parcelpoint`)

**2. Findings**

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---|---|---|---|---|---|
| 1 | 1 Code quality | ParcelService.java:42 | `parcel.getCode() == code` compares String references. Codes read from `Scanner` are never the same object, so `pickUp` never finds a parcel and always returns 0. | Must fix | Use `parcel.getCode().equals(code)`. |
| 2 | 1 Code quality | ParcelService.java:76-79 | The `FileWriter` is never closed or flushed. `parcels.csv` is probably empty or incomplete, and a file handle leaks on every call. | Must fix | Wrap the writer in try-with-resources. |
| 3 | 1 Code quality | ParcelService.java:80-81 | Empty `catch (IOException e)` block hides write failures. | Should fix | Report the error, or let it propagate. |
| 4 | 1 Code quality | ParcelService.java:6 | Unused import `java.util.Date`. | Note | Delete the import. |
| 5 | 1 Code quality | ParcelService.java:12 | Field `maxParcels` is never used, so the limit is not enforced in `storeParcel` (line 24). | Should fix | Enforce the limit in `storeParcel`, or remove the field. |
| 6 | 1 Code quality | Main.java:8 | The `Scanner` on `System.in` is never closed. | Should fix | Open it in try-with-resources. |
| 7 | 1 Code quality | Main.java:13, 17 | `words[1..3]` and `Integer.parseInt` are used without checks. Missing arguments or non-numeric input crash the program with an uncaught exception. | Should fix | Check `words.length` and catch `NumberFormatException` per command. |
| 8 | 1 Code quality | ParcelService.java:49 (shown at Main.java:17) | `pickUp` returns 0 for an unknown code, so "not found" looks the same as "no fee" and prints "Fee: 0.0". | Should fix | Signal "not found" with an exception or `Optional`. |
| 9 | 1 Code quality | ParcelService.java:28, 44, 68; Parcel.java:13; ParcelService.java:53, 57, 59, 61 | Status strings ("STORED" appears twice, plus "PICKED_UP" and "NEW") and the numbers 3, 10.0, 15.0 and 20.0 are repeated as literals. | Note | Extract named constants. |
| 10 | 2 Cohesion | ParcelService.java:24-37 (`storeParcel`) | Cohesion is **procedural**. One method allocates a locker, changes status, records the parcel, sends the SMS and writes the file. | Should fix | Move the SMS and file saving out of this method. |
| 11 | 2 Cohesion | ParcelService.java:39-50 (`pickUp`) | Cohesion is **procedural**. It searches, frees the locker, sets status, saves the file and calculates the fee. | Should fix | Separate "find and release" from "calculate fee". |
| 12 | 2 Cohesion | ParcelService.java:64-72 (`printParcels`) | Cohesion is **logical**. The `forStaff` flag selects between two different reports. | Should fix | Split into two methods, one per report. |
| 13 | 2 Cohesion | Main.java:6-27 (`main`) | Cohesion is **procedural**. It combines input reading, parsing, dispatch, output and business logic (the free-locker count at line 22). | Should fix | Move the count into the service and give each command its own small method. |
| 14 | 2 Cohesion | ParcelService.java:9-83 | Weak class cohesion. One class handles locker allocation, fee policy, SMS notification, persistence and reporting. | Should fix | Pull the fee rule and persistence into separate classes. |
| 15 | 2 Cohesion | ParcelService.java:52-62 (`fee`) | The method uses none of the class's own attributes, only the `Parcel` argument. | Should fix | Move it to `Parcel` or a fee-policy class. |
| 16 | 2 Coupling | Parcel.java:7; ParcelService.java:28, 44 | **Content coupling.** `ParcelService` writes the public field `status` directly and bypasses `Parcel`'s interface. | Should fix | Make `status` private and add methods such as `markStored()` and `markPickedUp()`. |
| 17 | 2 Coupling | Main.java:19, 21; ParcelService.java:64 | **Control coupling.** The caller passes a boolean flag that decides what the callee does. | Should fix | Replace with two separate methods. |
| 18 | 2 Coupling | Main.java:22 | **Stamp coupling and reach-through.** `Main` depends on three classes and calls `getLockers().stream().filter(Locker::isFree).count()`, receiving the whole locker list just to count it. | Should fix | Add `countFreeLockers()` to `ParcelService`. |
| 19 | 2 Coupling | ParcelService.java:41-42 | Reach-through. The method gets a `Parcel` via `locker.getParcel()` and then calls `getCode()` on it. | Note | Ask `Locker` whether it holds a given code. |
| 20 | 2 Coupling | ParcelService.java:52 (`fee(Parcel, int)`) | **Stamp coupling.** The whole `Parcel` is passed but only `getSize()` is used. | Note | Pass the size as an argument. |
| 21 | 2 Encapsulation | ParcelService.java:20-22 | `getLockers()` returns the internal mutable list, so callers can add or remove lockers. | Should fix | Return `Collections.unmodifiableList(lockers)`. |
| 22 | 2 Connascence | Locker.java:5; Parcel.java:6; ParcelService.java:15-17, 56, 58; Main.java:9 | **Connascence of convention.** The size codes 1, 2 and 3 are a shared meaning across four files. | Should fix | Replace with an enum `Size`. |
| 23 | 2 Connascence | Parcel.java:7, 13; ParcelService.java:28, 44, 68 | **Connascence of convention.** The status strings "NEW", "STORED" and "PICKED_UP" must match exactly across classes. | Should fix | Replace with an enum `Status`. |
| 24 | 2 Connascence | Main.java:12-13; Parcel.java:9 | **Connascence of position.** Command words are read by index, and the constructor takes two adjacent `String` arguments (`code`, `studentId`) that are easy to swap. | Note | Name the parsed values, or use a small value type for the ids. |
| 25 | 2 Encapsulation | Parcel.java:7 | Non-private field `public String status`. All other fields are private. | Should fix | Make it private and add a getter. |

**3. Counts**

- Check 1 (Code quality): 9 findings
- Check 2 (Cohesion, coupling, connascence): 16 findings (the table rows numbered 10-25)

Not found:
- Methods that are too long.
- Unused parameters.
- Common coupling (no shared global state).
- Connascence of name or algorithm between classes.
- Non-private fields other than `Parcel.status`.