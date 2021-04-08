import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * References used to help create this class:
 * https://www.youtube.com/watch?v=S3BDYd8CgBA
 * https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
 */

/**
 * THIS CLASS WAS ONLY MEANT TO BE RUN ONCE.
 * SINCE THE TEXT FILE HAS ALREADY BEEN CREATED THIS CLASS IS NO LONGER NECESSARY TO RUN THE MAIN FUNCTION.
 */

public class JsoupTest {
    //this block is used to initialize the class
    public static void main(String[] args) throws IOException {
        runAll(reader());
    }

    //Standard scanner to read a text file.
    public static Map reader(){
        Map<Integer,String> linkMap = new TreeMap<>();
        String links = "ml-latest-small/links.csv";
        File file = new File(links);
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();

            while(scanner.hasNextLine()){
                String value = scanner.nextLine();
                String[] linkArray = value.split(",");
                linkMap.put(Integer.parseInt(linkArray[0]),linkArray[1]);
            }
            scanner.close();
        }
        catch (FileNotFoundException f){
            f.printStackTrace();
        }

        //System.out.println(linkMap);
        return linkMap;
    }

    /**
     * This method runs the getDetails function for every iteration possible using the map returned from the reader() function.
     * In this function we create a Soundmix object from each line read with the scanner.
     * The objects are then added to an array.
     * This function also creates a textfile containing every soundmix from the array created.
     * @param map
     * @throws IOException
     */
    public static void runAll(Map<Integer, String> map) throws IOException {
        ArrayList<Soundmix> soundMixes = new ArrayList();
        try {
            for(Map.Entry<Integer, String> entry : map.entrySet()){
                Soundmix soundmix = new Soundmix(entry.getKey(), getDetails(entry.getValue()));
                System.out.println(soundmix.getMovieID());
                soundMixes.add(soundmix);
            }
        }
        catch (NullPointerException n){
            n.printStackTrace();
        }
        File file = new File("ml-latest-small/soundMixes.txt");
        FileWriter fileWriter = new FileWriter(file);
        for(Soundmix mix : soundMixes){
            fileWriter.write(mix.getMovieID() + "," + mix.getSoundMix() + System.getProperty( "line.separator" ));
        }
        fileWriter.close();
    }

    //This function pings Imdb using the identifier contained in the links dataset.
    public static String getDetails(String value){
        String sound = "";
        String url = "https://www.imdb.com/title/tt" +
                value +
                "/technical?ref_=tt_ql_dt_6";
        //Document doc = Jsoup.connect(url).get();
        //System.out.println(doc.getElementsByClass("dataTable"));
        try {
            Document doc = Jsoup.connect(url).get();
            Elements table = doc.getElementsByClass("dataTable");
            for(Element e : table.select("tr")){
                if(e.text().contains("Sound")){
                    sound = e.text().replace("Sound Mix", "");
                    //System.out.println(sound);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return sound;
    }
}
