import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.util.ArrayList;

public class Square {
    private final Color backgroundColor;
    private final String description;
    private final Position position;
    private ArrayList<Player> players;
    public Square(Color backgroundColor, String description, Position position) {
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.position = position;
        this.players = new ArrayList<>();
    }
    public void draw() {
        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledCircle(position.getX(), position.getY(), 2);
        int x = -1;
        int y = 1;
        for (Player player: players) {
            StdDraw.picture(position.getX() + x * 2, position.getY() + y * 2, player.getImage());
            x = x * -1;
            y = x * y;
        }
    }
    /** Returns a card corresponding to the proper square.
     * Might also apply some biases depending on the grade.
    public Card drawCard() {
        if (backgroundColor.equals(Color.RED)) {
            return CardSet.drawRedCard(0);
        } else {
            return CardSet.drawGreenCard();
        }
    } */
    public Position getPosition() {
        return position;
    }
    public String getDescription() {
        return description;
    }
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
