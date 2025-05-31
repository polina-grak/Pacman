package pacman;

import pacman.controller.KeyController;
import pacman.controller.MenuController;
import pacman.model.GameTableModel;
import pacman.view.GameBoardView;
import pacman.view.Menu;
import pacman.view.ResourceManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        ResourceManager.loadAllResources();
        GameTableModel gameBoardModel = new GameTableModel(20, 20);
        GameBoardView gameBoardView = new GameBoardView(gameBoardModel);
        KeyController controller = new KeyController(gameBoardModel, gameBoardView);
        gameBoardView.setVisible(true);
        gameBoardView.requestFocusInWindow();

//        ResourceManager.loadAllResources();
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                MenuController controller = new MenuController();
//                Menu menuView =new Menu(controller);
//            }
//        });
    }


}
