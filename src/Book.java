import java.io.*;
import java.util.ArrayList;

/**
 * Created by Toby on 2/12/2015.
 *
 * makes a book class
 */
public class Book {
    private String title,author,isbn ;
    private Rating rating;
    public static Book currentBook;
    public static ArrayList<Book> pastBooks;

    public Book(String i, String t, String a)
    {
        isbn = i;
        title = t;
        author = a;
        //TODO: check that isbn and title+author match up? maybe?? idk.
    }
    public Book( String t, String a)
    {
        title = t;
        author = a;
        //TODO: get isbn by title and author
    }
    public Book(){}         //This constructor keeps popping up for some reason,
                            // I think it has something to do with GoodReadsBook
                            // See: Right-click > Find Usages
                            // I guess it can stay tho, no harm

    public Book(String ISBN) {
        setISBN(ISBN);
        //TODO: get title and author by ISBN
    }

    public String getISBN() {
        return isbn;
    }
    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    public String getTitle() {
        return title;
    }



    public String getAuthor() {
        return author;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public Rating getRating() { return rating; }
    public void setRating(Rating r) { this.rating = r; }

    public static void setCurrentBook(Book book) {
        if (currentBook!= book) {
            currentBook = book;
            UpdateCurrentBookInfo();
            DB.addBook(currentBook);
        }
    }
    public static Book getCurrentBook() { return currentBook; }

    public static void UpdateCurrentBookInfo() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("CurrentBookInfo.txt"));

            if (currentBook != null) {
                if (currentBook.getISBN() != null) {
                    writer.write(currentBook.getISBN());
                }

                writer.write(";");

                if (currentBook.getTitle() != null &&
                    currentBook.getAuthor() != null) {
                    writer.write(currentBook.getTitle());
                    writer.write(";");
                    writer.write(currentBook.getAuthor());
                } else {
                    writer.write(";");
                }
            }
            writer.close();
        } catch (IOException ioe) {
            Validator.messageBox("Problem writing to \"CurrentBookInfo.txt\"\n" + ioe, "Error");
        }
    }

    public static void GetCurrentBookInfo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("CurrentBookInfo.txt"));

            String data = reader.readLine();

            if (data != null) {
                Book b;

                String[] bookData = data.split(";", -1);

                if (!bookData[0].equals("")) {      //ISBN
                    b = new Book(bookData[0]);

                    if (!bookData[1].equals("")) {  //Title
                        b.setTitle(bookData[1]);
                    }
                    if (!bookData[2].equals("")) {  //Author
                        b.setAuthor(bookData[2]);
                    }
                } else {
                    b = new Book(bookData[0],bookData[1], bookData[2]);
                }
                currentBook = b;
            }
        } catch (IOException ioe) {
            Validator.messageBox("Problem reading from \"CurrentBookInfo.txt\"\n" + ioe, "Error");
        }
    }

    public static void AddBook(Book book) {
        pastBooks.add(book);
        UpdateBookData();
    }

    public static void RemoveBook(Book book) {
        pastBooks.remove(book);
        UpdateBookData();
    }

    public static ArrayList<Book> getPastBooks() {
        return pastBooks;
    }

    public static void UpdateBookData() {
        //TODO: Send pastBooks ArrayList to DB
        //Call this each time pastBooks ArrayList is updated
    }

    public static void GetLatestBookData() {
        //TODO: Populate pastBooks ArrayList from DB
        //Call this at application start (in frmMain)
    }

    @Override
    public String toString() {
        return this.getTitle() + "; " + this.getAuthor();
    }

    //TODO: Add sorting capabilities?
}
