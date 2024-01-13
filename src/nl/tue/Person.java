package nl.tue;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * The type Person.
 */
public abstract class Person implements Moving, Combat {
    private final String name;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    private int xPos;
    private final int yPos;
    private volatile int bulletX;
    private volatile int bulletY;
    private volatile boolean dead = false;

    private Battlefield battlefield;

    /**
     * Sets battlefield.
     *
     * @param battlefield the battlefield
     */
    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /**
     * Gets battlefield.
     *
     * @return the battlefield
     */
    public Battlefield getBattlefield() {
        return battlefield;
    }

    /**
     * Is dead boolean.
     *
     * @return the boolean
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Sets dead.
     *
     * @param dead the dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Gets bullet x.
     *
     * @return the bullet x
     */
    public int getBulletX() {
        return bulletX;
    }

    /**
     * Sets bullet x.
     *
     * @param bulletX the bullet x
     */
    public void setBulletX(int bulletX) {
        this.bulletX = bulletX;
    }

    /**
     * Gets bullet y.
     *
     * @return the bullet y
     */
    public int getBulletY() {
        return bulletY;
    }

    /**
     * Sets bullet y.
     *
     * @param bulletY the bullet y
     */
    public void setBulletY(int bulletY) {
        this.bulletY = bulletY;
    }

    private int skillX;
    private int skillY;

    /**
     * Gets skill x.
     *
     * @return the skill x
     */
    public int getSkillX() {
        return skillX;
    }

    /**
     * Sets skill x.
     *
     * @param skillX the skill x
     */
    public void setSkillX(int skillX) {
        this.skillX = skillX;
    }

    /**
     * Gets skill y.
     *
     * @return the skill y
     */
    public int getSkillY() {
        return skillY;
    }

    /**
     * Sets skill y.
     *
     * @param skillY the skill y
     */
    public void setSkillY(int skillY) {
        this.skillY = skillY;
    }

    private boolean moving = false;

    private boolean removed = false;

    /**
     * Is removed boolean.
     *
     * @return the boolean
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * Sets removed.
     *
     * @param removed the removed
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    // Combat Stats
    private int hp;
    private final int initialHP;
    private int atk;
    private int atkCD;
    private static boolean isAtkCD = true;

    /**
     * Gets hp.
     *
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets hp.
     *
     * @param hp the hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Gets atk.
     *
     * @return the atk
     */
    public int getAtk() {
        return atk;
    }

    /**
     * Sets atk.
     *
     * @param atk the atk
     */
    public void setAtk(int atk) {
        this.atk = atk;
    }

    /**
     * Gets atk cd.
     *
     * @return the atk cd
     */
    public int getAtkCD() {
        return atkCD;
    }

    /**
     * Sets atk cd.
     *
     * @param atkCD the atk cd
     */
    public void setAtkCD(int atkCD) {
        this.atkCD = atkCD;
    }

    /**
     * Gets is atk cd.
     *
     * @return the is atk cd
     */
    public boolean getIsAtkCD() {
        return isAtkCD;
    }

    /**
     * Sets is atk cd.
     *
     * @param atkCD the atk cd
     */
    public static void setIsAtkCD(boolean atkCD) {
        isAtkCD = atkCD;
    }

    private double skillMultiple;

    /**
     * Gets skill multiple.
     *
     * @return the skill multiple
     */
    public double getSkillMultiple() {
        return skillMultiple;
    }

    private BufferedImage skill;

    /**
     * Gets skill.
     *
     * @return the skill
     */
    public BufferedImage getSkill() {
        return skill;
    }

    private int skillCD; // ms
    private boolean isSkillCD;
    private int shieldPoints;

    /**
     * Gets shield points.
     *
     * @return the shield points
     */
    public int getShieldPoints() {
        return shieldPoints;
    }

    /**
     * Sets shield points.
     *
     * @param shieldPoints the shield points
     */
    public void setShieldPoints(int shieldPoints) {
        this.shieldPoints = shieldPoints;
    }
    // Combat Stats

    /**
     * Instantiates a new Person.
     *
     * @param name the name
     * @param xPos the x pos
     * @param yPos the y pos
     * @param hp   the hp
     * @param atk  the atk
     */
    public Person(String name, int xPos, int yPos, int hp, int atk) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.initialHP = hp;
        this.hp = hp;
        this.atk = atk;
    }

    /**
     * Instantiates a new Person.
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
    public Person(String name, int xPos, int yPos, int hp, int atk,
                  BufferedImage skill, double skillMultiple, int skillCD) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.initialHP = hp;
        this.hp = hp;
        this.atk = atk;
        this.skill = skill;
        this.skillMultiple = skillMultiple;
        this.skillCD = skillCD;
    }

    public void startMoving() {
        moving = true;
    }

    public void stopMoving() {
        moving = false;
    }


    /**
     * Updates the position of the object, considering scrolling.
     * If the object is marked as moving, its horizontal position (xPos) is adjusted
     * to account for the current scroll speed provided by the Battlefield class.
     * This method is typically called during each frame update to ensure the object
     * maintains its position relative to the scrolling background.
     *
     * @see Battlefield#getScrollSpeed()
     */
    public void updates() {
        if (moving) {
            xPos -= Battlefield.getScrollSpeed();
        }
    }

    /**
     * Gets the current horizontal position (x-coordinate) of the object, accounting for scrolling.
     * This method first updates the object's position using the `updates` method to ensure
     * that the position reflects the current scroll speed. 
     * It then returns the updated x-coordinate.
     *
     * @return The current horizontal position of the object, adjusted for scrolling.
     * @see #updates()
     */
    public int getXPos() {
        updates();
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    /**
     * Gets show.
     *
     * @return the show
     */
    public BufferedImage getShow() {
        return getPersonImage();
    }

    /**
     * Gets person image.
     *
     * @return the person image
     */
    protected abstract BufferedImage getPersonImage();

    /**
     * Gets skill cd.
     *
     * @return the skill cd
     */
    public int getSkillCD() {
        return skillCD;
    }

    /**
     * Sets skill cd.
     *
     * @param skillCD the skill cd
     */
    public void setSkillCD(int skillCD) {
        this.skillCD = skillCD;
    }

    /**
     * Sets is skill cd.
     *
     * @param skillCD the skill cd
     */
    public void setIsSkillCD(boolean skillCD) {
        this.isSkillCD = skillCD;
    }

    /**
     * Is skill cd boolean.
     *
     * @return the boolean
     */
    public boolean isSkillCD() {
        return isSkillCD;
    }

    /**
     * Gets initial hp.
     *
     * @return the initial hp
     */
    public int getInitialHP() {
        return initialHP;
    }

    @Override
    public void addStats(Graphics g, Color color) {
        int difX = 0;
        int difY = 40;
        int width = 200;
        int height = 20;

        // hp frame
        g.setColor(Color.BLACK);
        g.drawRect(this.getXPos() - difX, this.getYPos() - difY, width, height);

        // hp fill
        g.setColor(color);
        g.fillRect(this.getXPos() - difX, this.getYPos() - difY,
                (int) (width * this.getHp() / this.getInitialHP()), height);

        // Display HP value as text
        g.setColor(Color.BLACK); // Set the text color
        int hpValue = this.getHp(); // Get the HP value
        String hpText = "HP: " + hpValue; // Create the HP text
        Font fontHP = new Font("Arial", Font.PLAIN, 12); // Choose a font and size
        g.setFont(fontHP); // Set the font
        g.drawString(hpText, this.getXPos() - difX + 10, this.getYPos() - difY + 15);

        if (this.shieldExist) {
            // shield frame
            g.setColor(Color.BLACK);
            g.drawRect(this.getXPos() - difX, this.getYPos() - difY - 25, width, height);

            // shield fill
            g.setColor(Color.blue);
            g.fillRect(this.getXPos() - difX, this.getYPos() - difY - 25,
                    (int) (width * this.getShieldPoints() 
                    / (this.getHp() * this.getSkillMultiple())), height);

            // Display shield value as text
            g.setColor(Color.BLACK); // Set the text color
            int shieldValue = this.getShieldPoints(); // Get the HP value
            String shieldText = "Shield: " + shieldValue; // Create the HP text
            Font fontShield = new Font("Arial", Font.PLAIN, 12); // Choose a font and size
            g.setFont(fontShield); // Set the font
            g.drawString(shieldText, this.getXPos() - difX + 10, this.getYPos() - difY - 10);

        }
        if (this.skillCD > 0) {
            // Display skillCD value as text
            g.setColor(Color.BLACK); // Set the text color
            double skillCdValue = (double) this.getRemainingCD() / 1000; // Get the HP value
            String skillCdText = "Skill CD: " + skillCdValue; // Create the HP text
            Font fontCD = new Font("Arial", Font.PLAIN, 12); // Choose a font and size
            g.setFont(fontCD); // Set the font
            g.drawString(skillCdText, this.getXPos() - difX + 10, this.getYPos() - difY + 35);

        }

    }

    public int flyingBulletX(int selfX, int opponentX) {
        return findDelta(selfX, opponentX);
    }

    public int flyingBulletY(int selfY, int opponentY) {
        return findDelta(selfY, opponentY);
    }

    @Override
    public int findDelta(int bound1, int bound2) {
        Random randInt = new Random();
        return randInt.nextInt(0, Math.abs(bound2 - bound1));
    }

    private boolean reapplyAllowed = true;

    /**
     * Is reapply allowed boolean.
     *
     * @return the boolean
     */
    public boolean isReapplyAllowed() {
        return reapplyAllowed;
    }

    /**
     * Sets reapply allowed.
     *
     * @param reapplyAllowed the reapply allowed
     */
    public void setReapplyAllowed(boolean reapplyAllowed) {
        this.reapplyAllowed = reapplyAllowed;
    }

    private boolean unleashShield;

    /**
     * Is unleash shield boolean.
     *
     * @return the boolean
     */
    public boolean isUnleashShield() {
        return unleashShield;
    }

    /**
     * Sets unleash shield.
     *
     * @param unleashShield the unleash shield
     */
    public void setUnleashShield(boolean unleashShield) {
        this.unleashShield = unleashShield;
    }

    private boolean shieldExist = false;

    /**
     * Is shield exist boolean.
     *
     * @return the boolean
     */
    public boolean isShieldExist() {
        return shieldExist;
    }

    /**
     * Sets shield exist.
     *
     * @param shieldExist the shield exist
     */
    public void setShieldExist(boolean shieldExist) {
        this.shieldExist = shieldExist;
        this.unleashShield = shieldExist;
    }

    @Override
    public void normalAttack(Person... targets) throws InterruptedException {
        if (Battlefield.getMovementsPaused()) {
            for (Person target : targets) {
                // System.out.println(this.getName() + "->" + target.getName());
                if (!dead && target.getShieldPoints() > 0) {
                    if (!Battlefield.getMovementsPaused()) {
                        break;
                    }
                    // Update the location of characterShots for the attack effect
                    this.setBulletX(flyingBulletX(this.getXPos(), target.getXPos()));
                    this.setBulletY(flyingBulletY(this.getYPos(), target.getYPos()));
                    // Perform character attack logic
                    target.setShieldPoints(target.getShieldPoints() - this.getAtk());
                    // System.out.println(target.getName() + "Shield" + target.getHp());
                    if (target.getShieldPoints() <= 0) {
                        target.setShieldExist(false);
                    }
                    Thread.sleep(this.getAtkCD());
                    break;
                }
                if (!dead && target.getHp() > 0) {
                    if (!Battlefield.getMovementsPaused()) {
                        break;
                    }
                    // Update the location of characterShots for the attack effect
                    this.setBulletX(flyingBulletX(this.getXPos(), target.getXPos()));
                    this.setBulletY(flyingBulletY(this.getYPos(), target.getYPos()));
                    // Perform character attack logic
                    target.setHp(target.getHp() - this.getAtk());
                    // System.out.println(target.getName() + "HP" + target.getHp());
                    if (target.getHp() <= 0) {
                        target.setDead(true);
                        target.setRemoved(true);
                    }
                    Thread.sleep(this.getAtkCD());
                    break;
                }
            }
        }
    }

    private boolean unleashBomb;

    /**
     * Is unleash bomb boolean.
     *
     * @return the boolean
     */
    public boolean isUnleashBomb() {
        return unleashBomb;
    }

    private final int threadSleepTime = 50;

    @Override
    public void skillReleaseBoss(Person... targets) throws InterruptedException {
        if (Battlefield.getMovementsPaused()) {
            for (Person target : targets) {
                // System.out.println(this.getName() + "->" + target.getName());
                if (!dead && target.getShieldPoints() > 0) {
                    if (!Battlefield.getMovementsPaused()) {
                        break;
                    }
                    // Update the location of characterShots for the attack effect
                    this.setSkillX(target.getXPos());
                    this.setSkillY(target.getYPos());
                    unleashBomb = true;
                    // Perform character attack logic
                    target.setShieldPoints((int) (target.getShieldPoints() -  (target.getHp()
                                                - this.getAtk() * this.getSkillMultiple())));
                    // System.out.println(target.getName() + "Shield" + target.getHp());
                    if (target.getShieldPoints() <= 0) {
                        target.setShieldExist(false);
                    }
                    this.setRemainingCD(this.getSkillCD());
                    for (int i = 0; i < (1000 / threadSleepTime); i++) {
                        this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                        Thread.sleep(threadSleepTime);
                    }
                    unleashBomb = false;
                    for (int i = 0; i < ((this.getSkillCD() - 1000) / threadSleepTime); i++) {
                        this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                        Thread.sleep(threadSleepTime);
                    }
                    break;
                }
                if (!dead && target.getHp() > 0) {
                    if (!Battlefield.getMovementsPaused()) {
                        break;
                    }
                    this.setSkillX(target.getXPos());
                    this.setSkillY(target.getYPos());
                    unleashBomb = true;
                    target.setHp((int) (target.getHp()
                            - this.getAtk() * this.getSkillMultiple()));
                    // System.out.println(target.getName() + "HP" + target.getHp());
                    if (target.getHp() <= 0) {
                        target.setDead(true);
                        target.setRemoved(true);
                    }
                    this.setRemainingCD(this.getSkillCD());
                    for (int i = 0; i < (1000 / threadSleepTime); i++) {
                        this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                        Thread.sleep(threadSleepTime);
                    }
                    unleashBomb = false;
                    for (int i = 0; i < ((this.getSkillCD() - 1000) / threadSleepTime); i++) {
                        this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                        Thread.sleep(threadSleepTime);
                    }
                    break;
                }
            }
        }
    }

    private int remainingCD;

    /**
     * Gets remaining cd.
     *
     * @return the remaining cd
     */
    public int getRemainingCD() {
        return remainingCD;
    }

    /**
     * Sets remaining cd.
     *
     * @param remainingCD the remaining cd
     */
    public void setRemainingCD(int remainingCD) {
        this.remainingCD = remainingCD;
    }

    @Override
    public void useSkillShield(Person target) throws InterruptedException {
        Combat.super.useSkillShield(target);
    }

    @Override
    public void useSkillHeal(Person... targets) throws InterruptedException {
        this.setRemainingCD(this.getSkillCD());
        // character.setSkillX(character.getXPos() + 200);
        // character.setSkillY(character.getYPos());
        for (Person target : targets) {
            if (!dead && target.getHp() > 0) {
                if (!Battlefield.getMovementsPaused()) {
                    break;
                }
                target.setHp((int) Math.min(target.getHp() - this.getAtk()
                        * this.getSkillMultiple(), target.initialHP));
            }
            for (int i = 0; i < ((this.getSkillCD()) / targets.length / threadSleepTime); i++) {
                this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                Thread.sleep(threadSleepTime);
            }
        }


    }

    private boolean unleashRailGun;

    /**
     * Is unleash rail gun boolean.
     *
     * @return the boolean
     */
    public boolean isUnleashRailGun() {
        return unleashRailGun;
    }

    /**
     * Sets unleash rail gun.
     *
     * @param unleashRailGun the unleash rail gun
     */
    public void setUnleashRailGun(boolean unleashRailGun) {
        this.unleashRailGun = unleashRailGun;
    }
    
    private int rotateRailGunDegree;

    /**
     * Gets rotate rail gun degree.
     *
     * @return the rotate rail gun degree
     */
    public int getRotateRailGunDegree() {
        return rotateRailGunDegree;
    }

    /**
     * Sets rotate rail gun degree.
     *
     * @param rotateRailGunDegree the rotate rail gun degree
     */
    public void setRotateRailGunDegree(int rotateRailGunDegree) {
        this.rotateRailGunDegree = rotateRailGunDegree;
    }

    @Override
    public void useSkillRailGun(Person... targets) throws InterruptedException {
        this.setRemainingCD(this.getSkillCD());
        int nTarget = 0;
        for (Person target : targets) {
            nTarget++;
            if (!dead && !target.isDead()) {
                if (nTarget == 1) {
                    rotateRailGunDegree = 20;
                } else if (nTarget == 2) {
                    rotateRailGunDegree = 45;
                }
                target.setHp((int) (target.getHp()
                        - this.getAtk() * this.getSkillMultiple()));
                if (target.getHp() <= 0) {
                    target.setDead(true);
                    target.setRemoved(true);
                }
                for (int i = 0; i < (1000 / threadSleepTime); i++) {
                    this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                    Thread.sleep(threadSleepTime);
                }
                unleashRailGun = false;
                for (int i = 0; i < ((this.getSkillCD() - 1000) / threadSleepTime); i++) {
                    this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                    Thread.sleep(threadSleepTime);
                }
                this.setReapplyAllowed(true);
                break;
            }
        }
    }

    @Override
    public void useSkillCharm(Person target) throws InterruptedException {
        this.setRemainingCD(this.getSkillCD());
        if (Battlefield.getMovementsPaused()) {
            // this.setSkillX(this.getXPos() - 60);
            // this.setSkillY(this.getYPos());
            target.setAtk(target.getAtk() / 2);
            target.setHp((int) (target.getHp()
                    - this.getAtk() * this.getSkillMultiple()));
            if (target.getHp() <= 0) {
                target.setDead(true);
                target.setRemoved(true);
            }
            for (int i = 0; i < ((this.getSkillCD()) / threadSleepTime); i++) {
                this.setRemainingCD(this.getRemainingCD() - threadSleepTime);
                Thread.sleep(threadSleepTime);
            }
            target.setAtk(target.getAtk() * 2);
        }
    }
}
