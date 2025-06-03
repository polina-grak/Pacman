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
import pacman.controller.MenuController;

public class Menu extends JFrame {
    private Font baseFont;

    public Menu (MenuController controller){

        setSize(500,500);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,60));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
//
//        JPanel logoContainer = new JPanel();
//        logoContainer.setOpaque(false);
//
//
//
//
////
//            if (imageToDraw != null) {
//                ResizeIconLabel logoIcon = new ResizeIconLabel(imageToDraw);
//
//                JLabel logo = new JLabel(logoIcon.setImage(this));
//
//                logoContainer.add(logo);
//
//                addComponentListener(new ComponentAdapter() {
//                    @Override
//                    public void componentResized(ComponentEvent e) {
//
//                        logo = new JLabel(logoIcon.setImage(getFrame()));
//                        logo.revalidate();
//                        logo.repaint();
//                    }
//                });
//
//            }
//            else {
//                System.err.println("Logo not found");
//                logoContainer.add(new JLabel("Logo not found"));
//            }






//
//        ImagePanel panelLogo = new ImagePanel(imageToDraw);
//        panelLogo.setPreferredSize(new Dimension(w/2, h/2));
//
//
//        logoContainer.add(panelLogo);
//        mainPanel.add(logoContainer, BorderLayout.NORTH);
//
//
//        JPanel panelButtons = new JPanel(new GridLayout(3, 1, 20, 20));
//
//        panelButtons.setOpaque(false);
//        panelButtons.setPreferredSize(new Dimension(0, h/2));
//
//        panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//
//        JButton newGameBut = new JButton("New Game");
//        JButton highScoresBut = new JButton("High Scores");
//        JButton exitBut = new JButton("Exit");
//
//        newGameBut.setFont(baseFont);
//        newGameBut.setForeground(Color.orange);
//        newGameBut.setBackground(new Color(0,0,60));
//
//        highScoresBut.setFont(baseFont);
//        highScoresBut.setForeground(Color.orange);
//        highScoresBut.setBackground(new Color(0,0,60));
//
//        exitBut.setFont(baseFont);
//        exitBut.setForeground(Color.orange);
//        exitBut.setBackground(new Color(0,0,60));
//
//
//
//
//        panelButtons.add(newGameBut);
//        panelButtons.add(highScoresBut);
//        panelButtons.add(exitBut);

        newGameBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.mazeSizeChooser(Menu.this);
        }});

        highScoresBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.highScoreList(Menu.this);
            }});

        exitBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.exit(Menu.this);
            }});


        //mainPanel.add(panelButtons, BorderLayout.CENTER);



//        JPanel panelBottom = new JPanel(new BorderLayout(5, 5));
//        panelBottom.setOpaque(false);
//        JLabel label = new JLabel("©Created by Polina Grak", JLabel.CENTER);
//        label.setFont(new Font("Serif", Font.BOLD, 15));
//        panelBottom.add(label, BorderLayout.CENTER);
//
//        mainPanel.add(panelBottom, BorderLayout.SOUTH);

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

