import java.util.ArrayList;
import java.util.Random;

public class State {

    // Fields
    private ArrayList<Character> tiles;
    private ArrayList<Character> originalTiles;
    private ArrayList<Double> weights;
    private double entropy;

    // getter
    public double getEntropy() {return this.entropy; }

    // Constructor
    public State(ArrayList<Character> tiles, ArrayList<Double> weights) {

        // Store attributes
        this.tiles = new ArrayList<>(tiles);
        this.originalTiles = tiles;
        this.weights = weights;

        // Compute entropy
        this.computeEntropy();
    }

    // Calculate the entropy
    private void computeEntropy() {
        this.entropy = 0.0;
    }

    // Remove a state from the list
    public void removeTile(Character tile) {
        if (this.tiles.contains((Object)tile)) {
            this.tiles.remove((Object) tile);
            this.computeEntropy();
        }
    }

    // Collapse to single state
    public void collapseTo(Character tile) {

        // Ensure state is available
        if (!this.tiles.contains((Object)tile))
            throw new RuntimeException("State does not contain the given tile.\n");

        // Create new array list with only that tile
        this.tiles = new ArrayList<>();
        this.tiles.add(tile);
    }

    // Collapse to state based off weights
    public void collapse() {

        Random rand = new Random();

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
