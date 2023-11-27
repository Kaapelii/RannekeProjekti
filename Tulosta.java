import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

// ButtonHandler-luokka käyttöliittymän tulosta-napin käsittelyyn

class ButtonHandler {
    public static void handleButtonPress(int normaali, int lasten, int opiskelija, int elakelainen, int varusmies) {
        ReceiptHandler.createReceipt(normaali, lasten, opiskelija, elakelainen, varusmies);
    }
}

// ReceiptHandler-luokka kuittitietojen luomiseen ja tallentamiseen
class ReceiptHandler {
    public static void createReceipt(int normaali, int lasten, int opiskelija, int elakelainen, int varusmies) {
        try {
            String directoryPath = "Myyntitiedot/Kuittitiedot/";
            File dir = new File(directoryPath);
            if (!dir.exists()) {
            dir.mkdirs();
            }


            String receiptFileName = directoryPath + new SimpleDateFormat("dd-MM-yyyy_HH_mm").format(new Date()) + "_Kuitti.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(receiptFileName));
            
            Map<String, Integer> products = new HashMap<>();
            products.put("Normaali", normaali);
            //products.put("Lasten", lasten);
            products.put("Opiskelija", opiskelija);
            products.put("Elakelainen", elakelainen);
            products.put("Varusmies", varusmies);

            Map<String, Integer> prices = new HashMap<>();
            prices.put("Normaali", 22);
            prices.put("Lasten", 20);
            prices.put("Opiskelija", 20);
            prices.put("Elakelainen", 20);
            prices.put("Varusmies", 20);
            
            writer.println("Kuitti");
            writer.println("HauskaPaikkaTM");
            writer.println(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
            writer.println("--------------------------------");

            int sum = 0;
            
            if (lasten > 0) {
                for (Map.Entry<String, String> entry : Kayttoliittuma.lapsilista.entrySet()) {
                writer.println("Lasten " + prices.get("Lasten") + "€ [Nimi: " + entry.getKey() + "] [Huoltaja: " + entry.getValue() + "]");
                sum += prices.get("Lasten");
                }
            }
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                if (entry.getValue() > 0) {
                    int price = prices.get(entry.getKey());
                    writer.println(entry.getKey() + " x " + entry.getValue() + " " + price + "€");
                    sum += entry.getValue() * price;
                }
            }
            writer.println("--------------------------------");
            writer.println("Yht: " + sum + "€");
            writer.println("Hinnat sis alv. 24%");

            writer.close();
            
            
            //DailySalesWriter.updateDailySales(productName); 
            //TotalSalesWriter.updateTotalSales(productName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// DailySalesWriter-luokka päivittäisen myynnin koosteen ylläpitämiseen
class DailySalesWriter {
    public static void updateDailySales(String productName) {
        // Toteuta päivittäisen myynnin päivitys tarpeen mukaan
        
    }
}

// TotalSalesWriter-luokka kokonaismyynnin ylläpitämiseen
class TotalSalesWriter {
    public static void updateTotalSales(String productName) {
        // Toteuta kokonaismyynnin päivitys tarpeen mukaan
        
    }
}



