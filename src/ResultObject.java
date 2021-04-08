import javafx.beans.property.SimpleStringProperty;


/**
 * A bean for resultset-processing
 */
public class ResultObject {
    private SimpleStringProperty title;
    private SimpleStringProperty genre;
    private SimpleStringProperty rating;
    private SimpleStringProperty year;
    private SimpleStringProperty audio;


    /**
     * Constructor for the ResultObject-bean. Takes in strings instead of SimpleStringProperty for ease of use,
     * and rather transforms it in the constructor body instead.
     * @param title the title of the bean
     * @param genre the genre of the bean
     * @param rating the rating of the bean
     * @param year the year in which the bean was produced
     */
    public ResultObject(String title, String genre, String rating, String year, String audio) {
        //this.movieId = movieId;
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.rating = new SimpleStringProperty(rating);
        this.year = new SimpleStringProperty(year);
        this.audio = new SimpleStringProperty((audio));
    }

    public String getRating() {
        return rating.get();
    }

    public SimpleStringProperty ratingProperty() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    /**
     * Getter for the Title as a String instead of SimpleStringProperty
     * @return the title of the ResultObject;
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * getter for the SimpleStringProperty-version of the title
     * @return the titlel
     */
    public SimpleStringProperty titleProperty() {
        return title;
    }

    //The same goes for all the other getters and Setters. Nothing more exciting below
    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getAudio() {
        return audio.get();
    }

    public SimpleStringProperty audioProperty() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio.set(audio);
    }
}
