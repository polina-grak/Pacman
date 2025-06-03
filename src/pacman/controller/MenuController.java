package pacman.controller;

import pacman.model.GameModel;
import pacman.model.GameTableModel;
import pacman.model.PacmanModel;
import pacman.threads.PacmanMovementThread;
import pacman.view.GameBoardView;
import pacman.view.MazeSetterView;
import pacman.view.Menu;
import pacman.controller.HighScoreController;

import javax.swing.*;

public class MenuController {

    GameBoardView gameBoardView;
    private final int PACMAN_MOVE_DELAY = 250;
    //private final int GHOST_MOVE_DELAY = 250;
    private HighScoreController highScoreController;

    public void mazeSizeChooser(Menu menu) {
        MazeSetterView mazeSetterView = new MazeSetterView(menu, this);
        mazeSetterView.setVisible(true);
    }

    public void startPlay(JFrame menu, int selectedWidth, int selectedHeight) {
        Menu.close(menu);
        GameTableModel gameBoardModel = new GameTableModel(20, 20);
        PacmanModel pacmanModel = new PacmanModel();
        GameModel gameModel = new GameModel();
        this.gameBoardView = new GameBoardView(gameBoardModel, gameModel, pacmanModel);
        GameController gameController = new GameController(gameBoardView, gameBoardModel, pacmanModel, gameModel, highScoreController, 20, 20);
        KeyController controller = new KeyController(pacmanModel, gameBoardView, gameController);
        gameController.startGame();
        gameBoardView.setVisible(true);
        gameBoardView.requestFocusInWindow();
    }

    public void highScoreList(Menu menu) {
        MazeSetterView mazeSetterView = new MazeSetterView(menu, this);
        mazeSetterView.setVisible(true);
    }

    public void exit(Menu menu) {
        menu.dispose();
    }

    public void openMenu() {
        highScoreController = new HighScoreController();
        Menu menuView = new Menu(this);
        menuView.setVisible(true);
    }

}
