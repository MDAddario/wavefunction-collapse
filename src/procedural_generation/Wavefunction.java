package procedural_generation;

import java.util.ArrayList;
import java.util.Random;

public class Wavefunction {

    // Save sample board
    private Sample sample;

    // Dimensions of output
    private int height;
    private int width;

    // Number of times generation was attempted
    private int generationCount;

    // Random number handler
    private Random random;

    // Keep track of the superimposed states of tiles
    private State[][] states;

    // Getters
    public int       getHeight() { return this.height; }
    public int       getWidth()  { return this.width; }
    public State[][] getStates() { return this.states; }

    // Main
    public static void main(String[] args) {

        // Create wavefunction from default sample
        int height = 3;
        int width  = 3;
        Wavefunction phi = new Wavefunction(new Sample(), height, width);

        // Run the game
        boolean isDebug = false;
        phi.quantumLoop(isDebug);

        // Print sample and collapsed version
        phi.visualizeSample();
        phi.visualizePhi();
    }

    // Constructor
    public Wavefunction(Sample sample, int height, int width) {

        // Save the sample
        this.sample = sample;

        // Dimensions of new landscape
        this.height = height;
        this.width  = width;

        // Create random number generator
        this.random = new Random(System.currentTimeMillis());
    }

    // Create a fresh superposition of all states at all tiles
    private void freshSuperposition() {

        // Increment the generation number
        this.generationCount++;

        // Create states array
        this.states = new State[this.height][this.width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                this.states[i][j] = new State(this.sample.getTiles(), this.random);
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

        // Check all rules
        for (Rule antiRule : this.sample.getAntiRules())
            if (antiRule.isApplicable(pair, this)) {

                Pair targetPair = new Pair(pair, antiRule);
                try {
                    this.states[targetPair.i][targetPair.j].removeTile(antiRule.getSecondTile());

                } catch (CollapsedStateException ex) {
                    this.propagateAntiRules(targetPair);
                }
            }
    }

    // Performs the wavefunction collapse procedure until completion
    public void quantumLoop(boolean isDebug) {

        // Start fresh
        this.generationCount = 0;

        // Create a fresh superposition
        this.freshSuperposition();

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
        this.sample.visualize();
    }

    public void visualizePhi() {

        System.out.println("Visualizing phi:");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++)
                System.out.print(this.states[i][j].getTileZero());
            System.out.println();
        }
    }
}
