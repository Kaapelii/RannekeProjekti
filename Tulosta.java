import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;


// ButtonHandler-luokka käyttöliittymän tulosta-napin käsittelyyn

class ButtonHandler {
    public static void handleButtonPress(int normaali, int lasten, int opiskelija, int elakelainen, int varusmies) {
        ReceiptHandler.createReceipt(normaali, lasten, opiskelija, elakelainen, varusmies);
    }
}

// ReceiptHandler-luokka kuittitietojen luomiseen ja tallentamiseen
class ReceiptHandler {
    
    public static Map<String, Integer> products = new HashMap<>();
    public static Map<String, Integer> prices = new HashMap<>();

    public static int tapahtumasumma;
    
    public static void createReceipt(int normaali, int lasten, int opiskelija, int elakelainen, int varusmies) {
        try {
            String directoryPath = "Myyntitiedot/Kuittitiedot/";
            File dir = new File(directoryPath);
            if (!dir.exists()) {
            dir.mkdirs();
            }


            String receiptFileName = directoryPath + new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss").format(new Date()) + "_Kuitti.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(receiptFileName));
            
            
            products.put("Normaali", normaali);
            products.put("Lasten", lasten);
            products.put("Opiskelija", opiskelija);
            products.put("Elakelainen", elakelainen);
            products.put("Varusmies", varusmies);

            
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
            if (entry.getValue() > 0 && !entry.getKey().equals("Lasten")) {
                int price = prices.get(entry.getKey());
                writer.println(entry.getKey() + " x " + entry.getValue() + " " + price + "€");
                sum += entry.getValue() * price;
            }
            tapahtumasumma = sum;
        }
            writer.println("--------------------------------");
            writer.println("Yht: " + sum + "€");
            writer.println("Hinnat sis alv. 24%");

            writer.close();
            
            DailySalesWriter.updateDailySales(normaali, lasten, opiskelija, elakelainen, varusmies);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// DailySalesWriter-luokka päivittäisen myynnin koosteen ylläpitämiseen
class DailySalesWriter {

    // Päivittäisen myynnin seurantaan tarvittavat muuttujat
    private static int paivasumma; // Päivän myyntisumma
    private static Date lastTransactionDate; // Viimeisimmän tapahtuman päivämäärä
    private static SimpleDateFormat thisday = new SimpleDateFormat("dd_MM_yyyy"); // Päivämäärän muotoilu

    // Päivittää päivittäistä myyntiä ja luo päiväkoostetiedoston
    public static void updateDailySales(int normaali, int lasten, int opiskelija, int elakelainen, int varusmies) {
        
        // Nollataan päivän myyntisumma, jos päivä on vaihtunut tai jos edellistä tapahtumaa ei ole
        Date currentDate = new Date();
        if (lastTransactionDate == null || !thisday.format(lastTransactionDate).equals(thisday.format(currentDate))) {
            paivasumma = 0;
            lastTransactionDate = currentDate;
        }
        
        try { // Luodaan uusi tiedosto, jos ei ole olemassa
            String directoryPath = "Myyntitiedot/Paivakooste/";
            File dir = new File(directoryPath);
            if (!dir.exists()) {
                dir.mkdirs(); // Luo hakemisto, jos sitä ei ole olemassa
            }
            // Luo uusi tiedosto, jos ei ole olemassa
            String fileName = directoryPath + new SimpleDateFormat("dd_MM_yyyy").format(new Date()) + "_Paivakooste.txt";
            File file = new File(fileName); 
            boolean isNewFile = file.createNewFile(); // Palauttaa true, jos tiedosto on juuri luotu


            PrintWriter writer = new PrintWriter(new FileWriter(fileName, true)); // Tiedoston kirjoittaminen
            
            // Kirjoita olemassa oleva sisältö tai luo uusi otsikko
             if (isNewFile) {
                writer.println("Päivän myyntikooste " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
                writer.println("Päivän tuotto: " + paivasumma + "€");
            }

            // Laske päivän myyntisumma
            paivasumma += ReceiptHandler.tapahtumasumma;
            
            writer.println("-------------------------------------------");

            String currentTime = null; 

            // Lisää kuitin sisältö tiedostoon
            for (Map.Entry<String, Integer> entry : ReceiptHandler.products.entrySet()) {
                if (entry.getValue() > 0) {
                    int price = ReceiptHandler.prices.get(entry.getKey());
                    String time = new SimpleDateFormat("HH.mm.ss").format(new Date());

                    // Tulosta aika ja päivän myyntisumma, jos aika on muuttunut
                    if (!time.equals(currentTime)) {
                        writer.println(time + "  Yht. " + ReceiptHandler.tapahtumasumma + "€");
                        currentTime = time;
                    }

                    // Lisää tuote ja määrä tiedostoon
                    writer.println(
                            "  " + entry.getValue() * price + "€ - " +
                                    entry.getKey() + " x " + entry.getValue());

                }
            }
            writer.close();
            updateDailySum(); // Päivitä päivittäinen summa

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateDailySum() {
        try {
            String directoryPath = "Myyntitiedot/Paivakooste/";
            String fileName = directoryPath + new SimpleDateFormat("dd_MM_yyyy").format(new Date()) + "_Paivakooste.txt";
            File file = new File(fileName);

            // Lue tiedoston sisälstö
            StringBuilder fileContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber <= 2) continue; // skippaa "Header"
                fileContent.append(line).append("\n");
            }
            reader.close();

            // Write updated content back to the file
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, false)); // Set the second parameter to false to overwrite the file
            writer.println("Päivän myyntikooste " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            writer.println("Päivän tuotto: " + paivasumma + "€");
            writer.print(fileContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

