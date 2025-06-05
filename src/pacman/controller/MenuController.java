package pacman.controller;

import pacman.model.GameModel;
import pacman.model.GameTableModel;
import pacman.model.PacmanModel;
import pacman.view.GameBoardView;
import pacman.view.HighScoresView;
import pacman.view.MazeSetterView;
import pacman.view.Menu;


import javax.swing.*;

public class MenuController {

    GameBoardView gameBoardView;
    private final int PACMAN_MOVE_DELAY = 250;
    private HighScoreController highScoreController;

    public void mazeSizeChooser(Menu menu) {
        MazeSetterView mazeSetterView = new MazeSetterView(menu, this);
        mazeSetterView.setVisible(true);
    }

    public void startPlay(JFrame menu, int selectedWidth, int selectedHeight) {
        Menu.close(menu);
        GameTableModel gameBoardModel = new GameTableModel(selectedWidth, selectedHeight);
        PacmanModel pacmanModel = new PacmanModel();
        GameModel gameModel = new GameModel();
        this.gameBoardView = new GameBoardView(gameBoardModel, gameModel, pacmanModel,highScoreController);
        GameController gameController = new GameController(gameBoardView, gameBoardModel, pacmanModel, gameModel, highScoreController, selectedWidth, selectedHeight);
        KeyController controller = new KeyController(pacmanModel, gameBoardView, gameController);
        gameController.startGame();
        gameBoardView.setVisible(true);
        gameBoardView.requestFocusInWindow();
    }

    public void highScoreList(Menu menu, HighScoreController highScoreController) {
        HighScoresView highScoresView = new HighScoresView(menu, highScoreController);
        highScoresView.setVisible(true);
    }

    public void exit(Menu menu) {
        menu.dispose();
    }

    public void openMenu() {
        highScoreController = new HighScoreController();
        Menu menuView = new Menu(this, highScoreController);
        menuView.setVisible(true);
    }

}
