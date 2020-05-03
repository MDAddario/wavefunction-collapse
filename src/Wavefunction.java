import java.util.ArrayList;

public class Wavefunction {

    // Number of times generation was attempted
    private int generationCount;
    private int height;
    private int width;

    // Dimensions of output

    // Attributes to steal from the sample
    private ArrayList<Character> tiles;
    private ArrayList<Double> weights;
    private ArrayList<Rule> antiRules;

    // Constructor
    public Wavefunction(Sample sample, int height, int width) {

        // Take all the elements from the sample
        this.tiles = sample.getTiles();
        this.weights = sample.getWeights();
        this.antiRules = sample.getAntiRules();

        // Dimensions of new landscape
    }

}
