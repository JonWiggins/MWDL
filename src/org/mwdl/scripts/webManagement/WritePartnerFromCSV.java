package org.mwdl.scripts.webManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Run this to create all the Partner landing pages from the partnerData.csv in this dir
 *
 * @author Jonathan Wiggins
 * @version 7/10/17
 */

public class WritePartnerFromCSV {


    private static ArrayList<PartnerPage> partnerArrayList;


    public static void main(String[] args) {
    importData();
    exportData();
    }

    public static void importData(){
        Scanner s = null;
        partnerArrayList = new ArrayList<>();
        try {
            s = new Scanner(new File("partnerData.csv")).useDelimiter(",");
            //skip the first line


            //.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%")
            //.replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");
            s.nextLine();

            int lineNumber = 0;
            while (s.hasNextLine()) {
                int count = s.nextInt();
                boolean isActive = Boolean.valueOf(s.next());
                if (isActive) {
                    String note = s.next();
                    if(note == null) note = " ";
                    String title = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String urlTitle = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String link = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String text = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String img = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String ampImg = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String des = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;
                    String browse = s.next().replace("%newline%","\n").replace("%return%","/r").replace("%comma%",",");;

                    partnerArrayList.add(new PartnerPage(count, isActive, note, title, urlTitle, link, text, img, ampImg, des, browse));
                }
                System.out.println(lineNumber);
                s.nextLine();
                lineNumber++;

            }
            System.out.println("Successfully loaded " + partnerArrayList.size() +" partners from the csv file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void exportData(){
        for(PartnerPage p : partnerArrayList) {
            PartnerPageMaker.writeFullPartner(p);
            PartnerPageMaker.writeAMPPartner(p);
        }
        PartnerPageMaker.writePartnersPage();
        System.out.println("Finished");

    }

}
