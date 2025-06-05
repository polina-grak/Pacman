package pacman.view;

import pacman.controller.GameController;
import pacman.enums.GameResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.ParseException;

public class GameOverView extends JDialog {

    public GameOverView(GameResult result, int score, JFrame gameBoardView, GameController gameController) {
        super(gameBoardView, true);
        setSize(500, 350);
        setLocationRelativeTo(gameBoardView);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(350, 300));

        Font baseFont = (FontLoader.loadFont("/Font/pacman.ttf"));


        baseFont = (baseFont != null)
                ? baseFont.deriveFont(Font.BOLD, 20f)
                : new Font("Arial", Font.BOLD, 40);

        int w = getWidth();
        int h = getHeight();

        BufferedImage imageToDraw;
        if (result == GameResult.WIN) {
            imageToDraw = ResourceManager.getImage("VICTORY");
        } else {
            imageToDraw = ResourceManager.getImage("GAME_OVER");
        }


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 0, 60));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints tableContent = new GridBagConstraints();


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 0;
        tableContent.gridy = 0;
        tableContent.gridwidth = 2;
        tableContent.weightx = 1;
        tableContent.weighty = 1;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(0, 10, 0, 10);

        ImagePanel panelLogo = new ImagePanel(imageToDraw);
        panelLogo.setOpaque(false);
        panelLogo.setPreferredSize(new Dimension(w / 3, h / 3));
        mainPanel.add(panelLogo, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 0;
        tableContent.gridy = 1;
        tableContent.gridwidth = 1;
        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);

        JLabel scoreLabel = new JLabel("Score:");
        scoreLabel.setFont(baseFont);
        scoreLabel.setForeground(Color.orange);
        scoreLabel.setBackground(new Color(0, 0, 60));

        mainPanel.add(scoreLabel, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 1;
        tableContent.gridy = 1;
        tableContent.gridwidth = 1;
        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);

        JLabel scoreValue = new JLabel(String.valueOf(score));
        scoreValue.setFont(baseFont);
        scoreValue.setForeground(Color.orange);
        scoreValue.setBackground(new Color(0, 0, 60));

        mainPanel.add(scoreValue, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 0;
        tableContent.gridy = 2;
        tableContent.gridwidth = 1;
        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(baseFont);
        nameLabel.setForeground(Color.orange);
        nameLabel.setBackground(new Color(0, 0, 60));

        mainPanel.add(nameLabel, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 1;
        tableContent.gridy = 2;
        tableContent.gridwidth = 1;
        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);


        JTextField nameField = new JTextField();
        nameField.setFont(baseFont);
        nameField.setForeground(Color.orange);
        nameField.setBackground(new Color(0, 0, 0));

        mainPanel.add(nameField, tableContent);


        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 1;
        tableContent.gridy = 3;
        tableContent.gridwidth = 1;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(10, 10, 10, 10);
        tableContent.ipady = 10;

        JButton saveBut = new JButton("Save");
        saveBut.setFont(baseFont);
        saveBut.setForeground(Color.green);
        saveBut.setBackground(new Color(0, 0, 60));
        mainPanel.add(saveBut, tableContent);


        add(mainPanel, BorderLayout.CENTER);


        saveBut.addActionListener(e -> {
            try {
                String name = nameField.getText();
                if (name.trim().isEmpty()) {
                    throw new ParseException("enter name", 0);
                } else
                    gameController.gameIsEnded(name);
                setVisible(false);
            } catch (ParseException pe) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter your name",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
