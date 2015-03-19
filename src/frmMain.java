import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.*;

/**
 * Created by Haru on 2/19/2015.
 */
public class frmMain {

    //Initialize Form Components
    private JTabbedPane tabbedPane1;
    public JPanel panel1;
    private JPanel tabHome;
    private JPanel tabMembers;
    private JPanel tabPastBooks;
    private JPanel pnlNextMeeting;
    private JLabel lblBookTitle;
    private JButton btnAddBook;
    private JLabel lblDoneReading;
    private JButton btnReviewBook;
    private JLabel lblAuthorName;
    private JLabel lblLocation;
    private JLabel lblDateTime;
    private JLabel lblMeetingNotSet;
    private JButton btnSetMeeting;
    private JLabel lblNoBookSelected;
    private JList lboMembers;
    private JButton btnEmailMembers;
    private JButton btnAddMember;
    private JButton btnEditMember;
    private JPanel pnlNoBook;
    private JPanel pnlNoMeeting;
    private JList lboBookSuggestions;
    private JPanel tabBookSuggestions;
    private JList lboPastBooks;
    private JButton btnReviewPastBook;
    private JButton btnViewBook;
    private JPanel pnlMeetingInfo;
    private JPanel pnlBookInfo;
    private JButton btnGetSuggestions;

    public static String BookClubName;

    //Create test book object
    private Book b = new Book("The Hobbit", "Tolkien");
    private Book assignedReading;

    //Create test meeting object
    private Date meetingDate = new Date();
    private Meeting m = new Meeting(meetingDate, "MCTC T-Building, Room T-3050");
    private Meeting nextMeeting;

    //ArrayList of suggested books
    private static ArrayList bookSuggestions = new ArrayList();

    public frmMain() {
        //Meeting.setAssignedReading(b);
        /*Book.GetCurrentBookInfo();
        assignedReading = Book.getCurrentBook();
        nextMeeting = m;
//open database connection
        DB.Connect();


        ArrayList<Book> books = new ArrayList<Book>();



            lboPastBooks = new JList();
            DefaultListModel listbooks= new DefaultListModel();

            for (Book book : DB.getAllBooks()) {

                listbooks.addElement(book.getTitle());
            }
            lboPastBooks.setModel(listbooks);
            lboPastBooks.setEnabled(true);

        if (assignedReading != null) {
            lboBookSuggestions.setEnabled(true);
            PopulateBookSuggestions(assignedReading.getTitle(), assignedReading.getAuthor());
        } else {
            lboBookSuggestions.setEnabled(false);
            bookSuggestions.clear();
            bookSuggestions.add("No book to base suggestions off of, please click \"Get Suggestions\"");
        }
        */
        /** HOME TAB **/

        UpdateHomeTab();

        //Set Meeting
        btnSetMeeting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSetMeetingDialog();
            }
        });

        //Add Book
        btnAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddBookDialog();
            }
        });

        //Review Book
        btnReviewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBookReviewDialog();
            }
        });

        /** MEMBERS TAB **/
        UpdateMembersTab();
        btnEmailMembers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (lboMembers.isSelectionEmpty()) {
//                    Validator.messageBox("Please select members to email.", "Select Members");
//                } else {
                    ArrayList<Member> recipients = new ArrayList<Member>();
                    for (int index : lboMembers.getSelectedIndices()) {
                        recipients.add(Member.getAllMembers().get(index));
                    }
                    openEmailDialog(recipients);
//                }
            }
        });

        btnAddMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddMemberDialog();
            }
        });

        /** PAST BOOKS TAB **/
        btnReviewPastBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lboPastBooks.isSelectionEmpty()) {
                    Validator.messageBox("Please select a book to review", "Select Book");
                } else {
                    openBookReviewDialog();
                }
            }
        });

        /** BOOK SUGGESTIONS TAB **/
        UpdateBookSuggestionsTab();

        btnGetSuggestions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGetSuggestionsDialog();
            }
        });

        btnViewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lboBookSuggestions.isSelectionEmpty()) {
                    Validator.messageBox("Please select a book.", "Error");
                } else if (lboBookSuggestions.getSelectedIndices().length > 1) {
                    Validator.messageBox("Please select one book.", "Error");
                } else {
                    GoodReadsBook book = (GoodReadsBook)bookSuggestions.get(lboBookSuggestions.getSelectedIndex());

                    openShowBooksForm(book);
                }
            }
        });
    }

    private void UpdateHomeTab() {
        //TODO
        //Get book info
        if (assignedReading == null) {
            pnlBookInfo.setVisible(false);
            lblNoBookSelected.setVisible(true);
            btnAddBook.setText("Add Book");
        } else {
            lblNoBookSelected.setVisible(false);
            pnlBookInfo.setVisible(true);
            btnAddBook.setText("Edit Book Info");
            lblBookTitle.setText(assignedReading.getTitle());
            lblAuthorName.setText(assignedReading.getAuthor());
        }

        //Get meeting info
        if (nextMeeting == null) {
            pnlMeetingInfo.setVisible(false);
            lblMeetingNotSet.setVisible(true);
            btnSetMeeting.setText("Set Meeting");
        } else {
            pnlMeetingInfo.setVisible(true);
            lblMeetingNotSet.setVisible(false);
            btnSetMeeting.setText("Edit Meeting Info");
            DateFormat df = new SimpleDateFormat(Meeting.LONG_FRIENDLY_DATE_FORMAT);
            lblLocation.setText(nextMeeting.getLocation());
            lblDateTime.setText(df.format(nextMeeting.getDateTime()));
        }
    }

    public void UpdateBookSuggestionsTab() {
        DefaultListModel listModel = new DefaultListModel();
        this.PopulateBookSuggestions(b.getTitle(),b.getAuthor());
        for (Object o : bookSuggestions) {
            //TODO: get String data from book objects
            listModel.addElement(o.toString());
        }
        lboBookSuggestions.setModel(listModel);
    }

    public void UpdateMembersTab() {
        DefaultListModel listModel = new DefaultListModel();

        for (Member m : Member.getAllMembers()) {
            listModel.addElement(m.getName() + "; " + m.getEmail());
        }
        lboBookSuggestions.setModel(listModel);
    }

    private void openSetMeetingDialog() {
        //Initialize and open set meeting dialog
        dlgSetMeeting setMeetingDialog = new dlgSetMeeting();
        setMeetingDialog.setTitle("Set Meeting");

        //Set dimensions
        Dimension dimensions = new Dimension(325, 175);
        setMeetingDialog.setSize(dimensions);
        setMeetingDialog.setResizable(false);

        CenterOnScreen(setMeetingDialog);

        setMeetingDialog.setVisible(true);
    }

    private void openEmailDialog(ArrayList<Member> recipients) {
        //Initialize and open add book dialog
        dlgEmail emailDialog = new dlgEmail();
        emailDialog.setTitle("Send Email");
        emailDialog.recipientList = recipients;

        //Set dimensions
        Dimension dimensions = new Dimension(400, 400);
        emailDialog.setSize(dimensions);
        emailDialog.setResizable(false);

        CenterOnScreen(emailDialog);

        emailDialog.setVisible(true);
    }

    private void openAddBookDialog() {
        //Initialize and open add book dialog
        dlgAddBook addBookDialog = new dlgAddBook();
        addBookDialog.setTitle("Add Book");

        //Set dimensions
        Dimension dimensions = new Dimension(300, 175);
        addBookDialog.setSize(dimensions);
        addBookDialog.setResizable(false);

        CenterOnScreen(addBookDialog);

        addBookDialog.setVisible(true);


    }

    private void openBookReviewDialog() {
        //Initialize and open review book dialog
        dlgReviewBook reviewBookDialog = new dlgReviewBook();
        reviewBookDialog.setTitle("Review Book");

        //Set dimensions
        Dimension dimensions = new Dimension(300, 250);
        reviewBookDialog.setSize(dimensions);
        reviewBookDialog.setMinimumSize(dimensions);

        CenterOnScreen(reviewBookDialog);

        reviewBookDialog.setVisible(true);
    }

    private void openGetSuggestionsDialog() {
        //Initialize and open review book dialog
        dlgGetSuggestions getSuggestionsDialog = new dlgGetSuggestions();
        getSuggestionsDialog.setTitle("Review Book");

        //Set dimensions
        Dimension dimensions = new Dimension(315, 200);
        getSuggestionsDialog.setSize(dimensions);
        getSuggestionsDialog.setMinimumSize(dimensions);

        CenterOnScreen(getSuggestionsDialog);

        getSuggestionsDialog.setVisible(true);

        //TODO: call UpdateBookSuggestionsTab() upon closing dialog
    }



    private void openAddMemberDialog() {
        //Initialize and open review book dialog
        dlgAddMember AddMemberDialog = new dlgAddMember();
        AddMemberDialog.setTitle("Add Member");

        //Set dimensions
        Dimension dimensions = new Dimension(315, 200);
        AddMemberDialog.setSize(dimensions);
        AddMemberDialog.setMinimumSize(dimensions);

        CenterOnScreen(AddMemberDialog);

        AddMemberDialog.setVisible(true);
    }

    private void openShowBooksForm(GoodReadsBook book) {
        //Initialize form
        JFrame frame = new JFrame("frmShowBooks");
        frmShowBooks showBooksForm = new frmShowBooks();
        frame.setContentPane(showBooksForm.getPanel1());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        frame.setTitle(book.getTitle());    //Form title
        showBooksForm.populateDescription(book);
        showBooksForm.setAuthorTxtField(book.getAuthor());
        showBooksForm.setTitleTxtField(book.getTitle());

        //Set dimension properties
        Dimension dimensions = new Dimension(600, 400);
        frame.setSize(dimensions);
        frame.setMinimumSize(dimensions);

        CenterOnScreen(frame);

        //Show form
        frame.setVisible(true);
    }

    public static void CenterOnScreen(Window window) {
        //Center window on screen
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();   //Get screen size

        //Screen's center point
        int screenCenterX = screenDimensions.width / 2;
        int screenCenterY = screenDimensions.height / 2;

        //Window's center point
        int windowCenterX = window.getSize().width / 2;
        int windowCenterY = window.getSize().height / 2;

        window.setLocation(screenCenterX - windowCenterX, screenCenterY - windowCenterY);
    }

    public static void PopulateBookSuggestions(String bookTitle, String authorName) {
        GoodReadsResponse response = new GoodReadsResponse(bookTitle, authorName);
        response.populateFromAPI();
        response.searchForBooks();
        bookSuggestions = response.getSuggestedBooks();
    }


    public static boolean WindowClosed(Window window) {
        //TODO
        return false;
    }
}
