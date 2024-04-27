package examenFinal;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class FileHandler {
    public static void saveImage(BufferedImage image, File file) throws IOException {
        javax.imageio.ImageIO.write(image, "PNG", file);
    }

    public static BufferedImage loadImage(File file) throws IOException {
        return javax.imageio.ImageIO.read(file);
    }
}