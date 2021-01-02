public class RedCard  {
    private String description;
    private int threshold;
    private boolean above;
    public RedCard(String description, int threshold, boolean above) {
        this.above = above;
        this.description = description;
        this.threshold = threshold;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAbove() {
        return above;
    }


    public int getThreshold() {
        return threshold;
    }


    public int getOption1() {
        return 0;
    }

    public int getOption2() {
        return 0;
    }
}
