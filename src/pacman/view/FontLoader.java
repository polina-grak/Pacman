package pacman.view;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class FontLoader {

    public static Font loadFont(String fontPath) {
        try {
            InputStream fontStream = FontLoader.class.getResourceAsStream(fontPath);

            if (fontStream == null) {
                System.err.println("Font not found:" + fontPath);
                return null;
            }

            return Font.createFont(Font.TRUETYPE_FONT, fontStream);

        }
        catch (IOException | FontFormatException e) {
            System.err.println("error font loading:" + fontPath);
            return null;
        }
    }
}
