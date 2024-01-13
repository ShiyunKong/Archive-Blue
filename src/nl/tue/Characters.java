package nl.tue;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The type Characters.
 */
public class Characters extends Person {
    private List<BufferedImage> runAnimation;
    private BufferedImage shoot;
    private boolean isShooting = false;

    /**
     * Instantiates a new Characters.
     *
     * @param name          the name
     * @param runAnimation  the run animation
     * @param xPos          the x pos
     * @param yPos          the y pos
     * @param shoot         the shoot
     * @param hp            the hp
     * @param atk           the atk
     * @param skill         the skill
     * @param skillMultiple the skill multiple
     * @param skillCD       the skill cd
     */
    public Characters(String name, List<BufferedImage> runAnimation,
                      int xPos, int yPos, BufferedImage shoot,
                      int hp, int atk, BufferedImage skill, double skillMultiple, int skillCD) {

        super(name, xPos, yPos, hp, atk, skill, skillMultiple, skillCD);
        setAtkCD(1000);
        this.runAnimation = runAnimation;
        this.shoot = shoot;
    }

    /**
     * Instantiates a new Characters.
     *
     * @param name          the name
     * @param xPos          the x pos
     * @param yPos          the y pos
     * @param hp            the hp
     * @param atk           the atk
     * @param skill         the skill
     * @param skillMultiple the skill multiple
     * @param skillCD       the skill cd
     */
    public Characters(String name, int xPos, int yPos,
                      int hp, int atk, BufferedImage skill, double skillMultiple, int skillCD) {
        super(name, xPos, yPos, hp, atk, skill, skillMultiple, skillCD);
    }
    
    private int currentFrame = 0;
    private long lastFrameTime = 0;

    private BufferedImage updateAnimation() {
        long currentTime = System.currentTimeMillis();
        // Delay between frames in milliseconds
        int frameDelay = 200;
        if (currentTime - lastFrameTime > frameDelay) {
            currentFrame = (currentFrame + 1) % runAnimation.size();
            lastFrameTime = currentTime;
        }
        return runAnimation.get(currentFrame);
    }


    @Override
    public BufferedImage getShow() {
        BufferedImage show = null;
        if (!isShooting) {
            show = updateAnimation();
        } else {
            show = shoot;
        }
        return show;
    }

    @Override
    protected BufferedImage getPersonImage() {
        return null;
    }

    /**
     * Is shooting boolean.
     *
     * @return the boolean
     */
    public boolean isShooting() {
        return isShooting;
    }

    /**
     * Sets shooting.
     *
     * @param shooting the shooting
     */
    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
}
