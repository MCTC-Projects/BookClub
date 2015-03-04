import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DayDay on 2/26/2015.
 */
public class Meeting {
    private Date DateTime;
    private String Location;
    private Book assignedReading;

    private static Meeting nextMeeting;

    public static final String FRIENDLY_DATE_FORMAT = "MMMM dd, yyyy HH:mm a";
    public static final String LONG_FRIENDLY_DATE_FORMAT = "EEEE, MMMM dd, yyyy 'at' HH:mm a";
    public static final String DETAILED_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String SHORT_DATE_FORMAT = "MM/dd/yyyy";

    public Meeting(Date dateTime, String location) {
        this.DateTime = dateTime;
        this.Location = location;
    }

    public Meeting(Date dateTime, String location, Book book) {
        this.DateTime = dateTime;
        this.Location = location;
        this.assignedReading = book;
    }

    public Date getDateTime() {
        return this.DateTime;
    }

    public void setDateTime(Date newDateTime) {
        this.DateTime = newDateTime;
    }

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String newLocation) {
        this.Location = newLocation;
    }

    public Book getAssignedReading() {
        return this.assignedReading;
    }

    public void setAssignedReading(Book book) {
        this.assignedReading = book;
        UpdateNextMeetingInfo();
    }

    public static Meeting getNextMeeting() {
        return nextMeeting;
    }

    public static void setNextMeeting(Meeting meeting) {
        nextMeeting = meeting;
        UpdateNextMeetingInfo();

        //Emailer emailer = new Emailer();  //unnecessary since sendEmail method is static

        //Email info
        String senderEmail = frmLogin.USER_EMAIL;
        String senderPassword = frmLogin.USER_PASSWORD;
        ArrayList<Member> recipients = Member.AllMembers;
        String subject = "Next Book Club Meeting";

        //message data
        String meetingLocation = meeting.getLocation();
        String meetingDate = Validator.getDateString(meeting.getDateTime(), LONG_FRIENDLY_DATE_FORMAT);
        String bookInfoString =
                "\"" + meeting.getAssignedReading().getTitle() +
                        "\" by " + meeting.getAssignedReading().getAuthor();

        String message =
                "Dear book club member,\n" +
                "Our next meeting will be on " + meetingDate +
                ". We will be meeting at " + meetingLocation +
                ". Please read " + bookInfoString + " before we meet.";

        if (!recipients.isEmpty()) {
            for (Member r : recipients) {
                Emailer.sendEmail(senderEmail, senderPassword, r.getEmail(), subject, message);
            }
        } else {
            Validator.messageBox("No members have been registered.", "Error");
        }
    }

    public static void UpdateNextMeetingInfo() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("NextMeetingInfo.txt"));

            DateFormat df = new SimpleDateFormat(DETAILED_DATE_FORMAT);

            writer.write(df.format(nextMeeting.getDateTime()));
            writer.newLine();
            writer.write(nextMeeting.getLocation());
            writer.close();
        } catch (IOException ioe) {
            Validator.messageBox("Problem writing to \"NextMeetingInfo.txt\"\n" + ioe, "Error");
        }
    }

    public static Meeting GetNextMeetingInfo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("NextMeetingInfo.txt"));

            String[] lines = new String[3];
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                lines[i] = line;
                i++;
                line = reader.readLine();
            }

            String dateString = lines[0];
            Date date = Validator.ParseDate(dateString, DETAILED_DATE_FORMAT);

            String location = lines[1];

            Meeting m = new Meeting(date, location);

            return m;
        } catch (IOException ioe) {
            Validator.messageBox("Problem reading from \"NextMeetingInfo.txt\"\n" + ioe, "Error");
            return null;
        }
    }
}
