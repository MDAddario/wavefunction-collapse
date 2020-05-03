import java.util.ArrayList;

public class Sample {

    // Relevant information to be passed to wavefunction
    private ArrayList<Tile> tiles;
    private ArrayList<Rule> antiRules;

    // Getters
    public ArrayList<Tile> getTiles()     { return this.tiles; }
    public ArrayList<Rule> getAntiRules() { return this.antiRules; }

    // Default board
    private static char[][] DEFAULT_BOARD = {{'L', 'L', 'L', 'L', 'L', 'L'},
                                             {'L', 'L', 'L', 'C', 'C', 'L'},
                                             {'L', 'L', 'C', 'S', 'S', 'C'},
                                             {'L', 'C', 'S', 'S', 'S', 'S'},
                                             {'C', 'S', 'S', 'S', 'S', 'S'}};

    // The main attraction
    public static void main(String[] args) {

        // Create the default sample
        Sample sample = new Sample();

        // Print the sample information
        System.out.println(sample);
    }

    // Default constructor
    public Sample() {
        this(DEFAULT_BOARD);
    }

    // Constructor
    public Sample(char[][] board) {

        // Determine tile types and count the occurrences
        this.tiles = new ArrayList<>();

        // Parse the input board
        for (char[] row : board)
            for (char type : row) {

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

        // Determine the rules
        ArrayList<Rule> rules = new ArrayList<>();
        int height = board.length;
        int width  = board[0].length;

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {

                // Check downward rule
                if (i != 0) {
                    Rule rule = new Rule(board[i][j], board[i-1][j], 'D');
                    if (!rules.contains(rule))
                        rules.add(rule);
                }
                // Check upward rule
                if (i != height-1) {
                    Rule rule = new Rule(board[i][j], board[i+1][j], 'U');
                    if (!rules.contains(rule))
                        rules.add(rule);
                }
                // Check rightward rule
                if (j != 0) {
                    Rule rule = new Rule(board[i][j], board[i][j-1], 'R');
                    if (!rules.contains(rule))
                        rules.add(rule);
                }
                // Check leftward rule
                if (j != width-1) {
                    Rule rule = new Rule(board[i][j], board[i][j+1], 'L');
                    if (!rules.contains(rule))
                        rules.add(rule);
                }
            }

        // Determine anti rules
        this.antiRules = new ArrayList<>();
        char[] relationships = {'U', 'D', 'L', 'R'};
        ArrayList<Character> types = Tile.getTypes(this.tiles);

        for (char firstType : types)
            for (char secondType : types)
                for (char relationship : relationships) {

                    // Construct rule
                    Rule rule = new Rule(firstType, secondType, relationship);

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
}
