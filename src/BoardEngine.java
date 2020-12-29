import java.awt.*;
import java.nio.file.Paths;
import java.util.*;

public class BoardEngine {
    public static final int SCALE = 16;
    public static final int HEIGHT = 60;
    public static final int WIDTH = 100;
    public static final int NUMSQUARES = 24;
    private static final String BACKGROUND;
    /** Tracks the players and their locations. */
    private HashMap<String, Integer> players;
    public static final Square[] SQUARESET;
    static {
        SQUARESET = new Square[NUMSQUARES];
        Color squareColor = Color.RED;
        for (int i = 0; i < NUMSQUARES; i += 1) {
            Position pos = new Position(0.4 * WIDTH * Math.sin(2 * Math.PI * 2 * i /NUMSQUARES) + WIDTH / 2, 0.9 * HEIGHT * (i + 1)/NUMSQUARES + 1);
            SQUARESET[i] = new Square(squareColor, "square" + i, pos);
            if (squareColor.equals(Color.RED)) {
                squareColor = Color.GREEN;
            } else {
                squareColor = Color.RED;
            }
        }
        BACKGROUND = Paths.get("randyland_background.PNG").toString();
    }
    /** Instantiate a boardEngine. */
    public BoardEngine() {
        this.players = new HashMap<>();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * SCALE, HEIGHT * SCALE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, SCALE - 2);
        StdDraw.setFont(font);
    }
    /** Start tracking players with unique String names. */
    public void addPlayer(String name) {
        players.put(name, 0);
    }
    /** Render the current board state. */
    public void showBoard() {
        StdDraw.clear(Color.BLACK);
        StdDraw.picture(WIDTH / 2, HEIGHT / 2, BACKGROUND);
        for (Square square : SQUARESET) {
            square.draw();
        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        BoardEngine engine = new BoardEngine();
        engine.showBoard();
    }
}
