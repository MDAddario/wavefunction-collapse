package procedural_generation;

import java.util.ArrayList;

public class Sample {

    // Relevant information to be passed to wavefunction
    private ArrayList<Tile> tiles;
    private ArrayList<Rule> antiRules;

    // Save the board so we can look at it
    private Type[][] board;

    // Getters
    ArrayList<Tile> getTiles()     { return this.tiles; }
    ArrayList<Rule> getAntiRules() { return this.antiRules; }

    // The main attraction
    public static void main(String[] args) {

        // Create the default sample
        Sample sample = new Sample();

        // Visualize the board
        sample.visualize();

        // Print the sample information
        System.out.println(sample);
    }

    // Default constructor
    public Sample() {
        this(DEFAULT_BOARD, 1.0);
    }

    // Constructor
    public Sample(String filename, double radius) {

        // Extract the type board from file
        this.extractTypes(filename);

        // Count the tiles
        this.countTiles();

        // Determine the rules
        this.determineRules(radius);
    }

    private void extractTypes(String filename) {
        this.board = null;
    }

    private void countTiles() {

        // Determine tile types and count the occurrences
        this.tiles = new ArrayList<>();

        // Parse the input board
        for (Type[] row : this.board)
            for (Type type : row) {

                // Create tile object
                Tile tile = new Tile(type);

                // If tile does not exist
                if (!this.tiles.contains(tile)) {
                    this.tiles.add(tile);

                // If tile does already exist
                } else {
                    int index = this.tiles.indexOf(tile);
                    this.tiles.get(index).incrementCount();
                }
            }

        // Compute the weights
        Tile.computeWeights(this.tiles);
    }

    private void determineRules(double radius) {

        // Determine the rules
        ArrayList<Direction> allDirs = Direction.getAllDirections(radius);
        ArrayList<Rule> rules = new ArrayList<>();
        int height = this.board.length;
        int width  = this.board[0].length;

        // Span all board tiles
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)

                // Span all possible rules
                for (Direction dir : allDirs)
                    if (dir.isPossible(i, j, height, width)) {
                        Rule rule = new Rule(this.board, i, j, dir);
                        if (!rules.contains(rule))
                            rules.add(rule);
                    }

        // Determine anti rules
        this.determineAntiRules(allDirs, rules);
    }

    private void determineAntiRules(ArrayList<Direction> allDirs, ArrayList<Rule> rules) {
        // Determine anti rules
        this.antiRules = new ArrayList<>();

        for (Tile firstTile : this.tiles)
            for (Tile secondTile : this.tiles)
                for (Direction dir : allDirs) {

                    // Construct rule
                    Rule rule = new Rule(firstTile, secondTile, dir);

                    // Add it to anti rules if they don't exist
                    if (!rules.contains(rule))
                        this.antiRules.add(rule);
                }
    }

    @Override
    public String toString() {

        String lineOne = "Type list: \n\t"   + Tile.getTypes(this.tiles)   + "\n";
        String lineTwo = "Weight list: \n\t" + Tile.getWeights(this.tiles) + "\n";
        StringBuilder lineThree = new StringBuilder("Anti-rule list: " + "\n");

        for (Rule rule : this.antiRules) {
            lineThree.append("\t");
            lineThree.append(rule.toString());
            lineThree.append("\n");
        }
        return lineOne + lineTwo + lineThree;
    }

    // Visualize the board
    public void visualize() {
        System.out.println("Visualizing sample:");
        for (Type[] row : this.board) {
            for (Type type : row)
                System.out.print(type + " ");
            System.out.println();
        }
    }
}
