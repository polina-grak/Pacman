package pacman.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import pacman.controller.MenuController;

public class Menu extends JFrame {

    public Menu (MenuController controller){

        setSize(500,500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        int w = getWidth();
        int h = getHeight();


        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(new Color(0,0,60));


        JPanel logoContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoContainer.setOpaque(false);

        JPanel panelLogo = new JPanel(new GridLayout(1, 1, 20, 20));
        panelLogo.setOpaque(false);
        panelLogo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        URL logoUrl = getClass().getResource("/Pac-Man-Logo.png");

        try {
            BufferedImage originalImage = ImageIO.read(logoUrl);

            if (logoUrl != null) {
                Image scaledImage = originalImage.getScaledInstance(w/2, h/2, Image.SCALE_SMOOTH);
                ImageIcon logo_icon = new ImageIcon(scaledImage);
                JLabel logo = new JLabel(logo_icon);
                panelLogo.add(logo);
            }
            else {
                System.err.println("Logo not found");
                panelLogo.add(new JLabel("Logo not found"));
            }
        } catch (IOException e) {
            System.err.println("Logo not found");
        }


        logoContainer.add(panelLogo);

        mainPanel.add(logoContainer, BorderLayout.NORTH);


        JPanel panelButtons = new JPanel(new GridLayout(3, 1, 20, 20));

        panelButtons.setOpaque(false);

        panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JButton newGameBut = new JButton("New Game");
        JButton highScoresBut = new JButton("High Scores");
        JButton exitBut = new JButton("Exit");

        panelButtons.add(newGameBut);
        panelButtons.add(highScoresBut);
        panelButtons.add(exitBut);

        newGameBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.mazeSizeChooser(Menu.this);
        }});


        mainPanel.add(panelButtons, BorderLayout.CENTER);



        JPanel panelBottom = new JPanel(new BorderLayout(5, 5));
        panelBottom.setOpaque(false);
        JLabel label = new JLabel("©Created by Polina Grak", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 15));
        panelBottom.add(label, BorderLayout.CENTER);

        mainPanel.add(panelBottom, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
    public static void close(JFrame menu){
        menu.dispose();
    }
}

