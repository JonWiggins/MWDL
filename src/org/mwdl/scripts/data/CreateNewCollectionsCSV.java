package org.mwdl.scripts.data;

import org.mwdl.scripts.webManagement.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This method is made to be used only once, that means if you are looking at it, and you aren't me
 * *Don't run it*
 * Use it instead to document the storage system used in this project
 *
 * @author Jonathan Wiggins
 * @version 2/9/18
 */
public class CreateNewCollectionsCSV {

    /**
     * This class is made to be run only a single time, it should read in the current collectionData.csv
     * It should then process the data and rewrite the document in a new formate
     * the old format was
     * Collection Number,Passed Inspection,Note,Title,URL Title,Publisher,Article Text,Article Image,AMP Image Version,Image Description,Browse Link
     * <p>
     * Wherein:
     * the title was stored redundantly alongside the URL Title
     * The Publisher was stored as an old link to the publishers page
     * The article text was stored in html form
     * the image was stored as a directly html <img> tag
     * the amp img, literally just a processed version of the <img> tag was stored redundantly
     * the browse link was stored in its entirety for not reason, as it can be generated from a generic search
     * link with the name of the collection attached to the end
     * <p>
     * The new collectionData.csv will store the information from the previous document in the new format
     * <p>
     * Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Length, Image Description
     * <p>
     * Wherein:
     * The Title will now be stored in plain text, and the urlTitle will be generated as needed rather than stored
     * The image will be stored as the file name in /images/collectionImages/collection"collectNumber.extension"
     * The image size as an int will be stored
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<String> toReformat = importData();
        ArrayList<String> cleanedUp = new ArrayList<>();

        for(String s : toReformat)
            cleanedUp.add(cleanLine(s));


        try{
            PrintWriter newcsv = new PrintWriter("newCollectionData.csv","UTF-8");

            newcsv.append("Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Length, Image Description\n");

            for(String s : cleanedUp){
                newcsv.append(s);
                newcsv.append("\n");
            }

            newcsv.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //TODO write the entirety of the cleanedUP arraylist to a new csv file
    }

    public static ArrayList<String> importData() {
        Scanner s = null;
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            s = new Scanner(new File("collectionData.csv"));

            //skip the first line
            s.nextLine();

            while (s.hasNextLine()) {
                toReturn.add(s.nextLine());
            }
            s.close();
            System.out.println("Successfully loaded " + toReturn.size() + " collections from the csv file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    /**
     * Do the following to a given line to clean it up:
     *  - If it contains "Could not be retrieved" or "too few elements to be parsed", return as is
     * Otherwise Implement the line in the following format:
     * Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Length, Image Description
     *
     * @param line a line from collectionData.csv to clean
     * @return the refined line
     */
    public static String cleanLine(String line){

        if(line.contains("too few elements to be parsed") ||
                line.contains("Could not retrieve collection:"))
            return line;

        Scanner s = new Scanner(line).useDelimiter(",");
        //Collection Number,Passed Inspection,Note,Title,URL Title,Publisher,Article Text,Article Image,AMP Image Version,Image Description,Browse Link
        //Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Width, Image Description

        String toReturn = "";

        //col #
        toReturn = toReturn.concat(s.next() + ",");
        //inspection
        toReturn = toReturn.concat(s.next() + ",");
        //note
        toReturn = toReturn.concat(s.next() + ",");
        //title
        toReturn = toReturn.concat(s.next() + ",");
        //urltitle
        s.next();
        //publisher
        if(s.hasNext()) {
            toReturn = toReturn.concat(s.next().replaceAll("<.*?>","").replace("Published by ","")+",");
        }
        //article text
        if(s.hasNext()){
            toReturn = toReturn.concat(s.next() + ",");
        }
        //System.out.println("1: " + toReturn);
        //articleImage
        if(s.hasNext()){
            String imageTag = s.next();
            Pattern imgNamePatter = Pattern.compile("\\/images\\/collection_images\\/(collection[0-9]{4}.\\S{3})");
            Matcher imgNameMatcher = imgNamePatter.matcher(imageTag);
            //capture image name in /image/collection_images/collection"number.extension"
            if(imgNameMatcher.find())
                toReturn = toReturn.concat(imgNameMatcher.group(1));
            toReturn = toReturn.concat(",");

            //capture image height
            Pattern imgHPattern = Pattern.compile("height=\\\"([0-9]{1,})\\\"");
            Matcher imgHMatcher = imgHPattern.matcher(imageTag);
            if(imgHMatcher.find())
                toReturn = toReturn.concat(imgHMatcher.group(1));
            toReturn = toReturn.concat(",");

            //capture image width
            Pattern imgWPattern = Pattern.compile("width=\\\"([0-9]{1,})\\\"");
            Matcher imgWMatcher = imgWPattern.matcher(imageTag);
            if(imgWMatcher.find())
                toReturn = toReturn.concat(imgWMatcher.group(1));
            toReturn = toReturn.concat(",");

            //skip amp version
            s.next();

            //image description
            toReturn.concat(s.next());
        }
        s.close();
        return toReturn;
    }


}
