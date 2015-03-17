import javax.swing.*;
import javax.mail.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Created by DayDay on 2/27/2015.
 */
public class Validator {
    public static boolean isPresent(JTextField textField, String name, boolean displaysError) {
        if (textField.getText().isEmpty()) {
            if (displaysError) {
                messageBox(name + " is a required field.", "Entry Error");
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

    public static boolean isValidGmailAddress(JTextField textField) {
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
            messageBox(error + "\n Please enter a valid Gmail address (@gmail.com).", "Invalid Email");
            textField.grabFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidMemberEmail(String userEmail) {
        ArrayList<String> memberEmails = new ArrayList<String>();

        if (Member.getAllMembers() != null) {
            for (Member m : Member.getAllMembers()) {
                memberEmails.add(m.getEmail());
            }

            if (memberEmails.contains(userEmail)) {
                return true;
            } else {
                messageBox(
                        "\"" + userEmail + "\" not found in member list." +
                        "\nIf you are not a member you must" +
                        " request to be added to this book club.",
                        "Error");
                return false;
            }
        } else {
            messageBox(
                    "A book club has not been started yet." +
                    "\nPlease click \"Start New Book Club\" to get started",
                    "Book club does not exist");
            return false;
        }
    }

    public static boolean isAuthenticUsernamePassword(final String username, final String password) {
        char[] pw = new char[password.length()];

        try {
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            String host = "smtp.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            // Get the Session object.
            Session session = Session.getDefaultInstance(props, auth);
            Transport transport = session.getTransport();

            transport.connect();
            transport.close();
            return true;
        } catch (NoSuchProviderException nsp) {
            System.out.println(nsp.getMessage());
            messageBox("Ivalid username/password combination.", "Error");
            return false;
        } catch (MessagingException me) {
            System.out.println(me.getMessage());
            messageBox("Ivalid username/password combination.", "Error");
            return false;
        }
    }

    public static void messageBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
