package pacman.view;

import pacman.controller.MenuController;
import pacman.controller.HighScoreController;
import pacman.model.HighScoreEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresView extends JDialog {
    HighScoreController highScoreController;
    private JList<String> highScoreJList;
    private DefaultListModel<String> listModel;

    public HighScoresView(JFrame menu,  HighScoreController highScoreController) {
        super(menu, true);
        setSize(300, 300);
        setLocationRelativeTo(menu);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 60));
        setTitle("Highscores table:");

        getContentPane().setBackground(new Color(0, 0, 60));

        this.highScoreController = highScoreController;

        Font baseFont = (FontLoader.loadFont("/Font/pacman.ttf"));
        Font dataFont = baseFont.deriveFont(Font.PLAIN, 14f);

        List<HighScoreEntry> highScoreList = highScoreController.getHighScores();


        listModel = new DefaultListModel<>();
        highScoreJList = new JList<>(listModel);
        highScoreJList.setBackground(new Color(0, 0, 60));
        highScoreJList.setFont(dataFont);
        highScoreJList.setForeground(Color.orange);

        JScrollPane listScrollPane = new JScrollPane(highScoreJList);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        if (highScoreList != null) {
            for (int i = 0; i < highScoreList.size(); i++) {
                HighScoreEntry entry = highScoreList.get(i);
                listModel.addElement(String.format("%d. %-20s %8d", (i + 1),entry.getPlayerName() , entry.getScore()));
                highScoreJList.setSelectedIndex(i);
                highScoreJList.ensureIndexIsVisible(i);
                highScoreJList.updateUI();
                if (i == highScoreList.size() - 1) {
                    listScrollPane.getVerticalScrollBar().setValue(listScrollPane.getVerticalScrollBar().getMaximum());
                }
                if (i == 0) {
                    listScrollPane.getVerticalScrollBar().setValue(listScrollPane.getVerticalScrollBar().getMinimum());
                }
            }
        }
        add(listScrollPane, BorderLayout.CENTER);
    }
}
