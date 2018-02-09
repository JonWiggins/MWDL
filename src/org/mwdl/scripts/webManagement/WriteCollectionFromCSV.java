package org.mwdl.scripts.webManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Run this class to generate all of the Collection Landing Pages from the collectionData.csv file in this dir
 *
 * @author Jonathan Wiggins
 * @version 6/20/17
 */

public class WriteCollectionFromCSV {

    private static ArrayList<Collection> collectionArrayList;

    public static void main(String[] args) {
        importData();
        exportData();
    }

    public static void importData() {
        Scanner s = null;
        collectionArrayList = new ArrayList<>();
        try {
            s = new Scanner(new File("collectionData.csv")).useDelimiter(",");
            //skip the first line


            //.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%")
            //.replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");
            s.nextLine();

            while (s.hasNextLine()) {
                int count = s.nextInt();
                boolean isActive = Boolean.valueOf(s.next());
                if (isActive) {
                    String note = s.next();
                    if(note == null) note = " ";
                    String title = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String urlTitle = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String pub = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String text = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String img = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String ampImg = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String des = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String browse = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");

                    collectionArrayList.add(new Collection(count, isActive, note, title, urlTitle, pub, text, img, ampImg, des, browse));
                }
                s.nextLine();

            }
            System.out.println("Successfully loaded " + collectionArrayList.size() +" collections from the csv file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportData() {
        for (Collection a : collectionArrayList) {
            CollectionPageMaker.writeAMPCollection(a);
            CollectionPageMaker.writeFullCollection(a);
        }
        CollectionPageMaker.writeCollectionsPage();
        System.out.println("Finished");
    }




}
