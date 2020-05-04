package procedural_generation;

class Pair {

    int i;
    int j;

    Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Pair(Pair pair, Rule rule) {
        this.i = pair.i + rule.getDirection().getDi();
        this.j = pair.j + rule.getDirection().getDj();
    }
}