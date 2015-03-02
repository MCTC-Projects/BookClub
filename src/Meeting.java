import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by DayDay on 2/26/2015.
 */
public class Meeting {
    private Date DateTime;
    private String Location;
    private Book AssignedReading;

    public Meeting(Date dateTime, String location) {
        this.DateTime = dateTime;
        this.Location = location;
        this.UpdateMeetingInfo();
    }

    public Meeting(Date dateTime, String location, Book book) {
        this.DateTime = dateTime;
        this.Location = location;
        this.AssignedReading = book;
        this.UpdateMeetingInfo();
    }

    public Date getDateTime() {
        return this.DateTime;
    }

    public void setDateTime(Date newDateTime) {
        this.DateTime = newDateTime;
        this.UpdateMeetingInfo();
    }

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String newLocation) {
        this.Location = newLocation;
        this.UpdateMeetingInfo();
    }

    public Book getAssignedReading() {
        return this.AssignedReading;
    }

    public void setAssignedReading(Book book) {
        this.AssignedReading = book;
        this.UpdateMeetingInfo();
    }

    public void UpdateMeetingInfo() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("NextMeetingInfo.txt"));

            writer.write(this.DateTime.toString());
            writer.newLine();
            writer.write(this.Location);
            if (this.AssignedReading != null) {
                writer.newLine();
                writer.write(
                        this.AssignedReading.getISBN() + ";" +
                        this.AssignedReading.getTitle() + ";" +
                        this.AssignedReading.getAuthor());
            }
            writer.close();
        } catch (IOException ioe) {
            Validator.messageBox("Error writing to \"NextMeetingInfo.txt\"", "Error");
            System.out.println("Error writing to \"NextMeetingInfo.txt\"");
            System.out.println((ioe));
        }
    }
}
