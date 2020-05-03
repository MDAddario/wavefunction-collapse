public class Tile {

    // Attributes
    private char type;
    private double weight;

    // getters and setters
    public char getType() {return this.type; }
    public double getWeight() { return this.weight; }
    public void setType(char type) {this.type = type; }
    public void setWeight(double weight) {this.weight = weight; }

    // Constructors
    public Tile(char type, double weight) {
        this.type = type;
        this.weight = weight;
    }

    public Tile(char type) {
        this(type, 0.0);
    }
}
