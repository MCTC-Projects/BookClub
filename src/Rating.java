import java.util.ArrayList;

/**
 * Created by Toby on 2/12/2015.
 *
 * makes rating class
 */
public class Rating {
    private String comments;
    private int rating;
    private String isbn;
    private int mid;
    private Book book;
    private Member member;

    private static ArrayList<Rating> allRatings = new ArrayList<Rating>();

    public Rating(String i, int m, int r, String c)
    {
        isbn = i;
        mid = m;
        rating = r;
        comments = c;

        allRatings.add(this);
    }
    public Rating(Book b, Member m, int r, String c){
        member = m;
        book = b;

        rating = r;
        comments = c;

        allRatings.add(this);
    }

    public Book getBook(){return this.book;}
    public String getIsbn() {
        return isbn;
    }

    public String getComments() {
        return comments;
    }
    public int getRating() {
        return rating;
    }
    public int getMID(){
        return mid;
    }
    public Member getMember(){return this.member;}

    public static ArrayList<Rating> getAllRatings() {
        return allRatings;
    }

    public static ArrayList<Rating> getRatingsByBook(Book b) {
        ArrayList<Rating> ratings = new ArrayList<Rating>();

        for (Rating r : allRatings) {
            if (r.getBook() == b) {
                ratings.add(r);
            }
        }

        return ratings;
    }

    public static Rating getRating(Book b, Member m) {
        ArrayList<Rating> ratingsForBook = getRatingsByBook(b);

        for (Rating r : ratingsForBook) {
            if (r.getMember() == m) {
                return r;
            }
        }

        Validator.messageBox("Could not find " + m.getName() + "'s rating for " + b.getTitle(), "Not found");
        return null;
    }
}
