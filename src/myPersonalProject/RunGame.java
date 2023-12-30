package src.myPersonalProject;
import javax.swing.*;
/**
 * Coding Resources Used for this project:
 * 1. https://zetcode.com/javagames/sokoban/ (Map Building and integration)
 * 2. https://learncodebygaming.com/blog/how-to-make-a-video-game-in-java-2d-basics (Used many code concepts from here (user input, drawing, Timer, etc.,))
 * 3. YouTube Video - Java Game Programming - Multiplayer Tank 2D Game (https://www.youtube.com/watch?v=7jgIr96VBVE)
 * 4. How to Make a 2D Game in Java - https://www.youtube.com/watch?v=om59cwR7psI&t=5s
 * 5. Simple Java 2 Player Fight Game - https://www.youtube.com/watch?v=DyHfK1u1nuM&t=278s
 */
public class RunGame extends JFrame {
    private Game gameArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RunGame game = new RunGame();
            game.init();
        });
    }

    public RunGame() {
        // No need to call init() in the constructor, as it's called in main()
    }

    private void init() {
        setTitle("READY-FIGHT");

        int screenWidth = 1695;
        int screenHeight = 895;


        gameArea = new Game(screenWidth, screenHeight);
        add(gameArea);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

