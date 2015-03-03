import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

public class dlgSetMeeting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtLocation;
    private JComboBox cboMonth;
    private JComboBox cboDay;
    private JComboBox cboYear;
    private JComboBox cboAmPm;
    private JComboBox cboTime;

    private String[] months = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    private Date now;

    public dlgSetMeeting() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        now = new Date();
        int currentYear = now.getYear() + 1900;
        int currentHour = now.getHours();
        int currentMinute = now.getMinutes();

        //populate month combo box
        for (String month : months) {
            this.cboMonth.addItem(month);
        }

        //populate day combo box
        for (int day = 1; day <= 31; day++) {
            this.cboDay.addItem(day);
        }

        //populate year combo box
        for (int year = currentYear; year <= currentYear + 5; year++) {
            this.cboYear.addItem(year);
        }

        //populate time combo box
        this.cboTime.addItem("12:00");  //start with 12:00
        this.cboTime.addItem("12:30");
        for (int hour = 1; hour < 12; hour++) {
            this.cboTime.addItem(hour + ":" + "00");
            this.cboTime.addItem(hour + ":" + "30");
        }

        //populate AM/PM combo box
        this.cboAmPm.addItem("AM");
        this.cboAmPm.addItem("PM");


// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String location = txtLocation.getText();
        String month = cboMonth.getSelectedItem().toString();
        String day = cboDay.getSelectedItem().toString();
        String year = cboYear.getSelectedItem().toString();
        String time = cboTime.getSelectedItem().toString();
        String amPm = cboAmPm.getSelectedItem().toString();

        String dateTimeString =
                month + " "
                + day + ", "
                + year + " "
                + time + " "
                + amPm;

        if (Validator.isPresent(txtLocation, "Location", true)) {
            Date dateTime = Validator.ParseDate(dateTimeString, Meeting.FRIENDLY_DATE_FORMAT);
            Meeting.setNextMeeting(new Meeting(dateTime, location));
            dispose();
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
