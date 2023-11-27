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
        int sum = 0;

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
            products.put("Lasten", lasten);
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
            writer.println(" ");

           
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

        TotalSalesWriter(sum);

            
        } catch (IOException e) {
            e.printStackTrace();
            
            
        }
        
    }
   
    private static void TotalSalesWriter(int sum) {
            try {
            String directoryPath = "Myyntitiedot/Kuittitiedot/";
            File dir = new File(directoryPath);
            if (!dir.exists()) {
            dir.mkdirs();
            }
            

        String totalsalesFileName = directoryPath + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_myyntitilanne.txt";
         int totalsum=0;


        totalsum=totalsum+sum;// ei täl hetkel toimi koska se ei koska totalsum ei pysy tallennettuna mihinkään ja joten se aina overridataan

        PrintWriter writer2 = new PrintWriter(new FileWriter(totalsalesFileName, false));
            writer2.println("Kokonaismyyntitilanne");
            writer2.println(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
            writer2.println("--------------------------------");
            writer2.println("Yht: " + totalsum + "€");
        writer2.close();
        }catch (IOException e) {
            e.printStackTrace();
            
        }
    }


}

