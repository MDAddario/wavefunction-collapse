import java.util.ArrayList;

public class Wavefunction {

    // Number of times generation was attempted
    private int generationCount;

    // Dimensions of output
    private int height;
    private int width;

    // Attributes to steal from the sample
    private ArrayList<Character> tiles;
    private ArrayList<Double> weights;
    private ArrayList<Rule> antiRules;

    // Keep track of the superposed states
    private ArrayList<Character>[][] states;

    // Constructor
    public Wavefunction(Sample sample, int height, int width) {

        // Take all the elements from the sample
        this.tiles = sample.getTiles();
        this.weights = sample.getWeights();
        this.antiRules = sample.getAntiRules();

        // Dimensions of new landscape
        this.height = height;
        this.width = width;

        // Start fresh
        this.generationCount = 0;
    }

    private void createFreshSuperposition() {

        // Increment the generation number
        this.generationCount++;

        // Create states array
        this.states = new ArrayList<Character>[this.height][this.width];

        // Begin with all states available
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                this.states[i][j] = this.tiles.clone();
    }

}
