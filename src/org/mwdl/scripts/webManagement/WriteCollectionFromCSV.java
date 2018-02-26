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
        Scanner fullFile = null;
        collectionArrayList = new ArrayList<>();
        try {
            fullFile = new Scanner(new File("newCollectionData.csv"));

            //skip the first line
            fullFile.nextLine();

            while (fullFile.hasNextLine()) {

                Scanner currentLine = new Scanner(fullFile.nextLine().replace("%newline%","\n").replace("%return%","\r")).useDelimiter(",");

                //Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Width, Image Description
                String count = currentLine.next();
                boolean isActive = Boolean.valueOf(currentLine.next());
                if (isActive) {
                    String note = currentLine.next().replace("%comma%",",");
                    if(note == null) note = " ";
                    String title = currentLine.next().replace("%comma%",",");
                    String pub = currentLine.next().replace("%comma%",",");
                    String text = currentLine.next().replace("%comma%",",");
                    String img = currentLine.next().replace("%comma%",",");
                    String imgH = currentLine.next().replace("%comma%",",");
                    String imgW = currentLine.next().replace("%comma%",",");
                    String des = currentLine.next().replace("%comma%",",");
                    if(imgH.equals(""))
                        imgH = "250";
                    if(imgW.equals(""))
                        imgW = "250";
                    Collection toAdd = new Collection(Integer.parseInt(count), isActive, note, title, pub, text, img, Integer.parseInt(imgH), Integer.parseInt(imgW), des);
                    collectionArrayList.add(toAdd);
                    System.out.println("Successfully Read Collection: " + toAdd.title);
                }
                else
                    System.out.println("Skipping collection " + count +" as it is inactive");

            }
            System.out.println("Successfully loaded " + collectionArrayList.size() +" collections from the csv file.");
        } catch (FileNotFoundException e) {
            System.err.println("Error Reading Collections from file: " + e.getMessage());
        }
    }

    public static void exportData() {

        for (Collection a : collectionArrayList) {
            System.out.println("Writing Collection: " + a.title);
            CollectionPageMaker.writeAMPCollection(a);
            CollectionPageMaker.writeFullCollection(a);
        }

        CollectionPageMaker.writeCollectionsPage(collectionArrayList);
        System.out.println("Finished");
    }




}
