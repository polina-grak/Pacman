package pacman.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Resize extends JFrame {
    private String imagePath;
    private Image originalImage;
    public Resize(String imagePath) {
        this.imagePath = imagePath;
        try {
        BufferedImage originalImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            System.out.println("Error reading image file");
        }
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(imageLabel, BorderLayout.CENTER);

        imageLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = imageLabel.getWidth();
                int h = imageLabel.getHeight();
                if (w <= 0 || h <= 0) return;

                // Масштабируем изображение
                Image scaled = originalImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            }
        });
        setVisible(true);
    }
}
