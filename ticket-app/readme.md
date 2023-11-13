# Project compilation - Linux:
1. Run compile-and-run.sh
 
# Demo - Linux:
1. Run compile-and-run.sh if not ran previously
2. Run demo.sh

# Project compilation - Windows:
- requires at least JDK 17 to be installed and JAVA_HOME to be set.
1. Run compile-and-run.bat in Powershell as admin.
 
# Demo - Windows:
1. Run compile-and-run.bat if not ran previously
2. Run demo.bat


# Assumptions:
- The system is exclusively user-facing - there are no ways to modify data other than by reserving seats. 
- User input - for example selecting the screening or seats - should be handled by the front-end.   
- Reservation expires 30 minutes after screening start, so anyone arriving late may still use their ticket but there are no interruptions in the middle of the screening.
- Second part of a surname (after the dash) also needs to be at least 3 letters long.
- Seats and seat rows are numbered 1-n.
- When the user selects a date and time to look for screenings, the system gives all screenings on the same day after the given time, or after 20 minutes from now, whichever is greater.  
- Names and surnames may be up to 50 characters long.