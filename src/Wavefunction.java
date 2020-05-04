import java.util.ArrayList;
import java.util.Random;

public class Wavefunction {

    // Save sample board
    private char[][] board;

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

    // Main
    public static void main(String[] args) {

        // Create wavefunction from default sample
        int height = 8;
        int width  = 8;
        Wavefunction phi = new Wavefunction(new Sample(), height, width);

        // Run the game
        boolean isDebug = true;
        phi.quantumLoop(isDebug);

        // Print sample and collapsed version
        phi.visualizeSample();
        phi.visualizePhi();
    }

    // Constructor
    public Wavefunction(Sample sample, int height, int width) {

        // Save the board
        this.board = sample.getBoard();

        // Take all the elements from the sample
        this.tiles = sample.getTiles();
        ArrayList<Rule> antiRules = sample.getAntiRules();
        this.antiUpRules    = Rule.upRules(antiRules);
        this.antiDownRules  = Rule.downRules(antiRules);
        this.antiLeftRules  = Rule.leftRules(antiRules);
        this.antiRightRules = Rule.rightRules(antiRules);

        // Dimensions of new landscape
        this.height = height;
        this.width  = width;

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

        // Constants necessary for min entropy search
        double MAX_ENTROPY = 9999.0;
        double EPSILON     = 0.0001;

        // Determine the smallest entropy over the board
        double min_entropy = MAX_ENTROPY;

        for (State[] row : this.states)
            for (State state : row)
                if (!state.isCollapsed())
                    if (state.getEntropy() < min_entropy)
                        min_entropy = state.getEntropy();

        // Determine all states with this entropy
        ArrayList<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (!this.states[i][j].isCollapsed())
                    if (Math.abs(this.states[i][j].getEntropy() - min_entropy) < EPSILON)
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

        // Check downward rule
        if (pair.i != 0) {
            for (Rule antiRule : this.antiDownRules)
                if (this.states[pair.i][pair.j].getTileZero().equals(antiRule.getFirstTile())) {
                    try {
                        this.states[pair.i-1][pair.j].removeTile(antiRule.getSecondTile());

                    } catch (CollapsedStateException ex) {
                        this.propagateAntiRules(pair.down());
                    }
                }
        }
        // Check upward rule
        if (pair.i != this.height-1) {
            for (Rule antiRule : this.antiUpRules)
                if (this.states[pair.i][pair.j].getTileZero().equals(antiRule.getFirstTile())) {
                    try {
                        this.states[pair.i+1][pair.j].removeTile(antiRule.getSecondTile());

                    } catch (CollapsedStateException ex) {
                        this.propagateAntiRules(pair.up());
                    }
                }
        }
        // Check rightward rule
        if (pair.j != 0) {
            for (Rule antiRule : this.antiLeftRules)
                if (this.states[pair.i][pair.j].getTileZero().equals(antiRule.getFirstTile())) {
                    try {
                        this.states[pair.i][pair.j-1].removeTile(antiRule.getSecondTile());

                    } catch (CollapsedStateException ex) {
                        this.propagateAntiRules(pair.left());
                    }
                }
        }
        // Check leftward rule
        if (pair.j != this.width-1) {
            for (Rule antiRule : this.antiRightRules)
                if (this.states[pair.i][pair.j].getTileZero().equals(antiRule.getFirstTile())) {
                    try {
                        this.states[pair.i][pair.j+1].removeTile(antiRule.getSecondTile());

                    } catch (CollapsedStateException ex) {
                        this.propagateAntiRules(pair.right());
                    }
                }
        }
    }

    // Performs the wavefunction collapse procedure until completion
    public void quantumLoop(boolean isDebug) {

        while(true) {

            if (isDebug) {
                System.out.println("Start of the loop.");
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        System.out.print(this.states[i][j] + " ");
                    }
                    System.out.println();
                }
            }

            // Find the pair of lowest entropy
            Pair pair = this.isolateLowestEntropy();

            if (isDebug) {
                System.out.println("Entropies.");
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        System.out.print(this.states[i][j].getEntropy() + " ");
                    }
                    System.out.println();
                }
            }

            // Check for procedure completed
            if (pair == null)
                break;

            if (isDebug) {
                System.out.println("Lowest entropy pair (i, j).");
                System.out.println("(" + pair.i + ", " + pair.j + ")");
            }

            // Collapse the state
            this.states[pair.i][pair.j].collapse();

            if (isDebug) {
                System.out.println("State collapsed.");
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        System.out.print(this.states[i][j] + " ");
                    }
                    System.out.println();
                }
            }

            if (isDebug) {
                System.out.println("isCollapsed()?");
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        System.out.print(this.states[i][j].isCollapsed() + " ");
                    }
                    System.out.println();
                }
            }

            // Propagate anti rules
            try {
                this.propagateAntiRules(pair);

            } catch (ContractionException ex) {

                // Contradiction, must restart
                this.freshSuperposition();

                if (isDebug) {

                    System.out.println("==FRESH SUPERPOSITION==.");
                }
            }

            if (isDebug) {
                System.out.println("Signal propagated.");
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        System.out.print(this.states[i][j] + " ");
                    }
                    System.out.println();
                }
            }

            if (isDebug) {
                System.out.println("isCollapsed()?");
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        System.out.print(this.states[i][j].isCollapsed() + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        // Print the success
        if (isDebug)
            System.out.println("Wavefunction collapse complete after " + this.generationCount + " iterations.\n");
    }

    public void visualizeSample() {

        System.out.println("Visualizing sample.");
        for (char[] row : this.board) {
            for (char c : row)
                System.out.print(c + " ");
            System.out.println();
        }
    }

    public void visualizePhi() {

        System.out.println("Visualizing sample.");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++)
                System.out.print(this.states[i][j].getTileZero());
            System.out.println();
        }
    }
}
