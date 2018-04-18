package org.mwdl.scripts.analytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Hold the data lines for the analytics emails for each partner
 * To use the program, comment and uncomment the lines under //FIXME tags
 *  to fit the ammount of TODO that you are going to generate analytics for
 *
 * @author Jon Wiggins
 * @version 2/7/18
 */

public class AnalyticsPartner {

    public String name;
    public String hub;

    public ArrayList<String> JanCollections;
    public ArrayList<String> FebCollections;
    public ArrayList<String> MarchCollections;
    public ArrayList<String> AprilCollections;
    public ArrayList<String> MayCollections;
    public ArrayList<String> JuneCollections;
    public ArrayList<String> JulyCollections;
    public ArrayList<String> AugustCollections;
    public ArrayList<String> SeptCollections;
    public ArrayList<String> OctCollections;
    public ArrayList<String> NovCollections;
    public ArrayList<String> DecCollections;


    public AnalyticsPartner(String name){
        this.name = name.replace("&amp;", "&");

        if(name.equals("")) this.name = "unpublished";

        fetchHub();

        JanCollections = new ArrayList<>();
        FebCollections = new ArrayList<>();
        MarchCollections = new ArrayList<>();
        AprilCollections = new ArrayList<>();
        MayCollections = new ArrayList<>();
        JuneCollections = new ArrayList<>();
        JulyCollections = new ArrayList<>();
        AugustCollections = new ArrayList<>();
        SeptCollections = new ArrayList<>();
        OctCollections = new ArrayList<>();
        NovCollections = new ArrayList<>();
        DecCollections = new ArrayList<>();
    }

    private void fetchHub(){

        //remove the end numbers of the code after the -
        //String
        try {
            Scanner s = new Scanner(new File("HubPartnerMap.csv"));
            while(s.hasNextLine()){
                String currentLine = s.nextLine();
                if(currentLine.contains(name)) {
                    try {
                        Scanner liner = new Scanner(currentLine).useDelimiter(",");
                        liner.next();
                        liner.next();
                        hub = liner.next();
                        if(!hub.contains("-")) hub = liner.next();
                        if(!hub.contains("-")) hub = liner.next();
                        if(!hub.contains("-")) hub = liner.next();
                        if(!hub.contains("-")) hub = liner.next();
                        if(!hub.contains("-")) hub = liner.next();
                        if(!hub.contains("-")) hub = liner.next();
                        hub = hub.split("-")[0];
                        if(name.equals("Utah State Historical Society")){
                            System.out.println("USHS overridden to appear as DHA");
                            hub = "Department of Heritage and Arts";
                        }
                        return;
                    }catch (NullPointerException e){
                        hub = "unpublished";
                    }
                }
            }
            hub = "unpublished";
        } catch (FileNotFoundException | NoSuchElementException e) {
            System.err.println("Could not fetch hub for " + name);
        }

    }

    /**
     * Given a line and a month, adds the line to the list for the given month
     */
    public void addLineForMonth(String line, String month){
        //FIXME add more months here
        if(month.equalsIgnoreCase("January")) JanCollections.add(line);
        else if(month.equalsIgnoreCase("February")) FebCollections.add(line);
        else if (month.equalsIgnoreCase("March")) MarchCollections.add(line);
    }
}
