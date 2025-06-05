package pacman.view;

import pacman.controller.HighScoreController;
import pacman.model.GameModel;
import pacman.model.GameTableModel;


import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import pacman.model.PacmanModel;
import pacman.threads.AnimationThread;
import pacman.enums.ItemDirection;

public class GameBoardView extends JFrame {

    private final GameTableModel tableModel;
    private final GameModel gameModel;
    private final PacmanModel pacmanModel;

    private final JTable gameTable;
    private final JPanel centerPanel;
    private final HighScoreController highScoreController;

    private JLabel scoreValue;
    private JLabel highScoreValue;
    private JLabel timeCounterValue;
    private Font baseFont;
    private JPanel bottomPanel;

    private final AnimationThread animationThread;
    private volatile int animationFrameCounter = 0;
    private final int ANIMATION_DELAY_MS = 250;


    public GameBoardView(GameTableModel tableModel, GameModel gameModel, PacmanModel pacmanModel, HighScoreController highScoreController) {

        this.tableModel = tableModel;
        this.gameModel = gameModel;
        this.pacmanModel = pacmanModel;

        this.highScoreController = highScoreController;
        setLayout(new BorderLayout());
        pack();
        setMinimumSize(new Dimension(550, 650));
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 60));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.baseFont = (FontLoader.loadFont("/Font/pacman.ttf"));


        JPanel topPanel = topPanel(baseFont);

        this.gameTable = new JTable(tableModel);
        gameTable.setOpaque(false);
        gameBoardDesign();

        JScrollPane scrollPane = new JScrollPane(gameTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(0, 0, 60));
        centerPanel.add(scrollPane);


        this.bottomPanel = bottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateComponentSizes();
            }
        });

        animationThread = new AnimationThread(this, ANIMATION_DELAY_MS);
        animationThread.start();
        gameModel.setAnimationThread(animationThread);

    }

    public void GameKeyListener(KeyListener listener) {
        this.addKeyListener(listener);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void incrementAnimationFrameCounter() {
        this.animationFrameCounter++;
    }

    public int getAnimationFrame() {
        return this.animationFrameCounter;
    }

    public JTable getGameTable() {
        return this.gameTable;
    }

    public void stopAnimationThread() {
        if (animationThread != null) {
            animationThread.stopThread();
        }
    }

    public ItemDirection getItemDirectionFromModel() {
        return pacmanModel.getItemDirection() != null ? pacmanModel.getItemDirection() : ItemDirection.NONE;
    }


    private void updateComponentSizes() {
        int panelWidth = centerPanel.getWidth() - 10;
        int panelHeight = centerPanel.getHeight() - 10;
        int numCols = tableModel.getColumnCount();
        int numRows = tableModel.getRowCount();

        int cellSizeByWidth = (panelWidth > 0 && numCols > 0) ? panelWidth / numCols : 20;
        int cellSizeByHeight = (panelHeight > 0 && numRows > 0) ? panelHeight / numRows : 20;
        int cellSize = Math.max(5, Math.min(cellSizeByWidth, cellSizeByHeight));

        gameTable.setRowHeight(cellSize);
        Dimension tableDim = new Dimension(cellSize * numCols, cellSize * numRows);
        gameTable.setPreferredScrollableViewportSize(tableDim);
        gameTable.setMinimumSize(tableDim);
        gameTable.setPreferredSize(tableDim);
        gameTable.setMaximumSize(tableDim);

        for (int i = 0; i < numCols; i++) {
            TableColumn column = gameTable.getColumnModel().getColumn(i);
            column.setMinWidth(cellSize);
            column.setMaxWidth(cellSize);
            column.setPreferredWidth(cellSize);
        }

        this.revalidate();
        this.repaint();

    }

    private void gameBoardDesign() {

        gameTable.setDefaultRenderer(Object.class, new PacmanCellRenderer(this));

        final int cellSize = 25;
        gameTable.setRowHeight(cellSize);
        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            TableColumn column = gameTable.getColumnModel().getColumn(i);
            column.setMinWidth(cellSize);
            column.setMaxWidth(cellSize);
            column.setPreferredWidth(cellSize);
        }

        gameTable.setTableHeader(null);
        gameTable.setShowGrid(false);
        gameTable.setIntercellSpacing(new Dimension(0, 0));
        gameTable.setCellSelectionEnabled(false);
        gameTable.setFocusable(false);
        gameTable.setBackground(Color.BLACK);
        gameTable.setOpaque(true);

    }


    private JPanel topPanel(Font baseFont) {
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(0, 0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        baseFont = (baseFont != null)
                ? baseFont.deriveFont(Font.BOLD, 20f)
                : new Font("Arial", Font.BOLD, 40);

        Font headerFont = baseFont.deriveFont(Font.BOLD, 20f);
        Font bodyFont = baseFont.deriveFont(Font.BOLD, 15f);

        GridBagConstraints topIndicators = new GridBagConstraints();

        topIndicators.fill = GridBagConstraints.HORIZONTAL;
        topIndicators.insets = new Insets(5, 10, 0, 10);

        topIndicators.gridx = 0;
        topIndicators.gridy = 0;

        JLabel scoreLabel = new JLabel("Score:");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(scoreLabel, topIndicators);

        topIndicators.gridx = 1;
        topIndicators.gridy = 0;
        JLabel highScoreLabel = new JLabel("High Score:");
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(highScoreLabel, topIndicators);

        topIndicators.gridx = 2;
        topIndicators.gridy = 0;
        JLabel timeCounterLabel = new JLabel("Time Counter:");
        timeCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(timeCounterLabel, topIndicators);

        topIndicators.gridx = 0;
        topIndicators.gridy = 1;
        scoreValue = new JLabel(String.valueOf(gameModel.getScore()));
        scoreValue.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(scoreValue, topIndicators);

        topIndicators.gridx = 1;
        topIndicators.gridy = 1;
        highScoreValue = new JLabel(String.valueOf(highScoreController.getHighestScore()));
        highScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(highScoreValue, topIndicators);

        topIndicators.gridx = 2;
        topIndicators.gridy = 1;
        timeCounterValue = new JLabel("00:00");
        timeCounterValue.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(timeCounterValue, topIndicators);

        scoreLabel.setFont(headerFont);

        scoreLabel.setForeground(Color.orange);
        highScoreLabel.setFont(headerFont);
        highScoreLabel.setForeground(Color.orange);
        timeCounterLabel.setFont(headerFont);
        timeCounterLabel.setForeground(Color.orange);
        scoreValue.setFont(bodyFont);
        scoreValue.setForeground(Color.orange);
        highScoreValue.setFont(bodyFont);
        highScoreValue.setForeground(Color.orange);
        timeCounterValue.setFont(bodyFont);
        timeCounterValue.setForeground(Color.orange);

        return topPanel;
    }


    private JPanel bottomPanel() {

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(new Color(0, 0, 60));

        baseFont = (baseFont != null)
                ? baseFont.deriveFont(Font.BOLD, 20f)
                : new Font("Arial", Font.BOLD, 40);

        BufferedImage imageToDraw = ResourceManager.getImage("LIVE");
        Graphics2D g2d = imageToDraw.createGraphics();
        ImageIcon iconFromBufferedImage = new ImageIcon(imageToDraw);

        for (int i = 0; i < gameModel.getLives(); i++) {
            JLabel lifeLabel = new JLabel(iconFromBufferedImage);
            bottomPanel.add(lifeLabel);
        }

        return bottomPanel;
    }

    public void updateLives(int currentLives) {
        bottomPanel.removeAll();
        this.bottomPanel = bottomPanel();
        bottomPanel.revalidate();
        bottomPanel.repaint();
        add(bottomPanel, BorderLayout.SOUTH);
    }


    public void scoreUpdate() {
        scoreValue.setText(String.valueOf(gameModel.getScore()));
    }

    public void updateTimeCounter(int minutes, int seconds) {
        timeCounterValue.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public static void close(JFrame gameBoardView) {
        gameBoardView.dispose();
    }
}
