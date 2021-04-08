/**
 * Bean class to generate sound mix objects.
 */
public class Soundmix {
    private int movieID;
    private String soundMix;

    public Soundmix(int movieID, String soundMix) {
        this.movieID = movieID;
        this.soundMix = soundMix;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getSoundMix() {
        return soundMix;
    }

    public void setSoundMix(String soundMix) {
        this.soundMix = soundMix;
    }
}
