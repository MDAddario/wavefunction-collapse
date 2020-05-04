package procedural_generation;

import java.util.ArrayList;
import java.util.Random;

class Wavefunction {

    // Save sample board
    private Sample sample;

    // Dimensions of output
    private int height;
    private int width;

    // Random number handler
    private Random random;

    // Keep track of the superimposed states of tiles
    private State[][] states;

    // Default arguments
    private static final String DEFAULT_OUTPUT = "assets/coast_output.png";
    private static final String DEFAULT_FORMAT = "png";

    // Main
    public static void main(String[] args) {

        // Create wavefunction from default sample
        int height = 10;
        int width = 10;
        Wavefunction phi = new Wavefunction(new Sample(), height, width);

        // Visualize the sample
        phi.visualizeSample();

        // Run the simulations
        phi.quantumLoop();

        // Print sample and collapsed version
        phi.visualizePhi();

        // Save collapsed version to file
        phi.saveImage();
    }

    // Constructor
    Wavefunction(Sample sample, int height, int width) {

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

        // Create states array
        this.states = new State[this.height][this.width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                this.states[i][j] = new State(this.sample.getTiles(), this.random);
    }

    // Select the lowest entropy state to be collapsed
    private Pair isolateLowestEntropy() {

        // Constants necessary for min entropy search
        final double MAX_ENTROPY = 9999.0;
        final double EPSILON     = 0.0001;

        // Determine the smallest entropy over the board
        double minEntropy = MAX_ENTROPY;

        for (State[] row : this.states)
            for (State state : row)
                if (state.isNotCollapsed() && state.getEntropy() < minEntropy)
                    minEntropy = state.getEntropy();

        // Determine all states with this entropy
        ArrayList<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (this.states[i][j].isNotCollapsed() &&
                        Math.abs(this.states[i][j].getEntropy() - minEntropy) < EPSILON)
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
        for (Rule antiRule : Rule.scrambleRules(this.sample.getAntiRules(), this.random))
            if (antiRule.getDirection().isPossible(pair, this.height, this.width) &&
                    this.states[pair.i][pair.j].getTileZero().equals(antiRule.getFirstTile())) {

                Pair targetPair = new Pair(pair, antiRule);
                try {
                    this.states[targetPair.i][targetPair.j].removeTile(antiRule.getSecondTile());

                } catch (CollapsedStateException ex) {
                    this.propagateAntiRules(targetPair);
                }
            }
    }

    // Performs the wavefunction collapse procedure until completion
    void quantumLoop() {

        // Create a fresh superposition
        this.freshSuperposition();

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
    }

    // Extract the types
    private Type[][] extractTypes() {

        // Allocate output
        Type[][] types = new Type[this.states.length][this.states[0].length];

        // Snatch the types
        for (int i = 0; i < this.states.length; i++)
            for (int j = 0; j < this.states[0].length; j++)
                types[i][j] = this.states[i][j].getTileZero().getType();

        return types;
    }

    // Save collapsed state to an image
    void saveImage(String output, String format) {

        try {
            this.sample.getImgProc().constructImageFromTypes(this.extractTypes(), output, format);

        } catch (Exception ex) {
            System.out.println("Could not save image.");
        }
    }

    void saveImage() {
        this.saveImage(DEFAULT_OUTPUT, DEFAULT_FORMAT);
    }

    void visualizeSample() {
        this.sample.visualize();
    }

    void visualizePhi() {

        System.out.println("Visualizing phi:");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++)
                System.out.print(this.states[i][j].getTileZero());
            System.out.println();
        }
    }
}
