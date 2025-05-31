package pacman.view;
import pacman.model.CellState;
import pacman.model.ItemDirection;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PacmanCellRenderer extends JPanel implements TableCellRenderer {
    private CellState currentState;
    private GameBoardView gameBoardView;

    private BufferedImage pacmanRight0;
    private BufferedImage pacmanRight1;
    private BufferedImage ghostImage;

    public PacmanCellRenderer (GameBoardView gameBoardView){
        this.gameBoardView = gameBoardView;
        setOpaque(true);

        this.pacmanRight0 = ImageLoader.loadImage("/Pacman/mspacman-right_0.png");
        this.pacmanRight1 = ImageLoader.loadImage("/Pacman/mspacman-right_1.png");


    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.currentState = (CellState) value;
        if (currentState == CellState.WALL) {
            setBackground(Color.BLUE);
        } else {
            setBackground(Color.BLACK);
        }
        return this;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int currentAnimationTick = gameBoardView.getAnimationFrame();

        switch (currentState) {
            case PACMAN:
                ItemDirection pacmanDir = gameBoardView.getItemDirectionFromModel();
                ItemAnimation currentPacmanAnimation = null;
                if (pacmanDir != null) {
                    switch (pacmanDir) {
                        case UP:
                            currentPacmanAnimation = ResourceManager.getAnimation("PACMAN_UP");
                            break;
                        case DOWN:
                            currentPacmanAnimation = ResourceManager.getAnimation("PACMAN_DOWN");
                            break;
                        case LEFT:
                            currentPacmanAnimation = ResourceManager.getAnimation("PACMAN_LEFT");
                            break;
                        case RIGHT:
                            currentPacmanAnimation = ResourceManager.getAnimation("PACMAN_RIGHT");
                            break;
                    }

                }
                if (currentPacmanAnimation != null) {
                    int animationTick = gameBoardView.getAnimationFrame();
                    BufferedImage spriteToDraw = currentPacmanAnimation.getFrame(animationTick);
                    if (spriteToDraw != null) {
                        g2d.drawImage(spriteToDraw, 0, 0, getWidth(), getHeight(), null);
                    } else {
                        drawFallbackPacman(g2d);
                    }
                } else {
                    drawFallbackPacman(g2d);
                }
                break;

        }
    }
    private void drawFallbackPacman(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
    }
}
