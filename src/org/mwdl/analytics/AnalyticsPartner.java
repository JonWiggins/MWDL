package org.mwdl.analytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hold the data lines for the analytics emails for each partner
 *
 * @author Jon Wiggins
 * @version 5/9/18
 */

public class AnalyticsPartner {

    public String name;
    public String hubID;
    public String hubName;

    //Holds the given analytics lines in ArrayList that correspond to the month that they took place in
    private HashMap<String, ArrayList<String>> monthToLinesForMonth;

    /**
     * Create a new AnalyticsPartner, with the given name
     * Figures out what Hub the partner belongs too, and sets associated fields
     * Note that if there is an error, this method may print an error message and exit the program so debugging can
     *  take place
     *
     * @param name the name of the partner as a String
     */
    public AnalyticsPartner(String name){
        this.name = name.replace("&amp;", "&");

        if(name.equals("")) this.name = "unpublished";

        try {
            setHubName();
        } catch (FileNotFoundException e) {
            System.err.println("Could not read HubPartnerMapData.csv while looking for Hub for Partner: " + name);
            System.exit(0);
        }

        monthToLinesForMonth = new HashMap<>();
    }


    /**
     * Called on creation of the AnalyticsPartner object, this method searched HubPartnerMap.csv to figure out
     *  what hub the partner with this object's name belongs to, and sets the HubID and HubName fields
     *  Note that if the hub id/name cannot be found, they are both set to "unpublished"
     *
     * @throws FileNotFoundException If the HubPartnerMap.csv cannot be found
     */
    private void setHubName() throws FileNotFoundException {
        HashMap<String, String> hubidtoName = new HashMap<>();

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


        Scanner s = new Scanner(new File("HubPartnerMap.csv"));
        while(s.hasNextLine()){
            String currentLine = s.nextLine();
            if(currentLine.contains(name)){
                Pattern pattern = Pattern.compile("\\w*,(\\S{3})-[0-9]{2}-");
                Matcher matcher = pattern.matcher(currentLine);
                try {
                    if(matcher.find())
                        hubID = matcher.group(1);
                    else
                        hubID = "unpublished";
                }catch(IllegalStateException e){
                    System.out.println(currentLine);
                    System.out.println(name);
                    System.exit(1);
                }
            }

        }
        //check if there is still no hubID set
        // if it reached this point with a null hubID, the marker does not exist in the map
        if(hubID == null){
            hubID = "unpublished";

        }

        //set the Hub Name to the corresponding hubID
        hubName = hubidtoName.get(hubID);

    }


    /**
     * Given a line to add, and a month, adds the line to the associated ArrayList
     *
     * @param line An analytics line to be added to a list for this partner
     * @param month the name of the month
     */
    public void addLineForMonth(String line, String month){
        //normalize the month name just because I am not sure if I capitalize or not everywhere
        month = month.toUpperCase();

        //If the current month is not already stored in the HashMap, create a new list, add the line, and add it
        if(!monthToLinesForMonth.containsKey(month)){
            ArrayList<String> toAdd = new ArrayList<>();
            toAdd.add(line);
            monthToLinesForMonth.put(month, toAdd);
        }
        else{
            //otherwise, get the list for that month, and add the line
            ArrayList<String> toAppend = monthToLinesForMonth.get(month);
            toAppend.add(line);
            monthToLinesForMonth.put(month, toAppend);
        }
    }

    /**
     * Given a month name as a string, returns the corresponding ArrayList of lines
     *
     * @param month a month name as a string
     * @return the corresponding ArrayList of lines
     *          Note that if the month name is invalid, an empty ArrayList is returned
     */
    public ArrayList<String> getMonthLines(String month){
        //normalize the month name just because I am not sure if I capitalize or not everywhere
        month = month.toUpperCase();

        if(monthToLinesForMonth.containsKey(month)){
            return monthToLinesForMonth.get(month);
        }else{
            return new ArrayList<>();
        }
    }
}
