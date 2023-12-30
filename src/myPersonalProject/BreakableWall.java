package src.myPersonalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * NOTE
 * The BreakableWall class represents a wall in the game that can be damaged and broken.
 * It implements the Drawable interface for rendering on the graphical user interface.
 * Breakable walls provide interactive elements, affecting the game state based on health.
 */
public class BreakableWall extends JComponent implements Drawable {

    // Position of the breakable wall
    private int x, y;

    // Dimensions of the breakable wall
    private int width, height;

    // Health of the breakable wall
    private int health = 20;

    // Visibility status of the breakable wall
    private boolean visible;

    // Image representing the breakable wall
    private BufferedImage image;

    // Rectangle bounds for collision detection
    private Rectangle bounds;

    /**
     * Constructor for BreakableWall that initializes its position, loads the wall image, and sets initial health.
     *
     * @param x The x-coordinate of the breakable wall
     * @param y The y-coordinate of the breakable wall
     */
    public BreakableWall(int x, int y) {
        // Assign coordinates to class variables
        this.x = x;
        this.y = y;

        // Initialize the graphical components, such as loading images
        init();

        // Set dimensions based on the loaded image
        this.width = image.getWidth();
        this.height = image.getHeight();

        // Set initial visibility and create a bounding rectangle for collision detection
        this.visible = true;
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Initialize the graphical components, such as loading the breakable wall image.
     * This method is called once during the construction of the BreakableWall object.
     */
    private void init() {
        try {
            // Load the image for the breakable wall from the resources
            image = ImageIO.read(getClass().getResource("resources/images/BreakableWallTile.png"));
        } catch (IOException e) {
            // Print stack trace if image loading fails (critical for game operation)
            e.printStackTrace();
        }
    }

    /**
     * Draw the breakable wall image on the screen and update its visibility based on health.
     *
     * @param g The graphics object to draw on
     */
    public void drawImage(Graphics g) {
        // Call the paintComponent method to perform any necessary painting (not utilized in this class)
        paintComponent(g);

        // Create a new Graphics2D object for advanced graphics operations
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw the breakable wall image at the specified position
        g2d.drawImage(image, x, y, this);

        // Check if the wall's health is depleted, set visibility to false if so
        if (this.health == 0) {
            this.visible = false;
        }
    }

    /**
     * Empty paintComponent method. It is not used in this class, but it's here to satisfy
     * the requirements of extending JComponent. It contributes to the overall structure of the game components.
     *
     * @param g The graphics object to paint on
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Get the bounding rectangle of the breakable wall for collision detection.
     * This is crucial for detecting interactions with other game entities.
     *
     * @return The bounding rectangle of the breakable wall
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Set the health of the breakable wall.
     *
     * @param health The new health value to be added to the existing health
     */
    public void setHealth(int health) {
        this.health += health;
    }

    /**
     * Check if the breakable wall is visible.
     *
     * @return True if the wall is visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the visibility status of the breakable wall.
     *
     * @param visible The new visibility status
     */
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
