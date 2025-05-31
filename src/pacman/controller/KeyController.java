package pacman.controller;

import pacman.model.GameTableModel;
import pacman.model.ItemDirection;
import pacman.view.GameBoardView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {
    private GameTableModel model;
    private GameBoardView view;

    public KeyController(GameTableModel model, GameBoardView view) {
        this.model = model;
        this.view = view;

        this.view.GameKeyListener(this);
    }
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
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
            model.requestPacmanMove(intendedDirection);
        }

    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
}
