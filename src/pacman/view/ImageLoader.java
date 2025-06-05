package pacman.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {
    public static BufferedImage loadImage(String ImagePath) {

        try {
            InputStream imageStream = ImageLoader.class.getResourceAsStream(ImagePath);

            if (imageStream == null) {
                System.err.println("Image not found:" + ImagePath);
                return null;
            }
            BufferedImage image = ImageIO.read(imageStream);
            return image;

        } catch (IOException e) {
            System.err.println("error image loading:" + ImagePath);
        }
        return null;
    }

}
