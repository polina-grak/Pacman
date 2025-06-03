package pacman.model;

import pacman.enums.GameState;
import pacman.threads.AnimationThread;

public class GameModel {
    private GameState currentGameState;
    AnimationThread animationThread;
    private int score = 0;
    private int lives = 3;
    private int timeElapsed = 0;

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
