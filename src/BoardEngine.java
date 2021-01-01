import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.nio.file.Paths;
import java.util.*;

public class BoardEngine {
    public static final int SCALE = 16;
    public static final int HEIGHT = 60;
    public static final int WIDTH = 100;
    public static final int NUMSQUARES = 24;
    private static final String BACKGROUND;
    public static final Square[] SQUARESET;
    /** Tracks the players and their locations. */
    private ArrayList<Player> players;
    private boolean gameOver;

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
        this.players = new ArrayList<>();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * SCALE, HEIGHT * SCALE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, SCALE - 2);
        StdDraw.setFont(font);
    }
    /** Start tracking players with unique String names. */
    public void addPlayer(Player player) {
        players.add(player);
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
    /** Displays information about the state of the game. */
    public void displayHud() {
        //Theoretically would display semester, leaderboard, current players turn.
        //TODO: need to resize image to make room.
    }
    /** Add players to the game. */
    public void enterName() {
        StringBuilder parser = new StringBuilder();
        StdDraw.setPenColor(Color.WHITE);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.BLACK);
                char c = StdDraw.nextKeyTyped();
                parser.append(c);
                //System.out.print(c);
                if (c == ' ') {
                    Player player = new Player(parser.toString());
                    addPlayer(player);
                    charSelect(player);
                    break;
                }
            }
            StdDraw.clear(Color.BLACK);
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter player name. Press space to enter name.");
            StdDraw.text(WIDTH / 2, 25, parser.toString());
            StdDraw.show();
        }

    }
    public void charSelect(Player player) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                switch (c) {
                    case (1):
                }
            }
        }
    }
    public void initialize() {
        boolean complete = false;
        while (!complete) {
            StdDraw.clear(Color.BLACK);
            enterName();
            displayText("Add another player? (y/n)");
            while (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (!(StdDraw.nextKeyTyped() == 'y')) {
                complete = true;
            }
        }
        showBoard();
        turnLoop();
    }

    public void turnLoop() {
        while (!gameOver) {

        }
    }

    public void displayText(String text) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, text);
        StdDraw.show();
    }

    public static void main(String[] args) {
        BoardEngine engine = new BoardEngine();
        engine.initialize();
    }
}
