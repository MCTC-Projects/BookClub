/**
 * Created by daryl on 3/19/2015.
 */
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class EmailChecker {

    public static void check(final String user,
                             final String password) {
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
                                    user, password);
                        }
                    });

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            //Create and close session with store object(email)
            store.connect();
            store.close();

            System.out.println("Success?");


        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}