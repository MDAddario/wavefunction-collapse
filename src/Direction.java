import java.util.ArrayList;

public class Direction {

    // Attributes
    private int di;
    private int dj;

    // Constructor
    public Direction(int di, int dj) {
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

    // Create all possible directions within a radius
    public static ArrayList<Direction> getAllDirections(double radius) {

        // Stability
        double EPSILON = 0.0001;

        // Output array
        ArrayList<Direction> allDirections = new ArrayList<>();

        // Span the unit square
        for (int di = (int)Math.floor(-radius); di <= radius + EPSILON; di += 1)
            for (int dj = (int)Math.floor(-radius); dj <= radius + EPSILON; dj += 1)
                if (Math.sqrt(di * di + dj * dj) < radius + EPSILON)
                    allDirections.add(new Direction(di, dj));

        return allDirections;
    }

    // Check if the direction is possible from the given coordinates
    public boolean isPossible(Direction dir, int i, int j, int height, int width) {
        return 0 <= i + dir.di && i + dir.di < height &&
                0 <= j + dir.dj && j + dir.dj < width;
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
