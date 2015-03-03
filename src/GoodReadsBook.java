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
public class GoodReadsBook extends Book{
    String imageUrl;
    Integer numPages;
    Integer numReviews;
    Integer numRatings;


    GoodReadsBook(String t,String a) {this.setTitle(t);this.setAuthor(a);}

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }

    public Double getAveRating() {
        return AveRating;
    }

    public void setAveRating(Double aveRating) {
        AveRating = aveRating;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    Integer id;
    Double AveRating;
    String description;

    public void setDescription(String description) {
        this.description = description;
    }


    public void setImageUrl(String u){this.imageUrl=u;}
    public void setNumPages(int nPages){this.numPages = nPages;}

    @Override
    public String toString() {
        return this.getTitle() + "; " + this.getAuthor();
    }
}
