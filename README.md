# voicemail-cleanup
This task will clean up voicemails retained by Twilio.
It should be run periodically.

It will list all recordings, including SID and timestamp.
By subtracting from current time it will determine the age of the recording.
Recordings older than 30 days will be deleted.

This is done for two reasons.
One is that Twilio charges for message storage.
The first 5000 minutes (as of 1/28/2021) are free, but eventually that
limit will be hit.
For our usage, 30 days should keep us well below the threshold.

The second reason is that in the event of a hack, this will lower our exposure
to leaked messages.