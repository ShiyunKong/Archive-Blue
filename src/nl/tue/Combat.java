package nl.tue;

import java.awt.*;

/**
 * The interface Combat.
 */
public interface Combat {
    /**
     * Add stats.
     *
     * @param g     the g
     * @param color the color
     */
    void addStats(Graphics g, Color color);

    /**
     * Flying bullet x int.
     *
     * @param selfX     the self x
     * @param opponentX the opponent x
     * @return the int
     */
    default int flyingBulletX(int selfX, int opponentX) { // character x, enemy x
        return findDelta(selfX, opponentX);
    }

    /**
     * Flying bullet y int.
     *
     * @param selfY     the self y
     * @param opponentY the opponent y
     * @return the int
     */
    default int flyingBulletY(int selfY, int opponentY) { // character y, enemy y
        return findDelta(selfY, opponentY);
    }

    /**
     * Find delta int.
     *
     * @param bound1 the bound 1
     * @param bound2 the bound 2
     * @return the int
     */
    default int findDelta(int bound1, int bound2) {
        int delta = bound2 - bound1;
        int pos = delta / 2 + bound1;
        return pos;
    }

    /**
     * Normal attack.
     *
     * @param target the target
     * @throws InterruptedException the interrupted exception
     */
    void normalAttack(Person... target) throws InterruptedException;

    /**
     * Skill release boss.
     *
     * @param targets the targets
     * @throws InterruptedException the interrupted exception
     */
    void skillReleaseBoss(Person... targets) throws InterruptedException;

    /**
     * Use skill shield.
     *
     * @param target the target
     * @throws InterruptedException the interrupted exception
     */
    default void useSkillShield(Person target) throws InterruptedException {
        target.setShieldPoints((int) (target.getInitialHP() * target.getSkillMultiple()));
        target.setShieldExist(true);
        target.setRemainingCD(target.getSkillCD());
        for (int i = 0; i < ((target.getSkillCD()) / 50); i++) {
            target.setRemainingCD(target.getRemainingCD() - 50);
            Thread.sleep(50);
        }
        target.setReapplyAllowed(true);
    }

    /**
     * Use skill heal.
     *
     * @param targets the targets
     * @throws InterruptedException the interrupted exception
     */
    void useSkillHeal(Person... targets) throws InterruptedException;

    /**
     * Use skill rail gun.
     *
     * @param targets the targets
     * @throws InterruptedException the interrupted exception
     */
    void useSkillRailGun(Person... targets) throws InterruptedException;

    /**
     * Use skill charm.
     *
     * @param target the target
     * @throws InterruptedException the interrupted exception
     */
    void useSkillCharm(Person target) throws InterruptedException;
}
