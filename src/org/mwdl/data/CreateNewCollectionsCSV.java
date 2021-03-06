package org.mwdl.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is made to be used only once, that means if you are looking at it, and you aren't me
 * *Don't run it*
 * Use it instead to document the storage system used in this project
 *
 * @author Jonathan Wiggins
 * @version 2/9/18
 */
@Deprecated
public class CreateNewCollectionsCSV {

    /**
     * This class is made to be run only a single TODO, it should read in the current collectionData.csv
     * It should then process the data and rewrite the document in a new formate
     * the old format was
     * Collection Number,Passed Inspection,Note,Title,URL Title,Publisher,Article Text,Article Image,AMP Image Version,Image Description,Browse Link
     * <p>
     * Wherein:
     * the title was stored redundantly alongside the URL Title
     * The Publisher was stored as an old link to the publishers page
     * The article text was stored in html form
     * the imageName was stored as a directly html <imageName> tag
     * the amp imageName, literally just a processed version of the <imageName> tag was stored redundantly
     * the browse link was stored in its entirety for not reason, as it can be generated from a generic search
     * link with the name of the collection attached to the end
     * <p>
     * The new collectionData.csv will store the information from the previous document in the new format
     * <p>
     * Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Length, Image Description
     * <p>
     * Wherein:
     * The Title will now be stored in plain text, and the urlTitle will be generated as needed rather than stored
     * The imageName will be stored as the file name in /images/collectionImages/collection"collectNumber.extension"
     * The imageName size as an int will be stored
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

        //articleImage
        if(s.hasNext()){
            String imageTag = s.next();
            Pattern imgNamePattern = Pattern.compile("\\/images\\/collection_images\\/(collection[0-9]{4}.\\S{3})");
            Matcher imgNameMatcher = imgNamePattern.matcher(imageTag);
            //capture imageName name in /imageName/collection_images/collection"number.extension"
            if(imgNameMatcher.find())
                toReturn = toReturn.concat(imgNameMatcher.group(1));
            toReturn = toReturn.concat(",");

            //capture imageName height
            Pattern imgHPattern = Pattern.compile("height=\\\"([0-9]{1,})\\\"");
            Matcher imgHMatcher = imgHPattern.matcher(imageTag);
            if(imgHMatcher.find())
                toReturn = toReturn.concat(imgHMatcher.group(1));
            toReturn = toReturn.concat(",");

            //capture imageName width
            Pattern imgWPattern = Pattern.compile("width=\\\"([0-9]{1,})\\\"");
            Matcher imgWMatcher = imgWPattern.matcher(imageTag);
            if(imgWMatcher.find())
                toReturn = toReturn.concat(imgWMatcher.group(1));
            toReturn = toReturn.concat(",");

            //skip amp version
            s.next();

            //imageName article
            String imgDes = s.next().replace("<br>","");
            Pattern imgDesPattern = Pattern.compile("<a\\b[^>]*>(.*?)<\\/a>");
            Matcher imgDesMatcher = imgDesPattern.matcher(imgDes);
            if(imgDesMatcher.find())
                toReturn = toReturn.concat(imgDesMatcher.group(1));
        }
        s.close();
        return toReturn;
    }


}
