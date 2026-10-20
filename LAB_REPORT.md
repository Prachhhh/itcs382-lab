# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/Prachhhh/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 25.0.4.1 |
| Maven | 2.10.0 |
| IDE | Visual Studio Code |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | Claude |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | Java:S106 | Main.java:Ln9 | Replace this use of System.out by a logger. |
| 2 | Java:S106 | Main.java:Ln15 | Replace this use of System.out by a logger. |
| 3 | Java:S106 | Main.java:Ln17 | Replace this use of System.out by a logger. |
| 4 | Java:S106 | Main.java:Ln22 | Replace this use of System.out by a logger. |
| 5 | Java:S2143 | ParcelService.java:Ln1 | Use the "java.time" API f or date and time. |
| 6 | Java:S1128 | ParcelService.java:Ln6 | Remove this unused import 'java.util.Date'. |
| 7 | Java:S1068 | ParcelService.java:Ln12 | Remove this unused "maxParcels" private field. |
| 8 | Java:S106 | ParcelService.java:Ln30 | Replace this use of System.out by a logger. |
| 9 | Java:S4973 | ParcelService.java:Ln42 | Strings and Boxed types should be compared using "equals()". |
| 10 | Java:S106 | ParcelService.java:Ln67 | Replace this use of System.out by a logger. |
| 11 | Java:S106 | ParcelService.java:Ln69 | Replace this use of System.out by a logger. |
| 12 | Java:S2095 | ParcelService.java:Ln76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | Java:S108 | ParcelService.java:Ln80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 20-Oct-26 |
| AI tool and exact model | Claude, Sonnet 5.5 Medium |
| Commit reviewed | |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Files read: Locker.java, Main.java, Parcel.java, ParcelService.java (all in package parcelpoint) |
| Output file in the reviews folder | Texts |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | parcel.getCode() == code compares String references. Codes read from Scanner are never the same object, so pickUp never finds a parcel and always returns 0. | both | SonarQube rule | Yes, because Both sonarQube and AI have the same answer |
| 2 | ParcelService.java:76-79 | The FileWriter is never closed or flushed. parcels.csv is probably empty or incomplete, and a file handle leaks on every call. | both | SonarQube rule | Yes, because Both sonarQube and AI have the same answer |
| 3 | ParcelService.java:80-81 | Empty catch (IOException e) block hides write failures. | both | SonarQube rule | Yes, because Both sonarQube and AI have the same answer |
| 4 | ParcelService.java:6 | Unused import java.util.Date. | both | SonarQube rule | Yes, because Both sonarQube and AI have the same answer |
| 5 | ParcelService.java:12 | Field maxParcels is never used, so the limit is not enforced in storeParcel (line 24). | both | SonarQube rule | Yes, because Both sonarQube and AI have the same answer |
| 5 | ParcelService.java:12 | Field maxParcels is never used, so the limit is not enforced in storeParcel (line 24). | both | SonarQube rule | Yes, because Both sonarQube and AI have the same answer |

2.1 Name two problems that only the AI review found.
    a. Non-private field public String status. All other fields are private.
    b. Connascence of position. Command words are read by index, and the constructor takes two adjacent String arguments (code, studentId) that are easy to swap.

2.2 Name one problem that only SonarQube found, or write none.
    Use the "java.time" API f or date and time.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
    Every finding is right because I have checked all the finding and the reason that AI have is reasonable.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | Commit |
| 2 | Fixed | Commit |
| 3 | Fixed | Commit |
| 4 | Fixed | Commit |
| 5 | Fixed | Commit |
| 6 | Fixed | Commit |
| 7 | Fixed | Commit |
| 8 | Fixed | Commit |
| 9 | Fixed | Commit |
| 10 | Fixed | Commit |
| 11 | Fixed | Commit |
| 12 | Fixed | Commit |
| 13 | Fixed | Commit |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
