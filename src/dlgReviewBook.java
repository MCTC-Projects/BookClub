import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class dlgReviewBook extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSlider sliderRating;
    private JTextArea txtComments;

    public dlgReviewBook() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();

        for (int i = 1; i <= 5; i++) {
            labelTable.put(i, new JLabel("" + i));
        }

        sliderRating.setLabelTable(labelTable);
        sliderRating.setPaintLabels(true);

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
        int rating = sliderRating.getValue();
        String comments = txtComments.getText();

        Rating r = new Rating(new Book("Blah", "B. Blah"), new Member(1, "Me", "me@google.com"), rating, comments); //TODO: get book and member info

        //Testing
        Validator.messageBox(
                "Book Title: " + r.getBook().getTitle() +
                        "\nAuthor: " + r.getBook().getAuthor() +
                        "\nReviewer Rating: " + r.getRating() + " / 5" +
                        "\nReviewer Comments:\n" + r.getComments() +
                        "\nReviewer Name: " + r.getMember().getName(),
                "Review");
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
