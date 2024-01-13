package nl.tue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


/**
 * The type Victory screen.
 */
public class VictoryScreen extends JPanel {
    private Clip audioClip;

    private void playBackgroundMusic() {
        String path = System.getProperty("user.dir") + "/src/music/";
        try {
            File audioFile = new File(path + "Party Time.wav");
            audioClip = AudioSystem.getClip();
            audioClip.open(AudioSystem.getAudioInputStream(audioFile));
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates a new Victory screen.
     */
    public VictoryScreen() {
        playBackgroundMusic();
        setLayout(new BorderLayout());

        // Create a label for the "Victory" title
        JLabel title = new JLabel("Victory");
        title.setFont(new Font("Times New Roman", Font.PLAIN, 48));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.CENTER);

        // Create an OSD message label
        JLabel osdMessage = new JLabel("Left click anywhere to return to the main menu");
        osdMessage.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        osdMessage.setHorizontalAlignment(JLabel.CENTER);
        add(osdMessage, BorderLayout.SOUTH);

        // Add a mouse listener to detect clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // When the user clicks, return to the main menu
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VictoryScreen.this);
                frame.dispose(); // Close the VictoryScreen
                // Stop the background music
                if (audioClip != null) {
                    audioClip.stop();
                    audioClip.close();
                }

                SwingUtilities.invokeLater(() -> {
                    JFrame mainMenuFrame = new JFrame("Archive Blue");
                    mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainMenuFrame.setSize(1920, 1080);
                    mainMenuFrame.add(new MainMenu());
                    mainMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    mainMenuFrame.setUndecorated(true);
                    mainMenuFrame.setVisible(true);
                });
            }

        });
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Archive Blue");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.add(new VictoryScreen());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
            frame.setUndecorated(true);
            // Hide the window decorations (title bar, close button, etc.)
            frame.setVisible(true);
        });
    }
}
