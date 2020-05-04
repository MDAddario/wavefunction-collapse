package procedural_generation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.HashMap;

class ImageProcessing {

    // Pixel nested class
    class Pixel {

        // Attribute
        private int[] rgb;

        // Constructor
        Pixel(int[] pixels) {
            this.rgb = pixels.clone();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pixel)
                return this.rgb[0] == ((Pixel)obj).rgb[0] &&
                        this.rgb[1] == ((Pixel)obj).rgb[1] &&
                        this.rgb[2] == ((Pixel)obj).rgb[2];
            return false;
        }

        @Override
        public int hashCode() {
            return this.rgb[0] + this.rgb[1] + this.rgb[2];
        }

        @Override
        public String toString() {
            return this.rgb[0] + " " + this.rgb[1] + " " + this.rgb[2] + " ";
        }
    }

    // HashMaps
    private HashMap<Pixel, Type> pixelToType;
    private HashMap<Type, Pixel> typeToPixel;
    private Type[][]             types;

    // Default names
    private static final String DEFAULT_INPUT  = "assets/flower_sample.png";
    private static final String DEFAULT_OUTPUT = "assets/flower_output.png";
    private static final String DEFAULT_FORMAT = "png";

    // Constructor
    ImageProcessing(String input) throws Exception {

        // Create the hash maps
        this.pixelToType = new HashMap<>();
        this.typeToPixel = new HashMap<>();

        // Build the type array
        this.constructTypeArray(input);
    }

    ImageProcessing() throws Exception {
        this(DEFAULT_INPUT);
    }

    // Main attraction
    public static void main(String[] args) throws Exception {

        // Create our object
        ImageProcessing imgProc = new ImageProcessing();

        // Print the type array
        imgProc.visualize();

        // Create the output image
        imgProc.constructImageFromTypes();
    }

    // Construct type array from input image
    private void constructTypeArray(String input) throws Exception{

        // Read image
        BufferedImage image = ImageIO.read(new File(input));
        WritableRaster raster = image.getRaster();

        // Start with type value zero
        Type type = Type.getFirstType();

        // Scan every pixel
        int width = image.getWidth();
        int height = image.getHeight();

        // Create types array
        this.types = new Type[height][width];

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {

                // Extract pixel
                Pixel pixel = new Pixel(raster.getPixel(xx, yy, (int[]) null));

                // Check type associated to pixel
                Type queriedType = this.pixelToType.get(pixel);

                // Pixel has already been seen
                if (queriedType != null) {
                    this.types[yy][xx] = queriedType;
                    continue;
                }

                // Add pixel and type to hash maps
                this.pixelToType.put(pixel, type);
                this.typeToPixel.put(type, pixel);
                this.types[yy][xx] = type;

                // Move on to next type
                type = type.getNextType();
            }
        }
    }

    // Output an image from types
    void constructImageFromTypes(Type[][] types, String output, String format) throws Exception {

        // Image dimensions
        int width = types[0].length;
        int height = types.length;

        // Create new image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++)
            for (int yy = 0; yy < height; yy++) {

                // Get the tile
                Type type = types[yy][xx];

                // Fetch the pixel
                Pixel pixel = this.typeToPixel.get(type);

                // Color the pixel
                raster.setPixel(xx, yy, pixel.rgb);
            }

        // Write to file
        ImageIO.write(image, format, new File(output));
    }

    void constructImageFromTypes() throws Exception {
        this.constructImageFromTypes(this.types, DEFAULT_OUTPUT, DEFAULT_FORMAT);
    }

    // Visualize the type array
    private void visualize() {
        System.out.println("Visualizing type array:");
        for (Type[] row : this.types) {
            for (Type type : row)
                System.out.print(type + " ");
            System.out.println();
        }
    }
}
