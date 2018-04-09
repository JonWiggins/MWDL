package org.mwdl.scripts.analytics;

import org.mwdl.scripts.data.DataFetcher;
import org.mwdl.scripts.webManagement.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * How to do Analytics for a new Month:
 * 1. Add the new Analytics Data to /MasterLists/
 * 2. Name it %monthName%.csv
 * 3. Add the month name to the months ArrayList
 * 4. Add a new ArrayList and needed methods to the AnalyticsPartner class
 * 5. Add the month options to the control statements in this class denoted with //FIXME
 * 6. Run this class's main method
 *
 *
 * At this point there should be a folder in the cwd titled GeneratedAnalytics which holds csv files for each hub
 * Now open up VS and run the MWDLAnalyticsEmails program. It will generated all of the emails you need to send out
 *  and save them into the PhpStorm project folder for MWDL.org
 * Note: If you are not me the directories will be broken and you will need to fix them for your computer
 *
 *
 * @author Jon Wiggins
 * @version 2/7/18
 */

public class AnalyticsGen {

    public static int count;

    public static HashMap<String, String> hubidtoName = new HashMap<String, String>();

    public static void main(String[] args) {
        count = 0;

        ArrayList<String> months = new ArrayList<>();

        //FIXME add new month here
        months.add("January");
        months.add("February");
        //months.add("March");
        //months.add("April");
        //months.add("May");
        //months.add("June");
        //months.add("July");
        //months.add("August");
        //months.add("September");
        //months.add("October");
        //months.add("November");
        //months.add("December");


        hubidtoName.put("bcc","Buffalo Bill Center");
        hubidtoName.put("bsu","Boise State University");
        hubidtoName.put("byu","Brigham Young University");
        hubidtoName.put("icl","Idaho Commission for Libraries");
        hubidtoName.put("ihs","Idaho State Historical Society");
        hubidtoName.put("ore","University of Oregon Libraries");
        hubidtoName.put("slc","Salt Lake Community College Libraries");
        hubidtoName.put("suu","Southern Utah University");
        hubidtoName.put("uam","Utah Division of Arts and Museums");
        hubidtoName.put("uid","University of Idaho Library");
        hubidtoName.put("unl","University of Nevada - Las Vegas");
        hubidtoName.put("unr","University of Nevada - Reno");
        hubidtoName.put("usa","Utah State Archives");
        hubidtoName.put("usu","Utah State University");
        hubidtoName.put("uuu","University of Utah");
        hubidtoName.put("uvu","Utah Valley University");
        hubidtoName.put("wsu","Weber State University");
        hubidtoName.put("dha","Department of Heritage and Arts");
        hubidtoName.put("unpublished","unpublished");

        //temp for Uinta
        //hubidtoName.put("uin","Uintah County Library");

        ArrayList<AnalyticsPartner> partners = getPartnersForMonths(months);

        try {
            exportData(partners, months);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("file not found except");
        }
    }

    public static ArrayList<AnalyticsPartner> getPartnersForMonths(ArrayList<String> months){
        try {
            ArrayList<AnalyticsPartner> partners = new ArrayList<>();
            for(String month : months) {
                Scanner analytics = new Scanner(new File("MasterLists/" + month + ".csv"));

                //first, extract the title line, which we don't need
                String nextAnaLine = analytics.nextLine();

                while (analytics.hasNextLine()) {
                    String publisher = "";
                    String collectionName = "";
                    String currentLine = analytics.nextLine().replace(",$0.00", "");
                    try {
                        int currentCollectionNumber = analyticsNumberExtractor(currentLine);
                        if (currentCollectionNumber != 0) {
                            //you now have the collection number, now search through the collection data to find its publisher

                            try {
                                Collection current = DataFetcher.fetchCollection(currentCollectionNumber);
                                if(current != null) {
                                    publisher = current.publisher;
                                    collectionName = current.title.replace("%comma%", " ");
                                }else{
                                    publisher = "Null Publisher";
                                }
                            } catch (NoSuchElementException e) {
                                //System.err.println("Could not fetch data for collection" + currentCollectionNumber);
                            }
                        }
                        currentLine = collectionName.concat(",").concat(currentLine);
                        //see if the partner exists in the list, add it if not
                        boolean isAlreadyListed = false;
                        for (AnalyticsPartner p : partners) {
                            if (p.name.equalsIgnoreCase(publisher)) {
                                isAlreadyListed = true;

                                //FIXME update months here
                                if(month.equalsIgnoreCase("January")) p.JanCollections.add(currentLine);
                                else if(month.equalsIgnoreCase("February")) p.FebCollections.add(currentLine);
                                //else if (month.equalsIgnoreCase("March")) p.MarchCollections.add(currentLine);
                                else {
                                    System.err.println("Error: Month not found. It was: " + month);
                                    System.exit(0);
                                }

                            }
                        }

                        if (!isAlreadyListed) {
                            AnalyticsPartner p = new AnalyticsPartner(publisher);

                            //FIXME update months here
                            System.out.println(currentLine);
                            if (month.equalsIgnoreCase("January")) p.JanCollections.add(currentLine);
                            else if(month.equalsIgnoreCase("February")) p.FebCollections.add(currentLine);

                            else {
                                System.err.println("Error: Month not found. It was: " + month);
                                System.exit(0);
                            }
                            partners.add(p);
                        }

                    } catch (NumberFormatException e) {
                        //this is not a collection page, so skip it
                        //System.err.println("Collection " + currentLine + " is not a collection.");
                    }
                }
            }
            return  partners;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static int analyticsNumberExtractor(String line) throws NumberFormatException {
        Scanner r = new Scanner(line).useDelimiter(",");
        return Integer.valueOf(r.next()
                .replace("www.", "")
                .replace("mwdl.org", "")
                .replace("/collections/", "")
                .replace(".php", ""));
    }

    public static String publisherExtractor(String line) {
        Scanner pubextract = new Scanner(line).useDelimiter(",");
        String number = pubextract.next();
        String isActive = pubextract.next();
        String note = pubextract.next();
        String title = pubextract.next();
        String urlTitle = pubextract.next();
        String rawPublisher = pubextract.next();

        //refine the publisher
        String refinedPublisher = rawPublisher
                .replace("Published by", "")
                .replaceAll("<.*?>", "")
                .replace("!", "")
                .replace(" ", "")
                .replace(":", "")
                .replace(".", "")
                .replace(",", "")
                .replace("(", "")
                .replace(")", "")
                .replace("'", "")
                .replace("\"", "")
                .replace("?", "")
                .replace("-", "")
                .replace("/", "");

        return refinedPublisher;
    }

    public static void exportData(ArrayList<AnalyticsPartner> partners, ArrayList<String> months) throws FileNotFoundException, UnsupportedEncodingException {
        //create a list of all the hubs
        System.out.println("Beginning Data export");
        ArrayList<String> hubs = new ArrayList<>();
        for(AnalyticsPartner p : partners){
            if(!hubs.contains(p.hub)) hubs.add(p.hub);
        }

        for (String hub : hubs) {
            String hubName = hub.replace("%comma%", "").replace("&amp;", "");
            if (hubName.equalsIgnoreCase("")) hubName = "unpublished";
            System.out.println("==== Now exporting hub: " + hub+ "====");
            String FileLocAndName = "GeneratedAnalytics/" + hubidtoName.get(hubName)+ " Analytics.csv";
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");
            for (String month : months) {
                System.out.println("Month:" + month);
                writer.println("Stats for " + month + " 2018");
                count++;
                for(AnalyticsPartner p : partners) {
                    if(p.hub != null && p.hub.equals(hub)) {
                        System.out.print("- " + p.name + " ");
                        writer.println(p.name);
                        writer.println("Collection Title,Page,Pageviews,Unique Pageviews,Avg. Time on Page,Entrances,Bounce Rate,% Exit");
                        ArrayList<String> collections = new ArrayList<>();

                        //FIXME update months here
                        if (month.equalsIgnoreCase("January")) collections = p.JanCollections;
                        else if (month.equalsIgnoreCase("February")) collections = p.FebCollections;

                        else {
                            System.err.println("Error: Month not found. It was: " + month);
                            System.exit(0);
                        }
                        System.out.println(" size: " + collections.size());
                        for(String c : collections) writer.println(c);
                    }
                }

            }
            writer.close();
        }
    }
}