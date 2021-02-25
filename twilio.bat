rem Sample Windows bat file to run the cleanup.
rem May be scheduled through Task Scheduler.

set TWILIO_ACCOUNT_SID=<SID from console>
set TWILIO_AUTH_TOKEN=<AUTH TOKEN from console>
set TWILIO_LOG=d:\logs\twiliolog.txt
set TWILIO_ERR=d:\logs\twilioerr.txt

java -jar voicemail-cleanup.jar

rem Comment the pause out for fully non-interactive executon
pause