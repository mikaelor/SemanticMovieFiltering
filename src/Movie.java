/**
 * Standard bean class to create Movie objects
 */
public class Movie {
    int movieID;
    String title;
    String genre;
    double rating;
    int year;

    public Movie(int movieID, String title, String genre, double rating, int year) {
        this.movieID = movieID;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
