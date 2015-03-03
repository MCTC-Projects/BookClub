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

    public frmLogin() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidInput()) {

                }
            }
        });
    }

    private boolean isValidInput() {
        return
                Validator.isPresent(txtEmail, "Email", true) &&
                Validator.isValidEmail(txtEmail, "Email", true);
    }

    public static void main(String[] args) {
        //TODO: initialize form
        JFrame frame = new JFrame("frmLogin");
        frame.setContentPane(new frmLogin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setTitle("Login");    //Form title

        //Set dimension properties
        Dimension dimensions = new Dimension(230, 115);
        frame.setSize(dimensions);
        frame.setMinimumSize(dimensions);

        //Center form on screen
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();   //Get screen size

        //Screen's center point
        int screenCenterX = screenDimensions.width / 2;
        int screenCenterY = screenDimensions.height / 2;

        //Form's center point
        int frameCenterX = frame.getSize().width / 2;
        int frameCenterY = frame.getSize().height / 2;

        frame.setLocation(screenCenterX - frameCenterX, screenCenterY - frameCenterY);

        frame.setVisible(true);
    }
}
