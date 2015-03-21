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

    public static boolean isFutureDate(Date date, boolean displaysError) {
        Date currentDateTime = new Date();

        if (date.before(currentDateTime)) {
            if (displaysError) {
                messageBox("Please enter a future date value.\nCurrent date/time: "
                                + getDateString(currentDateTime, Meeting.DETAILED_DATE_FORMAT)
                                + "\nYou entered: " + getDateString(date, Meeting.DETAILED_DATE_FORMAT),
                        "Entry Error");
            }
            return false;
        }
        return true;
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

        try {

            // create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3s.host", "pop.gmail.com");
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3s.starttls.enable", "true");

            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    username, password);
                        }
                    });

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            //Create and close session with store object(email)
            store.connect();
            store.close();

            System.out.println("Username/password authenticated successfully!");
            return true;

        } catch (NoSuchProviderException e) {
            messageBox("Invalid username/password","Error");
            System.out.println(e);
            return false;
        } catch (MessagingException e) {
            //TODO: figure out why this SSLHandshakeException keeps getting thrown here:
            //javax.mail.MessagingException: Connect failed;
            //  nested exception is:
            //      javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException:
            //      PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
            //      unable to find valid certification path to requested target

            messageBox("Invalid username/password","Error");
            System.out.println(e);
            return false;
        } catch (Exception e) {
            messageBox("Invalid username/password","Error");
            System.out.println(e);
            return false;
        }
    }


    public static void messageBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
