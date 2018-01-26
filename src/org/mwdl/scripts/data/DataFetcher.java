package org.mwdl.scripts.data;

import org.mwdl.scripts.webManagement.Collection;
import org.mwdl.scripts.webManagement.PartnerPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Jonathan Wiggins
 * @version 6/28/17
 */

public class DataFetcher {

    public static Collection fetchCollection(int collectionNumber) throws FileNotFoundException {
        Scanner collectionData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (collectionData.hasNext()) {
            String currentDataLine = collectionData.next();
            Scanner n = new Scanner(currentDataLine).useDelimiter(",");
            String currentDataNumber = n.next();
            if (currentDataNumber.contains(String.valueOf(collectionNumber))) {
                return new Collection(collectionNumber, Boolean.valueOf(n.next()), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next());
            }
        }
        return null;
    }

    public static PartnerPage fetchPartner(int partnerNumber) throws FileNotFoundException {
        Scanner partnerData = new Scanner(new File("partnerData.csv")).useDelimiter("\n");
        while (partnerData.hasNext()) {
            String currentDataLine = partnerData.next();
            Scanner n = new Scanner(currentDataLine).useDelimiter(",");
            String currentDataNumber = n.next();
            if (currentDataNumber.contains(String.valueOf(partnerNumber))) {
                return new PartnerPage(partnerNumber, Boolean.valueOf(n.next()), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next());
            }
        }
        return null;
    }

    public static Collection fetchFromTitle(String title) throws FileNotFoundException {
        Scanner collectionData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (collectionData.hasNext()) {
            try {
                String currentDataLine = collectionData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                String note = n.next();
                String ctitle = n.next();
                String urlTitle = n.next();
                if (title.equalsIgnoreCase(ctitle) || title.equalsIgnoreCase(urlTitle)) {
                    return new Collection(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), note, ctitle, urlTitle, n.next(), n.next(), n.next(), n.next(), n.next(), n.next());
                }
            } catch (NoSuchElementException e) {
                System.err.print("Error: Check DataFetcher");
            }
        }
        return null;
    }

    public static ArrayList<Collection> getAllCollectionsFromPartner(int partner) throws FileNotFoundException {
        ArrayList<Collection> list = new ArrayList<>();
        Scanner collectionData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (collectionData.hasNext()) {
            try {
                String currentDataLine = collectionData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                String note = n.next();
                String ctitle = n.next();
                String urlTitle = n.next();
                String publisher = n.next();

                if (publisher.contains(String.valueOf(partner))) {
                    list.add(new Collection(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), note, ctitle, urlTitle, publisher, n.next(), n.next(), n.next(), n.next(), n.next()));
                }
            } catch (NoSuchElementException e) {
                System.err.print("Error: Check DataFetcher");
            }
        }
        return list;
    }

    public static ArrayList<Collection> getAllActiveCollectionsFromPartner(int partner) throws FileNotFoundException {
        ArrayList<Collection> list = new ArrayList<>();
        Scanner collectionData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (collectionData.hasNext()) {
            try {
                String currentDataLine = collectionData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                String note = n.next();
                String cTitle = n.next();
                String urlTitle = n.next();
                String publisher = n.next();

                if (Boolean.valueOf(isActive) && publisher.contains(String.valueOf(partner))) {
                    list.add(new Collection(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), note, cTitle, urlTitle, publisher, n.next(), n.next(), n.next(), n.next(), n.next()));
                }
            } catch (NoSuchElementException e) {
                System.err.print("Error: Check DataFetcher");
            }
        }
        return list;
    }

    public static ArrayList<Collection> getAllActiveCollections() throws FileNotFoundException {
        ArrayList<Collection> list = new ArrayList<>();
        Scanner collectionData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (collectionData.hasNext()) {
            try {
                String currentDataLine = collectionData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                if (Boolean.valueOf(isActive)) {
                    list.add(new Collection(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next()));
                }
            } catch (NoSuchElementException e) {
                System.err.print("Error: Check DataFetcher");
            }
        }
        return list;
    }


    public static ArrayList<PartnerPage> getAllActivePartners() throws FileNotFoundException {
        ArrayList<PartnerPage> list = new ArrayList<>();
        Scanner partnerData = new Scanner(new File("partnerData.csv")).useDelimiter("\n");
        while (partnerData.hasNext()) {
            try {
                String currentDataLine = partnerData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                if (Boolean.valueOf(isActive)) {
                    list.add(new PartnerPage(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next(), n.next()));
                }
            } catch (NoSuchElementException e) {
                System.err.print("Error: Check DataFetcher");
            }
        }
        return list;
    }

    public static ArrayList<Integer> getInactiveCollectionsNumbers() throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner partnerData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (partnerData.hasNext()) {
            try {
                String currentDataLine = partnerData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                if (!Boolean.valueOf(isActive)) {
                    list.add(Integer.valueOf(currentDataNumber));
                }
            } catch (NoSuchElementException | NumberFormatException e) {
                System.err.print("Error: Check DataFetcher");
            }

        }
        return list;
    }

    public static ArrayList<Integer> getInactivePartnerNumbers() throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner partnerData = new Scanner(new File("partnerData.csv")).useDelimiter("\n");
        while (partnerData.hasNext()) {
            try {
                String currentDataLine = partnerData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                if (!Boolean.valueOf(isActive)) {
                    list.add(Integer.valueOf(currentDataNumber));
                }
            } catch (NoSuchElementException | NumberFormatException e) {
                System.err.print("Error: Check DataFetcher");
            }

        }
        return list;
    }


    public static ArrayList<Collection> getAMPImgsWithOutHeight() throws FileNotFoundException {
        ArrayList<Collection> list = new ArrayList<>();
        Scanner collectionData = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        while (collectionData.hasNext()) {
            try {
                String currentDataLine = collectionData.next();
                Scanner n = new Scanner(currentDataLine).useDelimiter(",");
                String currentDataNumber = n.next();
                String isActive = n.next();
                if (Boolean.valueOf(isActive)) {
                    String note = n.next();
                    String title = n.next();
                    String urlTitle = n.next();
                    String pub = n.next();
                    String text = n.next();
                    String img = n.next();
                    String ampImage = n.next();
                    if (Boolean.valueOf(isActive)) {
                        if (!ampImage.contains("height") || !ampImage.contains("width")) {
                            list.add(new Collection(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), note, title, urlTitle, pub, text, img, ampImage, n.next(), n.next()));
                        }
                    }
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<String> getInactiveCollectionLines() throws FileNotFoundException {
        ArrayList<String> toReturn = new ArrayList<>();
        Scanner collections = new Scanner(new File("collectionData.csv")).useDelimiter("\n");
        collections.next();
        while (collections.hasNext()) {

            String currentDataLine = collections.next();
            Scanner n = new Scanner(currentDataLine).useDelimiter(",");
            String currentDataNumber = n.next();
            String isActive = n.next();
            if (!Boolean.valueOf(isActive)) {
                toReturn.add(currentDataLine);
            }
        }
        return toReturn;

    }


    public static ArrayList<Collection> createCollectionsFromLines(ArrayList<String> dataLines){
        ArrayList<Collection> toReturn = new ArrayList<>();
        for(String s : dataLines){
            try {
                Scanner line = new Scanner(s).useDelimiter(",");
                String currentDataNumber = line.next();
                String isActive = line.next();
                toReturn.add(new Collection(Integer.valueOf(currentDataNumber), Boolean.valueOf(isActive), line.next(), line.next(), line.next(), line.next(), line.next(), line.next(), line.next(), line.next(), line.next()));
            }catch (NoSuchElementException e){
                continue; // for cleen
            }
        }


        return toReturn;
    }


}
