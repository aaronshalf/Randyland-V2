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
    }
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
