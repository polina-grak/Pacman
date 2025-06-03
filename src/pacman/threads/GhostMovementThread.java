package pacman.threads;

import pacman.controller.GameController;
import pacman.enums.GhostType;
import pacman.enums.GameState;
import pacman.model.GameModel;
import pacman.model.GameTableModel;

public class GhostMovementThread extends Thread {
    private final GameModel model;
    private final GameController gameController;
    private final GhostType ghostType;
    private final int moveDelayMs;
    private volatile boolean running = true;

    public GhostMovementThread(GameModel model, GameController gameController, GhostType ghostType, int moveDelayMs) {
        this.model = model;
        this.gameController = gameController;
        this.ghostType = ghostType;
        this.moveDelayMs = moveDelayMs;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (model.getCurrentGameState() == GameState.RUNNING) {
                    gameController.processGhostMove(ghostType);
                }
                Thread.sleep(moveDelayMs);
            } catch (InterruptedException e) {
                running = false;
            }
        }
        System.out.println("GhostMovementThread for " + ghostType.name() + " stopped");
    }

    public void stopThread() {
        running = false;
    }
}

