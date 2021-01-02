public class GreenCard{
    private String description;
    private int option1;
    private int option2;
    public GreenCard(String description, int option1, int option2) {
        this.option1 = option1;
        this.description = description;
        this.option2 = option2;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAbove() {
        return false;
    }


    public int getThreshold() {
        return 0;
    }

    public int getOption1() {
        return option1;
    }

    public int getOption2() {
        return option2;
    }
}
