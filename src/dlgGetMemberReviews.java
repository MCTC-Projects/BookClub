import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class dlgGetMemberReviews extends JDialog {
    private JPanel contentPane;
    private JButton btnGetMemberReviews;
    private JButton btnClose;
    private JList lboMembers;

    private ArrayList<Member> members;
    private Book book;

    public dlgGetMemberReviews(Book b) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnGetMemberReviews);

        members = new ArrayList<Member>();
        book = b;

        DefaultListModel listModel = new DefaultListModel();

        for (Rating r : Rating.getRatingsByBook(b)) {
            Member m = r.getMember();

            if (!members.contains(m)) {
                members.add(m);
                listModel.addElement(m.getName());
            }
        }
        lboMembers.setModel(listModel);

        btnGetMemberReviews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnClose.addActionListener(new ActionListener() {
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
        if (lboMembers.isSelectionEmpty()) {
            Validator.messageBox("Please select a member", "Select member");
        } else {
            Member member = members.get(lboMembers.getSelectedIndex());
            Rating rating = Rating.getRating(book, member);

            String message = "Rating: " + rating.getRating() +
                    "\nComments:\n" + rating.getComments();

            Validator.messageBox(message, member.getName());
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
