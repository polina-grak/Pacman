package pacman.model;

import pacman.enums.CellState;
import pacman.enums.GhostType;
import pacman.enums.ItemDirection;
import pacman.threads.GhostMovementThread;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Ghost {
    private int startRow;
    private int startCol;
    public int row;
    public int col;
    public ItemDirection currentDirection;
    public CellState cellUnderneath;
    public GhostType type;
    public CellState ghostType;
    public GhostMovementThread ghostThread = null;

    private boolean isReleased;
    private final int releaseDelayTicks;
    private int currentTickCounter;

    public Queue<ItemDirection> plannedPath;
    private int ticksSinceLastPathRecalculation;


    public Ghost(GhostType type, int startRow, int startCol, ItemDirection startDirection, CellState ghostType, CellState initialCellUnderneath, int releaseDelayTicks) {
        this.type = type;
        this.startRow = startRow;
        this.startCol = startCol;
        this.row = startRow;
        this.col = startCol;
        this.currentDirection = startDirection;
        this.ghostType = ghostType;
        this.isReleased = false;
        this.cellUnderneath = initialCellUnderneath;
        this.releaseDelayTicks = releaseDelayTicks;
        this.currentTickCounter = 0;

        this.plannedPath = new LinkedList<>();
        this.ticksSinceLastPathRecalculation = 0;

    }


    public boolean isReleased() {
        return isReleased;
    }

    public void releaseGhost(int recalculateIntervalTicks) {
        this.isReleased = true;
        this.ticksSinceLastPathRecalculation = recalculateIntervalTicks;
        System.out.println("Ghost " + type.name() + " is now released.");

    }

    public boolean canBeReleased() {
        return this.currentTickCounter >= this.releaseDelayTicks;
    }

    public void incrementTickCounter() {
        if (!isReleased) {
            this.currentTickCounter++;
        }
    }

    public void resetTickCounter() {
        this.currentTickCounter = 0;
    }

    public void incrementTicksSinceLastPathRecalculation() {
        if (isReleased) {
            this.ticksSinceLastPathRecalculation++;
        }
    }

    public void resetTicksSinceLastPathRecalculation() {
        this.ticksSinceLastPathRecalculation = 0;
    }

    public int getTicksSinceLastPathRecalculation() {
        return ticksSinceLastPathRecalculation;
    }

    public Queue<ItemDirection> getPlannedPath() {
        return plannedPath;
    }

    public void setPlannedPath(Queue<ItemDirection> path) {
        this.plannedPath = path;
        this.resetTicksSinceLastPathRecalculation();
    }

    public void setCurrentDirection(ItemDirection currentDirection) {
        this.currentDirection = currentDirection;
    }


    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }
}


