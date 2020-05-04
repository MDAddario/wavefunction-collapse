package procedural_generation;

import java.util.ArrayList;

class Sample {

    // Relevant information to be passed to wavefunction
    private ArrayList<Tile> tiles;
    private ArrayList<Rule> antiRules;
    private ImageProcessing imgProc;

    // Have a default input file
    private static final String DEFAULT_INPUT = "assets/coast_sample.png";

    // Getters
    ArrayList<Tile> getTiles()     { return this.tiles; }
    ArrayList<Rule> getAntiRules() { return this.antiRules; }
    ImageProcessing getImgProc()   { return this.imgProc; }

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
    Sample() {
        this(DEFAULT_INPUT, 1.0);
    }

    // Constructor
    Sample(String input, double radius) {

        // Extract the type board from file
        try {
            this.imgProc = new ImageProcessing(input);

        } catch (Exception e) {
            System.out.println("Image could not be loaded.");
        }

        // Count the tiles
        this.countTiles();

        // Determine the rules
        this.determineRules(radius);
    }

    private void countTiles() {

        // Determine tile types and count the occurrences
        this.tiles = new ArrayList<>();

        // Parse the input board
        for (Type[] row : this.imgProc.getTypes())
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
        int height = this.imgProc.getTypes().length;
        int width  = this.imgProc.getTypes()[0].length;

        // Span all board tiles
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)

                // Span all possible rules
                for (Direction dir : allDirs)
                    if (dir.isPossible(i, j, height, width)) {
                        Rule rule = new Rule(this.imgProc.getTypes(), i, j, dir);
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
    void visualize() {
        System.out.println("Visualizing sample:");
        for (Type[] row : this.imgProc.getTypes()) {
            for (Type type : row)
                System.out.print(type + " ");
            System.out.println();
        }
    }
}
