package pacman.model;

import pacman.enums.CellState;
import pacman.enums.GameState;
import pacman.enums.UpgradeState;
import pacman.threads.AnimationThread;

import java.util.Arrays;
import java.util.List;

public class GameModel {
    private GameState currentGameState;
    AnimationThread animationThread;
    private int score = 0;
    private int lives = 3;
    private int timeElapsed = 0;
    private int upgradeSecondsLeft = 0;

    public final List<CellState> availableUpgrades = Arrays.asList(
            CellState.INVISIBILITY_UPGRADE,
            CellState.SCORE_MULT_UPGRADE,
            CellState.EXTRA_LIFE_UPGRADE,
            CellState.GHOST_EATER_UPGRADE,
            CellState.EXTRA_FOOD

    );
    private UpgradeState upgradeState = UpgradeState.NONE;
    public UpgradeState getUpgradeState() {
        return upgradeState;
    }
    public void setUpgradeState(UpgradeState upgradeState) {
        this.upgradeState = upgradeState;
        if (upgradeState == UpgradeState.NONE) {
            this.upgradeSecondsLeft = 0;
        } else {
            this.upgradeSecondsLeft = 10;
        }
    }
    public void decrementUpgradeSecondsLeft() {
        if (upgradeSecondsLeft > 0) {
            upgradeSecondsLeft--;
        } else {
            this.upgradeState = UpgradeState.NONE;
            this.upgradeSecondsLeft = 0;
        }
    }

    public List<CellState> getAvailableUpgrades() {

        return availableUpgrades;
    }

    public void resetUpgradeState() {}


    public GameModel() {

    }

    public int getScore() {
        return this.score;
    }

    public void updateBoardViewScore(int scoreIncrement) {
        this.score += scoreIncrement;
    }

    public int getLives() {
        return this.lives;
    }

    public void updateLives(int livesDecrement) {
        this.lives -= livesDecrement;

    }
    public void increaseLives() {
        this.lives +=1;

    }

    public GameState getCurrentGameState() {

        return this.currentGameState;
    }

    public synchronized void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void incrementTimer() {
        this.timeElapsed++;
    }

    public boolean isGameRunning() {
        return this.currentGameState == GameState.RUNNING;
    }

    public void setAnimationThread(AnimationThread animationThread) {
        this.animationThread = animationThread;
    }
    public AnimationThread getAnimationThread() {
       return animationThread;
    }
}
