package org.mwdl.data;

import org.mwdl.webManagement.Collection;
import org.mwdl.webManagement.Partner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Just a bunch of random helper methods.
 * Most are used in other classes in the creation and utilization of MWDL data.
 * Though some just spit out info about the data.
 * Documented on the basis of this method names.
 * Most follow the general outline of running through each row in the partner and collection data files
 * and adding each row that meats a certain condition to an ArrayList.
 *
 * @author Jonathan Wiggins
 * @version 5/9/18
 */

public class DataFetcher {

    /**
     * Gets all of the Active Collections from the Collection Data csv
     *
     * @return an ArrayList of Collections
     */
    public static ArrayList<Collection> getAllActiveCollections() {

        ArrayList<Collection> toReturn = new ArrayList<>();
        try {

            Scanner collectionData = new Scanner(new File(ProjectConstants.CollectionDataCSV));
            ArrayList<String> toParse = new ArrayList<>();

            while (collectionData.hasNextLine()) {

                Pattern pattern = Pattern.compile("([0-9]{4}),[Tt][Rr][Uu][Ee],(.*?),(.*?),(.*?),(.+),(collection[0-9]{4}.{2,4}),([0-9]*),([0-9]*),(.*)");
                Matcher matcher = pattern.matcher(collectionData.nextLine());
                if (matcher.find()) {
                    //int collectionNumber, boolean isActive, String note, String title, String publisher, String text, String imageName, int height, int width, String des
                    int collectionNumber = Integer.valueOf(matcher.group(1));
                    String note = matcher.group(2);
                    String title = matcher.group(3);
                    String pub = matcher.group(4);
                    String text = matcher.group(5);
                    String img = matcher.group(6);
                    String imgHRaw = matcher.group(7);
                    String imgWRaw = matcher.group(8);
                    String des = matcher.group(9);
                    int imgH;
                    int imgW;
                    if (imgHRaw.equalsIgnoreCase(""))
                        imgH = 0;
                    else
                        imgH = Integer.valueOf(imgHRaw);

                    if (imgWRaw.equalsIgnoreCase(""))
                        imgW = 0;
                    else
                        imgW = Integer.valueOf(imgWRaw);


                    //remove excess commas from the image des
                    // often times when the csv is opened in excel, it will be saved with about 10 extra commas
                    // on the end
                    //Keep removing the last character from the string until it no longer ends with ,
                    //No description should ever end with a , anyways
                    while(des.endsWith(",")){
                        des = des.substring(0, des.length() - 1);
                    }

                    toReturn.add(new Collection(collectionNumber, true, note, title, pub, text, img, imgH, imgW, des));

                }

            }

        } catch (IOException e) {
            System.err.println("Could not access the Collection Data csv file at DataFetcher.getAllActivePartners");
            System.exit(1);
        }
        return toReturn;
    }

    /**
     * Get all the active partners from the Partner Data csv
     *
     * @return an ArrayList of PartnerPages
     */
    public static ArrayList<Partner> getAllActivePartners() {
        ArrayList<Partner> toReturn = new ArrayList<>();

        try {
            Scanner data = new Scanner(new File(ProjectConstants.PartnerDataCSV));

            while (data.hasNextLine()) {

                Pattern pattern = Pattern.compile("([0-9]{3}),[Tt][Rr][Uu][Ee],(.*?),(.*?),(http:.*?),(.+),(partner[0-9]{3}.{2,4}),([0-9]*),([0-9]*),(.*)");
                Matcher matcher = pattern.matcher(data.nextLine());

                if(matcher.find()){
                    //Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description
                    int partnerNumber = Integer.parseInt(matcher.group(1));
                    boolean isActive = true;
                    String note = matcher.group(2);
                    String name = matcher.group(3);
                    String link = matcher.group(4);
                    String text = matcher.group(5);
                    String img = matcher.group(6);
                    String rawImgH = matcher.group(7);
                    String rawImgW = matcher.group(8);
                    String des = matcher.group(9);

                    int imgH;
                    int imgW;

                    if(rawImgH.equalsIgnoreCase(""))
                        imgH = 0;
                    else
                        imgH = Integer.valueOf(rawImgH);

                    if(rawImgW.equalsIgnoreCase(""))
                        imgW = 0;
                    else
                        imgW = Integer.valueOf(rawImgW);

                    toReturn.add(new Partner(partnerNumber, true, note, name, link, text, img, imgH, imgW, des));
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("Could not access the Partner Data csv file at DataFetcher.getAllActivePartners");
            System.exit(1);
        }


        return toReturn;
    }


    /**
     * Fetches the Collection object with the given collectionNumber
     * Requires that the Collection is active
     *
     * @param collectionNumber the number of an active collection
     * @return the Collection, null if it cannot be found
     */
    public static Collection fetchCollection(int collectionNumber) {
        for (Collection element : getAllActiveCollections()) {
            if (element.collectionNumber == collectionNumber) {
                return element;
            }
        }
        return null;
    }

    public static Partner fetchPartner(int partnerNumber){
        for(Partner element : getAllActivePartners()){
            if (element.partnerNumber == partnerNumber)
                return element;
        }
        return null;
    }

    public static Collection fetchFromTitle(String title) {
        for (Collection element : getAllActiveCollections()) {
            if (element.title.equals(title))
                return element;
        }
        return null;
    }


    /**
     * Gets all the active Collection From a specified Partner
     *
     * @param name the name of the partner, as will be given by Collection.publisher
     * @return an ArrayList of Collections
     */
    public static ArrayList<Collection> getAllActiveCollectionsFromPartner(String name) {
        ArrayList<Collection> toReturn = new ArrayList<>();

        for (Collection element : getAllActiveCollections()) {
            if (element.publisher.equals(name)) {
                toReturn.add(element);
            }
        }
        return toReturn;
    }

    public static ArrayList<Integer> getInactiveCollectionsNumbers() throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner partnerData = new Scanner(new File(ProjectConstants.CollectionDataCSV)).useDelimiter("\n");
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
                System.err.println("DataFetcher Error: NoSuchElementException at getInactiveCollectionsNumbers ");
            }

        }
        return list;
    }


    public static ArrayList<Integer> getInactivePartnerNumbers() throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner partnerData = new Scanner(new File(ProjectConstants.PartnerDataCSV)).useDelimiter("\n");
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


    public static ArrayList<String> getInactiveCollectionLines() throws FileNotFoundException {
        ArrayList<String> toReturn = new ArrayList<>();
        Scanner collections = new Scanner(new File(ProjectConstants.CollectionDataCSV));
        //skip the titles line
        collections.nextLine();

        while (collections.hasNextLine()) {

            String currentDataLine = collections.nextLine();
            Scanner n = new Scanner(currentDataLine).useDelimiter(",");
            String currentDataNumber = n.next();
            String isActive = n.next();
            if (!Boolean.valueOf(isActive)) {
                toReturn.add(currentDataLine);
            }
        }
        return toReturn;
    }

}
