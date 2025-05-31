package pacman.model;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GameTableModel extends AbstractTableModel {

    private CellState[][] board;
    private final Random random = new Random();

    private static final CellState D = CellState.DOT;
    private static final CellState W = CellState.WALL;
    private static final CellState GW = CellState.GHOST_WALL;
    private static final CellState E = CellState.EMPTY;
    private static final CellState GS1 = CellState.GHOST_SPOT1;
    private static final CellState GS2 = CellState.GHOST_SPOT2;
    private static final CellState GS3 = CellState.GHOST_SPOT3;
    private static final CellState GS4 = CellState.GHOST_SPOT4;

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
            {D, D, W, D, D},
            {D, W, W, W, D},
            {D, D, W, D, D},
            {W, D, D, D, W}
    };
    private static final CellState[][] PATTERN_D = {
            {D, D, D, D, D},
            {D, W, W, W, D},
            {D, W, W, W, D},
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
            {W, GS4, GS1, GS3, W},
            {W, E,GS2, E, W},
            {W, W, W, W, W}
    };
    private static final List<CellState[][]> PATTERNS = List.of(PATTERN_A, PATTERN_B, PATTERN_C, PATTERN_D, PATTERN_E);
    private static final int PATTERN_SIZE = 5;

    private int pacmanRow;
    private int pacmanCol;
    private ItemDirection pacmanCurrentDirection;
    private ItemDirection intendedDirection = ItemDirection.NONE;

    private int score = 0;
    private int lives = 3;

    public GameTableModel(int width, int height) {
        createNewBoard(width, height);
    }

    public void createNewBoard(int width, int height) {
        this.board = new CellState[height][width];
        this.score = 0;
        this.lives = 3;
        generateMazeWithPatterns();
        initializeEntities();

        fireTableStructureChanged();

    }

    private void generateMazeWithPatterns() {
        for (int y = 0; y < getRowCount(); y += PATTERN_SIZE) {
            for (int x = 0; x < getColumnCount(); x += PATTERN_SIZE) {
                if (x==0 && y==0){
                    CellState[][] randomPattern = PATTERNS.getFirst();
                    copyPatternToBoard(randomPattern, x, y);}
                else {CellState[][] randomPattern = PATTERNS.get(random.nextInt(PATTERNS.size()));
                copyPatternToBoard(randomPattern, x, y);}
            }
        }

        buildGhostArea();
        buildOuterBorder();

        board[PATTERN_SIZE / 2][PATTERN_SIZE / 2] = CellState.PACMAN;

    }

    private void buildGhostArea () {
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

    private void initializeEntities(){
        this.pacmanRow = PATTERN_SIZE / 2;
        this.pacmanCol = PATTERN_SIZE / 2;
        board [PATTERN_SIZE / 2][PATTERN_SIZE / 2] = CellState.PACMAN;
        this.pacmanCurrentDirection = ItemDirection.RIGHT;

    }

    public void requestPacmanMove(ItemDirection intendedDirection){
        int nextRow = pacmanRow;
        int nextCol = pacmanCol;

        switch (intendedDirection) {
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
        if (isValidMove(nextRow, nextCol)) {

            CellState contentOfNextCell = board[nextRow][nextCol];
            board[pacmanRow][pacmanCol] = CellState.EMPTY;
            fireTableCellUpdated(pacmanRow, pacmanCol);
            pacmanRow = nextRow;
            pacmanCol = nextCol;
            this.pacmanCurrentDirection = intendedDirection;

            if (contentOfNextCell == CellState.DOT) {
                this.score += 10; //Add action update score
                if (contentOfNextCell == CellState.POWER_PELLET) {
                    this.score += 50; //Add action update score
                    // Activate hunter mode
                }
                //Add check on meeting with ghost
            }
            board[pacmanRow][pacmanCol] = CellState.PACMAN;
            fireTableCellUpdated(pacmanRow, pacmanCol);
        }
        else {
            pacmanCurrentDirection = intendedDirection;
            fireTableCellUpdated(pacmanRow, pacmanCol);
        }

    }

    public void moveGhosts(){

    }

    public int getScore() {
        return this.score;
    }
    public int getLives() {
        return this.lives;
    }

    private boolean isValidMove(int row, int col){
        if (board[row][col] == CellState.WALL) {
            return false;
        }
        return true;

    }

    public ItemDirection getItemDirection(){
        return this.pacmanCurrentDirection;
    }
    public Point getItemPosition(){
        return new Point(pacmanCol, pacmanRow);
    }

    @Override
    public int getRowCount() { return (board == null) ? 0 : board.length; }
    @Override
    public int getColumnCount() { return (board == null || board.length == 0) ? 0 : board[0].length; }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == pacmanRow && columnIndex == pacmanCol) {
            return CellState.PACMAN;
        }
        return board[rowIndex][columnIndex];
    }
    public void setCellState(int row, int col, CellState state) {
        if (row >= 0 && row < getRowCount() && col >= 0 && col < getColumnCount()) {
            board[row][col] = state;
            fireTableCellUpdated(row, col);
        }
    }


}