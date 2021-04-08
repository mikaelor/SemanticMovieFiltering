import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import java.util.ArrayList;

/**
 *This class is used to create the model for our ontology.
 */

public class Ontology {
    //this line creates the model object in which we add resources.
    OntModel model = ModelFactory.createOntologyModel();

    //this scope of strings is necessary to define prefixes of which we add to our model.
    String mTitle = "http://purl.org/dc/terms/title";
    String mRating = "http://schema.org/Rating";
    String mGenre = "http://dbpedia.org/ontology/genre";
    String mPublished = "https://schema.org/datePublished";
    String mIdentifier = "https://schema.org/identifier";
    String mSound = "https://schema.org/audio";

    //In this block we create all the properties we find necessary to apply to our movie resources
    public void makeModel(ArrayList<Movie> movies, ArrayList<Soundmix> sounds){
        DatatypeProperty hasTitle = model.createDatatypeProperty(mTitle);
        DatatypeProperty wasPublished = model.createDatatypeProperty(mPublished);
        DatatypeProperty hasRating = model.createDatatypeProperty(mRating);
        DatatypeProperty hasGenre = model.createDatatypeProperty(mGenre);
        DatatypeProperty hasMovieID = model.createDatatypeProperty(mIdentifier);
        DatatypeProperty hasSoundMix = model.createDatatypeProperty(mSound);


        //This block creates resources and applies literals to those resources
        for(Movie m : movies) {
            Resource movie = model.createResource("Movie " + (m.movieID));
            Literal movieTitle = model.createLiteral(m.title);
            Literal movieYear = model.createTypedLiteral(m.year);
            Literal movieRating = model.createTypedLiteral(m.rating);
            Literal movieIdentifier = model.createTypedLiteral(m.movieID);

            //Necessary to create and apply soundMix literals
            for(Soundmix soundmix : sounds){
                try{
                    if(soundmix.getMovieID() == m.movieID){
                        Literal movieSound = model.createTypedLiteral(soundmix.getSoundMix());
                        movie.addLiteral(hasSoundMix,movieSound);
                    }
                }
                catch (NullPointerException n){}
            }

            String genre = m.genre.replace("|", ", ");
            Literal movieGenre = model.createTypedLiteral(genre);

            movie.addLiteral(hasMovieID, movieIdentifier);
            movie.addProperty(hasGenre, movieGenre);
            movie.addLiteral(hasRating, movieRating);
            movie.addLiteral(wasPublished, movieYear);
            movie.addLiteral(hasTitle, movieTitle);
        }
        //Uncomment this line to show the model
       // model.write(System.out, "TURTLE");
    }

    //Executes the model and returns a formatted result.
    public void queryEngine(String q){
        Query query = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results, query);

        qe.close();
    }

    //We use this block to send the result to the QueryHandler class.
    public ResultSet queryThingOdin(String q){
        Query qu = QueryFactory.create(q);
        QueryExecution qe = QueryExecutionFactory.create(qu, model);
        ResultSet results = qe.execSelect();
        return results;

    }
}