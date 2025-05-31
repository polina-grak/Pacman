package pacman.controller;

import pacman.view.GameBoardView;
import javax.swing.SwingUtilities;


public class AnimationThread extends java.lang.Thread {
    private final GameBoardView gameBoardView;
    private final int delayMillis;
    private volatile boolean running = true;


    public AnimationThread(GameBoardView gameBoardView, int delayMillis) {
        this.gameBoardView = gameBoardView;
        this.delayMillis = delayMillis;

        setDaemon(true);
    }

    public void run() {
        while (running) {
            gameBoardView.incrementAnimationFrameCounter();

            SwingUtilities.invokeLater(() -> {
                if (gameBoardView.getGameTable() != null) {
                    gameBoardView.getGameTable().repaint();
                }

            });

            try {
                AnimationThread.sleep(delayMillis);
            } catch (InterruptedException e) {
                running = false;
            }
        }
        System.out.println("Animation thread had been stopped");
    }

    public void stopThread() {
        running = false;
        interrupt();
    }
}
