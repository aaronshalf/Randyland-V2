import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.io.IOException;
import java.util.Scanner;


public class Randyland {
    /** The deck of Red/Green cards. */
    private static Stack<RGCard> RGDeck;
    /** Generates a deck of random card values. Ideally should have an alternative
     * method that imports a CSV or something so we can actually write the cards. */
    private static void randomDeckGen(int n) {
        RGDeck = new Stack<>();
        for (int i = 0; i < n; i += 1) {
            int a = StdRandom.uniform(-2, 5);
            int b = StdRandom.uniform(-4, 3);
            RGDeck.add(new RGCard("Randomly generated card", a, b, StdRandom.uniform(-3, 4)));
        }
    }
    /** The board class will be instantiated at every game.
     * Contains all the relevant data, such as the board layout, the
     * players in the game, and which squares they're on. */
    public static class Board {
        private Square[] layout;
        private Map<String, Integer> locations;
        public Set<Player> players;
        private int markingPeriod;
        private boolean gameOver;
        /** Board constructor, to be instantiated before the game begins.*/
        public Board() {
            players = new HashSet<>();
            layout = new Square[24];
            boolean color = false;
            for (int i = 0; i < 24; i += 1) {
                layout[i] = new Square(color);
                color = !color;
            }
            Randyland.randomDeckGen(50);
            int markingPeriod = 0;
            locations = new HashMap<>();
            gameOver = false;
        }
        /** Advance a player [dist] squares. If it's from a roll, they will land
         * on the square and activate effects. Otherwise, they'll just end up
         * at whatever square they're meant to go to. If they would go back before
         * the start, Mr Li gives them a boost, putting them 2 spaces ahead of their
         * current space (not where they would end up). */
        private void advance(Player player, int dist, boolean roll) {
            int currLoc = locations.get(player.name);
            if (currLoc + dist >= layout.length) {
                System.out.println(player.name + " has won the game (theoretically)");     //more stuff should happen here but lazy.
                gameOver = true;
                return;
            }
            if (currLoc == -1) {
                locations.put(player.name, currLoc + dist);
                layout[currLoc + dist].land(player);
            } else if (currLoc + dist < 0) {
                System.out.println("You seem to be having a rough time.");
                System.out.println("Mr Li notices you struggling and offers you");
                System.out.println("after school tutoring. He gets you ahead a couple spaces instead.");
                layout[currLoc].leave(player);
                locations.put(player.name, currLoc + 2);
                layout[currLoc + 2].introduce(player);
            } else if (roll == true) {
                layout[currLoc].leave(player);
                locations.put(player.name, currLoc + dist);
                int result = layout[currLoc + dist].land(player);
                advance(player, result, false);
            } else {
                layout[currLoc].leave(player);
                locations.put(player.name, currLoc + dist);
                layout[currLoc + dist].introduce(player);
            }
        }
        /** Add a player through input. Main method should loop until input of n.
         * Players should land at start of board. */
        public void addPlayers(Scanner input) {
            String name;
            do {
                while (!input.hasNext()) {
                    System.out.println("Please enter a string name for your character");
                    input.next();
                }
                name = input.next();
                Player player = new Player(name);
                if (players.contains(player)) {
                    System.out.println("Name must be unique.");
                    continue;
                }
                players.add(player);
                locations.put(player.name, -1);
                input.nextLine();                          // I literally have no idea how this works
            } while (name == null);
        }
        /** Pass a marking period. Each player should take a turn. IDK what else is supposed to happen. */
        private void passPhase(Scanner input) {
            for (Player player : players) {
                if (gameOver) {
                    return;
                }
                /** The starting turn for the players. They'll randomly end up at a space between 0 and 5.*/
                if (locations.get(player.name) == -1) {
                    int start = StdRandom.uniform(1, 5);
                    System.out.println("    " + "Hello " + player.name + ", welcome to Tech, a school with truly outstanding administration");
                    System.out.println("You begin your first marking period placed in a class.");
                    System.out.println("The class was randomly selected when the administrators decided ");
                    System.out.println("to drunkenly throw darts at a computer screen. Because of this, " );
                    System.out.println("you ended up placed in a class roughly " + (start - 1) + " levels above " );
                    System.out.println("where you should start. You will now advance to that square. ");
                    advance(player, start, true);
                } else {
                    int roll = StdRandom.uniform(1, 3);
                    System.out.println(player.name + " is flipping a coin, result: " + roll);
                    advance(player, roll, true);
                }
            }
            markingPeriod += 1;
            if (markingPeriod % 3 == 0) {
                System.out.println("A new semester has ended! Now grading stuff should happen.");
                //TODO: stuff
            }
        }
        /** Displays relevant statistics when necessary. */
        private void displayGameState() {
            if (gameOver) {
                System.out.print("Now choose a college I guess I haven't written this code yet");
                return;
            }
            System.out.println("\n Current Game Status: ");
            System.out.println("The current marking period is " + (markingPeriod % 3 + 1));
            System.out.println("The current semester is " + (markingPeriod / 3 + 1));
            for (Player player : players) {
                System.out.print("Player " + player.name + " currently located at square " + locations.get(player.name));
                System.out.println(" with score of " + player.randoMeter);
            }
        }
    }
    /** The red/green cards. Each have text descriptions as display,
     *and contain the two options if drawn as a green card, or an integer
     *effect if drawn as a red card.*/
    private static class RGCard {
        private String title;
        private int option1;
        private int option2;
        private int effect;
        /** Create a card with the parameters given.*/
        private RGCard(String text, int a, int b, int eff) {
            title = text;
            option1 = a;
            option2 = b;
            effect = eff;
        }
        /** Draw the red side of a card. */
        private int drawRed() {      //Currently only results in shifts in location. Could implement both a change in score and location.
            System.out.println(title + " with effect " + effect);
            return effect;
        }
        /** Draw the green side of a card. */
        private int[] drawGreen() {
            System.out.println("Change your score by " + option1 + " or " + option2);
            return new int[]{option1, option2};             //TODO somehow implement user choice.
        }
    }
    /** The player class. Each player has a unique name and a randoMeter score.*/
    private static class Player {
        private boolean depressed;
        private String name;
        private int randoMeter;
        /** The player constructor, each player starts with a score of 5. */
        private Player(String name) {
            this.name = name;
            randoMeter = 5;
            depressed = false;
        }
        /** Should ideally allow the user to choose one option or the other
         * via input. No clue how though. */
        private int choose(int[] options) {
                        //TODO: IMPLEMENT SOME WAY OF CHOOSING BETWEEN THE OPTIONS
            return options[0];
        }
        /** Change the players randoMeter by a certain amount. */
        private void shift(int amount) {
            randoMeter += amount;
            if (randoMeter >= 10) {
                randoMeter = 10;
                System.out.println("I diagnose you with depression.");
                depressed = true;
                //TODO bad things happen now
            }
            if (randoMeter <= 0) {
                System.out.println("You're failing your classes.");
                //TODO bad things also happen here
            }
        }
        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            if (other.getClass() != this.getClass()) {
                return false;
            }
            return ((Player) other).name.equals(this.name);
        }
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
    /** The squares of the board. Contain a list tracking
     * the players currently on that square as well as the
     * type of square.*/
    private static class Square {
        private boolean red;
        private List<Player> members;
        /** Create a generic colored square. */
        private Square(boolean color) {
            red = color;
            members = new LinkedList<>();
        }
        /**Land a player on this square. Will result in the
         * appropriate card draw, or, if it's a start square,
         * will introduce the player. */
        private int land(Player player) {
            members.add(player);
            RGCard top = RGDeck.pop();
            if (RGDeck.size() == 0) {
                Randyland.randomDeckGen(50);
            }
            if (red) {
                int influence = top.drawRed();
                return influence;
            } else {
                int[] result = top.drawGreen();
                player.shift(player.choose(result));
                return 0;
            }
        }
        /** If a player is moved to a square with a process
         * that would not result in a card being drawn,
         * use this method instead of land. */
        private void introduce(Player player) {
            members.add(player);
        }
        /** Remove a player from the set of players on this square.*/
        private void leave(Player player) {
            members.remove(player);
        }
    }
    public static void main(String[] args) throws IOException {
        int N;
        Board board = new Board();
        Scanner input = new Scanner(System.in);
        System.out.println("\n You are now beginning a game of Randyland. "
                + "Game currently under dev.\n"
                + " I'd explain the rules but "
                + "that would take a lot of work.\n");
        System.out.print("Name your characters: ");
        String repeat = "y";
        do {
            System.out.print("\nEnter name as a unique String: "); //do something
            board.addPlayers(input);
            System.out.print("\nWould you like to add another char? (y/n)");
            repeat = input.nextLine();
        } while (!repeat.equalsIgnoreCase("n") && !repeat.equalsIgnoreCase("no"));
        System.out.print("\n All characters have been added. ");
        System.out.println("Now beginning turns.");
        do {
            board.passPhase(input);
            board.displayGameState();
        } while(!board.gameOver);
        input.close();
    }
}
