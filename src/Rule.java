public class Rule {

    private char firstTile;
    private char secondTile;
    private char relationship;

    public Rule(char firstTile, char secondTile, char relationship) {
        this.firstTile = firstTile;
        this.secondTile = secondTile;
        this.relationship = relationship;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rule)
            return this.firstTile == ((Rule)obj).firstTile &&
                    this.secondTile == ((Rule)obj).secondTile &&
                    this.relationship == ((Rule)obj).relationship;
        return false;
    }

    @Override
    public String toString() {
        String relation;
        if (this.relationship == 'U')
            relation = " is above ";
        else if (this.relationship == 'D')
            relation = " is below ";
        else if (this.relationship == 'L')
            relation = " is left of ";
        else if (this.relationship == 'R')
            relation = " is right of ";
        else
            relation = " is broken w.r.t. ";
        return this.firstTile + relation + this.secondTile;
    }
}