public class RedCard {
    private String description;
    private int threshold;
    private boolean above;
    public RedCard(String description, int threshold, boolean above) {
        this.above = above;
        this.description = description;
        this.threshold = threshold;
    }
}
