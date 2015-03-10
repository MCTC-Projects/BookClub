import javax.swing.*;
import java.awt.event.*;

public class dlgAddBook extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtISBN;

    public dlgAddBook() {
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
            if (Validator.isPresent(txtISBN, "ISBN", false)) {
                Book.currentBook = new Book(ISBN_Validator.getValidISBN13(txtISBN.getText()));
            } else {
                Book.currentBook = new Book(txtTitle.getText(), txtAuthor.getText());
            }
            dispose();
        }
    }

    private boolean isValidInput() {
        if (Validator.isPresent(txtISBN, "ISBN", false)) {
            if (ISBN_Validator.isValidISBN(txtISBN.getText())) {
                return true;
            } else {
                txtISBN.grabFocus();
                return false;
            }
        } else if (
                Validator.isPresent(txtTitle, "Book Title", false) &&
                Validator.isPresent(txtAuthor, "Author Name", false)) {
                return true;
        } else {
            Validator.messageBox("Please enter either the book's title and author name, or its ISBN", "Entry Error");
            return false;
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
