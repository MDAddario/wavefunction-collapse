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
    private ArrayList<ArrayList<ArrayList<Character>>> states;

    // Main
    public static void main(String[] args) {

    }

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

    // Create a fresh superposition of all states at all tiles
    private void createFreshSuperposition() {

        // Increment the generation number
        this.generationCount++;

        // Create states array
        this.states = new ArrayList<>();
        for (int i = 0; i < height; i++) {

            this.states.add(new ArrayList<>());
            for (int j = 0; j < width; j++)
                this.states.get(i).add(new ArrayList<>(this.tiles));
        }
    }
}
