public class Player {
    private String name;
    private int location;
    private int meter;
    private boolean depressed;
    private String image;
    public Player(String name) {
        this.name = name;
        this.location = 0;
        this.meter = 5;
        this.depressed = false;
        this.image = "";
    }
    public int getLocation() {
        return location;
    }
    public int getMeter() {
        return meter;
    }
    public String getGrade() {
        switch (meter) {
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
    public void changeMeter(int off) {
        this.meter += off;
        if (this.meter >= 10) {
            this.meter = 10;
            this.depressed = true;
        }
        if (this.meter <= 0) {
            this.meter = 0;
        }
    }
    public void changeLocation(int off) {
        this.location += off;
    }
    public void setImage(String filepath) {
        this.image = filepath;
    }
}
