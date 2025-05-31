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


        BufferedImage[] ghostRedUpFrames = new BufferedImage[2];
        ghostRedUpFrames[0]= ImageLoader.loadImage("/Ghosts/Red/blinky-up-0.png");
        ghostRedUpFrames[1]= ImageLoader.loadImage("/Ghosts/Red/blinky-up-1.png");
        animations.put("GHOST_RED_UP", new ItemAnimation(filterNulls(ghostRedUpFrames)));


        images.put("GAME_OVER_SCREEN", ImageLoader.loadImage("/Other/gameover.png"));


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
