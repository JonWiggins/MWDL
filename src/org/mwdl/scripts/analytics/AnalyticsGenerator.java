package org.mwdl.scripts.analytics;

import org.mwdl.scripts.data.DataFetcher;
import org.mwdl.scripts.webManagement.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO - Fill in class desc
 *
 * @author Jonathan Wiggins
 * @version 4/18/18
 */
public class AnalyticsGenerator {

    public static HashSet<String> months;
    public static ArrayList<AnalyticsPartner> partners;
    public static ArrayList<Collection> activeCollections;
    public static HashMap<String, String> hubidtoName;

    public static void main(String[] args) {

        //init boi
        months = new HashSet<>();
        partners = new ArrayList<>();

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

        try {
            activeCollections = DataFetcher.getAllActiveCollections();
        } catch (FileNotFoundException e) {
            System.err.println("Could not get the active collections: Just gonna exit");
            System.exit(0);
        }


        //add months
        //FIXME add the months here
        months.add("January");


        //TODO for each month, load the file for that month, give it to parseFileAndAddLines()

        //TODO

    }

    /**
     * Given a scanner that is the start of a MasterList
     *  Parses each line in the list,
     * @param scanner
     */
    public static void parseFileAndAddLines(Scanner scanner, String month){

        //eat the first list, the titles
        String currentLine = scanner.nextLine();

        while(scanner.hasNextLine()){

            //get the next line and clean it
            currentLine = scanner.nextLine().replace(",$0.00", "");
            String currentPublisher = pubExtractor(currentLine);

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
            }
        }
    }

    /**
     * Given a line from a MasterList, returns the name of the Publisher of that collection
     * If the given line is not a collection page, returns MWDL as the publisher
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


        //if one could not be found, result MWDL
        return "MWDL";
    }
}
