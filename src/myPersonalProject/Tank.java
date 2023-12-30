package src.myPersonalProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
/**
 * NOTE
 * The Tank class represents a player-controlled tank in the game.
 * It implements the Drawable interface for rendering on the graphical user interface.
 * Tanks have the ability to move, rotate, and fire bullets based on user input.
 */
public class Tank extends JComponent implements Drawable {
    ///////----------------------Tank properties--------------------------------------------------------------------///////
    private int x, y;
    private final int r = 2;
    private int vx, vy, bx, by;
    private short angle;
    private BufferedImage image;
    private boolean UpPressed, DownPressed, RightPressed, LeftPressed, FirePressed;
    private final int up, down, right, left, fire;
    private ArrayList<Bullet> bullets;
    private int rateOfFire, repaintCount = 0, health = 50, lives = 4, damage, oldX, oldY;
    private Rectangle bounds;
    ///////------------------------------------------------------------------------------------------///////
    /**
     * Constructor for the Tank class, initializing its properties and loading the tank image.
     *
     * @param x     Initial x-coordinate of the tank
     * @param y     Initial y-coordinate of the tank
     * @param vx    Initial velocity along the x-axis
     * @param vy    Initial velocity along the y-axis
     * @param angle Initial angle of the tank
     * @param up    Key code for moving up
     * @param down  Key code for moving down
     * @param left  Key code for rotating left
     * @param right Key code for rotating right
     * @param fire  Key code for firing bullets
     */
    public Tank(int x, int y, int vx, int vy, short angle, int up, int down, int left, int right, int fire) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.fire = fire;
        this.rateOfFire = 20;
        this.damage = -10;
        // Loading tank image
        init();
        // Creating a bounding rectangle for collision detection
        bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        // Creating a list to store bullets fired by the tank
        bullets = new ArrayList<>();

    }
    /**
     * Initialize the graphical components by loading the tank image from the resources.
     * This method gets called once during the creation of a Tank object.
     */
    private void init() {
        try {
            image = ImageIO.read(getClass().getResource("resources/images/PlayersTank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the tank image on the game screen at its designated position and rotation.
     *
     * @param g The graphics object that helps us draw
     */
    public void drawImage(Graphics g) {
        // Before we draw, make sure we're ready to paint
        paintComponent(g);
        // Creating an AffineTransform for rotation
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), image.getWidth() / 2, image.getHeight() / 2);
        // Creating a Graphics2D object for some advanced graphics operations
        Graphics2D graphic2D = (Graphics2D) g;
        // Drawing our rotated tank image at the specified position
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
     * Set the x-coordinate of the tank.
     *
     * @param x The new x-coordinate of the tank
     */
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    /**
     * Get the tank image.
     *
     * @return The image representing our mighty tank
     */
    public BufferedImage getImage() {
        return image;
    }
    /**
     * Set the velocity along the x-axis of the tank.
     *
     * @param vx The new velocity along the x-axis of the tank
     */
    public void setVx(int vx) {
        this.vx = vx;
    }
    public void setVy(int vy) {
        this.vy = vy;
    }
    /**
     * Set the angle of the tank.
     *
     * @param angle The new angle of the tank
     */
    public void setAngle(short angle) {
        this.angle = angle;
    }
    /**
     * Set the health of the tank.
     *
     * @param health The new health of the tank
     */
    public void setHealth(int health) {
        this.health += health;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setRateOfFire(int rateOfFire) {
        this.rateOfFire = rateOfFire;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    ///////------------------------------------------------------------------------------------------///////
    public int getRateOfFire() {
        return rateOfFire;
    }

    @Override
    public int getX() {
        return x;
    }
    public int getOldX() {
        return oldX;
    }
    @Override
    public int getY() {
        return y;
    }
    public int getOldY() {
        return oldY;
    }
    public short getAngle() {
        return angle;
    }

    public int getHealth() {
        return health;
    }
    public int getLives() {
        return lives;
    }
    ///////------------------------------------------------------------------------------------------///////
    public int getDamage() {
        return damage;
    }
    public void toggleUpPressed() {
        this.UpPressed = true;
    }
    public void toggleDownPressed() {
        this.DownPressed = true;
    }
    public void toggleRightPressed() {
        this.RightPressed = true;
    }
    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    public void toggleFirePressed() { this.FirePressed = true;}
    public void unToggleUpPressed() {
        this.UpPressed = false;
    }
    public void unToggleDownPressed() {
        this.DownPressed = false;
    }
    public void unToggleRightPressed() {
        this.RightPressed = false;
    }
    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    public void unToogleFirePressed() { this.FirePressed = false;}
    public boolean isUpPressed() {
        return UpPressed;
    }
    ///////------------------------------------------------------------------------------------------///////
    /**
     * KeyPressed event handler for tank movement and actions and getting input using action listner
     *
     * @param ke The KeyEvent object representing the key press
     */
    public void keyPressed(KeyEvent ke) {

        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            toggleUpPressed();
        }
        if (keyPressed == down) {
            toggleDownPressed();
        }
        if (keyPressed == left) {
            toggleLeftPressed();
        }
        if (keyPressed == right) {
            toggleRightPressed();
        }
        if (keyPressed == fire) {
            toggleFirePressed();
        }
        update();
    }

    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();

        if (keyReleased  == up) {
            unToggleUpPressed();
            vx = 0;
        }
        if (keyReleased == down) {
            unToggleDownPressed();
            vy = 0;
        }

        if (keyReleased  == left) {
            unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            unToggleRightPressed();
        }

        if (keyReleased == fire) {
            unToogleFirePressed();
        }
        update();
    }
    /**
     * Update the tank's state based on user input and game conditions.
     * Moves the tank, rotates it, and fires bullets if necessary.
     * Also updates the bounding rectangle and triggers a repaint.
     */
    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.FirePressed) {
            this.fire();
        }
        bounds.setLocation(x, y);
        this.repaint();
        repaintCount += 1;
    }
    ///////------------------------------------------------------------------------------------------///////

    /**
     * Fire a bullet from the tank's current position and angle.
     * The firing rate is controlled to avoid rapid firing.
     */    private void fire() {
        if (repaintCount % rateOfFire == 0) {
            bx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
            by = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
            bullets.add(new Bullet(x + 10 + 8 * bx,y + 10 + 8 * by, angle));
        }
    }

    /**
     * Rotate the tank to the left by a specified angle increment. 360 rotation
     */    private void rotateLeft() {
        if (this.angle - 3 == 0) {
            this.angle = 360;
        }
        this.angle -= 3;
    }

    private void rotateRight() {
        if (this.angle + 3 == 360) {
            this.angle = 0;
        }
        this.angle += 3;
    }
    /**
     * Move the tank backward/Forward based on its current angle and velocity.
     * Ensures that the tank stays within the game boundaries.
     */
    private void moveBackwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }
    /**
     * Check and adjust the tank's position to stay within the game boundaries.
     */
    private void checkBorder() {
        if (x <    32)    x = 32;
        if (x >= 1834)    x = 1834;
        if (y <    32)    y = 32;
        if (y >=  819)    y = 819;
    }
    /**
     * Get the bounding rectangle of the tank for collision detection.
     *
     * @return The bounding rectangle of the tank
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Push the tank away from a wall depending on its direction of movement.
     * This prevents the tank from going through walls.
     */
    public void pushFromWall() {
        if (DownPressed){
            vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
            x += 4 * vx;
            y += 4 * vy;

        } else if (UpPressed) {
            vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
            x -= 4 * vx;
            y -= 4 * vy;
        }
    }
    /**
     * Convert the tank's position and angle to a string representation.
     *
     * @return A string containing the x-coordinate, y-coordinate, and angle of the tank
     */
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
}