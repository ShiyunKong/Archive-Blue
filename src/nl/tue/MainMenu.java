package nl.tue;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * The type Main menu.
 */
public class MainMenu extends JPanel {
    private Clip audioClip;

    private static JFrame mainMenuFrame;

    /**
     * Instantiates a new Main menu.
     */
    public MainMenu() {
        setLayout(new BorderLayout());

        // Create a JLabel for the background image
        JLabel backgroundLabel = StaticValue.mainBg;
        add(backgroundLabel, BorderLayout.CENTER); // Center the background JLabel

        // Create a panel for buttons and set its layout to FlowLayout
        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center-align buttons
        buttonPanel.setOpaque(true); // Make the button panel transparent

        // Get the background color of the JLabel
        Color bgColor = backgroundLabel.getBackground();

        // Create and configure UI components (buttons)
        JButton startButton = createButton("Start Game", bgColor);
        // JButton settingsButton = createButton("Settings", bgColor);
        JButton creditsButton = createButton("Credits", bgColor);
        JButton quitButton = createButton("Quit", bgColor);

        // Add action listeners to the buttons
        startButton.addActionListener(e -> {
            // Handle the "Start Game" button click
            // Start the game or switch to the game screen
            System.out.println("Start Game button clicked.");
            // Close the current main menu frame
            mainMenuFrame.dispose();
            // Stop the background music
            if (audioClip != null) {
                audioClip.stop();
                audioClip.close();
            }

            // Create and show the background frame
            SwingUtilities.invokeLater(() -> {
                JFrame backgroundFrame = new JFrame("Archive Blue");
                backgroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                backgroundFrame.setExtendedState(
                    JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
                backgroundFrame.setUndecorated(
                    true); // Hide the window decorations (title bar, close button, etc.)
                backgroundFrame.add(new Battlefield());
                backgroundFrame.setVisible(true);
            });
        });

        // settingsButton.addActionListener(e -> {
        //    // Handle the "Settings" button click
        //    // Open the settings screen
        //    System.out.println("Settings button clicked.");

        // });

        creditsButton.addActionListener(e -> {
            // Handle the "Credits" button click
            // Show the credits screen
            System.out.println("Credits button clicked.");
            // Close the current main menu frame
            mainMenuFrame.dispose();
            // Stop the background music
            if (audioClip != null) {
                audioClip.stop();
                audioClip.close();
            }

            // Create and show the background frame
            SwingUtilities.invokeLater(() -> {
                JFrame backgroundFrame = new JFrame("Archive Blue");
                backgroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                backgroundFrame.setExtendedState(
                    JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
                backgroundFrame.setUndecorated(
                    true); // Hide the window decorations (title bar, close button, etc.)
                backgroundFrame.add(new CreditsScreen());
                backgroundFrame.setVisible(true);
            });
        });

        quitButton.addActionListener(e -> {
            // Handle the "Quit" button click
            // Exit the program
            System.out.println("Quit button clicked.");
            System.exit(0); // Terminate the program
        });

        // Add buttons to the buttonPanel
        buttonPanel.add(startButton);
        // buttonPanel.add(settingsButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(quitButton);

        // Add the buttonPanel to the main menu panel (at the center)
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the layout opaque so that the background image is visible
        setOpaque(false);
        playBackgroundMusic();
    }

    private void playBackgroundMusic() {
        String path = System.getProperty("user.dir") + "/src/music/";
        try {
            File audioFile = new File(path + "Step by Step.wav");
            audioClip = AudioSystem.getClip();
            audioClip.open(AudioSystem.getAudioInputStream(audioFile));
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK); // Set text color
        button.setPreferredSize(new Dimension(120, 40)); // Adjust button size
        button.setBackground(bgColor); // Set the button's background to match the JLabel background
        button.setOpaque(true); // Make the button opaque

        return button;
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
            mainMenuFrame = new JFrame("Archive Blue");
            mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainMenuFrame.setSize(1920, 1080); // Adjust the frame size
            mainMenuFrame.add(new MainMenu());
            mainMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the JFrame to fullscreen
            mainMenuFrame.setUndecorated(
                true); // Hide the window decorations (title bar, close button, etc.)
            mainMenuFrame.setVisible(true);
        });
    }
}
