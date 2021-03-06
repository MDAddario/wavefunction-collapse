package procedural_generation;

import java.util.ArrayList;
import java.util.Random;

class State {

    // Fields
    private ArrayList<Tile> tiles;
    private double          entropy;
    private Random          random;

    // Getter
    Tile   getTileZero() { return this.tiles.get(0); }
    double getEntropy()  { return this.entropy; }

    // Constructor
    State(ArrayList<Tile> tiles, Random random) {

        // Store attributes
        this.tiles  = new ArrayList<>(tiles);
        this.random = random;

        // Compute entropy
        this.computeEntropy();
    }

    // Calculate the entropy
    private void computeEntropy() {

        // shannon_entropy_for_square = log(sum(weight)) - (sum(weight * log(weight)) / sum(weight))
        double sumA = 0.0;
        double sumB = 0.0;

        for (Tile tile : this.tiles) {
            double weight = tile.getWeight();
            sumA += weight;
            sumB += weight * Math.log(weight);
        }

        if (sumA == 0.0)
            throw new ArithmeticException("Entropy is bad.");

        this.entropy = Math.log(sumA) - sumB / sumA;
    }

    // Remove a tile from the state
    void removeTile(Tile tile) throws CollapsedStateException, ContractionException {

        // Compute entropy if tile removed
        if (this.tiles.contains(tile)) {
            this.tiles.remove(tile);

            // Check for newly collapsed state
            if (this.tiles.size() == 1)
                throw new CollapsedStateException("State has reached collapsed state.\n");

            // Check for a contradiction
            if (this.tiles.isEmpty())
                throw new ContractionException("Contradiction achieved.\n");

            // Else we need to know the entropy
            this.computeEntropy();
        }
    }

    // Collapse to state based off weights
    void collapse() {

        // Determine the number of all the weights (between 0 and 1)
        double weightSum = 0.0;
        for (Tile tile : this.tiles)
            weightSum += tile.getWeight();

        // Generate a random number between zero and weightSum
        double sampled = this.random.nextDouble() * weightSum;

        // Determine what bracket the sampled number falls into
        for (Tile tile : this.tiles) {

            // Not it
            if (sampled > tile.getWeight()) {
                sampled -= tile.getWeight();

            // Found it
            } else {

                // Create new array list with only that tile
                this.tiles = new ArrayList<>();
                this.tiles.add(tile);
                return;
            }
        }
        throw new ArithmeticException("Collapse() method unable to select a state.\n");
    }

    // Check if state is still in a superposition
    boolean isNotCollapsed() {
        return this.tiles.size() != 1;
    }

    @Override
    public String toString() {
        return this.tiles.toString();
    }
}
