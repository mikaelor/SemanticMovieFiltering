import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable{

    @FXML private CheckBox titleCheck;
    @FXML private CheckBox yearCheck;
    @FXML private CheckBox genreCheck;
    @FXML private CheckBox ratingCheck;
    @FXML private CheckBox soundCheck;
    @FXML private Button resetButton;
    @FXML private Button searchButton;
    @FXML private TextField titleSearch;
    @FXML private TextField yearFromSearch;
    @FXML private TextField yearToSearch;
    @FXML private ChoiceBox genreSearch;
    @FXML private TextField ratingFromSearch;
    @FXML private TextField ratingToSearch;
    @FXML private TableView<ResultObject> resultTable;
    @FXML private TableColumn<ResultObject,String> title;
    @FXML private TableColumn<ResultObject,String> genre;
    @FXML private TableColumn<ResultObject,String> rating;
    @FXML private TableColumn<ResultObject,String> year;
    @FXML private TableColumn<ResultObject,String> audio;
    @FXML private ChoiceBox sorterBox;
    @FXML private CheckBox ascBox;
    @FXML private CheckBox descBox;
    @FXML private TextField resultLimiter;


    /**
     * Initializer method for the Scene. Only used to fill our choiceBox/dropdown with options,
     * set the value of this dropdown to blank, and check off the checkbox for title for an easy start
     * @param url Necessity for the method to run
     * @param rb ResourceBundle necessary for the method to run.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        genreSearch.getItems().addAll("Action","Adventure", "Animation","Children",
                "Comedy","Crime","Documentary","Drama","Fantasy","Film-Noir","Horror","IMAX","Musical",
                "Mystery","Romance","Sci-Fi","Thriller","War","Western","");
        genreSearch.setValue("");
        sorterBox.getItems().addAll("Title","Year","Rating","Genre");
        sorterBox.setValue("");
        titleCheck.setSelected(true);

    }

    /**
     * Resets the checkBoxes, and the genre-dropdown
     */
    public void resetBoxes(){
        titleCheck.setSelected(false);
        yearCheck.setSelected(false);
        genreCheck.setSelected(false);
        ratingCheck.setSelected(false);
        soundCheck.setSelected(false);
        genreSearch.setValue("");
    }


    /**
     * A method to get the values from all the TextFields in the GUI
     * Used to generate "FILTER(?n ?n)" parts of queries
     * Used primarily by checkCheckBoxes()
     * @return a String in the form of SPARQL Filter statement.
     */
    public String getSearchValues(){
        //Multiple options for pre-made strings to use for easy access.
        String filterText = "FILTER(%1$s,%2$s) ";
        String filterText2 = "FILTER(%1$s %2$s) ";
        String filterText3 = "FILTER regex(%1$s %2$s) ";
        String finalText = "";

        //Bracket for search by Title
        //All brackets will check for text-value in the text-fields and respond if there is
        if(!titleSearch.getText().equals("")){
            String searchText = "'" + titleSearch.getText() + "'";
            finalText += String.format(filterText3,"?title, ",searchText);

        //Bracket for search by Year
        }if(!yearFromSearch.getText().equals("")){
            if(!yearToSearch.getText().equals("")) {
                if(Integer.parseInt(yearFromSearch.getText()) > Integer.parseInt(yearToSearch.getText())){
                    return ("error");
                }
                finalText += String.format(filterText2, "?year >= ", yearFromSearch.getText());
                finalText += String.format(filterText2, "?year <= ", yearToSearch.getText());
            } else {
                finalText += String.format(filterText2,"?year = ", yearFromSearch.getText());
            }
        }

        //Bracket for search by Genre
        if(!genreSearch.getValue().equals("")){
            String genreText = "'" + genreSearch.getValue() + "'";
            finalText += String.format(filterText3,"?genre, ", genreText);
        }

        //Bracket for search by Rating
        if(!ratingFromSearch.getText().equals("")) {
            if (!ratingToSearch.getText().equals("")) {
                finalText += String.format(filterText2, "?rating >= ", ratingFromSearch.getText());
                finalText += String.format(filterText2, "?rating <= ", ratingToSearch.getText());
            } else {
                finalText += String.format(filterText2, "?rating = ", ratingFromSearch.getText());
            }
        }
        return finalText;
    }

    /**
     * Checks what checkBoxes are checked, and adds the necessary Strings to the query for the corresponding checkbox.
     * Fills in both the prefixes, as well as the "WHERE" statement with results from getSearchValues()
     * @return a string which is the complete SPARQL query String.
     */
    public String checkCheckBoxes(){
        //A simple counter to see if any checkboxes has been checked at all
        int counter = 0;
        String prefixes = "PREFIX sc: <http://schema.org/>" +
                "PREFIX scs: <https://schema.org/>" +
                "PREFIX pr: <http://purl.org/dc/terms/>" +
                "PREFIX db: <http://dbpedia.org/ontology/> ";
        String selector = "SELECT%1$s ";
        String selectorAdd = "";
        String where = "WHERE {%1$s";
        String addWhere = "";
        String sorter = "ORDER BY %1$s(?%2$s)";
        String limiter = "LIMIT %1$s";
        //Checks all the checkboxes
        if(titleCheck.isSelected()){
            selectorAdd += " ?title";
            counter++;
        }
        if(yearCheck.isSelected()){
            selectorAdd += " ?year";
            counter++;
        }
        if(genreCheck.isSelected()){
            selectorAdd += " ?genre";
            counter++;
        }
        if(ratingCheck.isSelected()){
            selectorAdd += " ?rating";
            counter++;
        }
        if(soundCheck.isSelected()){
            selectorAdd += " ?audio";
            counter++;
        }

        //Adds all the relations movies might have to the query for easy use of the code.
        addWhere += "?movie pr:title ?title . ";
        addWhere += "?movie scs:datePublished ?year . ";
        addWhere += "?movie db:genre ?genre . ";
        addWhere += "?movie sc:Rating ?rating . ";
        addWhere += "?movie scs:audio ?audio . ";

        //Generates the final query-String with all sub-strings put together, along with getSearchValues()
        String finalSelector = String.format(selector,selectorAdd);
        String finalWhere = String.format(where,addWhere);
        String filters = getSearchValues();
        if(filters.equals("error")){
            return filters;
        }
        String finalString = prefixes + finalSelector + finalWhere + filters + "}";
        if(!sorterBox.getValue().equals("")){
            if(ascBox.isSelected() && !descBox.isSelected()){
                finalString += String.format(sorter,"ASC",sorterBox.getValue().toString().toLowerCase());
            }
            else if(descBox.isSelected() && !ascBox.isSelected()){
                finalString += String.format(sorter,"DESC",sorterBox.getValue().toString().toLowerCase());
            }
            else {
                return "sorter";
            }

        }

        if(!resultLimiter.getText().equals("")){
            finalString += String.format(limiter,resultLimiter.getText());
        }

        //If no checkboxes are selected, finalString is set to blank,
        // which will trigger a different response than a query when passed along to query();
        if(counter == 0){
           finalString = "";
        }

        return finalString;

    }

    /**
     * Handles the results returned by a query by making more manageable objects(beans)
     * out of each QuerySolution object. Objects are stashed in an ArrayList which is passed along for display in our GUI
     * in another method (displayResult())
     * @param res the resultset returned from a query
     */
    public void handleResults(ResultSet res){
        ArrayList<ResultObject> rList = new ArrayList<>();
        List<QuerySolution> testList =  ResultSetFormatter.toList(res);

        //Generates a ResultObject for every QuerySolution-element
        for(QuerySolution x : testList){

            //Ints and Doubles which are not found will be set to 0/0.0. This is not pretty in the GUI
            String title = null;
            String genre = null;
            String year = null;
            String rating = null;
            String audio = null;
            if(x.get("title") != null){
                title =  x.get("title").toString();
            }
            if(x.get("genre") != null){
                genre = x.get("genre").toString();
            }

            if(x.get("audio") != null){
                audio = x.get("audio").toString();
            }

            //Some entries have a wrong year entered. Therefore we catch these with our NumberFormatException
            if(x.get("year") != null){
                try{
                    //Some funky typeconversions are necessary to fit our ResultObject-needs.
                    year = x.get("year").toString().substring(0, 4);
                } catch (NumberFormatException ex){
                    year = "Invalid Numbering";
                }

            }
            if(x.get("rating") != null){
                    String temp = x.get("rating").toString().substring(0,3);
                    rating = temp;

            }
            ResultObject r = new ResultObject(title,genre,rating,year,audio);
            rList.add(r);
        }
        //Sends the ArrayList to displayResult for further prep before being displayed.
        displayResult(rList);
    }

    /**
     * Takes a list of ResultObject beans and makes them into a ObservableList,
     * making it possible to display the results in a JavaFX TableView
     * @param e an ArrayList of ResultObjects
     */
    public void displayResult(ArrayList<ResultObject> e){
        //Generates the ObservableList and copies all elements from given argument ArrayList into it
        ObservableList<ResultObject> rso = FXCollections.observableArrayList();
        rso.addAll((e));
        //Finds the proper values to be filled into each TableRow by the getter methods of each value in the bean.
        title.setCellValueFactory(new PropertyValueFactory<ResultObject,String>("title"));
        genre.setCellValueFactory(new PropertyValueFactory<ResultObject,String>("genre"));
        rating.setCellValueFactory(new PropertyValueFactory<ResultObject,String>("rating"));
        year.setCellValueFactory(new PropertyValueFactory<ResultObject,String>("year"));
        audio.setCellValueFactory(new PropertyValueFactory<ResultObject,String>("audio"));
        resultTable.setItems(rso);
    }

    public void errorMessage(String problem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("QueryWizard");
        alert.setHeaderText("An error has occured in your query!");
        alert.setContentText("Problem: " + problem);

        alert.showAndWait();
    }

    /**
     * By the use of the static QueryHandler in the main class, query() is able to access the Ontology,
     * query it, and receive the result, which is passed along to handleResults() for GUI display
     */
    public void query() {
        String qString = checkCheckBoxes();
        //If query-String is blank, prompt the user to check a checkBox
        if (qString.equals("")) {
            errorMessage("You must select at least one value to be returned!");
        }else if(qString.equals("error")){
            errorMessage("Year span must be from lower-to-higher!");
        }else if (qString.equals("sorter")){
            errorMessage("You must select either Ascending or Descending sort!");
        }
        else{
            handleResults(Main.qh.handleQuery(qString));
            //Line below only gets the GUI changes, and not the System.out which the line above does.
            //handleResults(Main.qh.getSet(qString));
        }
    }

    public void ascSet(){
        if(descBox.isSelected()){
            descBox.setSelected(false);
        }
    }
    public void descSet(){
        if (ascBox.isSelected()){
            ascBox.setSelected(false);
        }
    }

}
