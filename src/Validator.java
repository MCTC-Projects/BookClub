import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DayDay on 2/27/2015.
 */
public class Validator {
    public static boolean isPresent(JTextField textField, String name, boolean displaysError) {
        if (textField.getText().isEmpty()) {
            if (displaysError) {
                messageBox(name + " is a required field.", "Input Error");
                textField.grabFocus();
            }
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNumeric(JTextField textField, String name) {
        try {
            Double.parseDouble(textField.getText());
        } catch (NumberFormatException nfe) {
            messageBox(name + " can only contain a numeric value.", "Entry Error");
            textField.grabFocus();
            return  false;
        }
        return true;
    }

    public static boolean isInteger(JTextField textField, String name, boolean displaysError) {
        try {
            Long.parseLong(textField.getText());
        } catch (NumberFormatException nfe) {
            if (displaysError) {
                messageBox(name + " can only contain a numeric integer value.", "Entry Error");
            }
            textField.grabFocus();
            return  false;
        }
        return true;
    }

    public static boolean isInteger(String s, String name, boolean displaysError) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException nfe) {
            if (displaysError) {
                messageBox(name + " can only contain a numeric integer value.", "Entry Error");
            }
            return  false;
        }
        return true;
    }

    public static boolean isCorrectLength(JTextField textField, int length, String name, boolean displaysError) {
        String s = textField.getText();

        if (s.length() != length) {
            if (displaysError) {
                messageBox(name + " must be " + length + " characters long.", "Entry Error");
            }
            return false;
        }
        return true;
    }

    public static boolean isCorrectLength(JTextField textField, int minLength, int maxLength, String name, boolean displaysError) {
        String s = textField.getText();
        int sLength = s.length();

        if (sLength < minLength || sLength > maxLength) {
            if (displaysError) {
                messageBox(name + " length must be between " + minLength + " and " + maxLength + " characters long.", "Entry Error");
            }
            return false;
        }
        return true;
    }

    public static boolean numberIsWithinRange(JTextField textField, String name, double min, double max) {
        Double number = Double.parseDouble(textField.getText());
        if (number < min || number > max) {
            messageBox(name + " must be between " + min + " and " + max, "Entry Error");
            textField.grabFocus();
            return false;
        } else {
            return true;
        }
    }

    public static boolean dateIsWithinRange(Date date, String name, Date min, Date max) {
        SimpleDateFormat df = new SimpleDateFormat(Meeting.SHORT_DATE_FORMAT);
        if (date.before(min) || date.after(max)) {

            messageBox(name + " must be a date between "
                            + df.format(min) + " and "
                            + df.format(max),
                            "Entry Error");
            return false;
        } else {
            return true;
        }
    }

    public static Date ParseDate(String date, String dateFormatString) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
        Date parsedDate;
        try {
            parsedDate = df.parse(date);
        } catch (ParseException pe) {
            messageBox("Problem processing date/time data\n" + pe, "Error");
            return null;
        }
        return parsedDate;
    }

    public static String getDateString(Date date, String dateFormatString) {
        SimpleDateFormat df  = new SimpleDateFormat(dateFormatString);
        return df.format(date);
    }

    public static boolean isValidGmailAddress(JTextField textField, String name, boolean displaysError) {
        String error = "Invalid email address";

        String s = textField.getText();
        String[] parts = s.split("@");

        if (parts.length != 2) {
            messageBox(error, "Invalid Email");
            textField.grabFocus();
            return false;
        }

        String domainPart = parts[1];

        if (!domainPart.equalsIgnoreCase("gmail.com")) {
            messageBox(error + "\n Please use a Gmail address.", "Invalid Email");
            textField.grabFocus();
            return false;
        }
        return true;
    }

    public static void messageBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
