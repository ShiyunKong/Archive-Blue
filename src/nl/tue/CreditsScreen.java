package nl.tue;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * The type Credits screen.
 */
public class CreditsScreen extends JPanel {
    private Clip audioClip;
    private final Timer scrollTimer;
    private int yPosition; // Start position off-screen
    private final String[] credits = {
        "Producer 1: Shiyun Kong",
        "Producer 2: Xiuqi Shi",
        "Many thanks to Graphics and Sound from Yostar",
    };
    private final String osdText = "Left click to exit to main menu";

    /**
     * Instantiates a new Credits screen.
     */
    public CreditsScreen() {
        playBackgroundMusic();
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(Color.BLACK);
        yPosition = (1080 - credits.length * 40) / 2; // Center the text vertically

        scrollTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yPosition--;
                repaint();

                if (yPosition + credits.length * 40 <= 0) {
                    // Credits have scrolled off the screen, stop the timer
                    scrollTimer.stop();
                }
            }
        });

        scrollTimer.start();

        // Add a mouse listener to return to the main menu when clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Stop the timer and close the credits screen
                scrollTimer.stop();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(CreditsScreen.this);
                frame.dispose();
                // Stop the background music
                if (audioClip != null) {
                    audioClip.stop();
                    audioClip.close();
                }
                // Create and show the main menu
                JFrame mainMenuFrame = new JFrame("Archive Blue");
                mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainMenuFrame.setSize(1920, 1080);
                mainMenuFrame.add(new MainMenu());
                mainMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                mainMenuFrame.setUndecorated(true);
                mainMenuFrame.setVisible(true);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        for (int i = 0; i < credits.length; i++) {
            int stringWidth = g.getFontMetrics().stringWidth(credits[i]);
            int xPosition = (1920 - stringWidth) / 2; // Center the text horizontally
            g.drawString(credits[i], xPosition, yPosition + i * 40);
        }

        // Draw the OSD text
        int osdWidth = g.getFontMetrics().stringWidth(osdText);
        g.drawString(osdText, (1920 - osdWidth) / 2, 1000);
    }

    private void playBackgroundMusic() {
        String path = System.getProperty("user.dir") + "/src/music/";
        try {
            File audioFile = new File(path + "Mischievous Step.wav");
            audioClip = AudioSystem.getClip();
            audioClip.open(AudioSystem.getAudioInputStream(audioFile));
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Credits");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.add(new CreditsScreen());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setVisible(true);
        });
    }
}
