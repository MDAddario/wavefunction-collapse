public class Rule {

    private char firstType;
    private char secondType;
    private char relationship;

    public Rule(char firstType, char secondType, char relationship) {
        this.firstType    = firstType;
        this.secondType   = secondType;
        this.relationship = relationship;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rule)
            return this.firstType     == ((Rule)obj).firstType &&
                    this.secondType   == ((Rule)obj).secondType &&
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
            relation = " left of ";
        else if (this.relationship == 'R')
            relation = " right of ";
        else
            relation = " BROKEN ";
        return this.firstType + relation + this.secondType;
    }
}