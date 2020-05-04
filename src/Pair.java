public class Pair {

    public int i;
    public int j;

    public Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Pair down() {
        return new Pair(this.i-1, this.j);
    }

    public Pair up() {
        return new Pair(this.i+1, this.j);
    }

    public Pair left() {
        return new Pair(this.i, this.j-1);
    }

    public Pair right() {
        return new Pair(this.i, this.j+1);
    }
}