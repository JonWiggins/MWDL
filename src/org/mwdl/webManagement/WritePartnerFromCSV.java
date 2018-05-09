package org.mwdl.webManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Run this to create all the Partner landing pages from the newPartnerData.csv in this dir
 *
 * It will write all of the Partner landing pages into partners/ and amppartners/
 *
 * @author Jonathan Wiggins
 * @version 5/9/18
 */

public class WritePartnerFromCSV {


    private static ArrayList<PartnerPage> partnerArrayList;


    public static void main(String[] args) {
        importData();

        PartnerPageMaker.writeGivenPartnerPages(partnerArrayList);
    }

    public static void importData() {
        Scanner s = null;
        partnerArrayList = new ArrayList<>();
        try {
            s = new Scanner(new File("newPartnerData.csv"));
            //skip the first line
            s.nextLine();

            while (s.hasNextLine()) {
                Scanner currentLine = new Scanner(s.nextLine()).useDelimiter(",");
                String partnerNumber = getNextElement(currentLine);
                boolean isActive = Boolean.valueOf(getNextElement(currentLine));
                if (isActive) {
                    //Note that the partner data is stored in the format
                    //Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description
                    String note = currentLine.next();
                    if (note == null) note = " ";
                    String name = getNextElement(currentLine);
                    String link = getNextElement(currentLine);
                    String text = getNextElement(currentLine);
                    String img = getNextElement(currentLine);
                    String imgH = getNextElement(currentLine);
                    String imgW = getNextElement(currentLine);
                    String des = "";
                    if (currentLine.hasNext())
                        des = getNextElement(currentLine);

                    partnerArrayList.add(new PartnerPage(Integer.valueOf(partnerNumber), isActive, note, name, link, text, img, imgH, imgW, des));
                    System.out.println("Loaded " + partnerNumber);
                }
                System.out.println(partnerNumber);
            }
            System.out.println("Successfully loaded " + partnerArrayList.size() + " partners from the csv file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String getNextElement(Scanner toscan) {
        return toscan.next().replace("%newline%", "\n").replace("%return%", "\r").replace("%comma%", ",");
    }
}