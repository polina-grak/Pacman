package pacman.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import pacman.controller.HighScoreController;
import pacman.controller.MenuController;

public class Menu extends JFrame {
    private Font baseFont;
    HighScoreController highScoreController;

    public Menu (MenuController controller, HighScoreController highScoreController){

        setSize(500,500);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,60));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.highScoreController = highScoreController;

        this.baseFont = (FontLoader.loadFont ("/Font/pacman.ttf"));


        baseFont = (baseFont != null)
                ? baseFont.deriveFont(Font.BOLD, 20f)
                : new Font("Arial", Font.BOLD, 40);

        int w = getWidth();
        int h = getHeight();

        BufferedImage imageToDraw = ResourceManager.getImage("PACMAN_LOGO");


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0,0,60));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints tableContent = new GridBagConstraints();


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=0;
        tableContent.gridy=0;

        tableContent.weightx = 1;
        tableContent.weighty = 1;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(0, 20, 0, 20);

        ImagePanel panelLogo = new ImagePanel(imageToDraw);
        panelLogo.setOpaque(false);
        panelLogo.setPreferredSize(new Dimension(w/4, h/3));
        mainPanel.add(panelLogo, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=0;
        tableContent.gridy=1;

        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);

        JButton newGameBut = new JButton("New Game");
        newGameBut.setFont(baseFont);
        newGameBut.setForeground(Color.orange);
        newGameBut.setBackground(new Color(0,0,60));

        mainPanel.add(newGameBut, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=0;
        tableContent.gridy=2;

        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);

        JButton highScoresBut = new JButton("High Scores");
        highScoresBut.setFont(baseFont);
        highScoresBut.setForeground(Color.orange);
        highScoresBut.setBackground(new Color(0,0,60));

        mainPanel.add(highScoresBut, tableContent);

        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx=0;
        tableContent.gridy=3;

        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 30, 10);

        JButton exitBut = new JButton("Exit");
        exitBut.setFont(baseFont);
        exitBut.setForeground(Color.orange);
        exitBut.setBackground(new Color(0,0,60));

        mainPanel.add(exitBut, tableContent);


        newGameBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.mazeSizeChooser(Menu.this);
        }});

        highScoresBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.highScoreList(Menu.this, highScoreController);
            }});

        exitBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.exit(Menu.this);
            }});


        add(mainPanel);
        setVisible(true);
    }
    public static void close(JFrame menu){
        menu.dispose();
    }

    private Menu getFrame() {
        return this;
    }
}

