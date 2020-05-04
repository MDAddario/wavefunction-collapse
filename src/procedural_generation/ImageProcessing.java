package procedural_generation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

public class ImageProcessing {

    private static final String INPUT  = "assets/flower_sample.png";
    private static final String OUTPUT = "assets/flower_output.png";
    private static final String FORMAT = "png";

    public static void main(String[] args) throws Exception {
        BufferedImage img = colorImage(ImageIO.read(new File(INPUT)));
        ImageIO.write(img, FORMAT, new File(OUTPUT));
    }

    private static BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = 0;
                pixels[1] = 0;
                pixels[2] = 255;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
}
