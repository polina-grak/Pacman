package pacman.controller;

import pacman.enums.*;

import pacman.model.*;
import pacman.threads.AnimationThread;
import pacman.threads.GhostMovementThread;
import pacman.threads.PacmanMovementThread;
import pacman.threads.TimerThread;
import pacman.view.GameBoardView;
import pacman.view.GameOverView;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static pacman.enums.CellState.*;
import static pacman.model.GameTableModel.PATTERN_SIZE;

public class GameController {

    private final PacmanMovementThread pacmanMovementThread;

    private TimerThread timerThread;
    private final int GHOST_MOVE_DELAY_MS = 600;
    private final int PACMAN_MOVE_DELAY = 500;
    private static final int RECALCULATE_PATH_INTERVAL_TICKS = 5;


    private final PacmanModel pacmanModel;
    private final GameTableModel gameTableModel;
    private final GameBoardView gameBoardView;
    private final HighScoreController highScoreController;
    private final GameModel gameModel;
    private final ArrayList<Ghost> ghosts = new ArrayList<>();


    public GameController(GameBoardView gameBoardView, GameTableModel gameBoardModel, PacmanModel pacmanModel, GameModel gameModel, HighScoreController highScoreController, int width, int height) {
        this.gameBoardView = gameBoardView;
        this.highScoreController = highScoreController;
        this.pacmanModel = pacmanModel;
        this.gameModel = gameModel;
        this.gameTableModel = gameBoardModel;


        int redGhostDelaySeconds = 5;
        int pinkGhostDelaySeconds = 15;
        int cyanGhostDelaySeconds = 28;

        int redDelayTicks = (redGhostDelaySeconds * 1000) / GHOST_MOVE_DELAY_MS;
        int pinkDelayTicks = (pinkGhostDelaySeconds * 1000) / GHOST_MOVE_DELAY_MS;
        int cyanDelayTicks = (cyanGhostDelaySeconds * 1000) / GHOST_MOVE_DELAY_MS;

        Ghost redGhost = new Ghost(GhostType.RED, height - 2, 1, ItemDirection.UP, GHOST_RED, CellState.GHOST_EMPTY, redDelayTicks);
        ghosts.add(redGhost);
        Ghost pinkGhost = new Ghost(GhostType.PINK, height - 2, 2, ItemDirection.UP, GHOST_PINK, CellState.GHOST_EMPTY, pinkDelayTicks);
        ghosts.add(pinkGhost);
        Ghost cyanGhost = new Ghost(GhostType.CYAN, height - 2, 3, ItemDirection.UP, GHOST_CYAN, CellState.GHOST_EMPTY, cyanDelayTicks);
        ghosts.add(cyanGhost);


        redGhost.ghostThread = new GhostMovementThread(this.gameModel, this, GhostType.RED, GHOST_MOVE_DELAY_MS);
        pinkGhost.ghostThread = new GhostMovementThread(this.gameModel, this, GhostType.PINK, GHOST_MOVE_DELAY_MS);
        cyanGhost.ghostThread = new GhostMovementThread(this.gameModel, this, GhostType.CYAN, GHOST_MOVE_DELAY_MS);

        pacmanMovementThread = new PacmanMovementThread(this, gameModel, PACMAN_MOVE_DELAY);

        initializeEntities();

    }

    public void startGame() {

        pacmanMovementThread.start();


        for (Ghost ghost : this.ghosts) {
            ghost.ghostThread.start();
        }

        timerThread = new TimerThread(gameModel, this);
        timerThread.start();

        this.gameModel.setCurrentGameState(GameState.RUNNING);
    }

    private void initializeEntities() {
        int pacmanRow = PATTERN_SIZE / 2;
        int pacmanCol = PATTERN_SIZE / 2;

        this.pacmanModel.setRow(pacmanRow);
        this.pacmanModel.setCol(pacmanCol);
        if (pacmanRow < gameTableModel.getRowCount() && pacmanCol < gameTableModel.getColumnCount()) {
            gameTableModel.setCellState(pacmanRow, pacmanCol, CellState.PACMAN);
        }
        this.pacmanModel.setPacmanCurrentDirection(ItemDirection.RIGHT);
        this.pacmanModel.setPacmanIntendedDirection(ItemDirection.NONE);

        for (Ghost ghost : this.ghosts) {
            gameTableModel.setCellState(ghost.getStartRow(), ghost.getStartCol(), ghost.ghostType);
            ghost.row = ghost.getStartRow();
            ghost.col = ghost.getStartCol();
            ghost.resetTickCounter();
        }

    }


    public synchronized void updatePacmanMovementBasedOnIntention() throws InterruptedException {
        if (this.pacmanModel.getPacmanIntendedDirection() != ItemDirection.NONE) {
            boolean movedSuccessfully = requestPacmanMove(this.pacmanModel.getPacmanIntendedDirection());
            if (!movedSuccessfully) {
                this.pacmanModel.setPacmanIntendedDirection(ItemDirection.NONE);
            }
        }
    }


    public synchronized boolean requestPacmanMove(ItemDirection intendedDirection) throws InterruptedException {
        if (intendedDirection == ItemDirection.NONE) {
            return false;
        }

        int nextRow = pacmanModel.getRow();
        int nextCol = pacmanModel.getCol();


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
        if (gameTableModel.isValidPacmanMove(nextRow, nextCol)) {

            CellState contentOfNextCell = gameTableModel.getCellState(nextRow, nextCol);
            gameTableModel.setCellState(pacmanModel.getRow(), pacmanModel.getCol(), CellState.EMPTY);
            pacmanModel.setRow(nextRow);
            pacmanModel.setCol(nextCol);
            pacmanModel.setPacmanIntendedDirection(intendedDirection);

            if (contentOfNextCell == CellState.DOT) {
                if (gameModel.getUpgradeState() != UpgradeState.SCORE_MULT_UPGRADE_STATE) {
                    this.gameModel.updateBoardViewScore(10);
                } else {
                    this.gameModel.updateBoardViewScore(50);
                }
                this.gameTableModel.eatDot();
            } else if (contentOfNextCell == CellState.EXTRA_LIFE_UPGRADE) {
                this.gameModel.increaseLives();
                gameBoardView.updateLives(gameModel.getLives());
            } else if (contentOfNextCell == CellState.EXTRA_FOOD) {
                if (gameModel.getUpgradeState() != UpgradeState.SCORE_MULT_UPGRADE_STATE) {
                    this.gameModel.updateBoardViewScore(100);
                } else {
                    this.gameModel.updateBoardViewScore(500);
                }
            } else if (contentOfNextCell == CellState.GHOST_EATER_UPGRADE) {
                ghostEaterReset();
            } else if (contentOfNextCell == CellState.INVISIBILITY_UPGRADE) {
                gameModel.setUpgradeState(UpgradeState.INVISIBILITY_UPGRADE_STATE);
            } else if (contentOfNextCell == CellState.SCORE_MULT_UPGRADE) {
                gameModel.setUpgradeState(UpgradeState.SCORE_MULT_UPGRADE_STATE);
            }


            gameBoardView.scoreUpdate();
            checkDotsCount();


            for (Ghost ghost : this.ghosts) {
                if (this.pacmanModel.getRow() == ghost.row && this.pacmanModel.getCol() == ghost.col) {
                    if (gameModel.getUpgradeState() != UpgradeState.INVISIBILITY_UPGRADE_STATE) {
                        pacmanCaught();
                        return true;
                    } else {
                        gameTableModel.setCellState(ghost.row, ghost.col, ghost.ghostType);
                    }
                }
            }

            gameTableModel.setCellState(pacmanModel.getRow(), pacmanModel.getCol(), CellState.PACMAN);

            return true;
        } else {
            if (pacmanModel.getPacmanIntendedDirection() != intendedDirection) {
                pacmanModel.setPacmanIntendedDirection(intendedDirection);
            }
            return false;
        }
    }

    public void processGhostMove(GhostType ghostType) throws InterruptedException {

        if (gameModel.getCurrentGameState() != GameState.RUNNING) {
            return;
        }

        Ghost ghost = null;
        for (Ghost g : ghosts) {
            if (g.type == ghostType) {
                ghost = g;
                break;
            }
        }

        ghost.incrementTickCounter();

        if (!ghost.isReleased()) {
            if (ghost.canBeReleased()) {
                ghost.releaseGhost(RECALCULATE_PATH_INTERVAL_TICKS);
            }
        } else {
            ghost.incrementTicksSinceLastPathRecalculation();

            if (ghost.getPlannedPath() == null || ghost.getPlannedPath().isEmpty() ||
                    ghost.getTicksSinceLastPathRecalculation() >= RECALCULATE_PATH_INTERVAL_TICKS) {

                int targetRow = pacmanModel.getRow();
                int targetCol = pacmanModel.getCol();


                PathNode targetNode = gameTableModel.findPathNodeWithA(ghost.row, ghost.col, targetRow, targetCol);
                if (gameModel.getUpgradeState() == UpgradeState.INVISIBILITY_UPGRADE_STATE) {
                    targetNode = gameTableModel.findPathNodeWithA(ghost.row, ghost.col, ghost.getStartRow(), ghost.getStartCol());
                }

                if (targetNode != null) {
                    PathNode startNodeEquivalent = new PathNode(ghost.row, ghost.col, null, null, 0, 0);
                    Queue<ItemDirection> newPath = gameTableModel.reconstructPath(targetNode, startNodeEquivalent);

                    if (newPath != null && !newPath.isEmpty()) {
                        ghost.setPlannedPath(newPath);
                    } else {
                        ghost.setPlannedPath(new LinkedList<>());
                    }
                } else {
                    ghost.setPlannedPath(new LinkedList<>());
                }

                ghost.resetTicksSinceLastPathRecalculation();
            }


            Queue<ItemDirection> currentPath = ghost.getPlannedPath();
            if (currentPath != null && !currentPath.isEmpty()) {
                ItemDirection nextMoveDirection = currentPath.peek();

                int nextRow = ghost.row;
                int nextCol = ghost.col;

                switch (nextMoveDirection) {
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
                    case NONE:
                        return;
                }

                if (gameTableModel.isValidGhostMove(nextRow, nextCol)) {
                    currentPath.poll();


                    gameTableModel.setCellState(ghost.row, ghost.col, getRandomCellUnderneath(ghost.cellUnderneath));

                    int prevGhostRow = ghost.row;
                    int prevGhostCol = ghost.col;

                    ghost.row = nextRow;
                    ghost.col = nextCol;
                    ghost.setCurrentDirection(nextMoveDirection);

                    CellState contentOfNextCell = gameTableModel.getCellState(nextRow, nextCol);
                    if (contentOfNextCell == CellState.PACMAN || (nextRow == pacmanModel.getRow() && nextCol == pacmanModel.getCol())) {
                        if (gameModel.getUpgradeState() != UpgradeState.INVISIBILITY_UPGRADE_STATE) {
                            gameTableModel.setCellState(prevGhostRow, prevGhostCol, ghost.cellUnderneath);
                            ghost.setCurrentDirection(nextMoveDirection);
                            gameTableModel.setCellState(ghost.row, ghost.col, ghost.ghostType);
                            pacmanCaught();

                            return;
                        } else {

                            gameTableModel.setCellState(prevGhostRow, prevGhostCol, ghost.cellUnderneath);
                            ghost.setCurrentDirection(nextMoveDirection);
                        }
                    } else if (gameTableModel.isGhost(nextRow, nextCol)) {

                        ghost.getPlannedPath().clear();

                        gameTableModel.setCellState(ghost.row, ghost.col, ghost.ghostType);
                        return;
                    } else {
                        ghost.cellUnderneath = contentOfNextCell;
                    }
                    gameTableModel.setCellState(nextRow, nextCol, ghost.ghostType);

                } else {

                    if (currentPath != null) {
                        currentPath.clear();
                    }
                }
            }
        }
    }

    public CellState getRandomCellUnderneath(CellState cellToRestore) {
        Random random = new Random();
        if (cellToRestore == EMPTY && random.nextDouble() < 0.25 && gameModel.getTimeElapsed() % 5 == 0) {
            int randomIndex = random.nextInt(gameModel.availableUpgrades.size());
            cellToRestore = gameModel.availableUpgrades.get(randomIndex);
            return cellToRestore;
        } else if (cellToRestore == DOT && random.nextDouble() < 0.25 && gameModel.getTimeElapsed() % 5 == 0) {
            int randomIndex = random.nextInt(gameModel.availableUpgrades.size());
            cellToRestore = gameModel.availableUpgrades.get(randomIndex);
            this.gameTableModel.eatDot();
            return cellToRestore;
        }

        return cellToRestore;
    }

    private synchronized void pacmanCaught() throws InterruptedException {

        gameModel.updateLives(1);
        gameBoardView.updateLives(gameModel.getLives());


        if (gameModel.getLives() <= 0) {
            this.gameOver(GameResult.LOSE);
        } else {
            gameModel.setCurrentGameState(GameState.PACMAN_CAUGHT_PAUSE);

            resetLevelState();
        }


    }

    private synchronized void resetLevelState() throws InterruptedException {
        try {
            for (Ghost ghost : this.ghosts) {
                gameTableModel.setCellState(ghost.row, ghost.col, ghost.cellUnderneath);
                ghost.cellUnderneath = CellState.EMPTY;
            }
            initializeEntities();
            this.gameModel.setCurrentGameState(GameState.RUNNING);

        } catch (Exception e) {
            System.out.println("Smth wrong!");
        }
    }

    private synchronized void ghostEaterReset() {

        for (Ghost ghost : this.ghosts) {
            gameTableModel.setCellState(ghost.row, ghost.col, ghost.cellUnderneath);
            ghost.cellUnderneath = CellState.EMPTY;
            gameTableModel.setCellState(ghost.getStartRow(), ghost.getStartCol(), ghost.ghostType);
            ghost.row = ghost.getStartRow();
            ghost.col = ghost.getStartCol();
            ghost.resetTickCounter();
        }

    }

    public void incrementTimer() {
        gameModel.incrementTimer();
        int minutes = gameModel.getTimeElapsed() / 60;
        int seconds = gameModel.getTimeElapsed() % 60;
        gameBoardView.updateTimeCounter(minutes, seconds);

    }

    public void checkDotsCount() {
        if (gameTableModel.getDotsCount() == 0) {
            gameOver(GameResult.WIN);
        }
    }

    public void gameOver(GameResult result) {
        gameModel.setCurrentGameState(GameState.GAME_OVER);
        System.out.println("GAME OVER");
        int score = gameModel.getScore();

        GameOverView gameOverView = new GameOverView(result, score, gameBoardView, this);
        gameOverView.setVisible(true);

    }


    public void gameIsEnded(String name) {
        int score = gameModel.getScore();
        highScoreController.addScore(name, score);
        closeGame();
    }


    public void closeGame() {

        AnimationThread animationThread = gameModel.getAnimationThread();
        animationThread.stopThread();
        pacmanMovementThread.stopThread();
        timerThread.stopThread();
        for (Ghost ghost : this.ghosts) {
            ghost.ghostThread.stopThread();
        }
        GameBoardView.close(gameBoardView);
        MenuController controller = new MenuController();
        controller.openMenu();
    }


}
