package nl.tue;

import java.awt.image.BufferedImage;

/**
 * The type Soldier.
 */
public class Soldier extends Person {

    /**
     * Instantiates a new Soldier.
     *
     * @param name the name
     * @param xPos the x pos
     * @param yPos the y pos
     * @param hp   the hp
     * @param atk  the atk
     */
    public Soldier(String name, int xPos, int yPos, int hp, int atk) {
        super(name, xPos, yPos, hp, atk);
        setAtkCD(2500);
    }

    @Override
    public BufferedImage getShow() {
        return StaticValue.enemy;
    }
    
    @Override
    protected BufferedImage getPersonImage() {
        return null;
    }
}
