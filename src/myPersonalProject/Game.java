package src.myPersonalProject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * NOTE
 *
 * The Game class represents the main game environment, including tanks, bullets, and walls.
 * It extends JPanel and implements the ActionListener interface for game updates.
 */
public class Game extends JPanel implements ActionListener {
    private BufferedImage background;
    private Tank PlayerOne;
    private Tank PlayerTwo;
    private boolean gameOver;
    private int screenWidth, screenHeight;
    private ArrayList<Wall> NonBreakablewalls = new ArrayList<>();
    private ArrayList<BreakableWall> breakableWalls = new ArrayList<>();
    private GameUI ui;


    // http://zetcode.com/tutorials/javagamestutorial/sokoban/ --> website that explains printing such maps
    //-------------------------------------   Map Design  -----------------------------------------------------//
    private String level = "#####################################################\n" +
            "#    $        $        $    $        $       $      #\n" +
            "#    $        ##########    ##########       $      #\n" +
            "#    $        $  #   # $    $ #   #  $       $      #\n" +
            "#    $        $  # # # $    $ # # #  $       $      #\n" +
            "#    $        $        $    $        $       $      #\n" +
            "#    $        ########## $  ##########       $      #\n" +
            "#    $                   #                   $      #\n" +
            "######$$$$               $               $$$$########\n" +
            "######$$$$               #               $$$$########\n" +
            "#    $       $           $          $        $      #\n" +
            "#    $       $           #          $        $      #\n" +
            "#    $       $           $          $        $      #\n" +
            "#    $       $           #          $        $      #\n" +
            "#    $       $           $          $        $      #\n" +
            "#    $       $           #          $        $      #\n" +
            "#    $   $   $   $   $   $   $   $  $    $   $      #\n" +
            "#    $   $       $   $       $   $       $   $      #\n" +
            "######$$$$       $   $       $   $       $$$$########\n" +
            "######$$$$###$$$$$   $       $   $$$$$###$$$$########\n" +
            "#    $                                       $      #\n" +
            "#    $                                       $      #\n" +
            "#    $                                       $      #\n" +
            "#    $                                       $      #\n" +
            "#    $   $   $   $   $   $   $   $   $   $   $      #\n" +
            "#    $   $   $   $   $   $   $   $   $   $   $      #\n" +
            "#    $                                       $      #\n" +
            "#####################################################\n";

    /**
     * Constructs the Game with the specified screen width and height.
     *
     * @param screenWidth  The width of the game screen.
     * @param screenHeight The height of the game screen.
     */
    public Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        init();
    }

    /**
     * Initializes the game environment, including tanks, walls, and UI.
     */
    private void init() {
        setOpaque(false); // Sets the panel to be transparent.
        addKeyListener(new TAdapter()); // Adds a key listener for tank controls.
        setFocusable(true); // Allows the panel to receive focus.
        setDoubleBuffered(true); // Enables double buffering for smoother rendering.

        Wall wall;
        BreakableWall bwall;
        int x = 0;
        int y = 0;
        int i = 0;
        while (i < level.length()) {
            char item = level.charAt(i);
            int SPACE = 32;// Spacing between map elements.
            if (item == '\n') {
                y += SPACE;
                x = 0;
            } else if (item == '#') {
                wall = new Wall(x, y);
                NonBreakablewalls.add(wall);
                x += SPACE;
            } else if (item == '$') {
                bwall = new BreakableWall(x, y);
                breakableWalls.add(bwall);
                x += SPACE;
            } else if (item == ' ') {
                x += SPACE;
            }
            i++;
        }
        // creates tank and place them on the map positions
        PlayerOne = new Tank(32, 32, 0, 0, (short) 0, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
        PlayerTwo = new Tank(screenWidth - 110, screenHeight - 85, 0, 0, (short) 180, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_H);

        try {
            background = ImageIO.read(getClass().getResource("resources/images/StageBackground.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // creates UI
        ui = new GameUI(PlayerOne, PlayerTwo);
        gameOver = false;


        // timer to know when to update everything
        int DELAY = 10;
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Scales the map based on the specified width and height scales.
     *
     * @param scaleWidth  The scaling factor for the width.
     * @param scaleHeight The scaling factor for the height.
     */
    private void scaleMap(float scaleWidth, float scaleHeight) {
        StringBuilder scaledLevel = new StringBuilder();
        for (int i = 0; i < level.length(); i++) {
            char item = level.charAt(i);
            if (item == '\n') {
                scaledLevel.append('\n');
            } else if (item == '#') {
                scaledLevel.append('#');
            } else if (item == '$') {
                scaledLevel.append('$');
            } else if (item == ' ') {
                scaledLevel.append(' ');
            } else if (item == '%') {
                scaledLevel.append('%');
            }
        }
        level = scaledLevel.toString();
    }


    public Dimension getPreferredSize() {
        return new Dimension(screenWidth, screenHeight);
    }


    /**
     * Paints the game components, including tanks, bullets, walls, and UI.
     *
     * @param g The Graphics object to paint on.
     */    // were transparent.
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(background, 0, 0, this);

        // draws everything
        if (!gameOver) {
            ArrayList<Bullet> bulletsT1 = PlayerOne.getBullets();
            ArrayList<Bullet> bulletsT2 = PlayerTwo.getBullets();
            for (int i = 0; i < bulletsT1.size(); i++) {
                Bullet bullet = bulletsT1.get(i);
                bullet.drawImage(g);
            }
            for (int i = 0; i < bulletsT2.size(); i++) {
                Bullet bullet = bulletsT2.get(i);
                bullet.drawImage(g);
            }
            for (int i = 0; i < NonBreakablewalls.size(); i++) {
                Wall wall = NonBreakablewalls.get(i);
                wall.drawImage(g);
            }
            for (int i = 0; i < breakableWalls.size(); i++) {
                BreakableWall bwall = breakableWalls.get(i);
                bwall.drawImage(g);
            }
            PlayerOne.drawImage(g);
            PlayerTwo.drawImage(g);
            ui.drawImage(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        updateTanks();
        updateBullets();
        updateWalls();
        collision();
        repaint();
    }


    /**
     * Updates tank positions, health, and lives.
     */
    private void updateTanks() {
        PlayerOne.update();
        PlayerTwo.update();
    }

    /**
     * Updates bullet positions and checks for bullet visibility.
     */
    private void updateBullets() {
        ArrayList<Bullet> bulletsT1 = PlayerOne.getBullets();
        ArrayList<Bullet> bulletsT2 = PlayerTwo.getBullets();
        for (int i = 0; i < bulletsT1.size(); i++) {
            Bullet b = bulletsT1.get(i);
            if (b.isVisible()) {
                b.move();
            } else {
                bulletsT1.remove(i);
            }
        }
        for (int i = 0; i < bulletsT2.size(); i++) {
            Bullet b = bulletsT2.get(i);
            if (b.isVisible()) {
                b.move();
            } else {
                bulletsT2.remove(i);
            }
        }
    }

    /**
     * Updates breakable walls, removing them when not visible to prevent collisions.
     */
    private void updateWalls() {
        for (int i = 0; i < breakableWalls.size(); i++) {
            BreakableWall bwall = breakableWalls.get(i);
            if (!bwall.isVisible()) {
                breakableWalls.remove(i);
            }
        }
    }
///////---------------------------------COLLISION------------------------------------------------------

    /**
     * The collision method handles the collision detection and resolution in the game.
     * It checks for collisions between tanks, bullets, and walls, updating game state accordingly.
     */
    public void collision() {
        // Get bounding rectangles for tanks and bullets
        Rectangle r1 = PlayerOne.getBounds(); // Bounding rectangle for tank1
        Rectangle r2 = PlayerTwo.getBounds(); // Bounding rectangle for tank2
        ArrayList<Bullet> b1 = PlayerOne.getBullets(); // Bullets from tank1
        ArrayList<Bullet> b2 = PlayerTwo.getBullets(); // Bullets from tank2

        // Check collisions for bullets from tank1
        for (int i = 0; i < b1.size(); i++) {
            Bullet b = b1.get(i);
            Rectangle r3 = b.getBounds(); // Bounding rectangle for bullet from tank1

            // Handle collisions with tank2
            if (r3.intersects(r2)) {
                b.setVisible(false); // Hide the bullet
                PlayerTwo.setHealth(PlayerOne.getDamage()); // Reduce tank2 health

                // Check if tank2 is health is destroyed
                if (PlayerTwo.getHealth() <= 0) {
                    PlayerTwo.setLives(PlayerTwo.getLives() - 1); // Decrease tank2 lives

                    // Check if tank2 has no lives left, end the game
                    if (PlayerTwo.getLives() == 0) {
                        gameOver = true;
                    } else {
                        PlayerTwo.setHealth(50); // Reset tank2 health
                    }
                }
            }

            // Handle collisions with breakable walls
            for (int j = 0; j < breakableWalls.size(); j++) {
                BreakableWall w = breakableWalls.get(j);
                if (w.isVisible()) {
                    Rectangle r4 = w.getBounds(); // Bounding rectangle for breakable wall

                    // Check if bullet from tank1 intersects with breakable wall
                    if (r4.intersects(r3)) {
                        b.setVisible(false); // Hide the bullet
                        w.setHealth(-10); // Damage breakable wall
                    }
                }
            }

            // Handle collisions with non-breakable walls
            for (int j = 0; j < NonBreakablewalls.size(); j++) {
                Wall w = NonBreakablewalls.get(j);
                if (w.isVisible()) {
                    Rectangle r4 = w.getBounds(); // Bounding rectangle for non-breakable wall

                    // Check if bullet from tank1 intersects with non-breakable wall
                    if (r4.intersects(r3)) {
                        b.setVisible(false); // Hide the bullet
                    }
                }
            }
        }

        // Same collision checks for bullets from tank2
        for (int i = 0; i < b2.size(); i++) {
            Bullet b = b2.get(i);
            Rectangle r3 = b.getBounds();

            // Handle collisions with tank1
            if (r3.intersects(r1)) {
                b.setVisible(false);
                PlayerOne.setHealth(PlayerTwo.getDamage());

                if (PlayerOne.getHealth() <= 0) {
                    PlayerOne.setLives(PlayerOne.getLives() - 1);

                    if (PlayerOne.getLives() == 0) {
                        gameOver = true;
                    } else {
                        PlayerOne.setHealth(60);
                    }
                }
            }

            // Handle collisions with breakable walls
            for (int j = 0; j < breakableWalls.size(); j++) {
                BreakableWall w = breakableWalls.get(j);
                if (w.isVisible()) {
                    Rectangle r4 = w.getBounds();
                    if (r4.intersects(r3)) {
                        b.setVisible(false);
                        w.setHealth(-10);
                    }
                }
            }

            // Handle collisions with non-breakable walls
            for (int j = 0; j < NonBreakablewalls.size(); j++) {
                Wall w = NonBreakablewalls.get(j);
                if (w.isVisible()) {
                    Rectangle r4 = w.getBounds();
                    if (r4.intersects(r3)) {
                        b.setVisible(false);
                    }
                }
            }
        }

        // Check collisions between tanks and walls, pushing tanks back
        for (int i = 0; i < NonBreakablewalls.size(); i++) {
            Wall wall = NonBreakablewalls.get(i);
            Rectangle bounds = wall.getBounds();

            // Check if tank1 intersects with wall, push tank1 away from the wall
            if (bounds.intersects(r1)) {
                PlayerOne.pushFromWall();
            } else if (bounds.intersects(r2)) {
                // Check if tank2 intersects with wall, push tank2 away from the wall
                PlayerTwo.pushFromWall();
            }
        }

        // Check collisions between breakable walls and tanks, pushing tanks back
        for (int i = 0; i < breakableWalls.size(); i++) {
            BreakableWall bWall = breakableWalls.get(i);
            if (bWall.isVisible()) {
                Rectangle r4 = bWall.getBounds();

                // Check if tank1 intersects with breakable wall, push tank1 away from the wall
                if (r4.intersects(r1)) {
                    PlayerOne.pushFromWall();
                } else if (r4.intersects(r2)) {
                    // Check if tank2 intersects with breakable wall, push tank2 away from the wall
                    PlayerTwo.pushFromWall();
                }
            }
        }
    }

    /**
     * The TAdapter class extends KeyAdapter and handles user keyboard inputs for tank movement and shooting.
     */
    private class TAdapter extends KeyAdapter {
        /**
         * Handles key release events for both tanks.
         *
         * @param e The KeyEvent object representing the key release event.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            for (Tank tank : Arrays.asList(PlayerOne, PlayerTwo)) tank.keyReleased(e);
        }

        /**
         * Handles key press events for both tanks.
         *
         * @param e The KeyEvent object representing the key press event.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            for (Tank tank : Arrays.asList(PlayerOne, PlayerTwo)) tank.keyPressed(e);
        }
    }
}