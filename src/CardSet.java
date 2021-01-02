import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CardSet {
    private static final ArrayList<GreenCard> GREENCARDS = new ArrayList<>();
    private static final HashMap<Integer, ArrayList<RedCard>> REDCARDS = new HashMap<>();
    static {
        REDCARDS.put(0, new ArrayList<>()); //freshman year
        REDCARDS.put(1, new ArrayList<>());
        REDCARDS.put(2, new ArrayList<>());
        REDCARDS.put(3, new ArrayList<>()); //senior year
        try (Scanner scanner = new Scanner(new File("cards.csv"))) {
            while (scanner.hasNextLine()) {
                getCardFromLine(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static RedCard drawRedCard(int year) {
        assert (year < 4 & year >= 0): "must be a valid year";
        int index = BoardEngine.RANDOM.nextInt(REDCARDS.get(year).size());
        return REDCARDS.get(year).get(index);
    }
    public static GreenCard drawGreenCard() {
        int index = BoardEngine.RANDOM.nextInt(GREENCARDS.size());
        return GREENCARDS.get(index);
    }
    public static void getCardFromLine(String line) {
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                String str = rowScanner.next();
                if (str.equals("g")) {
                    scanGreen(rowScanner);
                } else {
                    scanRed(rowScanner);
                }
            }
        }
    }
    public static void scanGreen(Scanner scanner) {
        //System.out.println("scanning green...");
        String desc = scanner.next();
        int option1 = scanner.nextInt();
        int option2 = scanner.nextInt();
        GreenCard card = new GreenCard(desc, option1, option2);
        GREENCARDS.add(card);
        //System.out.println(card.getDescription());
    }
    public static void scanRed(Scanner scanner) {
        //System.out.println("scanning red...");
        RedCard card = new RedCard(scanner.next(), scanner.nextInt(), scanner.nextBoolean());
        REDCARDS.get(scanner.nextInt()).add(card);
        //System.out.println(card.getDescription());
    }
    public static void main(String[] args) {
        RedCard card = drawRedCard(1);
        System.out.println(card.getDescription() + " " + card.getAbove() + " " + card.getThreshold());
        GreenCard gcard = drawGreenCard();
        System.out.println(gcard.getDescription() + " " + gcard.getOption1() + " " + gcard.getOption2());
    }
}
