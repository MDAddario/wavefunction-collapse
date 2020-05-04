import java.util.ArrayList;
import java.util.Random;

public class Wavefunction {

    // Attributes to steal from the sample
    private ArrayList<Tile> tiles;
    private ArrayList<Rule> antiUpRules;
    private ArrayList<Rule> antiDownRules;
    private ArrayList<Rule> antiLeftRules;
    private ArrayList<Rule> antiRightRules;

    // Dimensions of output
    private int height;
    private int width;

    // Number of times generation was attempted
    private int generationCount;

    // Random number handler
    private Random random;

    // Keep track of the superimposed states of tiles
    private State[][] states;

    // Getter for debug purposes
    public State[][] getStates() { return this.states; }

    // Main
    public static void main(String[] args) {

        /*
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
        */
    }

    // Constructor
    public Wavefunction(Sample sample, int height, int width) {

        // Take all the elements from the sample
        this.tiles = sample.getTiles();
        ArrayList<Rule> antiRules = sample.getAntiRules();
        this.antiUpRules    = Rule.upRules(antiRules);
        this.antiDownRules  = Rule.downRules(antiRules);
        this.antiLeftRules  = Rule.leftRules(antiRules);
        this.antiRightRules = Rule.rightRules(antiRules);

        // Dimensions of new landscape
        this.height = height;
        this.width = width;

        // Start fresh
        this.generationCount = 0;

        // Create random number generator
        this.random = new Random(System.currentTimeMillis());

        // Create a fresh superposition
        this.freshSuperposition();
    }

    // Create a fresh superposition of all states at all tiles
    private void freshSuperposition() {

        // Increment the generation number
        this.generationCount++;

        // Create states array
        this.states = new State[this.height][this.width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                this.states[i][j] = new State(this.tiles, this.random);
    }

    // Select the lowest entropy state to be collapsed
    private Pair isolateLowestEntropy() {

        // Determine the smallest entropy over the board
        double min_entropy = 99999999.0;
        double epsilon     = 0.000001;

        for (State[] row : this.states)
            for (State state : row)
                if (!state.isCollapsed())
                    if (state.getEntropy() < min_entropy)
                        min_entropy = state.getEntropy();

        // Determine all states with this entropy
        ArrayList<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (Math.abs(this.states[i][j].getEntropy() - min_entropy) < epsilon)
                    pairs.add(new Pair(i, j));

        // All states have been collapsed
        if (pairs.isEmpty())
            return null;

        // Pick a random state from the list
        int index = this.random.nextInt(pairs.size());
        return pairs.get(index);
    }

    // Propagate the anti rules to the neighbors
    private void propagateAntiRules(Pair pair) throws ContractionException {

    }

    // Performs the wavefunction collapse procedure until completion
    public void quantumLoop() {

        while(true) {

            // Find the pair of lowest entropy
            Pair pair = this.isolateLowestEntropy();

            // Check for procedure completed
            if (pair == null)
                break;

            // Collapse the state
            this.states[pair.i][pair.j].collapse();

            // Propagate anti rules
            try {
                this.propagateAntiRules(pair);

            } catch (ContractionException ex) {

                // Contradiction, must restart
                this.freshSuperposition();
            }
        }
        // Print the success
        System.out.println("Wavefunction collapse complete after " + this.generationCount + " iterations.\n");
    }
}
