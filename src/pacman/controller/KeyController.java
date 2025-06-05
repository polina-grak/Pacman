package pacman.controller;

import pacman.enums.ItemDirection;
import pacman.model.PacmanModel;
import pacman.view.GameBoardView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {
    private final PacmanModel model;
    private final GameBoardView view;
    public GameController controller;

    public KeyController(PacmanModel model, GameBoardView view, GameController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
        this.view.GameKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (e.isControlDown() && e.isShiftDown() && keyCode == KeyEvent.VK_Q) {
            controller.closeGame();
            return;
        }

        ItemDirection intendedDirection = null;


        switch (keyCode) {
            case KeyEvent.VK_UP:
                intendedDirection = ItemDirection.UP;
                break;
            case KeyEvent.VK_DOWN:
                intendedDirection = ItemDirection.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                intendedDirection = ItemDirection.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                intendedDirection = ItemDirection.RIGHT;
                break;

        }
        if (intendedDirection != null) {
            model.setPacmanIntendedDirection(intendedDirection);
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
