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
    }
    public Book(){};
    public Book(String ISBN) {
        setISBN(ISBN);
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

    public static void UpdateCurrentBookInfo() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("AssignedReadingInfo.txt"));

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
            Validator.messageBox("Problem writing to \"AssignedReadingInfo.txt\"\n" + ioe, "Error");
        }
    }

    public static Book GetCurrentBookInfo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("AssignedReadingInfo.txt"));

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
                return b;
            }
            return null;
        } catch (IOException ioe) {
            Validator.messageBox("Problem reading from \"AssignedReadingInfo.txt\"\n" + ioe, "Error");
            return null;
        }
    }

    @Override
    public String toString() {
        return this.getTitle() + "; " + this.getAuthor();
    }
}
