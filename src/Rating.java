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

    public Rating(String i, int m, int r, String c)
    {
        isbn = i;
        mid = m;
        rating = r;
        comments = c;
    }
    public Rating(Book b, Member m, int r, String c){
        member = m;
        book = b;

        rating = r;
        comments = c;
    };

    public String getBook() {
        return isbn;
    }

    public String getComments() {
        return comments;
    }
    public int getRating() {
        return rating;
    }
    public int getMember(){
        return mid;
    }
}
