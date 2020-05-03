import java.util.ArrayList;

public class Tile {

    // Attributes
    private char   type;
    private double weight;

    // Getters and setters
    public char   getType  ()              { return this.type; }
    public double getWeight()              { return this.weight; }
    public void   setType  (char type)     { this.type = type; }
    public void   setWeight(double weight) { this.weight = weight; }

    // Constructors
    public Tile(char type, double weight) {
        this.type   = type;
        this.weight = weight;
    }

    // Default constructor
    public Tile(char type) {
        this(type, 0.0);
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

    // Compute the weights for a set of tiles and counts
    public static void computeWeights(ArrayList<Tile> tiles, ArrayList<Integer> counts, int numTiles) {

        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).weight = ((double)counts.get(i)) / numTiles;
    }
}
