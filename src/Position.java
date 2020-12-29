
public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Creates a position from a reference position. */
    public Position(Position ref, int offX, int offY) {
        this.x = ref.getX() + offX;
        this.y = ref.getY() + offY;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (this == o) {
            return true;
        } else if (o.getClass() != this.getClass()) {
            return false;
        } else {
            Position other = (Position) o;
            return this.getX() == other.getX() && this.getY() == other.getY();
        }
    }
    @Override
    public int hashCode() {
        return (int) (31 * this.x + this.y);
    }
}
