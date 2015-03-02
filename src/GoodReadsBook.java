import java.net.URL;

/**
 * Created by boyd on 2/17/15.
 */




/*the goodreads api docs are at http://www.goodreads.com/api.
 *
 *
 *
 *api key I'm using J3GUE84DV610O7QQhEp5Jw
 *                              ^this is an O "capital oh"
 *
 *
 *
 */
public class GoodReadsBook {
    Book bk;
    URL imageUrl;
    Integer numPages, numReviews, numRatings;
    Double AveRating;

    GoodReadsBook(Book b) {this.bk = b;}

    public void setImageUrl(URL u){this.imageUrl=u;}
    public void setNumPages(int nPages){this.numPages = nPages;}
}
