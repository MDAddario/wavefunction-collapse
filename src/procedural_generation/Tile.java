package procedural_generation;

import java.util.ArrayList;

class Tile {

    // Attributes
    private Type   type;
    private int    count;
    private double weight;

    // Getters
    Type   getType()   { return this.type; }
    double getWeight() { return this.weight; }

    // Constructors
    Tile(Type type, int count, double weight) {
        this.type   = type;
        this.count  = count;
        this.weight = weight;
    }

    // Default constructor
    Tile(Type type) {
        this(type, 1, 0.0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile)
            return this.type == ((Tile)obj).type;
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return type + " ";
    }

    // Increment the counter
    void incrementCount() {
        this.count++;
    }

    // Compute the weights for a set of tiles and counts
    static void computeWeights(ArrayList<Tile> tiles) {

        // Determine the total number of tiles
        int sum = 0;
        for (Tile tile : tiles)
            sum += tile.count;

        // Take care of odd strange issue
        if (sum == 0)
            throw new ArithmeticException("sum variable has value zero.");

        // Compute the weights
        for (Tile tile : tiles)
            tile.weight = ((double)tile.count) / sum;
    }

    // Construct an array list of the available types
    static ArrayList<Type> getTypes(ArrayList<Tile> tiles) {

        // Create the array list
        ArrayList<Type> list = new ArrayList<>();

        // Populate
        for (Tile tile : tiles)
            list.add(tile.type);

        return list;
    }

    // Construct an array list of the weights
    static ArrayList<Double> getWeights(ArrayList<Tile> tiles) {

        // Create the array list
        ArrayList<Double> list = new ArrayList<>();

        // Populate
        for (Tile tile : tiles)
            list.add(tile.weight);

        return list;
    }
}
