package procedural_generation;

public class Wrapper {

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
        Wrapper.generate(input, output, format, radius, height, width);
    }

    public static void generate(String input, String output, String format, double radius, int height, int width) {

        // Create the sample object
        Sample sample = new Sample(input, radius);

        // Create the wavefunction
        Wavefunction phi = new Wavefunction(sample, height, width);

        // Run the simulations
        phi.quantumLoop();

        // Save collapsed version to file
        phi.saveImage(output, format);
    }
}
