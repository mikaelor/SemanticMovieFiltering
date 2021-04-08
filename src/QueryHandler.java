import org.apache.jena.query.ResultSet;

public class QueryHandler {
    public Ontology ont;

    /**
     * Instantiates the QueryHandler by giving it a Ontology which will be queried.
     * @param o the ontology
     */
    public QueryHandler(Ontology o){
        this.ont = o;
    }


    /**
     * Takes in a query, and returns the respective ResultSet from the ontology's queryEngine
     * @param query a query-string to be sent to the ontology
     * @return the ResultSet of given query
     */
    public ResultSet getSet(String query){
        return ont.queryThingOdin(query);
    }

    /**
     * Does the same as getSet(), except it also uses the a queryEngine version to System.out the results
     * @param query a query-string to be sent ot the ontology
     * @return the ResultSet of given query
     */
    public ResultSet handleQuery(String query){
        ont.queryEngine(query);
        return ont.queryThingOdin(query);
    }
}
