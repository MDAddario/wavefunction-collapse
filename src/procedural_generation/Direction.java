package procedural_generation;

import java.util.ArrayList;

class Direction {

    // Attributes
    private int di;
    private int dj;

    // Getters
    int getDi() { return this.di; }
    int getDj() { return this.dj; }

    // Constructor
    Direction(int di, int dj) {
        this.di = di;
        this.dj = dj;
    }

    @Override
    public String toString() {
        return "(" + this.di + ", " + this.dj + ") ";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Direction)
            return this.di == ((Direction)obj).di &&
                    this.dj == ((Direction)obj).dj;
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // Create all possible directions within a radius
    static ArrayList<Direction> getAllDirections(double radius) {

        // Stability
        final double EPSILON = 0.0001;

        // Output array
        ArrayList<Direction> allDirections = new ArrayList<>();

        // Span the unit square
        for (int di = (int) Math.floor(-radius); di <= radius + EPSILON; di += 1)
            for (int dj = (int) Math.floor(-radius); dj <= radius + EPSILON; dj += 1) {

                // Funny case
                if (di == 0 && dj == 0)
                    continue;
                // Choose those within the circle
                if (Math.sqrt((double)di * di + dj * dj) < radius + EPSILON)
                    allDirections.add(new Direction(di, dj));
            }
        return allDirections;
    }

    // Check if the direction is possible from the given coordinates
    boolean isPossible(int i, int j, int height, int width) {
        return 0 <= i + this.di && i + this.di < height &&
                0 <= j + this.dj && j + this.dj < width;
    }

    boolean isPossible(Pair pair, int height, int width) {
        return isPossible(pair.i, pair.j, height, width);
    }

    // Main attraction
    public static void main(String[] args) {

        System.out.println(Direction.getAllDirections(1.0));
        System.out.println();
        System.out.println(Direction.getAllDirections(1.5));
        System.out.println();
        System.out.println(Direction.getAllDirections(2.0));
    }
}
