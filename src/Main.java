import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is our main class.
 * This class runs the GUI
 */

public class Main extends Application {
    //Not very pretty, but is set to static to make interaction between MainWindowController and Main possible
    public static QueryHandler qh;

    public static void main(String[] args) throws IOException {
        csvReaderFunction();
        movieLibraryFunction(csvReaderFunction());
        //Necessary for the JavaFX application
        launch(args);
    }

    //Runs the CsvReader class and returns the movie and rating arrays
    public static Pair<ArrayList<Movie>, ArrayList<Rating>> csvReaderFunction() {
        CsvReader c = new CsvReader();
        c.push();
        return new Pair<>(c.movies, c.ratings);
    }

    /**
     * A bit of a messy block of code but its the function that creates most of our movie and rating objects
     * Also executes what is necessary to connect the soundmixes to their respective movie resources
     *
     * Creates an ontology object to run our ontology model.
     *
     * @param pair
     * @throws FileNotFoundException
     */
    public static void movieLibraryFunction(Pair<ArrayList<Movie>, ArrayList<Rating>> pair) throws FileNotFoundException {
        MovieLibrary movLib = new MovieLibrary();
        ArrayList<Movie> mov = new ArrayList<>(pair.getKey());
        ArrayList<Rating> rat = new ArrayList<>(pair.getValue());

        for(Movie movie :mov){
            movLib.movies.add(movie);
        }
        for(Rating rating : rat){
            movLib.ratings.add(rating);
        }
        movLib.addGenres();
        movLib.annotate(movLib.generateRatings());
        movLib.addRating();

        SoundHandler soundHandler = new SoundHandler();
        Ontology ont = new Ontology();
        ont.makeModel(movLib.movies,soundHandler.mixes);
        qh = new QueryHandler(ont);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(root, 877, 521));
        primaryStage.show();
    }
}