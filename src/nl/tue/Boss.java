package nl.tue;

import java.awt.image.BufferedImage;

/**
 * The type Boss.
 */
public class Boss extends Person {

    /**
     * Instantiates a new Boss.
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
    // Combat Stats
    public Boss(String name, int xPos, int yPos, int hp, int atk,
                BufferedImage skill, double skillMultiple, int skillCD) {
        super(name, xPos, yPos, hp, atk, skill, skillMultiple, skillCD);
        setAtkCD(5000);
    }

    @Override
    public BufferedImage getShow() {
        return StaticValue.boss;
    }

    @Override
    protected BufferedImage getPersonImage() {
        return null;
    }
}
