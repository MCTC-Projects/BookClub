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
    private JButton btnGetMemberReviews;

    public static String BookClubName;
    private static JFrame mainFrame = null;

    //Create test book object
    //private Book b = new Book("The Hobbit", "Tolkien");
    private Book assignedReading;

//    //Create test meeting object
//    private Date meetingDate = new Date();
//    private Meeting m = new Meeting(meetingDate, "MCTC T-Building, Room T-3050");
    private Meeting nextMeeting;

    //ArrayList of suggested books
    private static ArrayList bookSuggestions = new ArrayList();
    private static ArrayList<Book> pastBooks;

    public frmMain() {
        //Meeting.setAssignedReading(b);
        Book.GetCurrentBookInfo();
        assignedReading = Book.getCurrentBook();
        nextMeeting = Meeting.GetNextMeetingInfo();

        //open database connection
        DB.Connect();

        pastBooks = new ArrayList<Book>();

        pastBooks = DB.getAllBooks();

        //lboPastBooks = new JList();       //lboPastBooks is already instantiated
        DefaultListModel listbooks= new DefaultListModel();

        for (int i = 0; i < pastBooks.size(); i++) {
            //TODO: get String data from book objects

            listbooks.addElement(pastBooks.get(i).getTitle());
        }
        lboPastBooks.setModel(listbooks);


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
                openBookReviewDialog(Book.getCurrentBook());
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
                    int bookIndex = lboPastBooks.getSelectedIndex();
                    Book b = pastBooks.get(bookIndex);
                    openBookReviewDialog(b);
                }
            }
        });

        btnGetMemberReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lboPastBooks.isSelectionEmpty()) {
                    Validator.messageBox("Please select a book to review", "Select Book");
                } else {
                    int bookIndex = lboPastBooks.getSelectedIndex();
                    Book b = pastBooks.get(bookIndex);
                    openBookReviewDialog(b);
                }
            }
        });

        /** BOOK SUGGESTIONS TAB **/
        if (assignedReading != null) {
            lboBookSuggestions.setEnabled(true);
            PopulateBookSuggestions(assignedReading.getTitle(), assignedReading.getAuthor());
        } else {
            lboBookSuggestions.setEnabled(false);
            bookSuggestions.clear();
            bookSuggestions.add("No book to base suggestions off of, please click \"Get Suggestions\"");
        }

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
                    Validator.messageBox("Please select a book to view.", "Book not selected");
                }
                //lboBookSuggestions selection mode "single selection"
                //so user can only select one book
                //no need to check if user selects multiple books
                else {
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
        //this.PopulateBookSuggestions(b.getTitle(),b.getAuthor());
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

    private void openBookReviewDialog(Book b) {
        //Initialize and open review book dialog
        dlgReviewBook reviewBookDialog = new dlgReviewBook();
        reviewBookDialog.setTitle("Review: " + b.toString());

        //Set dimensions
        Dimension dimensions = new Dimension(300, 250);
        reviewBookDialog.setSize(dimensions);
        reviewBookDialog.setMinimumSize(dimensions);

        CenterOnScreen(reviewBookDialog);

        reviewBookDialog.bookToReview = b;

        reviewBookDialog.setVisible(true);
    }

    private void openGetMemberReviewsDialog(Book b) {
        //Initialize and open getMemberReviews dialog
        dlgGetMemberReviews getMemberReviewsDialog = new dlgGetMemberReviews(b);
        getMemberReviewsDialog.setTitle("Member reviews for: " + b.toString());

        //Set dimensions
        Dimension dimensions = new Dimension(300, 250);
        getMemberReviewsDialog.setSize(dimensions);
        getMemberReviewsDialog.setMinimumSize(dimensions);

        CenterOnScreen(getMemberReviewsDialog);

        getMemberReviewsDialog.setVisible(true);
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

    public static void RestartMainForm() {
        //Sometimes takes a while to restart, patience is key!

        if (mainFrame != null) {
            //dispose old frame if exists
            mainFrame.dispose();
        }

        mainFrame = new JFrame("frmMain");
        mainFrame.setContentPane(new frmMain().panel1);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();

        mainFrame.setTitle("Book Club");    //Form title

        //Set dimension properties
        Dimension dimensions = new Dimension(550, 350);
        mainFrame.setSize(dimensions);
        mainFrame.setMinimumSize(dimensions);

        CenterOnScreen(mainFrame);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        RestartMainForm();
    }
}
