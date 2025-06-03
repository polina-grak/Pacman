package pacman;

import pacman.controller.GameController;
import pacman.controller.HighScoreController;
import pacman.controller.KeyController;
import pacman.controller.MenuController;
import pacman.model.GameModel;
import pacman.model.GameTableModel;
import pacman.model.HighScoreEntry;
import pacman.model.PacmanModel;
import pacman.view.GameBoardView;
import pacman.view.GameOverView;
import pacman.view.Menu;
import pacman.view.ResourceManager;
import pacman.threads.PacmanMovementThread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        ResourceManager.loadAllResources();
//
//        GameTableModel gameBoardModel = new GameTableModel(20, 20);
//        PacmanModel pacmanModel = new PacmanModel();
//        GameModel gameModel = new GameModel();
//        GameBoardView gameBoardView = new GameBoardView(gameBoardModel, gameModel, pacmanModel);
//        HighScoreController highScoreController = new HighScoreController();
//        highScoreController.addScore("Test", 100);
//        highScoreController.addScore("Test2", 1000);
//        highScoreController.addScore("Test3", 10000);
//        highScoreController.addScore("Test4", 100000);
//
//        List<HighScoreEntry> list = highScoreController.getHighScores();

//        GameController gameController = new GameController(gameBoardView, gameBoardModel, pacmanModel, gameModel, highScoreController, 20, 20);
//        KeyController controller = new KeyController(pacmanModel, gameBoardView, gameController);
//        gameController.startGame();
//        gameBoardView.setVisible(true);
//        gameBoardView.requestFocusInWindow();

        ResourceManager.loadAllResources();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuController controller = new MenuController();
                controller.openMenu();
            }
        });
    }


}
