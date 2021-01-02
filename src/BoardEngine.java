import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Character.isDigit;

public class BoardEngine {
    public static final int SCALE = 16;
    public static final int HEIGHT = 60;
    public static final int WIDTH = 100;
    public static final int NUMSQUARES = 24;
    private static final String BACKGROUND;
    private static final String HEADROYCE;
    private static final String TECH;
    private static final String OHIGH;
    private static final String ODOWD;
    public static final Font DEFAULTFONT = new Font("Monaco", Font.BOLD, SCALE);
    public static final Font MEDFONT = new Font("Monaco", Font.PLAIN, 5 * SCALE);
    public static final Font BIGFONT = new Font("Monaco", Font.BOLD, 10 * SCALE);
    public static final Random RANDOM = new Random();
    /** Tracks the squares of the board, which contain details about the players on them.
     * Contains NUMSQUARES entries, for each possible square a player can stand on.
     * The points are organized in a sin wave. */
    public static final Square[] SQUARESET;
    /** Tracks the players and their locations. */
    private ArrayList<Player> players;
    /** Tracks the schools for the sake of unique avatar select. */
    private HashMap<Integer, String> schools;
    public boolean gameOver;
    private Player winner = null;
    private int markingPeriod;
    private int semester;
    private int year;

    static {
        SQUARESET = new Square[NUMSQUARES];
        Color squareColor = Color.RED;
        for (int i = 0; i < NUMSQUARES; i += 1) {
            Position pos = new Position(0.4 * WIDTH * Math.sin(2 * Math.PI * 2 * i /NUMSQUARES) + WIDTH * .5, 0.9 * HEIGHT * (i + 1)/NUMSQUARES + 1);
            SQUARESET[i] = new Square(squareColor, "square" + i, pos);
            if (squareColor.equals(Color.RED)) {
                squareColor = Color.GREEN;
            } else {
                squareColor = Color.RED;
            }
        }
        BACKGROUND = Paths.get("randyland_background.png").toString();
        HEADROYCE = Paths.get("headroyce.png").toString();
        TECH = Paths.get("tech.png").toString();
        OHIGH = Paths.get("ohigh.png").toString();
        ODOWD = Paths.get("odowd.png").toString();
    }
    /** Instantiate a boardEngine. */
    public BoardEngine() {
        this.players = new ArrayList<>();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * SCALE, HEIGHT * SCALE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setFont(DEFAULTFONT);
        schools = new HashMap<>();
        schools.put(1, HEADROYCE);
        schools.put(2, TECH);
        schools.put(3, OHIGH);
        schools.put(4, ODOWD);
        markingPeriod = 1;
        semester = 1;
        year = 1;
    }
    /** Start tracking players with unique String names. */
    public void addPlayer(Player player) {
        players.add(player);
        SQUARESET[0].getPlayers().add(player);
    }
    /** Render the current board state. */
    public void showBoard() {
        StdDraw.clear(Color.BLACK);
        StdDraw.picture(WIDTH * .5, HEIGHT * .5, BACKGROUND);
        for (Square square : SQUARESET) {
            square.draw();
        }
        StdDraw.show();
        StdDraw.pause(2000); //TODO: could change this to a keypress.
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
                //System.out.print(c);
                if (c == ' ') {
                    Player player;
                    if (parser.toString().equals("BOT")) {
                        player = new Player("BOT", true);
                    } else {
                        player = new Player(parser.toString(), false);
                    }
                    addPlayer(player);
                    charSelect(player);
                    break;
                }
                parser.append(c);
                if (c == '\\') {
                    parser = new StringBuilder();
                }

            }
            StdDraw.clear(Color.BLACK);
            StdDraw.setFont(MEDFONT);
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.text(WIDTH * .5, .7 * HEIGHT, "Enter player name, then");
            StdDraw.text(WIDTH * .5, .6 * HEIGHT, "press space to finish entry.");
            StdDraw.setFont(DEFAULTFONT);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(WIDTH * .5, .5 * HEIGHT, parser.toString());
            StdDraw.text(WIDTH * .5, .2 * HEIGHT, "(type \\ to clear entry)");
            StdDraw.show();
        }
    }
    /** Enables players to select their avatars. Must be unique. */
    public void charSelect(Player player) {
        StdDraw.clear(Color.BLACK);
        popText(player.getName().toUpperCase(), WIDTH * .5, HEIGHT * .75, Color.BLUE, BIGFONT);
        popText("Select your avatar.", WIDTH * .5, HEIGHT * .67, Color.WHITE, DEFAULTFONT);

        if (schools.get(1) != null) {
            StdDraw.picture(40, 30, HEADROYCE);
            StdDraw.text(40, 33, "(1)");
        }
        if (schools.get(2) != null) {
            StdDraw.picture(60, 30, TECH);
            StdDraw.text(60, 32, "(2)");
        }
        if (schools.get(3) != null) {
            StdDraw.picture(40, 10, OHIGH);
            StdDraw.text(40, 12, "(3)");
        }
        if (schools.get(4) != null) {
            StdDraw.picture(60, 10, ODOWD);
            StdDraw.text(60, 12, "(4)");
        }

        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (isDigit(c) && schools.containsKey(Character.getNumericValue(c))) {
                    player.setImage(schools.get(Character.getNumericValue(c)));
                    schools.remove(Character.getNumericValue(c));
                    break;
                } else {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.text(WIDTH * .5, .3 * HEIGHT, "Must be proper selection.");
                    StdDraw.show();
                }
            }
        }
        return;
    }
    /** Begin the player creation, then transition to game phase. */
    public void initialize() {
        while (true) {
            StdDraw.clear(Color.BLACK);
            enterName();
            displayText("Add another player? (y/n)");
            while (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (!(StdDraw.nextKeyTyped() == 'y')) {
                break;
            }
        }
        showBoard();
        turnLoop();
    }
    public void endScreen(Player player) {
        StdDraw.clear(Color.BLACK);
        //StdDraw.setPenColor(Color.BLUE);
        //StdDraw.setFont(BIGFONT);
        //StdDraw.text(WIDTH * .5,  0.8 * HEIGHT, player.getName() + "has won!!");
        popText(player.getName() + " has won!!", WIDTH * .5, HEIGHT * .75,  Color.BLUE, BIGFONT);
        //StdDraw.setPenColor(Color.WHITE);
        //StdDraw.setFont(DEFAULTFONT);
        //StdDraw.text(WIDTH * .5, .4 * HEIGHT, "you may exit the game now.");
        popText("(you may exit the game now.)", WIDTH * .5, .4 * HEIGHT,  Color.WHITE, DEFAULTFONT);
        StdDraw.pause(10000);
        System.exit(0);
    }
    /** Goes through each turn until the game ends. */
    public void turnLoop() {
        while (!gameOver) {
            //System.out.println(markingPeriod + " " + semester + " " + year);
            for (Player player: players) {
                if (player.getDepressed() && player.getDepressedTurns() == 0) {
                    displayText(player.getName() + ", your depression has cost you a turn. Your meter will be set to 9.");
                    player.resetDepressed();
                    player.changeMeter(-1);
                    StdDraw.pause(1500);
                    break;
                }
                int move = (player.flipCoin() ? 1:0) + 1;
                movePlayer(player, move);
                showBoard();
                int penalty = player.drawCard();
                if (penalty != 0) {
                    movePlayer(player, penalty);
                    showBoard();
                }
                int result = player.tick();
                if (result != 0) {
                    displayText(player.getName() + ", you received a failing grade for the marking period. You move back one space but increase your meter by 1.");
                    StdDraw.pause(1500);
                    movePlayer(player, result);
                    player.changeMeter(1);
                    showBoard();
                }
            }
            markingPeriod += 1;
            if (markingPeriod > 3) {
                markingPeriod = 1;
                semester += 1;
                if (semester > 2) {
                    semester = 1;
                    year += 1;
                }
            }
        }
        endScreen(winner);
        //leaderboard here?
    }
    /** Move a player a specified distance forward. */
    public void movePlayer(Player player, int distance) {
        if (player.getLocation() + distance >= NUMSQUARES) {
            gameOver = true;
            winner = player;
            endScreen(player);
            return;
        } else if (player.getLocation() + distance <= 0) {
            displayText("You seem to be struggling. Mr. Li puts in a good word for you. Move forward 3 squares.");
            movePlayer(player, 3);
            return;
        }
        SQUARESET[player.getLocation()].getPlayers().remove(player);
        SQUARESET[player.getLocation() + distance].getPlayers().add(player);
        player.changeLocation(distance);
    }
    /** Display a message in the middle of the screen. */
    public static void displayText(String text) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(DEFAULTFONT);
        StdDraw.text(WIDTH * .5, HEIGHT * .5, text);
        StdDraw.show();
    }

    /** Display a message at a location without clearing. */
    public static void popText(String text, double x, double y, Color color, Font font) {
        StdDraw.setPenColor(color);
        StdDraw.setFont(font);
        StdDraw.text(x, y, text);
        StdDraw.show();
    }

    public static void solicitKey(char key, boolean any) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (any || c == key) {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        BoardEngine engine = new BoardEngine();
        engine.initialize();
    }
}
