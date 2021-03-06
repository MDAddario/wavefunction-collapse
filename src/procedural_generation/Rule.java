package procedural_generation;

import java.util.ArrayList;
import java.util.Random;

class Rule {

    private Tile      firstTile;
    private Tile      secondTile;
    private Direction direction;

    // Getter
    Tile      getFirstTile()  { return this.firstTile; }
    Tile      getSecondTile() { return this.secondTile; }
    Direction getDirection()  { return this.direction; }

    Rule(Tile firstTile, Tile secondTile, Direction direction) {
        this.firstTile  = firstTile;
        this.secondTile = secondTile;
        this.direction  = direction;
    }

    Rule(Type[][] board, int i, int j, Direction dir) {
        this(new Tile(board[i][j]), new Tile(board[i + dir.getDi()][j + dir.getDj()]), dir);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rule)
            return this.firstTile.equals(((Rule)obj).firstTile) &&
                    this.secondTile.equals(((Rule)obj).secondTile) &&
                    this.direction.equals(((Rule)obj).direction);
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return this.firstTile.toString() + this.direction.toString() + this.secondTile.toString();
    }

    // Scramble the order of a list of rules
    static ArrayList<Rule> scrambleRules(ArrayList<Rule> rules, Random random) {

        // Output list
        ArrayList<Rule> output = new ArrayList<>(rules.size());

        // Randomly select when to add
        for (int i : new IndexRandomizer(rules.size(), random))
            output.add(rules.get(i));

        return output;
    }
}
