import java.util.ArrayList;

/**
 * Created by Toby on 2/12/2015.
 *
 * makes a book class
 */
public class Book {
    private String title,author,isbn;
    private Rating rating;
    public static ArrayList<Book> pastBooks;


    public Book(String t, String a)
    {
        title = t;
        author = a;
    }

    public Book(String ISBN) {
        setISBN(ISBN);
    }

    public String getISBN() {
        return isbn;
    }
    public void setISBN(String ISBN) {
        if (ISBN_Validator.ValidateISBN(ISBN)) {
            this.isbn = ISBN;
        }
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }

    public Rating getRating() { return rating; }
    public void setRating(Rating r) { this.rating = r; }
}
