package src.myPersonalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * NOTE
 * GameUI class represents the graphical user interface for the game,
 * displaying player health in the form of heart images.
 */
public class GameUI extends JComponent {

    // Tanks for player 1 and player 2
    private Tank t1;
    private Tank t2;

    // Image for representing player health (heart image)
    private BufferedImage image;

    /**
     * Constructor for GameUI that initializes the UI with two tanks.
     *
     * @param t1 The first tank (player 1)
     * @param t2 The second tank (player 2)
     */
    public GameUI(Tank t1, Tank t2) {
        // Assign tanks to class variables
        this.t1 = t1;
        this.t2 = t2;

        // Initialize the graphical components, such as loading images
        init();
    }

    /**
     * Initialize the graphical components, such as loading the player health image.
     * This method is called once during the construction of the GameUI object.
     */
    private void init() {
        try {
            // Load the image for player health (heart image)
            image = ImageIO.read(getClass().getResource("resources/images/PlayerHealthHeart.png"));
        } catch (IOException e) {
            // Print stack trace if image loading fails
            e.printStackTrace();
        }
    }

    /**
     * Draw the player health images on the screen.
     *
     * @param g The graphics object to draw on
     */
    public void drawImage(Graphics g) {
        // Call the paintComponent method to perform any necessary painting
        paintComponent(g);

        // Cast the Graphics object to Graphics2D for advanced graphics operations
        Graphics2D g2d = (Graphics2D) g;

        // Initial distances for drawing player health images for each player
        int distancep1 = 10;
        int distancep2 = 10;

        // Draw player 1's health hearts
        {
            int i = 0;
            // Iterate through each remaining life of player 1
            while (i < t1.getLives()) {
                // Draw a player health image (heart) for each remaining life of player 1
                g2d.drawImage(image, t1.getX() + distancep1, t1.getY() - 20, this);
                distancep1 += 12;
                i++;
            }
        }

        // Draw player 2's health hearts
        int i = 0;
        // Iterate through each remaining life of player 2
        while (i < t2.getLives()) {
            // Draw a player health image (heart) for each remaining life of player 2
            g2d.drawImage(image, t2.getX() + distancep2, t2.getY() - 20, this);
            distancep2 += 12;
            i++;
        }
    }
}
