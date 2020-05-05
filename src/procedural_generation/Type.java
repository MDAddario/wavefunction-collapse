package procedural_generation;

class Type {

    // Only attribute
    int value;

    int getValue() { return this.value; }

    Type(int type) {
        this.value = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type)
            return this.value == ((Type)obj).value;
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return this.value + " ";
    }

    static Type getFirstType() {
        return new Type(0);
    }

    Type getNextType() {
        return new Type(this.value + 1);
    }
}
