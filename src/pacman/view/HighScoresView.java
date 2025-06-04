package pacman.view;

import pacman.controller.MenuController;
import pacman.controller.HighScoreController;
import pacman.model.HighScoreEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresView extends JDialog {
    HighScoreController highScoreController;

    public HighScoresView(JFrame menu, MenuController controller, HighScoreController highScoreController) {
        super(menu, true);
        setSize(300, 300);
        setLocationRelativeTo(menu);
        setLayout(new BorderLayout(10, 10));
        //setMinimumSize(new Dimension(350, 300));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 60));

        getContentPane().setBackground(new Color(0, 0, 60));



        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(true);
        setTitle("Highscores table:");



        this.highScoreController = highScoreController;

        Font baseFont = (FontLoader.loadFont("/Font/pacman.ttf"));
        Font headerFont = baseFont.deriveFont(Font.BOLD, 16f);
        Font dataFont = baseFont.deriveFont(Font.PLAIN, 14f);

        JPanel highScores = new JPanel(new GridBagLayout());
        List<HighScoreEntry> highScoreList = highScoreController.getHighScores();
        highScores.setBackground(new Color(0, 0, 60));
        highScores.setOpaque(true);

        GridBagConstraints tableContent = new GridBagConstraints();

        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 0;
        tableContent.gridy = 0;
        tableContent.gridwidth = 1;
        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(0, 10, 0, 10);

        JLabel nameColumn = new JLabel("Name:");
        nameColumn.setFont(headerFont);
        nameColumn.setForeground(Color.orange);
        nameColumn.setBackground(new Color(0, 0, 60));
        nameColumn.setOpaque(true);
        highScores.add(nameColumn, tableContent);

        tableContent.fill = GridBagConstraints.HORIZONTAL;
        tableContent.gridx = 1;
        tableContent.gridy = 0;
        tableContent.gridwidth = 1;
        tableContent.weightx = 1;
        tableContent.weighty = 0;
        tableContent.anchor = GridBagConstraints.CENTER;
        tableContent.insets = new Insets(0, 10, 0, 10);

        JLabel scoreColumn = new JLabel("Score:");
        scoreColumn.setFont(headerFont);
        scoreColumn.setForeground(Color.orange);
        scoreColumn.setBackground(new Color(0, 0, 60));
        scoreColumn.setOpaque(true);
        highScores.add(scoreColumn, tableContent);

        if (highScoreList != null) {
            for (int i = 0; i < highScoreList.size(); i++) {
                HighScoreEntry entry = highScoreList.get(i);
                int currentRow = i;

                // Name
                tableContent.fill = GridBagConstraints.HORIZONTAL;
                tableContent.gridx = 0;
                tableContent.gridy = i+1;
                tableContent.gridwidth = 1;
                tableContent.weightx = 1;
                tableContent.weighty = 0;
                tableContent.anchor = GridBagConstraints.CENTER;
                tableContent.insets = new Insets(0, 10, 0, 10);

                JLabel nameLabel = new JLabel(entry.getPlayerName());
                nameLabel.setFont(dataFont);
                nameLabel.setForeground(Color.orange);
                nameLabel.setBackground(new Color(0, 0, 60));
                nameLabel.setOpaque(true);
                highScores.add(nameLabel, tableContent);

                // Score
                tableContent.fill = GridBagConstraints.HORIZONTAL;
                tableContent.gridx = 1;
                tableContent.gridy = i+1;
                tableContent.gridwidth = 1;
                tableContent.weightx = 1;
                tableContent.weighty = 0;
                tableContent.anchor = GridBagConstraints.CENTER;
                tableContent.insets = new Insets(0, 10, 0, 10);

                JLabel scoreLabel = new JLabel(entry.getScore() + "");
                scoreLabel.setFont(dataFont);
                scoreLabel.setForeground(Color.orange);
                scoreLabel.setBackground(new Color(0, 0, 60));
                scoreLabel.setOpaque(true);
                highScores.add(scoreLabel, tableContent);
            }
        }


        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(0, 0, 60));
        wrapper.setOpaque(true);
        wrapper.add(highScores);

        scrollPane.setViewportView(wrapper);
        scrollPane.getViewport().setBackground(new Color(0, 0, 60));
        scrollPane.getViewport().setOpaque(true);


        add(scrollPane, BorderLayout.CENTER);



    }
}
