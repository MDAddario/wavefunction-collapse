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
    private State[][] states;

    public State[][] getStates() {return this.states; }

    // Main
    public static void main(String[] args) {

        // Create wavefunction from default sample
        Wavefunction phi = new Wavefunction(new Sample(), 2, 2);

        // Create a fresh superposition
        phi.createFreshSuperposition();

        // Print all states
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                System.out.println("Tile: " + i + " " + j);
                System.out.println(phi.getStates()[i][j]);
            }

        // Collapse state
        phi.getStates()[0][0].removeTile('L');
        phi.getStates()[1][1].collapseTo('L');

        // Print all states
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                System.out.println("Tile: " + i + " " + j);
                System.out.println(phi.getStates()[i][j]);
            }

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
        this.states = new State[this.height][this.width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                this.states[i][j] = new State(this.tiles, this.weights);
    }
}
