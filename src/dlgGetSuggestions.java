import javax.swing.*;
import java.awt.event.*;

public class dlgGetSuggestions extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtTitle;
    private JTextField txtAuthor;





    public dlgGetSuggestions() {
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
            try {
                String title = txtTitle.getText();
                String author = txtAuthor.getText();
                frmMain.PopulateBookSuggestions(title, author);
                dispose();
                frmMain.RestartMainForm();
            } catch (Exception e) {
                Validator.messageBox(e.toString() + "\n\n" + e.getMessage(), "Error");
            }
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private boolean isValidInput() {
        return
                Validator.isPresent(txtTitle, "Title", true) &&
                Validator.isPresent(txtAuthor, "Author", true);
    }
}
