package pacman.view;

import pacman.enums.CellState;
import pacman.enums.ItemDirection;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PacmanCellRenderer extends JPanel implements TableCellRenderer {
    private CellState currentState;
    private GameBoardView gameBoardView;

    public PacmanCellRenderer(GameBoardView gameBoardView) {
        this.gameBoardView = gameBoardView;
        setOpaque(true);

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.currentState = (CellState) value;


        if (currentState == CellState.WALL) {
            setBackground(Color.BLUE);
        } else if (currentState == CellState.GHOST_WALL) {
            setBackground(new Color(100, 100, 100));
        } else setBackground(Color.BLACK);


        return this;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int currentAnimationTick = gameBoardView.getAnimationFrame();
        BufferedImage imageToDraw = null;

        ItemDirection itemDir = gameBoardView.getItemDirectionFromModel();

        ItemAnimation currentAnimation = null;


        switch (currentState) {
            case PACMAN:

                if (itemDir != null) {
                    switch (itemDir) {
                        case UP:
                            currentAnimation = ResourceManager.getAnimation("PACMAN_UP");
                            break;
                        case DOWN:
                            currentAnimation = ResourceManager.getAnimation("PACMAN_DOWN");
                            break;
                        case LEFT:
                            currentAnimation = ResourceManager.getAnimation("PACMAN_LEFT");
                            break;
                        case RIGHT:
                            currentAnimation = ResourceManager.getAnimation("PACMAN_RIGHT");
                            break;
                    }

                }
                if (currentAnimation != null) {
                    int animationTick = gameBoardView.getAnimationFrame();
                    BufferedImage spriteToDraw = currentAnimation.getFrame(animationTick);
                    if (spriteToDraw != null) {
                        g2d.drawImage(spriteToDraw, 0, 0, getWidth(), getHeight(), null);
                    } else {
                        drawFallbackPacman(g2d);
                    }
                } else {
                    drawFallbackPacman(g2d);
                }
                break;

            case GHOST_RED:

                if (itemDir != null) {
                    switch (itemDir) {
                        case UP:
                            currentAnimation = ResourceManager.getAnimation("GHOST_RED_UP");
                            break;
                        case DOWN:
                            currentAnimation = ResourceManager.getAnimation("GHOST_RED_DOWN");
                            break;
                        case LEFT:
                            currentAnimation = ResourceManager.getAnimation("GHOST_RED_LEFT");
                            break;
                        case RIGHT:
                            currentAnimation = ResourceManager.getAnimation("GHOST_RED_RIGHT");
                            break;
                    }
                }
                if (currentAnimation != null) {
                    int animationTick = gameBoardView.getAnimationFrame();
                    BufferedImage spriteToDraw = currentAnimation.getFrame(animationTick);
                    if (spriteToDraw != null) {
                        g2d.drawImage(spriteToDraw, 0, 0, getWidth(), getHeight(), null);
                    } else {
                        drawFallbackGhost(g2d);
                    }
                } else {
                    drawFallbackGhost(g2d);
                }
                break;
            case GHOST_PINK:
                if (itemDir != null) {
                    switch (itemDir) {
                        case UP:
                            currentAnimation = ResourceManager.getAnimation("GHOST_PINK_UP");
                            break;
                        case DOWN:
                            currentAnimation = ResourceManager.getAnimation("GHOST_PINK_DOWN");
                            break;
                        case LEFT:
                            currentAnimation = ResourceManager.getAnimation("GHOST_PINK_LEFT");
                            break;
                        case RIGHT:
                            currentAnimation = ResourceManager.getAnimation("GHOST_PINK_RIGHT");
                            break;
                    }
                }
                if (currentAnimation != null) {
                    int animationTick = gameBoardView.getAnimationFrame();
                    BufferedImage spriteToDraw = currentAnimation.getFrame(animationTick);
                    if (spriteToDraw != null) {
                        g2d.drawImage(spriteToDraw, 0, 0, getWidth(), getHeight(), null);
                    } else {
                        drawFallbackGhost(g2d);
                    }
                } else {
                    drawFallbackGhost(g2d);
                }
                break;
            case GHOST_CYAN:
                if (itemDir != null) {
                    switch (itemDir) {
                        case UP:
                            currentAnimation = ResourceManager.getAnimation("GHOST_CYAN_UP");
                            break;
                        case DOWN:
                            currentAnimation = ResourceManager.getAnimation("GHOST_CYAN_DOWN");
                            break;
                        case LEFT:
                            currentAnimation = ResourceManager.getAnimation("GHOST_CYAN_LEFT");
                            break;
                        case RIGHT:
                            currentAnimation = ResourceManager.getAnimation("GHOST_CYAN_RIGHT");
                            break;
                    }
                }
                if (currentAnimation != null) {
                    int animationTick = gameBoardView.getAnimationFrame();
                    BufferedImage spriteToDraw = currentAnimation.getFrame(animationTick);
                    if (spriteToDraw != null) {
                        g2d.drawImage(spriteToDraw, 0, 0, getWidth(), getHeight(), null);
                    } else {
                        drawFallbackGhost(g2d);
                    }
                } else {
                    drawFallbackGhost(g2d);
                }
                break;
            case DOT:
                imageToDraw = ResourceManager.getImage("DOT");
                break;
            case POWER_PELLET:
                imageToDraw = ResourceManager.getImage("POWER_PELLET");
                break;
            case EXTRA_LIFE_UPGRADE:
                imageToDraw = ResourceManager.getImage("EXTRA_LIFE");
                break;
            case EXTRA_FOOD:
                imageToDraw = ResourceManager.getImage("EXTRA_FOOD");
                break;
            case GHOST_EATER_UPGRADE:
                imageToDraw = ResourceManager.getImage("GHOST_EATER");
                break;
            case INVISIBILITY_UPGRADE:
                imageToDraw = ResourceManager.getImage("INVISIBILITY");
                break;


        }
        if (imageToDraw != null) {
            g2d.drawImage(imageToDraw, 0, 0, getWidth(), getHeight(), null);
        }

    }

    private void drawFallbackPacman(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
    }

    private void drawFallbackGhost(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
    }

}
