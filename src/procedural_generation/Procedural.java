package procedural_generation;

public class Procedural {

    private Wavefunction phi;

    public static void main() {

        // Set file names
        String input  = "assets/flower_sample.png";
        String output = "assets/flower_output.png";
        String format = "png";

        // Set simulation parameters
        double radius = 1.0;
        int    height = 12;
        int    width  = 12;

        // Generate
        Procedural proc = new Procedural(input, radius, height, width);
        proc.generate();
        proc.saveImage(output, format);
        int[][] blocks = proc.getIntegers();

        // Optional
        proc.setDimensions(10, 10);
        proc.generate();
        proc.saveImage(output, format);
        int[][] newBlocks = proc.getIntegers();
    }

    public Procedural(String input, double radius, int height, int width) {

        // Create the sample object
        Sample sample = new Sample(input, radius);

        // Create the wavefunction
        this.phi = new Wavefunction(sample, height, width);
    }

    public void setDimensions(int height, int width) {
        this.phi.setDimensions(height, width);
    }

    public void generate() {
        this.phi.quantumLoop();
    }

    public int[][] getIntegers() {
        return this.phi.extractIntegers();
    }

    public void saveImage(String output, String format) {
        phi.saveImage(output, format);
    }
}
