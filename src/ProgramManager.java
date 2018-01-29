import org.mwdl.scripts.analytics.AnalyticsGen;
import org.mwdl.scripts.data.DataFetcher;
import org.mwdl.scripts.webManagement.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Please never actually use this, I started to make it, and then realised how dumb it was.
 *
 * If needed, use the method calls to note program flow/order/understand how I made all this stuff
 *
 *
 * @author Jonathan Wiggins
 * @version 7/13/17
 */

@Deprecated //Read above ^

public class ProgramManager {

    public static void main(String[] args) {

        System.exit(0); //Somebody touca ma spaght

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Options:");
            System.out.println("1. Fetch Collections - Gen collectionData.csv");
            System.out.println("2. Fetch Partners - Gen partnerData.csv");
            System.out.println("3. Fetch P and C - Gen Both of the Above");
            System.out.println("4. Generate Collections from csv");
            System.out.println("5. Generate Partners from csv");
            System.out.println("6. Generate both from csv");
            System.out.println("7. Get and Generate Collections and Partners");
            System.out.println("8. Generate Analytics");
            System.out.println("9. Debug: Fetch Collection from number");
            System.out.println("10. Debug: Fetch Partner from number");
            System.out.println("11. Debug: Fetch All Active Collections");
            System.out.println("12. Debug: Fetch All Active Partners");
            System.out.println("13. Debug: Fetch All Active Collections From Partner");
            System.out.println("Exit");

            String command = s.next();
            if (command.equals("1")) {
                CollectionMaker.main(args);
            } else if (command.equals("2")) {
                PartnerMaker.main(args);
            } else if (command.equals("3")) {
                CollectionMaker.main(args);
                PartnerMaker.main(args);
            } else if (command.equals("4")) {
                WriteCollectionFromCSV.main(args);
            } else if (command.equals("5")) {
                WritePartnerFromCSV.main(args);
            } else if (command.equals("6")) {
                WriteCollectionFromCSV.main(args);
                WritePartnerFromCSV.main(args);
            } else if (command.equals("7")) {
                CollectionMaker.main(args);
                PartnerMaker.main(args);
                WriteCollectionFromCSV.main(args);
                WritePartnerFromCSV.main(args);
            } else if (command.equals("8")) {
                AnalyticsGen.main(args);
            } else if (command.equals("9")) {
                System.out.print("Enter collection number: ");
                int collectionN = s.nextInt();
                try {
                    System.out.println(DataFetcher.fetchCollection(collectionN));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (command.equals("10")) {
                System.out.println("Enter partner number: ");
                int partnerN = s.nextInt();
                System.out.println("TODO");
            } else if (command.equals("11")) {
                try {
                    ArrayList<Collection> cs = DataFetcher.getAllActiveCollections();
                    for (Collection c : cs)
                        System.out.println(c);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (command.equals("12")) {
                System.out.println("TODO");

            } else if (command.equals("13")) {
                System.out.print("Enter partner number: ");
                int pn = s.nextInt();
                ArrayList<Collection> asdf = null;
                try {
                    asdf = DataFetcher.getAllCollectionsFromPartner(pn);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                for(Collection c : asdf)
                    System.out.println(c);

            } else if (command.equalsIgnoreCase("exit") ||
                    command.equalsIgnoreCase("e")) {
                System.out.println("cya");
                break;
            } else {
                System.out.println("Command not recognised");
            }
        }


    }


}
