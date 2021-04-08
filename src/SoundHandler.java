import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class used to collect soundmixes generated in the Jsoup Class
 * This class sends the sounds array to the ontology
 */

public class SoundHandler {
    ArrayList<Soundmix> mixes;
    ArrayList<String> sounds;

    //Constructor that when object from class is instantiated will execute the fillArray() function
    public SoundHandler() throws FileNotFoundException {
        fillArray();
    }

    //Reads the soundmixes text file and adds them to the array mixes.
    public void fillArray() throws FileNotFoundException {
        mixes = new ArrayList<>();
        sounds = new ArrayList<>();

        //CHANGE THIS STRING WITH THE FULL PATH FROM ML-LATEST-SMALL/SOUNDMIXES.TXT
        File file = new File("ml-latest-small/soundMixes.txt");
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        while(scanner.hasNextLine()){
            String value = scanner.nextLine();
            try{
                String[] stringArray = value.split(",");
                Soundmix soundmix = new Soundmix(Integer.parseInt(stringArray[0]),stringArray[1]);
                mixes.add(soundmix);
            }
            catch (ArrayIndexOutOfBoundsException e){

            }
        }
        scanner.close();

        //Block of code to get an array containing only unique sound mixes
        for(Soundmix mix : mixes){
            try{
                String[] stringArray = mix.getSoundMix().split("\\|");
                for(String s : stringArray){
                    if(!sounds.contains(s)){
                        sounds.add(s);
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException e){

            }
        }
    }
}
