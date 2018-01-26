package org.mwdl.scripts.analytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Jon Wiggins
 * @version 6/23/17
 */

public class AnalyticsPartner {

    private String name;
    private String hub;

    //FIXME add new ArrayList for month here
    private ArrayList<String> MayCollections;
    private ArrayList<String> JuneCollections;
    private ArrayList<String> JulyCollections;
    private ArrayList<String> AugustCollections;
    private ArrayList<String> SeptCollections;
    private ArrayList<String> OctCollections;
    private ArrayList<String> NovCollection;
    private ArrayList<String> DecCollection;

    AnalyticsPartner(String name){
        this.name = name.replace("&amp;", "&");
        if(name.equals("")) this.name = "unpublished";

        //FIXME initialize new ArrayList here
        MayCollections = new ArrayList<>();
        JuneCollections = new ArrayList<>();
        JulyCollections = new ArrayList<>();
        AugustCollections = new ArrayList<>();
        SeptCollections = new ArrayList<>();
        OctCollections = new ArrayList<>();
        NovCollection = new ArrayList<>();
        DecCollection = new ArrayList<>();
        fetchHub();

    }

    //FIXME add adder for new month ArrayList here
    void addMayCollection(String collection){
        MayCollections.add(collection);
    }

    void addJuneCollection(String collection){
        JuneCollections.add(collection);
    }
    void addJulyCollection(String collection){
        JulyCollections.add(collection);
    }
    void addAugustCollection(String collection){
        AugustCollections.add(collection);
    }
    void addSeptCollection(String collection){
        SeptCollections.add(collection);
    }
    void addOctCollection(String collection){
        OctCollections.add(collection);
    }
    void addNovCollection(String collection){
        NovCollection.add(collection);
    }
    void addDecCollection(String collection){
        DecCollection.add(collection);
    }

    //FIXME add getter for new month ArrayList here
    ArrayList getMayCollections(){
        return MayCollections;
    }
    ArrayList getJuneCollections(){
        return JuneCollections;
    }
    ArrayList getJulyCollections(){
        return JulyCollections;
    }
    ArrayList getAugustCollections(){
        return AugustCollections;
    }
    ArrayList getSeptCollections(){
        return SeptCollections;
    }
    ArrayList getOctCollections(){
        return OctCollections;
    }
    ArrayList getNovCollections(){
        return NovCollection;
    }
    ArrayList getDecCollections(){
        return DecCollection;
    }

    public String getName(){
        return name;
    }
    String getHub(){
        return hub;
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


}
