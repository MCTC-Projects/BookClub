import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by DayDay on 3/2/2015.
 */
public class frmLogin {
    private JTextField txtEmail;
    private JButton btnLogin;
    private JPanel panel1;
    private JPasswordField txtPassword;

    public static String USER_EMAIL;
    public static String USER_PASSWORD;

    private static Window loginWindow;

    public frmLogin() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidInput()) {
                    //TODO: validate email and password
                    USER_EMAIL = txtEmail.getText();
                    USER_PASSWORD = txtPassword.getText();

                    //Initialize form
                    JFrame frame = new JFrame("frmMain");
                    frame.setContentPane(new frmMain().panel1);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setTitle("Book Club");    //Form title

                    //Set dimension properties
                    Dimension dimensions = new Dimension(600, 400);
                    frame.setSize(dimensions);
                    frame.setMinimumSize(dimensions);

                    frmMain.CenterOnScreen(frame);

                    //Show form
                    frame.setVisible(true);

                    loginWindow.dispose();
                }
            }
        });
    }

    private boolean isValidInput() {
        return
                Validator.isPresent(txtEmail, "Email", true) &&
                Validator.isPresent(txtPassword, "Password", true) &&
                Validator.isValidGmailAddress(txtEmail, "Email", true) &&
                Validator.isValidMemberEmail(txtEmail.getText());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmLogin");
        frame.setContentPane(new frmLogin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        loginWindow = frame;

        frame.setTitle("Login");    //Form title

        //Set dimension properties
        Dimension dimensions = new Dimension(300, 150);
        frame.setSize(dimensions);
        frame.setMinimumSize(dimensions);

        frmMain.CenterOnScreen(frame);

        frame.setVisible(true);

        //frame.dispose();
    }
}
