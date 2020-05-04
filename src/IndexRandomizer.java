import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

public class IndexRandomizer implements Iterable<Integer>{

    // Attributes
    private int    maxIndex;
    private Random random;

    // Constructor
    public IndexRandomizer(int maxIndex, Random random) {
        this.maxIndex = maxIndex;
        this.random   = random;
    }

    @Override
    public IndexIterator iterator() {
        return new IndexIterator();
    }

    public static void main(String[] args) {

        // Create new randomizer
        Random random = new Random();

        // Show off our scrambler!
        int maxIndex = 20;
        for (int i : new IndexRandomizer(maxIndex, random))
            System.out.print(i + " ");
        System.out.println();
    }

    private class IndexIterator implements Iterator<Integer> {

        // Attributes
        private LinkedList<Integer> list;

        // Constructor
        private IndexIterator() {

            // Create the linked list
            this.list = new LinkedList<>();

            // Populate it
            for (int i = 0; i < IndexRandomizer.this.maxIndex; i++)
                this.list.add(i);
        }

        @Override
        public boolean hasNext() {
            return !this.list.isEmpty();
        }

        @Override
        public Integer next() {

            // Panic
            if (this.list.isEmpty())
                throw new NoSuchElementException("No more indices.");

            // Pick a random value
            int index = IndexRandomizer.this.random.nextInt(this.list.size());
            return this.list.remove(index);
        }
    }
}
