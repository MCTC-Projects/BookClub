import javax.swing.*;
import java.awt.event.*;

public class dlgStartBookClub extends JDialog {
    private JPanel contentPane;
    private JButton btnStartBookClub;
    private JButton buttonCancel;
    private JTextField txtName;
    private JTextField txtEmailAddress;
    private JTextField txtBCN;

    public dlgStartBookClub() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnStartBookClub);

        btnStartBookClub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
        if (isValidInput()) {

            //Display warning
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Starting a new book club will erase all previous data\n" +
                            "and set you as the first member of a new book club.\n" +
                                    "Would you like to start a new book club anyway?", "Warning",
                    JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                //Clear all data
                Book.ClearAllBookData();
                Member.ClearAllMemberData();
                Meeting.ClearAllMeetingData();

                //TODO: auto-generate MID?
                Member newMember = new Member(0, txtName.getText(), txtEmailAddress.getText());
                Member.AddMember(newMember);
            }

            dispose();
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        dlgStartBookClub dialog = new dlgStartBookClub();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private boolean isValidInput() {
        return
                Validator.isPresent(txtName, "Name", true) &&
                Validator.isPresent(txtEmailAddress, "Email Address", true) &&
                Validator.isPresent(txtBCN, "Book Club Name", true) &&
                Validator.isValidGmailAddress(txtEmailAddress);
    }
}
