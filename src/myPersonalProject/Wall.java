package src.myPersonalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * NOTE
 * The Wall class represents a non-breakable wall in the game.
 * It implements the Drawable interface for rendering on the graphical user interface.
 * Walls are stationary entities and serve as obstacles for tanks.
 */
public class Wall extends JComponent implements Drawable {

    // Position of the wall
    private int x, y;

    // Dimensions of the wall image
    private int width, height;

    // Image representing the wall
    private BufferedImage image;

    // Rectangle bounds for collision detection
    private Rectangle bounds;

    /**
     * Constructor for Wall that initializes its position and loads the wall image.
     *
     * @param x The initial x-coordinate of the wall
     * @param y The initial y-coordinate of the wall
     */
    public Wall(int x, int y) {
        // Assign coordinates to class variables
        this.x = x;
        this.y = y;

        // Initialize the graphical components, such as loading the wall image
        init();

        // Set dimensions based on the loaded image
        this.width = image.getWidth();
        this.height = image.getHeight();

        // Create a bounding rectangle for collision detection
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Initialize the graphical components, such as loading the wall image.
     * This method is called once during the construction of the Wall object.
     */
    private void init() {
        try {
            // Load the image for the wall from the resources
            image = ImageIO.read(getClass().getResource("resources/images/NonBreakableWallTile.png"));
        } catch (IOException e) {
            // Print stack trace if image loading fails (critical for game operation)
            e.printStackTrace();
        }
    }
    /**
     * Draw the wall image on the screen at its specified position.
     *
     * @param g The graphics object to draw on
     */
    public void drawImage(Graphics g) {
        paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(image, x, y, this);
    }
    /**
     * Get the bounding rectangle of the wall for collision detection.
     * This is crucial for detecting interactions with other game entities.
     *
     * @return The bounding rectangle of the wall
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
