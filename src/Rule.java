public class Rule {

    private Tile firstTile;
    private Tile secondTile;
    private char relationship;

    public Rule(Tile firstTile, Tile secondTile, char relationship) {
        this.firstTile    = firstTile;
        this.secondTile   = secondTile;
        this.relationship = relationship;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rule)
            return this.firstTile.equals(((Rule)obj).firstTile) &&
                    this.secondTile.equals(((Rule)obj).secondTile) &&
                    this.relationship == ((Rule)obj).relationship;
        return false;
    }

    @Override
    public String toString() {
        String relation;
        if (this.relationship == 'U')
            relation = "is above ";
        else if (this.relationship == 'D')
            relation = "is below ";
        else if (this.relationship == 'L')
            relation = "left of ";
        else if (this.relationship == 'R')
            relation = "right of ";
        else
            relation = "BROKEN ";
        return this.firstTile + relation + this.secondTile;
    }
}