#!/bin/sh

# Sample Linux bash shell script file to run the cleanup.
# May be scheduled through cron or anacron.

export TWILIO_ACCOUNT_SID=<SID from console>
export TWILIO_AUTH_TOKEN=<AUTH TOKEN from console>
export TWILIO_LOG=/var/log/twiliolog.txt
export TWILIO_ERR=/var/log/twilioerr.txt

java -jar voicemail-cleanup.jar

# Comment out/remove for fully non-interactive execution
echo Press a key
read x <&1
