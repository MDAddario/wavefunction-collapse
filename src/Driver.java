import procedural_generation.Sample;
import procedural_generation.Wavefunction;

public class Driver {

    public static void main(String[] args) {

        // Select input sample and correlation radius
        String input  = "assets/flower_sample.png";
        double radius = 1.0;

        // Create the sample object
        Sample sample = new Sample(input, radius);

        // Visualize the sample
        //sample.visualize();

        // Create the wavefunction
        int height = 15;
        int width  = 15;
        Wavefunction phi = new Wavefunction(sample, height, width);

        // Run the simulations
        phi.quantumLoop();

        // Visualize collapsed version
        //phi.visualizePhi();

        // Save collapsed version to file
        String output = "assets/flower_output.png";
        String format = "png";
        phi.saveImage(output, format);
    }
}
