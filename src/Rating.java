/**
 * Created by Toby on 2/12/2015.
 *
 * makes rating class
 */
public class Rating {
    private String comments;
    private int rating;
    private Book book;
    private Member member;

    public Rating(Book b, Member m, int r, String c)
    {
        book = b;
        member = m;
        rating = r;
        comments = c;
    }

    public Book getBook() {
        return book;
    }

    public String getComments() {
        return comments;
    }
    public int getRating() {
        return rating;
    }
    public Member getMember(){
        return member;
    }
}
