package pacman.view;

import java.awt.image.BufferedImage;

public class ItemAnimation {

    private final BufferedImage[] frames;
    private final int frameCount;

    public ItemAnimation (BufferedImage[] frames){
        if (frames == null || frames.length == 0) {
            throw new IllegalArgumentException("No images");
        }
        this.frames = frames;
        this.frameCount = frames.length;

    }
    public BufferedImage getFrame(int animationTick) {
        if (frameCount == 0) {
            return null;
        }
        int frameIndex = animationTick % frameCount;
        return frames[frameIndex];
    }
    public int getFrameCount() {
        return frameCount;
    }

}
