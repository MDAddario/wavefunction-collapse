package procedural_generation;

public class Pair {

    public int i;
    public int j;

    public Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Pair(Pair pair, Rule rule) {
        this.i = pair.i + rule.getDirection().getDi();
        this.j = pair.j + rule.getDirection().getDj();
    }
}