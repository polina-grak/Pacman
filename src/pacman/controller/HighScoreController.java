package pacman.controller;

import pacman.model.HighScoreEntry;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HighScoreController {
    private List<HighScoreEntry> scores;
    private static final String HIGHSCORE_FILE = "HighScoreList";

    public HighScoreController() {
        this.scores = new ArrayList<>();
        loadHighScores();
    }

    public List<HighScoreEntry> getHighScores() {
        loadHighScores();
        return scores;
    }
    public int getHighestScore() {
        if (scores != null && !scores.isEmpty()) {
            return scores.getFirst().getScore();
        }
        return 0;
    }

    public void addScore(String playerName, int score) {
        if (playerName == null || playerName.trim().isEmpty()) {
            System.err.println("Player name is empty or null. Score not added to high scores.");
            return;
        }
        scores.add(new HighScoreEntry(playerName, score));
        Collections.sort(scores);

        saveHighScores();
    }

    private void loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE))) {
            Object loadedObject = ois.readObject();
            if (loadedObject instanceof List) {
                this.scores = (List<HighScoreEntry>) loadedObject;
            } else {
                System.err.println("Incorrect format");
                this.scores = new ArrayList<>();
            }
            Collections.sort(scores);

        } catch (java.io.FileNotFoundException e) {
            System.out.println("File wasn't found");
            this.scores = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Loading error " + e.getMessage());
            this.scores = new ArrayList<>();
        }
    }

    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            System.err.println("Error saving" + e.getMessage());
        }
    }


}




