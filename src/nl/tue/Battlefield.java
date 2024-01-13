package nl.tue;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


/**
 * The type Battlefield.
 */
public class Battlefield extends JPanel {
    private final int checkInterval = 10;
    private final ExecutorService es = Executors.newFixedThreadPool(50);
    // Change this to the number of methods you want to run concurrently
    private BufferedImage bg = StaticValue.bg;
    private int scrollXPos = 0;
    private static final int SCROLL_SPEED = 30;

    /**
     * Gets scroll speed.
     *
     * @return the scroll speed
     */
    public static int getScrollSpeed() {
        return SCROLL_SPEED;
    }

    private final int pauseOneAtPixel = (int) (4703 - 1080 / 1.2);
    private final int soldierJoin = pauseOneAtPixel - 600;
    private final int pauseTwoAtPixel = bg.getWidth() - 1920;
    private final int bossJoin = pauseTwoAtPixel - 600;
    private final Timer refresher; // Timer for scrolling
    private int movementsPauseCounter = 0;
    private static boolean shootRestart = false;
    // private boolean gameEndSignal = false;
    private static boolean movementsPaused = false;

    /**
     * Gets movements paused.
     *
     * @return the movements paused
     */
    public static boolean getMovementsPaused() {
        return movementsPaused;
    }

    /**
     * Sets movements paused.
     *
     * @param movementsPaused the movements paused
     */
    public static void setMovementsPaused(boolean movementsPaused) {
        Battlefield.movementsPaused = movementsPaused;
        Battlefield.shootRestart = true;
    }

    private boolean isInBattle = false;

    /**
     * Is in battle boolean.
     *
     * @return the boolean
     */
    public boolean isInBattle() {
        return isInBattle;
    }

    /**
     * Sets in battle.
     *
     * @param inBattle the in battle
     */
    public void setInBattle(boolean inBattle) {
        isInBattle = inBattle;
    }

    private static final int REFRESH_DELAY = 17;

    /**
     * Gets refresh delay.
     *
     * @return the refresh delay
     */
    public static int getRefreshDelay() {
        return REFRESH_DELAY;
    }

    // Object Initialization
    private final Characters character1Yuka = new Characters("Yuka", StaticValue.runRCharacter1,
            600, 400, StaticValue.stand1, 25000, 100,
            StaticValue.shield, 0.8, 5000);
    private final Characters character2Misaka = new Characters("Misaka", StaticValue.runRCharacter2,
            300, 150, StaticValue.stand2, 10000, 1000,
            StaticValue.railGun, 10.0, 5000);
    private final Characters character3Shokuho = new Characters("Shokuho", 
        StaticValue.runRCharacter3, 300, 600, StaticValue.stand3, 10000, 750,
            StaticValue.charm, 2.5, 2000);
    private final Characters character4Fuuka = new Characters("Fuuka", 25, 150, 5000,
            500, StaticValue.heal, -7.8, 4000);
    private final Soldier soldier1 = new Soldier("soldier1", 1800, 150, 5000, 1000);
    private final Soldier soldier2 = new Soldier("soldier2", 1800, 650, 5000, 1000);
    private final Boss boss = new Boss("boss", 1800, 375, 80000, 1000,
            StaticValue.bomb, 15, 4000);
    // Object Initialization

    /**
     * The Shield button.
     */
    // Buttons
    JButton shieldButton;
    /**
     * The Heal button.
     */
    JButton healButton;
    /**
     * The Rail gun button.
     */
    JButton railGunButton;
    /**
     * The Charm button.
     */
    JButton charmButton;
    private boolean unleashHeal;
    private boolean unleashCharm;
    private Clip audioClip;

    private void playBackgroundMusic() {
        String path = System.getProperty("user.dir") + "/src/music/";
        try {
            File audioFile = new File(path + "MX Adventure.wav");
            audioClip = AudioSystem.getClip();
            audioClip.open(AudioSystem.getAudioInputStream(audioFile));
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Instantiates a new Battlefield.
     */
    public Battlefield() {
        playBackgroundMusic();
        bg = StaticValue.bg;
        // Resize the image to fit the 1920x1080 frame
        int newHeight = 1080;
        int newWidth = (int) ((double) bg.getWidth() * newHeight / bg.getHeight());
        bg = resizeImage(bg, newWidth, newHeight);
        // Buttons
        shieldButton = createImageButton(StaticValue.shieldLabel);
        healButton = createImageButton(StaticValue.healLabel);
        railGunButton = createImageButton(StaticValue.railGunLabel);
        charmButton = createImageButton(StaticValue.charmLabel);

        // Add the buttons to the frame
        setLayout(null);  // Use null layout to position components manually
        shieldButton.setBounds(0, 0, 100, 100);
        healButton.setBounds(100, 0, 100, 100);
        railGunButton.setBounds(200, 0, 100, 100);
        charmButton.setBounds(300, 0, 100, 100);

        add(shieldButton);
        add(healButton);
        add(railGunButton);
        add(charmButton);

        character1Yuka.setBattlefield(this);
        character2Misaka.setBattlefield(this);
        character3Shokuho.setBattlefield(this);
        soldier1.setBattlefield(this);
        soldier2.setBattlefield(this);
        boss.setBattlefield(this);

        /*es.submit(() -> {
            try {
                refreshing();
                Thread.sleep(REFRESH_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });*/


        refresher = new Timer(REFRESH_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!movementsPaused) {
                    scrollXPos += SCROLL_SPEED;
                    if (scrollXPos >= (soldierJoin)) {
                        // Notify the soldier objects to start moving
                        soldier1.startMoving();
                        soldier2.startMoving();
                    }

                    if (scrollXPos >= pauseOneAtPixel 
                        && scrollXPos <= pauseOneAtPixel + SCROLL_SPEED) {
                        // first encounter
                        character1Yuka.setShooting(true);
                        character2Misaka.setShooting(true);
                        character3Shokuho.setShooting(true);
                        movementsPaused = true;
                        movementsPauseCounter++;
                    }
                    if (shootRestart) {
                        character1Yuka.setShooting(false);
                        character2Misaka.setShooting(false);
                        character3Shokuho.setShooting(false);
                        shootRestart = false;
                    }
                    if (scrollXPos >= (bossJoin)) {
                        // Notify the boss object to start moving
                        boss.startMoving();
                    }
                    if (scrollXPos >= pauseTwoAtPixel 
                        && scrollXPos <= pauseTwoAtPixel + SCROLL_SPEED) {
                        // second encounter
                        character1Yuka.setShooting(true);
                        character2Misaka.setShooting(true);
                        character3Shokuho.setShooting(true);
                        boss.stopMoving();
                        movementsPaused = true;
                        movementsPauseCounter++;
                    }
                } else { // pauseMovements true
                    battle();
                }
                repaint();
            }
        });

        refresher.start();
    }

    private void battle() {
        soldier1.stopMoving();
        soldier2.stopMoving();
        // First encounter combat initialization
        if (movementsPauseCounter == 1) {
            if (isInBattle) {
                if (soldier2.isDead() && soldier1.isDead()) {
                    // System.out.println("All soldiers are dead.");
                    movementsPaused = false;
                    shootRestart = true;
                    isInBattle = false;
                }
                if (character1Yuka.isDead() 
                    && character2Misaka.isDead() 
                    && character3Shokuho.isDead()) {
                    // System.out.println("Boss is dead.");
                    // gameEndSignal = true;
                    movementsPaused = false;
                    isInBattle = false;
                    refresher.stop();
                    // Stop the background music
                    if (audioClip != null) {
                        audioClip.stop();
                        audioClip.close();
                    }
                    switchToGameOverScreen();
                }
            } else {
                es.execute(() -> {
                    while ((!soldier1.isDead() || !soldier2.isDead()) && !character1Yuka.isDead()) {
                        try {
                            if (character1Yuka.isUnleashShield()) {
                                character1Yuka.useSkillShield(character1Yuka);
                                character1Yuka.setUnleashShield(false);
                                character1Yuka.setShieldExist(false);
                                character1Yuka.setShieldPoints(0);
                            } else {
                                character1Yuka.normalAttack(soldier2, soldier1);
                            }
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                es.execute(() -> {
                    while ((!soldier1.isDead() 
                        || !soldier2.isDead()) 
                        && !character2Misaka.isDead()) {
                        try {
                            if (character2Misaka.isUnleashRailGun()) {
                                character2Misaka.useSkillRailGun(soldier1, soldier2);
                                character2Misaka.setUnleashRailGun(false);
                            } else {
                                character2Misaka.normalAttack(soldier2, soldier1);
                            }
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                es.execute(() -> {
                    while ((!soldier1.isDead() 
                        || !soldier2.isDead()) 
                        && !character3Shokuho.isDead()) {
                        try {
                            if (unleashCharm) {
                                character3Shokuho.useSkillCharm(soldier1);
                                unleashCharm = false;
                            } else {
                                character3Shokuho.normalAttack(soldier1, soldier2);
                            }
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                es.execute(() -> {
                    while ((!soldier1.isDead() 
                        || !soldier2.isDead()) 
                        && !character3Shokuho.isDead()) {
                        try {
                            if (unleashCharm) {
                                character3Shokuho.useSkillCharm(soldier2);
                                unleashCharm = false;
                            }
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                es.execute(() -> {
                    while (!soldier1.isDead() || !soldier2.isDead()) {
                        try {
                            if (unleashHeal) {
                                character4Fuuka.useSkillHeal(character1Yuka);
                                unleashHeal = false;
                            }
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                es.execute(() -> {
                    while ((!character1Yuka.isDead() 
                        || !character2Misaka.isDead() 
                        || !character3Shokuho.isDead())
                            && !soldier1.isDead()) {
                        try {
                            // System.out.println("check");
                            soldier1.normalAttack(character1Yuka, 
                                character2Misaka, character3Shokuho);
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                es.execute(() -> {
                    while ((!character1Yuka.isDead() 
                        || !character2Misaka.isDead() 
                        || !character3Shokuho.isDead())
                            && !soldier2.isDead()) {
                        try {
                            soldier2.normalAttack(character1Yuka, 
                                character2Misaka, character3Shokuho);
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                isInBattle = true;
            }
        }
        // Second encounter combat initialization
        if (movementsPauseCounter == 2) {
            if (isInBattle) {
                // check all killed
                if (boss.isDead()) {
                    // System.out.println("Boss is dead.");
                    // gameEndSignal = true;
                    movementsPaused = false;
                    isInBattle = false;
                    refresher.stop();
                    // Stop the background music
                    if (audioClip != null) {
                        audioClip.stop();
                        audioClip.close();
                    }
                    switchToVictoryScreen();
                }
                if (character1Yuka.isDead() 
                    && character2Misaka.isDead() 
                    && character3Shokuho.isDead()) {
                    // System.out.println("Boss is dead.");
                    // gameEndSignal = true;
                    movementsPaused = false;
                    isInBattle = false;
                    refresher.stop();
                    // Stop the background music
                    if (audioClip != null) {
                        audioClip.stop();
                        audioClip.close();
                    }
                    switchToGameOverScreen();
                }
            } else {
                es.execute(() -> {
                    try {
                        while (!boss.isDead() && !character1Yuka.isDead()) {
                            if (character1Yuka.isUnleashShield()) {
                                character1Yuka.useSkillShield(character1Yuka);
                                character1Yuka.setUnleashShield(false);
                                character1Yuka.setShieldExist(false);
                                character1Yuka.setShieldPoints(0);
                            } else {
                                character1Yuka.normalAttack(boss);
                            }
                            Thread.sleep(checkInterval);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                es.execute(() -> {
                    try {
                        while (!boss.isDead() && !character2Misaka.isDead()) {
                            if (character2Misaka.isUnleashRailGun()) {
                                character2Misaka.useSkillRailGun(boss);
                            } else {
                                character2Misaka.normalAttack(boss);
                            }
                            Thread.sleep(checkInterval);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);

                    }
                });
                es.execute(() -> {
                    try {
                        while (!boss.isDead() && !character3Shokuho.isDead()) {
                            if (unleashCharm) {
                                character3Shokuho.useSkillCharm(boss);
                                unleashCharm = false;
                            } else {
                                character3Shokuho.normalAttack(boss);
                            }
                            Thread.sleep(checkInterval);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);

                    }
                });
                es.execute(() -> {
                    try {
                        while (!boss.isDead()) {
                            if (unleashHeal) {
                                character4Fuuka.useSkillHeal(character1Yuka, 
                                    character2Misaka, character3Shokuho);
                                unleashHeal = false;
                            }
                            Thread.sleep(checkInterval);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);

                    }
                });
                es.execute(() -> {
                    while ((!character1Yuka.isDead() 
                        || !character2Misaka.isDead() 
                        || !character3Shokuho.isDead()
                            && !boss.isDead())) {
                        try {
                            boss.normalAttack(character1Yuka, character2Misaka, character3Shokuho);
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                es.execute(() -> {
                    while ((!character1Yuka.isDead() 
                        || !character2Misaka.isDead() 
                        || !character3Shokuho.isDead())
                            && !boss.isDead()) {
                        try {
                            boss.skillReleaseBoss(character1Yuka, 
                                character2Misaka, character3Shokuho);
                            Thread.sleep(checkInterval);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                isInBattle = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Initialization
        super.paintComponent(g);
        g.fillRect(0, 0, 1920, 1080);
        // Background's position
        int viewWidth = 1920;
        int maxXPos = bg.getWidth() - viewWidth; // Maximum allowed xPos
        scrollXPos = Math.min(scrollXPos, maxXPos); // Ensure xPos doesn't go beyond the image width
        if (scrollXPos >= 0) {
            g.drawImage(bg.getSubimage(
                scrollXPos, 0, Math.min(viewWidth, bg.getWidth() - scrollXPos), bg.getHeight()),
                    0, 0, this);
        }


        // Soldiers' position
        if (!soldier1.isRemoved()) {
            if (scrollXPos >= soldierJoin) {
                g.drawImage(soldier1.getShow(), soldier1.getXPos(), soldier1.getYPos(), this);
                // health bar
                // soldier1.addHP(g, Color.red);
            }
        }
        if (!soldier2.isRemoved()) {
            if (scrollXPos >= soldierJoin) {
                g.drawImage(soldier2.getShow(), soldier2.getXPos(), soldier2.getYPos(), this);
                // health bar
                // soldier2.addHP(g, Color.red);
            }
        }


        // Boss's position
        if (scrollXPos >= bossJoin) {
            g.drawImage(boss.getShow(), boss.getXPos(), boss.getYPos(), this);
            // health bar
            // boss.addHP(g, Color.red);
        }
        // Combat switch
        if (movementsPaused) {
            // First encounter
            if (movementsPauseCounter == 1) {
                // bullets
                g.drawImage(rotateImageDegrees(StaticValue.shots, 45),
                        // character1Yuka.getBulletX(), character1Yuka.getBulletY(),
                        ((int) character1Yuka.getXPos() + character1Yuka.getBulletX() + 100),
                        ((int) character1Yuka.getYPos() + character1Yuka.getBulletY() + 100),
                        this);
                g.drawImage(StaticValue.shots,
                        ((int) character2Misaka.getXPos() + character2Misaka.getBulletX() + 100),
                        ((int) character2Misaka.getYPos() + character2Misaka.getBulletY() + 100),
                        this);
                g.drawImage(rotateImageDegrees(StaticValue.shots, 45),
                        ((int) character3Shokuho.getXPos() + character3Shokuho.getBulletX() + 100),
                        ((int) character3Shokuho.getYPos() + character3Shokuho.getBulletY() + 100),
                        this);
                g.drawImage(StaticValue.shotsEnemy,
                        ((int) soldier1.getXPos() + soldier1.getBulletX() + 100),
                        ((int) soldier1.getYPos() + soldier1.getBulletY() + 100),
                        this);
                g.drawImage(rotateImageDegrees(StaticValue.shotsEnemy, 45),
                        ((int) soldier2.getXPos() + soldier2.getBulletX() + 100),
                        ((int) soldier2.getYPos() + soldier2.getBulletY() + 100),
                        this);
                // health bar
                if (!character1Yuka.isRemoved()) {
                    // health bar
                    character1Yuka.addStats(g, Color.green);
                    // System.out.println("character 1 HP" + character1Yuka.getHp());
                }
                if (!character2Misaka.isRemoved()) {
                    // health bar
                    character2Misaka.addStats(g, Color.green);
                    // System.out.println("character 2 HP" + character2Misaka.getHp());
                }
                if (!character3Shokuho.isRemoved()) {
                    // health bar
                    character3Shokuho.addStats(g, Color.green);
                    // System.out.println("character 3 HP" + character3Shokuho.getHp());
                }
                // shield
                if (character1Yuka.isUnleashShield()) {
                    g.drawImage(StaticValue.shield,
                            character1Yuka.getXPos() + 200,
                            character1Yuka.getYPos(),
                            this);
                }
                // heal
                if (unleashHeal) {
                    g.drawImage(StaticValue.heal,
                            character1Yuka.getXPos() - 150,
                            character1Yuka.getYPos(),
                            this);
                }
                // railgun
                if (character2Misaka.isUnleashRailGun()) {
                    g.drawImage(rotateImageDegrees(StaticValue.railGun, 20),
                            character2Misaka.getXPos(),
                            character2Misaka.getYPos() - 200,
                            this);
                }
                // charm
                if (unleashCharm) {
                    g.drawImage(StaticValue.charm,
                            soldier1.getXPos() - 20,
                            soldier1.getYPos(),
                            this);
                    g.drawImage(StaticValue.charm,
                            soldier2.getXPos() - 20,
                            soldier2.getYPos(),
                            this);
                }
                if (!soldier1.isRemoved()) {
                    // health bar
                    soldier1.addStats(g, Color.red);
                    // System.out.println("soldier1 HP" + soldier1.getHp());
                }
                if (!soldier2.isRemoved()) {
                    // health bar
                    soldier2.addStats(g, Color.red);
                    // System.out.println("soldier2 HP" + soldier2.getHp());
                }
            }
            // Second encounter
            if (movementsPauseCounter == 2) {
                g.drawImage(StaticValue.shots,
                        ((int) character1Yuka.getXPos() + character1Yuka.getBulletX() + 100),
                        ((int) character1Yuka.getYPos() + character1Yuka.getBulletY() + 100),
                        this);
                g.drawImage(rotateImageDegrees(StaticValue.shots, 45),
                        ((int) character2Misaka.getXPos() + character2Misaka.getBulletX() + 100),
                        ((int) character2Misaka.getYPos() + character2Misaka.getBulletY() + 100),
                        this);
                g.drawImage(StaticValue.shots,
                        ((int) character3Shokuho.getXPos() + character3Shokuho.getBulletX() + 100),
                        ((int) character3Shokuho.getYPos() + character3Shokuho.getBulletY() + 100),
                        this);
                g.drawImage(StaticValue.shotsEnemy,
                        ((int) boss.getXPos() + boss.getBulletX() + 100),
                        ((int) boss.getYPos() + boss.getBulletY() + 100),
                        this);
                // health bar
                if (!character1Yuka.isRemoved()) {
                    // health bar
                    character1Yuka.addStats(g, Color.green);
                    // System.out.println("character 1 HP" + character1Yuka.getHp());
                }
                if (!character2Misaka.isRemoved()) {
                    // health bar
                    character2Misaka.addStats(g, Color.green);
                    // System.out.println("character 2 HP" + character2Misaka.getHp());
                }
                if (!character3Shokuho.isRemoved()) {
                    // health bar
                    character3Shokuho.addStats(g, Color.green);
                    // System.out.println("character 3 HP" + character3Shokuho.getHp());
                }
                // shield
                if (character1Yuka.isUnleashShield()) {
                    g.drawImage(StaticValue.shield,
                            character1Yuka.getXPos() + 200,
                            character1Yuka.getYPos(),
                            this);
                }
                // heal
                if (unleashHeal) {
                    g.drawImage(StaticValue.heal,
                            character1Yuka.getXPos() - 150,
                            character1Yuka.getYPos(),
                            this);
                }
                // railgun
                if (character2Misaka.isUnleashRailGun()) {
                    g.drawImage(rotateImageDegrees(StaticValue.railGun, 25),
                            character2Misaka.getXPos(),
                            character2Misaka.getYPos() - 200,
                            this);
                }
                // charm
                if (unleashCharm) {
                    g.drawImage(StaticValue.charm,
                            boss.getXPos() - 20,
                            boss.getYPos(),
                            this);
                }
                // Bomb
                if (boss.isUnleashBomb()) {
                    g.drawImage(boss.getSkill(),
                            boss.getSkillX(),
                            boss.getSkillY(),
                            this);
                }
                if (!boss.isRemoved()) {
                    // health bar
                    boss.addStats(g, Color.red);
                    // System.out.println("boss HP" + boss.getHp());
                }
            }
        }
        // Characters' position
        if (!character1Yuka.isRemoved()) {
            g.drawImage(character1Yuka.getShow(),
                    character1Yuka.getXPos(), character1Yuka.getYPos(), this);
            // health bar
            character1Yuka.addStats(g, Color.green);

        }
        if (!character2Misaka.isRemoved()) {
            g.drawImage(character2Misaka.getShow(),
                    character2Misaka.getXPos(), character2Misaka.getYPos(), this);
            // health bar
            character2Misaka.addStats(g, Color.green);

        }
        if (!character3Shokuho.isRemoved()) {
            g.drawImage(character3Shokuho.getShow(),
                    character3Shokuho.getXPos(), character3Shokuho.getYPos(), this);
            // health bar
            character3Shokuho.addStats(g, Color.green);

        }
        if (!character4Fuuka.isRemoved()) {
            // health bar
            character4Fuuka.addStats(g, Color.green);

        }
    }

    private JButton createImageButton(BufferedImage image) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(image));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click here
                // You can implement specific actions for each button
                if (button == shieldButton) {
                    if (character1Yuka.isReapplyAllowed()) {
                        // Action for shield button
                        character1Yuka.setUnleashShield(true);
                    }
                    character1Yuka.setReapplyAllowed(false);
                } else if (button == healButton) {
                    // Action for heal button
                    unleashHeal = true;
                } else if (button == railGunButton) {
                    if (character2Misaka.isReapplyAllowed()) {
                        // Action for railGun button
                        character2Misaka.setUnleashRailGun(true);
                    }
                    character2Misaka.setReapplyAllowed(false);
                } else if (button == charmButton) {
                    // Action for charm button
                    unleashCharm = true;
                }
            }
        });
        return button;
    }

    private BufferedImage rotateImageDegrees(BufferedImage originalImage, int degree) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        double radians = Math.toRadians(degree);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int newWidth = (int) Math.floor(cos * width + sin * height);
        int newHeight = (int) Math.floor(sin * width + cos * height);

        BufferedImage rotatedImage = 
            new BufferedImage(newWidth, newHeight, originalImage.getType());
        // Graphics2D g2d = rotatedImage.createGraphics();

        double x = (newWidth - width) / 2;
        double y = (newHeight - height) / 2;

        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(radians, width / 2, height / 2);

        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        op.filter(originalImage, rotatedImage);

        return rotatedImage;
    }


    // Resize the image while preserving aspect ratio
    private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = 
            new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    private void switchToVictoryScreen() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(Battlefield.this);
        currentFrame.dispose(); // Close the current Battlefield frame

        // Open the VictoryScreen frame
        SwingUtilities.invokeLater(() -> {
            JFrame victoryFrame = new JFrame("Victory Screen");
            victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            victoryFrame.setSize(1920, 1080);
            victoryFrame.add(new VictoryScreen());
            victoryFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
            victoryFrame.setUndecorated(true); 
            // Hide the window decorations (title bar, close button, etc.)
            victoryFrame.setVisible(true);
        });
    }

    private void switchToGameOverScreen() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(Battlefield.this);
        currentFrame.dispose(); // Close the current Battlefield frame

        // Open the VictoryScreen frame
        SwingUtilities.invokeLater(() -> {
            JFrame victoryFrame = new JFrame("Defeated Screen");
            victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            victoryFrame.setSize(1920, 1080);
            victoryFrame.add(new GameOverScreen());
            victoryFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
            victoryFrame.setUndecorated(true);
            // Hide the window decorations (title bar, close button, etc.)
            victoryFrame.setVisible(true);
        });
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        StaticValue.init();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Archive Blue");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.add(new Battlefield());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
            frame.setUndecorated(true);
            // Hide the window decorations (title bar, close button, etc.)
            frame.setVisible(true);
        });
    }

}
