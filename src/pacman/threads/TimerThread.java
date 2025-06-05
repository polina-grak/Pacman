package pacman.threads;

import pacman.controller.GameController;
import pacman.enums.UpgradeState;
import pacman.model.GameModel;

public class TimerThread extends Thread {
    private final GameModel gameModel;
    private final GameController gameController;
    private boolean running;
    private final int DELAY = 1000; // 1 second delay

    public TimerThread(GameModel gameModel, GameController gameController) {
        this.gameModel = gameModel;
        this.gameController = gameController;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(DELAY);
                if (gameModel.isGameRunning()) {
                    gameController.incrementTimer();
                    if (gameModel.getUpgradeState() != UpgradeState.NONE) {
                        gameModel.decrementUpgradeSecondsLeft();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopThread() {
        this.running = false;
    }
}

