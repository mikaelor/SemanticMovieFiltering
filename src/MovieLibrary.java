import java.util.*;

/**
 * This class handles all the necessary arrays for movie objects and ratings.
 */

public class MovieLibrary {
    ArrayList<Movie> movies;
    ArrayList<Rating> ratings;
    ArrayList<String> genres;
    HashMap<Integer, Double> meanRatings;
    Map<Integer, Double> ratingMap;

    //Constructor
    public MovieLibrary() {
        movies = new ArrayList<>();
        genres = new ArrayList<>();
        ratings = new ArrayList<>();
        meanRatings = new HashMap<>();
    }

    //Kind of a debugging function. Used to check what is created in the movie array
    public void printMovies(){
        for(Movie movie: movies){
            System.out.println(movie.movieID);
            System.out.println(movie.title);
            System.out.println(movie.genre);
            System.out.println(movie.rating);
            System.out.println(movie.year);
            System.out.println("----------");
            System.out.println("----------");
        }
    }

    //Function to extract and create a list of unique genres
    public void addGenres() {
        for (Movie m : movies) {
            genres.add(m.genre);
        }
       List<String> genres2 = new ArrayList<>();
        for(String s : genres){
            String[] value = s.split("[|]");
            for(String z : value){
                genres2.add(z);
            }
        }
        genres.clear();
        for(String s : genres2){
            if(!genres.contains(s)){
                genres.add(s);
            }
        }
    }

    /**
     * The three next functions generates and complements each movie with a mean rating.
     */

    public ArrayList<Integer> generateRatings() {
        ArrayList<Integer> ints = new ArrayList<>();
        for (Rating r : ratings) {
            if (!ints.contains(r.movieId)) {
                ints.add(r.movieId);
            }
        }
        //System.out.println(ints);
        return ints;
    }

    public void annotate(ArrayList<Integer> ints) {
        for (int i = 0; i < ints.size(); i++) {
            int uniqueIndex = 0;
            double meanRating = 0;
            try {
                for (Rating rating : ratings) {
                    if (rating.movieId == ints.get(i)) {
                        meanRating = meanRating + rating.rating;
                        uniqueIndex++;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
            meanRatings.put(ints.get(i), meanRating / uniqueIndex);
        }
        ratingMap = new TreeMap<Integer, Double>(meanRatings);
    }

    public void addRating() {
        for(Map.Entry<Integer, Double> entry : ratingMap.entrySet()){
            for(Movie movie : movies){
                if(entry.getKey() == movie.movieID){
                    movie.rating = entry.getValue();
                }
            }
        }
    }

}