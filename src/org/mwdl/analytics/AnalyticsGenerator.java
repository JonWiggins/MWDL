package org.mwdl.analytics;

import org.mwdl.data.DataFetcher;
import org.mwdl.data.ProjectConstants;
import org.mwdl.webManagement.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Note: This Analytics Generator works with only the new version of the website, wherein collection pages
 *  are stored as php files that are a version of their name
 *
 *
 * How to do Analytics for a new Month:
 * 1. Add the new Analytics Data to /MasterLists/
 *      Note that if it is taken directly from Google Analytics, you will need to remove the header and footer text
 *      If you open the document in a text editor, and open one of the existing documents, it will be very clear
 *      what text needs to be removed.
 * 2. Name it %monthName%.csv
 * 3. Add the month name to the months ArrayList
 * 4. Run this class's main method
 *
 *
 * At this point the directory given by ProjectConstants.GeneratedAnalyticsDirectory will hold a csv file for each
 *  hubID
 * Now open up VS and change the run the MWDLAnalyticsEmails program.
 * It will generated all of the emails you need to send out
 *  and save them into the PhpStorm project folder for MWDL.org
 *
 *
 * Note: If you are not me the directories will be broken and you will need to fix them for your computer
 * Note: The HashMap hubidtoName can be manipulated to get lists for specific partners
 *  One time a partner, not a hub, wanted their own stats, so you can change that partners code in HubPartnersMap.csv
 *   and add that code to hubidtoName, and rerun the program, to get a list for just that partner
 *   make sure to change it back when done.
 *
 *
 * @author Jon Wiggins
 * @version 5/7/2018
 */
public class AnalyticsGenerator {

    public static ArrayList<String> months;
    public static ArrayList<AnalyticsPartner> partners;
    public static ArrayList<Collection> activeCollections;
    public static HashMap<String, String> hubidtoName;



    public static void main(String[] args) {

        //init boi
        months = new ArrayList<>();
        partners = new ArrayList<>();
        hubidtoName = new HashMap<>();


        //FIXME add the months here
        //months.add("January");
        //months.add("February");
        //months.add("March");
        //months.add("April");
       // months.add("May");
       // months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        //months.add("October");
        //months.add("November");
        //months.add("December");


        //maps the 3 char hub ID to the full name
        hubidtoName.put("bcc","Buffalo Bill Center");
        hubidtoName.put("bsu","Boise State University");
        hubidtoName.put("byu","Brigham Young University");
        hubidtoName.put("icl","Idaho Commission for Libraries");
        hubidtoName.put("ihs","Idaho State Historical Society");
        hubidtoName.put("ore","Oregon Digital");
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
        hubidtoName.put("usl", "Utah State Library");
        hubidtoName.put("unpublished","unpublished");



        activeCollections = DataFetcher.getAllActiveCollections();

        //For each month, load the file and give it to parseFileAndAddLines
        //This will populate the partners ArrayList with all of the lines
        for(String month : months){
            try {
                Scanner currentFile = new Scanner(new File(ProjectConstants.MasterListDirectory + month + ".csv"));
                parseFileAndAddLines(currentFile, month);
            } catch (FileNotFoundException e) {
                System.err.println("Could not Read File for month" + month);
                System.exit(0);
            }

        }

        //for each hubID in hubidtoName
        //  Create new document named hubidtoName + " Analytics".csv
        //  for each month in months
        //      Write to document currentMonth + " 2018 Statistics"
        //          for each partner in partners
        //              if partner hubID is currentHub
        //                  if partner currentMonth is not empty
        //                      for each line in partner currentMonth lines list
        //                          write the line to the current document
        //      save the file
        //      print debug info?
        //fin

        try {


            for (String currentHub : hubidtoName.keySet()) {

                String FileLocAndName = ProjectConstants.GeneratedAnalyticsDirectory + hubidtoName.get(currentHub) + " Analytics.csv";
                PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");
                writer.println(hubidtoName.get(currentHub));

                for (String currentMonth : months) {
                    writer.println("Page View Statistics for " + currentMonth + " 2018");

                    for(AnalyticsPartner currentPartner : partners){

                        System.out.println(currentHub + " " + currentPartner.name);

                        if(currentPartner.hubID.equals(currentHub)){
                            ArrayList<String> currentList = currentPartner.getMonthLines(currentMonth);

                            if(currentList.size() > 0){
                                writer.println(currentPartner.name);
                                writer.println("Page,Pageviews,Unique Pageviews,Avg. Time on Page,Entrances,Bounce Rate,% Exit,Page Value");
                                for(String line : currentList){
                                    writer.println(line);
                                }
                            }
                        }
                    }

                    writer.println("");
                }
                writer.println("");
                writer.close();

                System.out.println("Finished printing for " + currentHub);

            }

        }catch(FileNotFoundException | UnsupportedEncodingException e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Given a scanner that is the start of a MasterList
     *  Parses each line in the list, and add the line to the corresponding list in the partners list
     *  If there is no partner with the same name already in the list, adds it
     * Note that this method will read to the end of the given scanner.
     *  This means that after this method has been run on a master list, each line from that list
     *  Has been placed in the appropriate AnalyticsPartner in the partners list
     */
    public static void parseFileAndAddLines(Scanner scanner, String month){

        //eat the first list, the titles
        String currentLine = scanner.nextLine();
        int count = 0;
        while(scanner.hasNextLine()){

            //get the next line and clean it
            currentLine = scanner.nextLine().replace(",$0.00", "");
            String currentPublisher = pubExtractor(currentLine).replace("%comma%",",");

            //denote if the partner has been located, if it hasn't it will need to be added
            boolean hasBeenFound = false;
            for(AnalyticsPartner p : partners){
                if(p.name.equals(currentPublisher)){
                    p.addLineForMonth(currentLine, month);
                    hasBeenFound = true;
                    break;
                }

            }
            //the partner is not already in the list of partners, so add it
            if(!hasBeenFound){
                AnalyticsPartner toAdd = new AnalyticsPartner(currentPublisher);
                toAdd.addLineForMonth(currentLine, month);
                partners.add(toAdd);
            }
            count++;
        }
        System.out.println("Completed Reading Master List for " + month +", " + count +" lines have been parsed.");
    }

    /**
     * Given a line from a MasterList, returns the name of the Publisher of that collection
     * If the given line is not a collection page, returns unpublished as the publisher
     * Otherwise,
     */
    public static String pubExtractor(String line){

        //extract the title of the page from the line
        Pattern linkPattern = Pattern.compile("mwdl\\.org\\/collections\\/(.+)\\.php");
        Matcher linkMatcher = linkPattern.matcher(line);
        String currentURLTitle = "";
        if(linkMatcher.find())
            currentURLTitle = linkMatcher.group(1);

        //search for a collection with that title
        for(Collection c : activeCollections)
            if(c.urlTitle.equals(currentURLTitle))
                return c.publisher;


        //if one could not be found, result unpublished
        return "unpublished";
    }
}
