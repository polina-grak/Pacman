package pacman.controller;

import pacman.model.GameTableModel;
import pacman.view.GameBoardView;
import pacman.view.MazeSetterView;
import pacman.view.Menu;

import javax.swing.*;

public class MenuController {

    public void mazeSizeChooser (Menu menu){
        MazeSetterView mazeSetterView = new MazeSetterView(menu,this );
        mazeSetterView.setVisible(true);
    }

    public void startPlay (JFrame menu, int selectedWidth, int selectedHeight){
        Menu.close (menu);
        GameTableModel gameBoardModel = new GameTableModel(selectedWidth, selectedHeight);
        GameBoardView gameBoardView = new GameBoardView(gameBoardModel);
        new KeyController(gameBoardModel, gameBoardView);
        gameBoardView.setVisible(true);
        gameBoardView.requestFocusInWindow();
    }
}
