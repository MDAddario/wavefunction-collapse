public class Rule {

    private Tile      firstTile;
    private Tile      secondTile;
    private Direction direction;

    // Getter
    public Tile      getSecondTile() { return this.secondTile; }
    public Direction getDirection()  { return this.direction; }

    public Rule(Tile firstTile, Tile secondTile, Direction direction) {
        this.firstTile  = firstTile;
        this.secondTile = secondTile;
        this.direction  = direction;
    }

    public Rule(char[][] board, int i, int j, Direction dir) {
        this(new Tile(board[i][j]), new Tile(board[i + dir.getDi()][j + dir.getDj()]), dir);
    }

    public boolean isApplicable(Pair pair, Wavefunction psi) {
        if (this.direction.isPossible(pair.i, pair.j, psi.getHeight(), psi.getWidth()))
            return psi.getStates()[pair.i][pair.j].getTileZero().equals(this.firstTile);
        return false;
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