import java.util.ArrayList;
import java.util.Random;

public class State {

    // Fields
    private ArrayList<Tile> tiles;
    private double          entropy;
    private Random          random;

    // Getter
    public double getEntropy() { return this.entropy; }

    // Constructor
    public State(ArrayList<Tile> tiles, Random random) {

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

        this.entropy = Math.log(sumA) - sumB / sumA;
    }

    // Remove a tile from the state
    public void removeTile(Tile tile) throws ContractionException {

        // Compute entropy if tile removed
        if (this.tiles.contains(tile)) {
            this.tiles.remove(tile);

            // Check for a contradiction
            if (this.tiles.size() == 0)
                throw new ContractionException("Contradiction achieved.\n");

            this.computeEntropy();
        }
    }

    // Collapse to state based off weights
    public void collapse() {

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
        throw new RuntimeException("Collapse() method unable to select a state.\n");
    }

    // Check if state is collapsed
    public boolean isCollapsed() {
        return this.tiles.size() == 1;
    }

    @Override
    public String toString() {
        return this.tiles.toString();
    }
}
