package src.myPersonalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * NOTE
 * The Bullet class represents a projectile fired by tanks in the game.
 * It implements the Drawable interface for rendering on the graphical user interface.
 * Bullets travel in a straight line, and their visibility is controlled based on the game state.
 */
public class Bullet extends JComponent implements Drawable {

    // Variables for the bullet's position and speed
    private int x, y, vx, vy;

    // Radius of our bullet
    private final int bulletRadius = 7;

    // The angle at which the bullet is fired
    private short angle;

    // Dimensions of the bullet image
    private int width, height;

    // The visibility status of our bullet
    private boolean visible;

    // Image representing our bullet
    private BufferedImage image;

    // Board dimensions to keep our bullet in check
    private final int BOARD_WIDTH = 1900, BOARD_HEIGHT = 896;


    // The rectangle bounding our bullet for collision detection
    private Rectangle bounds;

    /**
     * Constructor for Bullet class that sets up the initial position and loads the bullet image.
     *
     * @param x     The starting x-coordinate of our bullet
     * @param y     The starting y-coordinate of our bullet
     * @param angle The angle at which our bullet is fired
     */
    public Bullet(int x, int y, short angle) {
        // Storing our initial coordinates and angle
        this.x = x;
        this.y = y;
        this.angle = angle;

        // Making sure our bullet is visible
        this.visible = true;

        // Initializing our graphical components, loading the bullet image
        init();

        // Setting up dimensions based on the loaded image
        this.width = image.getWidth();
        this.height = image.getHeight();

        // Creating a bounding rectangle for collision detection
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Initialize the graphical components by loading the bullet image from the resources.
     * This method gets called once during the creation of a Bullet object.
     */
    private void init() {
        try {
            // Attempting to load the bullet image, and handling errors
            image = ImageIO.read(getClass().getResource("resources/images/TankBullets.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the bullet image on the game screen at its designated position and rotation.
     *
     * @param g The graphics object that helps us draw
     */
    public void drawImage(Graphics g) {
        // Before we draw, be ready to paint
        this.paintComponent(g);

        // Creating an AffineTransform for rotation
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), image.getWidth() / 2.0, image.getHeight() / 2.0);

        // Creating a Graphics2D object for some advanced graphics operations
        Graphics2D graphic2D = (Graphics2D) g;
        // Drawing our rotated bullet image at the specified position
        graphic2D.drawImage(image, rotation, null);
    }

    /**
     * Placeholder method. Not used in this class, but it's here because we extend JComponent.
     *
     * @param g The graphics object that we don't really use here
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Get the bullet image.
     *
     * @return The image representing our speedy bullet
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Get the y-coordinate of our bullet.
     *
     * @return The y-coordinate of our speedy bullet
     */
    public int getY() {
        return y;
    }

    /**
     * Get the x-coordinate of our bullet.
     *
     * @return The x-coordinate of our speedy bullet
     */
    public int getX() {
        return x;
    }

    /**
     * Get the angle at which our bullet is fired.
     *
     * @return The angle of our speedy bullet's trajectory
     */
    public short getAngle() {
        return angle;
    }

    /**
     * Check if our bullet is visible.
     *
     * @return True if our speedy bullet is visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the visibility status of our bullet.
     *
     * @param visible The new visibility status of our speedy bullet
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Get the bounding rectangle of our bullet for collision detection.
     *
     * @return The bounding rectangle of our speedy bullet
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Get the height of our speedy bullet image.
     *
     * @return The height of our speedy bullet image
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of our speedy bullet image.
     *
     * @return The width of our speedy bullet image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Move our bullet based on its trajectory and update its visibility.
     *  if they venture off-screen, we stop drawing them.
     */
    public void move() {
        // Calculate the components of the velocity vector based on the angle
        vx = (int) Math.round(bulletRadius * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(bulletRadius * Math.sin(Math.toRadians(angle)));

        // Update the position of the bullets and their speed
        x += 2 * vx;
        y += 2 * vy;

        // If the bullet goes off-screen, stop drawing it
        if (0 > x || x > BOARD_WIDTH || y < 0 || y > BOARD_HEIGHT) {
            this.visible = false;
        }

        // Update the position of our bounding rectangle
        bounds.setLocation(x, y);

        // Repaint the component to reflect the new position
        repaint();
    }
}