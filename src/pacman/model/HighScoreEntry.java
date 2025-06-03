package pacman.model;

import java.io.Serializable;
import java.util.Objects;

public class HighScoreEntry implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;
    private String playerName;
    private int score;

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(HighScoreEntry other) {
        return Integer.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return playerName + ": " + score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighScoreEntry that = (HighScoreEntry) o;
        return score == that.score && Objects.equals(playerName, that.playerName);
    }

    public int hashCode() {
        return Objects.hash(playerName, score);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof HighScoreEntry) {
            return compareTo((HighScoreEntry) o);
        }
        throw new ClassCastException("Object is not an instance of HighScoreEntry");
    }
}
