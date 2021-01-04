import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private int location;
    private int meter;
    private boolean depressed;
    private int depressedTurns;
    private String image;
    private ArrayList<Integer> reportCard;
    private boolean bot;
    /** Create a player with a unique name. They start on the first
     * square with 5 meter. Image is decided when they select an avatar.*/
    public Player(String name, boolean bot) {
        this.name = name;
        this.location = 0;
        this.meter = 5;
        this.depressed = false;
        this.depressedTurns = 4;
        this.image = "";
        this.reportCard = new ArrayList<>();
        this.bot = bot;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public int getLocation() {
        return location;
    }
    public boolean getDepressed() {
        return this.depressed;
    }
    public int getDepressedTurns() {
        return this.depressedTurns;
    }
    public ArrayList<Integer> getReportCard() {
        return reportCard;
    }
    public int getMeter() {
        return meter;
    }
    /** Return the players grade, determined by their meter score. */
    public static String getGrade(Integer score) {
        switch (score) {
            case (10):
                return "depression";
            case (9):
                return "A+";
            case (8):
                return "A";
            case (7):
                return "A-";
            case (6):
                return "B";
            case (5):
                return "B-";
            case (4):
                return "C";
            case (3):
                return "C-";
            case (2):
            default:
                return "Fail";
        }
    }
    /** Make them press a space bar and return the outcome of a simulated coin flip. */
    public boolean flipCoin() {
        BoardEngine.displayText(name + "'s turn: Press space to flip a coin");
        if (!bot) {
            BoardEngine.solicitKey(' ', false);
        }
        BoardEngine.displayText("The result of the flip was:");
        boolean flip = BoardEngine.RANDOM.nextBoolean();
        if (flip) {
            StdDraw.text(BoardEngine.WIDTH / 2, .45 * BoardEngine.HEIGHT, "Heads!");
        } else {
            StdDraw.text(BoardEngine.WIDTH / 2, .45 * BoardEngine.HEIGHT, "Tails!");
        }
        StdDraw.show();
        StdDraw.pause(1000);
        return flip;
    }
    /** Player draws a card for the square they're currently occupying. Then they
     * perform necessary actions dictated by that card. */
    public int drawCard() {
        int returnValue = 0;
        //if (BoardEngine.SQUARESET[location].getBackgroundColor().equals(Color.RED)) {
        if (location % 2 == 0) {                    //THIS ONLY WORKS FOR THE ALTERNATING BOARD SETUP. SWAP TO ABOVE OTHERWISE.
            returnValue = drawRedCard();
        } else {
            drawGreenCard();
        }
        if (!bot) {
            BoardEngine.popText("Press any key to continue.", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .1, Color.GREEN, BoardEngine.DEFAULTFONT);
            BoardEngine.solicitKey(' ', true);
        }
        return returnValue;
    }
    /** The process for drawing a red card. */
    public int drawRedCard() {
        RedCard card = CardSet.drawRedCard(location / 6);
        StdDraw.clear(Color.BLACK);
        BoardEngine.popText(name + " has drawn a red card!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .7, Color.WHITE, BoardEngine.MEDFONT);
        BoardEngine.popText(card.getDescription(), BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .5, Color.RED, BoardEngine.DEFAULTFONT);
        if (card.getAbove()) {
            BoardEngine.popText("You needed at least " + card.getThreshold() + " Randometer.", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .4, Color.WHITE, BoardEngine.DEFAULTFONT);
            StdDraw.pause(1000);
            if (meter >= card.getThreshold()) {
                BoardEngine.popText("Your meter is " + meter + ", so you stay!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .3, Color.WHITE, BoardEngine.DEFAULTFONT);
            } else {
                BoardEngine.popText("Your meter is " + meter + ", so you get sent back a square!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .3, Color.WHITE, BoardEngine.DEFAULTFONT);
                return -1;
            }
        } else {
            BoardEngine.popText("You needed less than " + card.getThreshold() + " Randometer.", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .4, Color.WHITE, BoardEngine.DEFAULTFONT);
            StdDraw.pause(1000);
            if (meter < card.getThreshold()) {
                BoardEngine.popText("Your meter is " + meter + ", so you stay!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .3, Color.WHITE, BoardEngine.DEFAULTFONT);
            } else {
                BoardEngine.popText("Your meter is " + meter + ", so you get sent back a square!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .3, Color.WHITE, BoardEngine.DEFAULTFONT);
                return -1;
            }
        }
        return 0;
    }
    /** The process for drawing a green card. */
    public void drawGreenCard() {
        boolean prevDepressed = this.depressed;
        GreenCard card = CardSet.drawGreenCard();
        StdDraw.clear(Color.BLACK);
        BoardEngine.popText(name + " has drawn a green card!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .7, Color.WHITE, BoardEngine.MEDFONT);
        BoardEngine.popText(card.getDescription(), BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .5, Color.GREEN, BoardEngine.DEFAULTFONT);
        BoardEngine.popText("Shift your Randometer by " + card.getOption1() + " or " + card.getOption2(), BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .4, Color.GREEN, BoardEngine.DEFAULTFONT);
        BoardEngine.popText("(Press 1 for option one, 2 for option two.)", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .37, Color.WHITE, BoardEngine.DEFAULTFONT);
        BoardEngine.popText("(Current meter is " + meter + ")", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .33, Color.WHITE, BoardEngine.DEFAULTFONT);
        char c = 0;
        while (true) {
            if (bot) {
                c = (char) (BoardEngine.RANDOM.nextInt(2) + 49);
            }
            if (StdDraw.hasNextKeyTyped() || bot) {
                if (!bot) {
                    c = StdDraw.nextKeyTyped();
                }
                if (c == '1') {
                    changeMeter(card.getOption1());
                    break;
                } else if (c == '2') {
                    changeMeter(card.getOption2());
                    break;
                } else {
                    continue;
                }
            }
        }
        BoardEngine.displayText("You selected option " + c + ". Your meter is now " + meter + ".");
        if (meter == 10 && !prevDepressed) {
            BoardEngine.popText("Due to your lack of social life, you have become depressed!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .25, Color.WHITE, BoardEngine.DEFAULTFONT);
            BoardEngine.popText("Reduce your Randometer within 3 turns or lose a turn!", BoardEngine.WIDTH * .5, BoardEngine.HEIGHT * .22, Color.WHITE, BoardEngine.DEFAULTFONT);
        }
    }
    /** Increase the players meter by the given offset. */
    public void changeMeter(int off) {
        this.meter += off;
        if (this.meter >= 10) {
            this.meter = 10;
            this.depressed = true;
        }
        if (this.meter <= 0) {
            this.meter = 0;
        }
        if (this.meter < 10 && this.depressed) {
            resetDepressed();
            BoardEngine.displayText("Your depression is magically cured!");
            StdDraw.pause(1000);
        }
    }
    /** Change the players location by the given offset.*/
    public void changeLocation(int off) {
        this.location += off;
    }
    /** Give the player an image if they've selected an avatar. */
    public void setImage(String filepath) {
        this.image = filepath;
    }
    /** Reset the players depression. */
    public void resetDepressed() {
        this.depressed = false;
        this.depressedTurns = 4;
    }
    /** Append to the report card, and tick down depression if necessary. */
    public int tick() {
        int result = 0;
        if (this.depressed && this.depressedTurns > 0) {
            this.depressedTurns -= 1;
            BoardEngine.displayText("Turns of depression left: " + depressedTurns);
            StdDraw.pause(750);
        }
        if (meter <= 2) {
            result = -1;
        }
        reportCard.add(meter);
        return result;
    }
}
