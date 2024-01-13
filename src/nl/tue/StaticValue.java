package nl.tue;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * The type Static value.
 */
public class StaticValue {
    /**
     * The constant bg.
     */
    public static BufferedImage bg = null; // background
    /**
     * The constant mainBg.
     */
    public static JLabel mainBg = null;
    /**
     * The constant stand1.
     */
    public static BufferedImage stand1 = null; // shooting stance
    /**
     * The constant stand2.
     */
    public static BufferedImage stand2 = null; // shooting stance
    /**
     * The constant stand3.
     */
    public static BufferedImage stand3 = null; // shooting stance
    /**
     * The constant enemy.
     */
    public static BufferedImage enemy = null; // enemy
    /**
     * The constant boss.
     */
    public static BufferedImage boss = null; // boss
    /**
     * The constant bomb.
     */
    public static BufferedImage bomb = null; // boss skill
    /**
     * The constant heal.
     */
    public static BufferedImage heal = null; // special character skill
    /**
     * The constant healLabel.
     */
    public static BufferedImage healLabel = null; // special character skill
    /**
     * The constant shield.
     */
    public static BufferedImage shield = null; // character 1 skill
    /**
     * The constant shieldLabel.
     */
    public static BufferedImage shieldLabel = null; // character 1 skill
    /**
     * The constant railGun.
     */
    public static BufferedImage railGun = null; // character 3 skill
    /**
     * The constant railGunLabel.
     */
    public static BufferedImage railGunLabel = null; // character 3 skill
    /**
     * The constant charm.
     */
    public static BufferedImage charm = null; // character 4 skill
    /**
     * The constant charmLabel.
     */
    public static BufferedImage charmLabel = null; // character 4 skill
    /**
     * The constant shots.
     */
    public static BufferedImage shots = null; // shots
    /**
     * The constant shotsEnemy.
     */
    public static BufferedImage shotsEnemy = null; // shots
    /**
     * The constant runRCharacter1.
     */
    public static List<BufferedImage> runRCharacter1 = new ArrayList<>();
    /**
     * The constant runRCharacter2.
     */
    // characters running right
    public static List<BufferedImage> runRCharacter2 = new ArrayList<>();
    /**
     * The constant runRCharacter3.
     */
    // characters running right
    public static List<BufferedImage> runRCharacter3 = new ArrayList<>();
    /**
     * The constant path.
     */
    // characters running right
    public static String path = System.getProperty("user.dir") + "/src/images/";

    /**
     * Enlarge image buffered image.
     *
     * @param originalImage     the original image
     * @param enlargementFactor the enlargement factor
     * @return the buffered image
     */
    static BufferedImage enlargeImage(BufferedImage originalImage, double enlargementFactor) {
        // Calculate the new dimensions
        int newWidth = (int) (originalImage.getWidth() * enlargementFactor);
        int newHeight = (int) (originalImage.getHeight() * enlargementFactor);

        // Create a new image with the calculated dimensions
        BufferedImage enlargedImage = 
                new BufferedImage(newWidth, newHeight, originalImage.getType());

        // Create a graphics context and draw the original image on the new image
        Graphics2D g2d = enlargedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return enlargedImage;
    }

    private static BufferedImage resizeImage(
        BufferedImage originalImage, int newWidth, int newHeight) {
        // Create a new BufferedImage with the desired dimensions
        BufferedImage resizedImage = 
                new BufferedImage(newWidth, newHeight, originalImage.getType());

        // Get the Graphics2D object from the new image
        Graphics2D g2d = resizedImage.createGraphics();

        // Use AffineTransformOp to perform the resize
        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance((double) newWidth 
                / originalImage.getWidth(), (double) newHeight / originalImage.getHeight()),
                AffineTransformOp.TYPE_BILINEAR
        );

        // Draw the original image onto the new image using the transform
        op.filter(originalImage, resizedImage);

        // Dispose of the Graphics2D object
        g2d.dispose();

        return resizedImage;
    }

    /**
     * Init.
     *
     * @throws IOException the io exception
     */
    // initialization
    public static void init() throws IOException {
        bg = ImageIO.read(new File(path + "longDesert trial.png"));
        mainBg = new JLabel(new ImageIcon(path + "main back.png"));
        stand1 = ImageIO.read(new File(path + "character 1 shooting.png"));
        stand2 = enlargeImage(ImageIO.read(new File(path + "character 3 shooting.png")),
                2.2);
        stand3 = enlargeImage(ImageIO.read(new File(path + "character 4 shooting.png")),
                2.2);
        enemy = ImageIO.read(new File(path + "enemy.png"));
        boss = ImageIO.read(new File(path + "boss.png"));
        bomb = ImageIO.read(new File(path + "boom.png"));
        heal = ImageIO.read(new File(path + "heal.png"));
        shield = ImageIO.read(new File(path + "shield.png"));
        railGun = ImageIO.read(new File(path + "railgun.png"));
        charm = ImageIO.read(new File(path + "charm.png"));
        shots = ImageIO.read(new File(path + "bullet.png"));
        shotsEnemy = ImageIO.read(new File(path + "enemy bullet.png"));
        runRCharacter1.add(ImageIO.read(new File(path + "character 1.png")));
        runRCharacter1.add(ImageIO.read(new File(path + "character 1 2.png")));
        runRCharacter2.add(enlargeImage(ImageIO.read(new File(path + "character 3.png")),
                2.2));
        runRCharacter2.add(enlargeImage(ImageIO.read(new File(path + "character 3 2.png")),
                2.2));
        runRCharacter3.add(enlargeImage(ImageIO.read(new File(path + "character 4.png")),
                2.2));
        runRCharacter3.add(enlargeImage(ImageIO.read(new File(path + "character 4 2.png")),
                2.2));
        healLabel = resizeImage(ImageIO.read(new File(path + "character4FuukaSkill.png")),
                100, 100);
        shieldLabel = resizeImage(ImageIO.read(new File(path + "character1YukaSkill.png")),
                100, 100);
        railGunLabel = resizeImage(ImageIO.read(new File(path + "character2MisakaSkill.png")),
                100, 100);
        charmLabel = resizeImage(ImageIO.read(new File(path + "character3ShokuhoSkill.png")),
                100, 100);

    }

}
