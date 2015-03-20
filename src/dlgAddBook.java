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
            GoodReadsBook grb;

            if (Validator.isPresent(txtISBN, "ISBN", false)) {
                //get book by ISBN
                String isbn10 = ISBN_Validator.getValidISBN10(txtISBN.getText());
                String isbn13 = ISBN_Validator.getValidISBN13(txtISBN.getText());

                String isbn = isbn10;
                while (true) {
                    grb = new GoodReadsBook(isbn);

                    GoodReadsResponse response = new GoodReadsResponse(grb);

                    //get remaining book info from API
                    response.populateFromAPI();
                    grb = response.getBk();

                    if (grb.getTitle() != null && grb.getAuthor() != null) {
                        break;
                    } else {
                        if (isbn.equals(isbn13)) {
                            //at this point both isbn-10 and isbn-13 did not return book info
                            break;
                        }
                        isbn = isbn13;
                    }
                }
            } else {
                //get book by title and author
                grb = new GoodReadsBook(txtTitle.getText(), txtAuthor.getText());

                GoodReadsResponse response = new GoodReadsResponse(grb);

                //get remaining book info from API
                response.populateFromAPI();
                grb = response.getBk();
            }

            String bookTitle = grb.getTitle();
            String bookISBN = grb.getISBN();

            String authorString = grb.getAuthor();      //author string format: ######Author Namehttp://...
                                                        //                        id |author name| url

            if (
                    bookTitle != null &&
                    authorString != null &&
                    bookISBN != null
                    ) {
                //parse author name from author data string
                int urlStartsHere = authorString.indexOf("http:");


                String authorName = authorString.substring(6, urlStartsHere);   //Author name starts after 6-digit ID
                // and ends when the url starts

                Book.setCurrentBook(new Book(bookISBN, bookTitle, authorName));

                dispose();
                frmMain.RestartMainForm();
            } else {
                Validator.messageBox(
                        "Could not get book information for: " +
                        ((!txtISBN.getText().isEmpty()) ? txtISBN.getText() : txtTitle.getText() + "; " + txtAuthor.getText()),
                        "Book not found");
            }
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
