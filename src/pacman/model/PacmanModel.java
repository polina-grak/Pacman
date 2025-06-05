package pacman.model;

import pacman.enums.ItemDirection;

public class PacmanModel {
    private int row;
    private int col;
    private ItemDirection pacmanCurrentDirection;
    private volatile ItemDirection pacmanIntendedDirection = ItemDirection.NONE;


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public ItemDirection getItemDirection() {
        return this.pacmanCurrentDirection;
    }

    public void setPacmanCurrentDirection(ItemDirection pacmanCurrentDirection) {
        this.pacmanCurrentDirection = pacmanCurrentDirection;
    }

    public ItemDirection getPacmanIntendedDirection() {
        return pacmanIntendedDirection;
    }

    public void setPacmanIntendedDirection(ItemDirection newIntendedDirection) {
        if (newIntendedDirection == ItemDirection.NONE) {
            this.pacmanIntendedDirection = ItemDirection.NONE;
            return;
        }
        this.pacmanIntendedDirection = newIntendedDirection;

        if (this.pacmanCurrentDirection != newIntendedDirection) {
            this.pacmanCurrentDirection = newIntendedDirection;
        }
    }
}
