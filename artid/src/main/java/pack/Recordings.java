// Use maven to install Twilio API
package pack;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Recording;

public class Recordings {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String LOG_FILE = System.getenv("TWILIO_LOG");
    public static final String ERR_FILE = System.getenv("TWILIO_ERR");
    static long localDateTimeDifference(LocalDateTime d1, LocalDateTime d2, ChronoUnit unit) {
        return unit.between(d1, d2);
    }

    static FileWriter fileWriter;
    static PrintWriter logfile = null;

    static void logger(String s) {
        System.out.print(s);
        if (logfile != null) {
            logfile.print(s);
        }
    }
    public static void main(String[] args) {

        try {
            if (ERR_FILE != null) {
                System.setErr(new PrintStream(new FileOutputStream(ERR_FILE, true)));
            }
            if (LOG_FILE != null) {
                fileWriter = new FileWriter(LOG_FILE, true); //Set true for append mode
                logfile = new PrintWriter(fileWriter);
            }
            
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            ResourceSet<Recording> recordings = Recording.reader().read();

            Recording recording;
            LocalDateTime now = LocalDateTime.now();
            logger("Run Time: " + now + "\n");

            while (recordings.iterator().hasNext()) {
                recording = recordings.iterator().next();

                /* The timestamp in a recording record, in theory, is UTC.  However the TZ code used is Z, not UTC or GMT.
                   Z means ZULU and is yet another valid designation for UTC, however it isn't handled correctly and the
                   'DateTimeDifference' function doesn't work correctly.  Converting to local time fixes this.  I honestly don't
                   know how this works at all but not converting to local time gave me fairly random differences.  I believe that
                   this difference is still wrong, but only by a day, and rocket science is not required.
                */

                LocalDateTime recordtime = recording.getDateCreated().toLocalDateTime();
                long days = localDateTimeDifference(recordtime, now, ChronoUnit.DAYS);
                logger(recording.getSid() + "  " + recordtime + "  " + days);
                if (days > 30) {
                    Recording.deleter(recording.getSid()).delete();
                    logger(" Deleting\n");
                }
                else {
                    logger("\n");
                }
            }
            logfile.close();
        } catch (IOException e) {
            System.err.println("An IO error occurred.");
            e.printStackTrace();
        }
    }
}
