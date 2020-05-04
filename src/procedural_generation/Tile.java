package procedural_generation;

import java.util.ArrayList;

public class Tile {

    // Attributes
    private char   type;
    private int    count;
    private double weight;

    // Getters
    public double getWeight() { return this.weight; }

    // Constructors
    public Tile(char type, int count, double weight) {
        this.type   = type;
        this.count  = count;
        this.weight = weight;
    }

    // Default constructor
    public Tile(char type) {
        this(type, 1, 0.0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile)
            return this.type == ((Tile)obj).type;
        return false;
    }

    @Override
    public String toString() {
        return type + " ";
    }

    // Increment the counter
    public void incrementCount() {
        this.count++;
    }

    // Compute the weights for a set of tiles and counts
    public static void computeWeights(ArrayList<Tile> tiles) {

        // Determine the total number of tiles
        int sum = 0;
        for (Tile tile : tiles)
            sum += tile.count;

        // Compute the weights
        for (Tile tile : tiles)
            tile.weight = ((double)tile.count) / sum;
    }

    // Construct an array list of the available types
    public static ArrayList<Character> getTypes(ArrayList<Tile> tiles) {

        // Create the array list
        ArrayList<Character> list = new ArrayList<>();

        // Populate
        for (Tile tile : tiles)
            list.add(tile.type);

        return list;
    }

    // Construct an array list of the weights
    public static ArrayList<Double> getWeights(ArrayList<Tile> tiles) {

        // Create the array list
        ArrayList<Double> list = new ArrayList<>();

        // Populate
        for (Tile tile : tiles)
            list.add(tile.weight);

        return list;
    }
}
