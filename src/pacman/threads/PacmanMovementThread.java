package pacman.threads;

import pacman.controller.GameController;
import pacman.enums.GameState;
import pacman.model.GameModel;

public class PacmanMovementThread extends Thread {
    private GameModel model;
    private GameController gameController;
    private final int moveDelayMs;
    private volatile boolean running = true;

    public PacmanMovementThread(GameController gameController, GameModel model,  int delayMillis) {
        this.gameController = gameController;
        this.model = model;
        this.moveDelayMs = delayMillis;
        setDaemon(true);
    }

    public void run() {
        while (running) {
            try {
                if (model.getCurrentGameState() == GameState.RUNNING) {
                    gameController.updatePacmanMovementBasedOnIntention();

                }
                Thread.sleep(moveDelayMs);
            } catch (InterruptedException e) {
                running = false;
            }
        }
        System.out.println("PacmanMovementThread stopped.");
    }

    public void stopThread() {
        running = false;
    }
}
