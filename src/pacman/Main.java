package pacman;

import pacman.controller.MenuController;
import pacman.view.ResourceManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        ResourceManager.loadAllResources();
        SwingUtilities.invokeLater(() -> {
            MenuController controller = new MenuController();
            controller.openMenu();
        });
    }
}
