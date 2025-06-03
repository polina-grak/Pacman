package pacman.model;

import pacman.controller.GameController;
import pacman.enums.CellState;
import pacman.enums.ItemDirection;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.List;

import static pacman.enums.CellState.*;


public class GameTableModel extends AbstractTableModel {

    private CellState[][] board;
    private final Random random = new Random();

    private static final CellState D = CellState.DOT;
    private static final CellState W = CellState.WALL;
    private static final CellState GW = CellState.GHOST_WALL;
    private static final CellState E = CellState.EMPTY;


    private static final CellState[][] PATTERN_A = {
            {W, W, D, W, W},
            {D, W, D, W, D},
            {D, D, D, D, D},
            {D, W, D, W, W},
            {W, W, D, W, W}
    };
    private static final CellState[][] PATTERN_B = {
            {W, D, D, D, W},
            {W, D, W, D, W},
            {D, D, W, D, D},
            {W, D, W, D, W},
            {W, D, D, D, W}
    };
    private static final CellState[][] PATTERN_C = {
            {W, D, D, D, W},
            {W, D, W, D, W},
            {D, D, W, D, D},
            {D, W, W, W, D},
            {D, D, D, D, D}
    };
    private static final CellState[][] PATTERN_D = {
            {D, D, D, D, D},
            {D, W, D, W, D},
            {D, W, D, D, D},
            {D, W, W, W, D},
            {D, D, D, D, D}
    };
    private static final CellState[][] PATTERN_E = {
            {W, D, D, D, W},
            {W, D, W, D, W},
            {D, D, W, D, D},
            {W, D, D, D, W},
            {W, W, D, W, W}
    };
    private static final CellState[][] PATTERN_GHOST_SPACE = {
            {E, E, E, E, W},
            {W, GW, GW, GW, W},
            {W, E, E, E, W},
            {W, E, E, E, W},
            {W, W, W, W, W}
    };
    private static final List<CellState[][]> PATTERNS = List.of(PATTERN_A, PATTERN_B, PATTERN_C, PATTERN_D, PATTERN_E);
    public static final int PATTERN_SIZE = 5;

    private int width;
    private int height;

    public int dotsCount = 0;


    public GameTableModel(int width, int height) {
        createNewBoard(width, height);
    }

    public void createNewBoard(int width, int height) {
        this.board = new CellState[height][width];
        this.width = width;
        this.height = height;
        generateMazeWithPatterns();

        fireTableStructureChanged();

    }

    public int getDotsCount() {
       return this.dotsCount;
    }

    public void eatDot(){
        this.dotsCount--;
    }

    private void generateMazeWithPatterns() {
        for (int y = 1; y < getRowCount(); y += PATTERN_SIZE) {
            for (int x = 1; x < getColumnCount(); x += PATTERN_SIZE) {
                if (x == 1 && y == 1) {
                    CellState[][] randomPattern = PATTERNS.getFirst();
                    copyPatternToBoard(randomPattern, x, y);
                } else {
                    CellState[][] randomPattern = PATTERNS.get(random.nextInt(PATTERNS.size()));
                    copyPatternToBoard(randomPattern, x, y);
                }
            }
        }

        buildGhostArea();
        buildOuterBorder();

        for (int y = 0; y < getRowCount(); y++) {
            for (int x = 0; x < getColumnCount(); x++) {
                if (board[y][x] == CellState.DOT) {
                    dotsCount++;
                }
            }
        }
       dotsCount = 1;


    }

    private void buildGhostArea() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        copyPatternToBoard(PATTERN_GHOST_SPACE, 0, numRows - PATTERN_SIZE);
    }

    private void copyPatternToBoard(CellState[][] pattern, int startX, int startY) {
        for (int py = 0; py < PATTERN_SIZE; py++) {
            for (int px = 0; px < PATTERN_SIZE; px++) {
                if (startY + py < getRowCount() && startX + px < getColumnCount()) {
                    board[startY + py][startX + px] = pattern[py][px];
                }
            }
        }
    }


    private void buildOuterBorder() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int col = 0; col < numCols; col++) {
            board[0][col] = CellState.WALL;
            board[numRows - 1][col] = CellState.WALL;
        }

        for (int row = 0; row < numRows; row++) {
            board[row][0] = CellState.WALL;
            board[row][numCols - 1] = CellState.WALL;
        }
    }


    public boolean isValidPacmanMove(int row, int col) {
        return board[row][col] != CellState.WALL && board[row][col] != CellState.GHOST_WALL;

    }

    public boolean isGhost(int row, int col) {
        return board[row][col] == GHOST_RED || board[row][col] == GHOST_PINK || board[row][col] == GHOST_CYAN;
    }

    public boolean isValidGhostMove(int row, int col) {
        int numRows = board.length;
        if (numRows == 0) {
            return false;
        }
        int numCols = board[0].length;
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            return false;
        }
        if (board[row][col] == CellState.WALL) {
            return false;
        }
        if (board[row][col] == GHOST_RED || board[row][col] == GHOST_PINK || board[row][col] == GHOST_CYAN) {
            return false;
        }
        return true;
    }

    private List<ItemDirection> getValidGhostMoves(Ghost ghost) {
        List<ItemDirection> validMoves = new LinkedList<>();
        for (ItemDirection direction : ItemDirection.values()) {
            if (direction == ItemDirection.NONE) continue;

            int nextRow = ghost.row;
            int nextCol = ghost.col;
            switch (direction) {
                case UP:
                    nextRow--;
                    break;
                case DOWN:
                    nextRow++;
                    break;
                case LEFT:
                    nextCol--;
                    break;
                case RIGHT:
                    nextCol++;
                    break;
            }
            if (isValidGhostMove(nextRow, nextCol)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    @Override
    public int getRowCount() {
        return (board == null) ? 0 : board.length;
    }

    @Override
    public int getColumnCount() {
        return (board == null || board.length == 0) ? 0 : board[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }

    public CellState getCellState(int row, int col) {
        if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount()) {
            return board[row][col];
        }
        return CellState.EMPTY;
    }

    public void setCellState(int row, int col, CellState state) {
        if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount()) {
            board[row][col] = state;
            fireTableCellUpdated(row, col);
        }
    }

    public PathNode findPathNodeWithA(int startR, int startC, int targetR, int targetC) {
        PriorityQueue<PathNode> openList = new PriorityQueue<>();
        HashSet<PathNode> closedList = new HashSet<>();

        PathNode startNode = new PathNode(startR, startC, null, null, 0, calculateHeuristic(startR, startC, targetR, targetC));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            PathNode currentNode = openList.poll();

            if (currentNode.row == targetR && currentNode.col == targetC) {
                return currentNode;
            }

            closedList.add(currentNode);


            for (ItemDirection direction : ItemDirection.values()) {
                if (direction == ItemDirection.NONE) continue;

                int nextRow = currentNode.row;
                int nextCol = currentNode.col;
                switch (direction) {
                    case UP:
                        nextRow--;
                        break;
                    case DOWN:
                        nextRow++;
                        break;
                    case LEFT:
                        nextCol--;
                        break;
                    case RIGHT:
                        nextCol++;
                        break;
                }

                if (!isValidGhostMove(nextRow, nextCol)) {
                    continue;
                }

                PathNode neighborNode = new PathNode(nextRow, nextCol, currentNode, direction, 0, 0);

                if (closedList.contains(neighborNode)) {
                    continue;
                }

                double tentativeGCost = currentNode.gCost + 1;

                boolean inOpenList = openList.contains(neighborNode);
                if (!inOpenList || tentativeGCost < neighborNode.gCost) {
                    neighborNode.parent = currentNode;
                    neighborNode.directionFromParent = direction;
                    neighborNode.gCost = tentativeGCost;
                    neighborNode.hCost = calculateHeuristic(nextRow, nextCol, targetR, targetC);


                    if (inOpenList) {
                        openList.remove(neighborNode);
                    }
                    openList.add(neighborNode);
                }
            }
        }
        return null;
    }

    private double calculateHeuristic(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    public Queue<ItemDirection> reconstructPath(PathNode targetNode, PathNode startNodeEquivalent) {
        LinkedList<ItemDirection> path = new LinkedList<>();
        PathNode current = targetNode;

        while (current.parent != null && !(current.parent.row == startNodeEquivalent.row && current.parent.col == startNodeEquivalent.col)) {
            if (current.directionFromParent == null) {
                System.err.println("Warning: directionFromParent is null during path reconstruction for node " + current);
                break;
            }
            path.addFirst(current.directionFromParent);
            current = current.parent;
            if (current == null) break;
        }

        if (current != null && current.directionFromParent != null && !(current.row == startNodeEquivalent.row && current.col == startNodeEquivalent.col)) {
            path.addFirst(current.directionFromParent);
        }


        return path;
    }

}