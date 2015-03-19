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
    private JButton btnStartNewBookClub;

    public static String USER_EMAIL;
    public static String USER_PASSWORD;

    private static Window loginWindow;

    public frmLogin() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidInput()) {
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
        btnStartNewBookClub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStartBookClubDialog();
            }
        });
    }

    private boolean isValidInput() {
        return
                //TODO: validate email and password
                Validator.isPresent(txtEmail, "Email", true) &&
                Validator.isPresent(txtPassword, "Password", true) &&
                Validator.isValidGmailAddress(txtEmail);
                //Validator.isAuthenticUsernamePassword(txtEmail.getText(), txtPassword.getText()); // &&
                //Validator.isValidMemberEmail(txtEmail.getText());
    }

    private void openStartBookClubDialog() {
        //Initialize and open set meeting dialog
        dlgStartBookClub startBookClubDialog = new dlgStartBookClub();
        startBookClubDialog.setTitle("Start New Book Club");

        //Set dimensions
        Dimension dimensions = new Dimension(325, 150);
        startBookClubDialog.setSize(dimensions);
        startBookClubDialog.setResizable(false);

        frmMain.CenterOnScreen(startBookClubDialog);

        startBookClubDialog.setVisible(true);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmLogin");
        frame.setContentPane(new frmLogin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        loginWindow = frame;

        frame.setTitle("Login");    //Form title

        //Set dimension properties
        Dimension dimensions = new Dimension(375, 165);
        frame.setSize(dimensions);
        frame.setMinimumSize(dimensions);

        frmMain.CenterOnScreen(frame);

        frame.setVisible(true);

        //frame.dispose();
    }
}
