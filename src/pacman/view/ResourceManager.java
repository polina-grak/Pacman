package pacman.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, BufferedImage> images = new HashMap<>();
    private static final Map<String, ItemAnimation> animations = new HashMap<>();

    public static void loadAllResources(){

        BufferedImage[] pacmanRightFrames = new BufferedImage[3];
        pacmanRightFrames[0]= ImageLoader.loadImage("/Pacman/mspacman-right_0.png");
        pacmanRightFrames[1]= ImageLoader.loadImage("/Pacman/mspacman-right_1.png");
        pacmanRightFrames[2]= ImageLoader.loadImage("/Pacman/mspacman-right_2.png");
        animations.put("PACMAN_RIGHT", new ItemAnimation(filterNulls(pacmanRightFrames)));

        BufferedImage[] pacmanUpFrames = new BufferedImage[3];
        pacmanUpFrames[0]= ImageLoader.loadImage("/Pacman/mspacman-up_0.png");
        pacmanUpFrames[1]= ImageLoader.loadImage("/Pacman/mspacman-up_1.png");
        pacmanUpFrames[2]= ImageLoader.loadImage("/Pacman/mspacman-up_2.png");
        animations.put("PACMAN_UP", new ItemAnimation(filterNulls(pacmanUpFrames)));

        BufferedImage[] pacmanLeftFrames = new BufferedImage[3];
        pacmanLeftFrames[0]= ImageLoader.loadImage("/Pacman/mspacman-left_0.png");
        pacmanLeftFrames[1]= ImageLoader.loadImage("/Pacman/mspacman-left_1.png");
        pacmanLeftFrames[2]= ImageLoader.loadImage("/Pacman/mspacman-left_2.png");
        animations.put("PACMAN_LEFT", new ItemAnimation(filterNulls(pacmanLeftFrames)));

        BufferedImage[] pacmanDownFrames = new BufferedImage[3];
        pacmanDownFrames[0]= ImageLoader.loadImage("/Pacman/mspacman-down_0.png");
        pacmanDownFrames[1]= ImageLoader.loadImage("/Pacman/mspacman-down_1.png");
        pacmanDownFrames[2]= ImageLoader.loadImage("/Pacman/mspacman-down_2.png");
        animations.put("PACMAN_DOWN", new ItemAnimation(filterNulls(pacmanDownFrames)));

        //Pinky

        BufferedImage[] ghostRedUpFrames = new BufferedImage[2];
        ghostRedUpFrames[0]= ImageLoader.loadImage("/Ghosts/Red/blinky-up-0.png");
        ghostRedUpFrames[1]= ImageLoader.loadImage("/Ghosts/Red/blinky-up-1.png");
        animations.put("GHOST_RED_UP", new ItemAnimation(filterNulls(ghostRedUpFrames)));

        BufferedImage[] ghostRedLeftFrames = new BufferedImage[2];
        ghostRedLeftFrames[0]= ImageLoader.loadImage("/Ghosts/Red/blinky-left-0.png");
        ghostRedLeftFrames[1]= ImageLoader.loadImage("/Ghosts/Red/blinky-left-1.png");
        animations.put("GHOST_RED_LEFT", new ItemAnimation(filterNulls(ghostRedLeftFrames)));


        BufferedImage[] ghostRedRightFrames = new BufferedImage[2];
        ghostRedRightFrames[0]= ImageLoader.loadImage("/Ghosts/Red/blinky-right-0.png");
        ghostRedRightFrames[1]= ImageLoader.loadImage("/Ghosts/Red/blinky-right-1.png");
        animations.put("GHOST_RED_RIGHT", new ItemAnimation(filterNulls(ghostRedRightFrames)));

        BufferedImage[] ghostRedDownFrames = new BufferedImage[2];
        ghostRedDownFrames[0]= ImageLoader.loadImage("/Ghosts/Red/blinky-down-0.png");
        ghostRedDownFrames[1]= ImageLoader.loadImage("/Ghosts/Red/blinky-down-1.png");
        animations.put("GHOST_RED_DOWN", new ItemAnimation(filterNulls(ghostRedDownFrames)));

        //Inky

        BufferedImage[] ghostCyanUpFrames = new BufferedImage[2];
        ghostCyanUpFrames[0]= ImageLoader.loadImage("/Ghosts/Cyan/inky-up-0.png");
        ghostCyanUpFrames[1]= ImageLoader.loadImage("/Ghosts/Cyan/inky-up-1.png");
        animations.put("GHOST_CYAN_UP", new ItemAnimation(filterNulls(ghostCyanUpFrames)));

        BufferedImage[] ghostCyanLeftFrames = new BufferedImage[2];
        ghostCyanLeftFrames[0]= ImageLoader.loadImage("/Ghosts/Cyan/inky-left-0.png");
        ghostCyanLeftFrames[1]= ImageLoader.loadImage("/Ghosts/Cyan/inky-left-1.png");
        animations.put("GHOST_CYAN_LEFT", new ItemAnimation(filterNulls(ghostCyanLeftFrames)));


        BufferedImage[] ghostCyanRightFrames = new BufferedImage[2];
        ghostCyanRightFrames[0]= ImageLoader.loadImage("/Ghosts/Cyan/inky-right-0.png");
        ghostCyanRightFrames[1]= ImageLoader.loadImage("/Ghosts/Cyan/inky-right-1.png");
        animations.put("GHOST_CYAN_RIGHT", new ItemAnimation(filterNulls(ghostCyanRightFrames)));


        BufferedImage[] ghostCyanDownFrames = new BufferedImage[2];
        ghostCyanDownFrames[0]= ImageLoader.loadImage("/Ghosts/Cyan/inky-down-0.png");
        ghostCyanDownFrames[1]= ImageLoader.loadImage("/Ghosts/Cyan/inky-down-1.png");
        animations.put("GHOST_CYAN_DOWN", new ItemAnimation(filterNulls(ghostCyanDownFrames)));


        //Pinky

        BufferedImage[] ghostPinkUpFrames = new BufferedImage[2];
        ghostPinkUpFrames[0]= ImageLoader.loadImage("/Ghosts/Pink/pinky-up-0.png");
        ghostPinkUpFrames[1]= ImageLoader.loadImage("/Ghosts/Pink/pinky-up-1.png");
        animations.put("GHOST_PINK_UP", new ItemAnimation(filterNulls(ghostPinkUpFrames)));


        BufferedImage[] ghostPinkLeftFrames = new BufferedImage[2];
        ghostPinkLeftFrames[0]= ImageLoader.loadImage("/Ghosts/Pink/pinky-left-0.png");
        ghostPinkLeftFrames[1]= ImageLoader.loadImage("/Ghosts/Pink/pinky-left-1.png");
        animations.put("GHOST_PINK_LEFT", new ItemAnimation(filterNulls(ghostPinkLeftFrames)));


        BufferedImage[] ghostPinkRightFrames = new BufferedImage[2];
        ghostPinkRightFrames[0]= ImageLoader.loadImage("/Ghosts/Pink/pinky-right-0.png");
        ghostPinkRightFrames[1]= ImageLoader.loadImage("/Ghosts/Pink/pinky-right-1.png");
        animations.put("GHOST_PINK_RIGHT", new ItemAnimation(filterNulls(ghostPinkRightFrames)));


        BufferedImage[] ghostPinkDownFrames = new BufferedImage[2];
        ghostPinkDownFrames[0]= ImageLoader.loadImage("/Ghosts/Pink/pinky-down-0.png");
        ghostPinkDownFrames[1]= ImageLoader.loadImage("/Ghosts/Pink/pinky-down-1.png");
        animations.put("GHOST_PINK_DOWN", new ItemAnimation(filterNulls(ghostPinkDownFrames)));




        images.put("DOT", ImageLoader.loadImage("/Food/food2.png"));

        images.put("POWER_PELLET", ImageLoader.loadImage("/Food/Pfood.png"));

        images.put("EXTRA_LIFE", ImageLoader.loadImage("/Food/extra_life.png"));

        images.put("EXTRA_FOOD", ImageLoader.loadImage("/Food/extra_food.png"));

        images.put("GHOST_EATER", ImageLoader.loadImage("/Food/ghost_eater.png"));

        images.put("INVISIBILITY", ImageLoader.loadImage("/Food/invincibility.png"));

        images.put("SCORE_MULT", ImageLoader.loadImage("/Food/score_multiplier.png"));

        images.put("PACMAN_LOGO", ImageLoader.loadImage("/Pac-Man-Logo.png"));

        images.put("GAME_OVER", ImageLoader.loadImage("/Other/gameover.png"));

        images.put("VICTORY", ImageLoader.loadImage("/Other/victory.png"));

        images.put("LIVE", ImageLoader.loadImage("/Other/heart13.png"));



    };


    private static BufferedImage[] filterNulls(BufferedImage[] frames) {
        if (frames == null) return new BufferedImage[0];
        java.util.List<BufferedImage> list = new java.util.ArrayList<>();
        for (BufferedImage frame : frames) {
            if (frame != null) {
                list.add(frame);
            }
        }
        return list.toArray(new BufferedImage[0]);
    }

    public static BufferedImage getImage(String key) {
        BufferedImage img = images.get(key);
        if (img == null) {
            System.err.println("Image wasn't found by key: " + key);
        }
        return img;
    }


    public static ItemAnimation getAnimation(String key) {
        ItemAnimation anim = animations.get(key);
        if (anim == null) {
            System.err.println(" No animation for key:" + key);
        }
        return anim;
    }
}
