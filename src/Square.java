import javax.swing.*;
import java.awt.*;

public class Square {
    private final Color backgroundColor;
    private final String description;
    private final Position position;
    public Square(Color backgroundColor, String description, Position position) {
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.position = position;
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
}
