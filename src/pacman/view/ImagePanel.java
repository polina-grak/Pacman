package pacman.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel(BufferedImage image) {
        this.image = image;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Scale the image to fit the panel's current size
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Optionally, draw a placeholder or error message if image isn't loaded
            g.setColor(Color.RED);
            g.drawString("Image not loaded", 10, 20);
        }
    }
}
