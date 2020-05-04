public class Rule {

    private Tile      firstTile;
    private Tile      secondTile;
    private Direction direction;

    public Rule(Tile firstTile, Tile secondTile, Direction direction) {
        this.firstTile  = firstTile;
        this.secondTile = secondTile;
        this.direction  = direction;
    }

    public Rule(char[][] board, int i, int j, Direction dir) {
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
    public String toString() {
        return this.firstTile.toString() + this.direction.toString() + this.secondTile.toString();
    }
}