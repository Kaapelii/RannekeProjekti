import java.util.HashMap;
import java.util.Map;

public class Hinnasto {
    public static Map<String, Integer> prices = new HashMap<>();

    static {
        prices.put("Normaali", 22);
        prices.put("Lasten", 16);
        prices.put("Opiskelija", 20);
        prices.put("Elakelainen", 20);
        prices.put("Varusmies", 20);
    }
}