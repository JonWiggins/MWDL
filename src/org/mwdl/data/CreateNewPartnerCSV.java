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
 * This class is made to be used only once, that means if you are looking and it, and you aren't me
 * *Don't run it*
 * Use it instead to document the storage system used in this project
 *
 * @author Jonathan Wiggins
 * @version 3/5/18
 */
@Deprecated
public class CreateNewPartnerCSV {

    /**
     * Currently, partnerData.csv stores the data for the partner landing pages in the form
     * Partner Number,Passed Inspection,Note,Name,URL Name,Link,Text,Partner Image,AMP Image Version,Image Description,Browse Link
     *
     * This program will move that information into a new file, newPartnerData.csv, which will
     *  store the data in the form
     * Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<String> toReformat = importData();
        ArrayList<String> cleanedUp = new ArrayList<>();

        for(String s : toReformat)
            cleanedUp.add(cleanLine(s));


        try{
            PrintWriter newcsv = new PrintWriter("newPartnerData.csv","UTF-8");

            newcsv.append("Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description\n");

            for(String s : cleanedUp){
                newcsv.append(s);
                newcsv.append("\n");
            }

            newcsv.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> importData(){
        Scanner s = null;
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            s = new Scanner(new File("partnerData.csv"));

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

    public static String cleanLine(String line){
        if(line.contains("too few elements to be parsed") ||
                line.contains("Could not retrieve collection:"))
            return line;

        Scanner s = new Scanner(line).useDelimiter(",");
        //Partner Number,Passed Inspection,Note,Name,URL Name,Link,Text,Partner Image,AMP Image Version,Image Description,Browse Link
        //Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description
        String toReturn = "";

        //partner #
        toReturn = toReturn.concat(s.next() + ",");
        //inspection
        toReturn = toReturn.concat(s.next() + ",");
        //note
        toReturn = toReturn.concat(s.next() + ",");
        //Name
        toReturn = toReturn.concat(s.next() + ",");
        //urlName
        s.next();

        //link
        if(s.hasNext()) {
            String linkTag = s.next();
            Pattern linkPattern = Pattern.compile("<a href=\"(.*?)\" ");
            Matcher linkMatcher = linkPattern.matcher(linkTag);
            if(linkMatcher.find())
                toReturn = toReturn.concat(linkMatcher.group(1));
            toReturn = toReturn.concat(",");
        }
        //article text
        if(s.hasNext()){
            toReturn = toReturn.concat(s.next() + ",");
        }

        //Image
        if(s.hasNext()){
            String imageTag = s.next();
            Pattern imgNamePattern = Pattern.compile("\\/images\\/partner_images\\/(partner[0-9]{3}.\\S{3})");
            Matcher imgNameMatcher = imgNamePattern.matcher(imageTag);
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

            //image article
            String imgDes = s.next().replace("<br>","");
            Pattern imgDesPattern = Pattern.compile("alt=\"(.*?)\" ");
            Matcher imgDesMatcher = imgDesPattern.matcher(imgDes);
            if(imgDesMatcher.find())
                toReturn = toReturn.concat(imgDesMatcher.group(1));
        }
        s.close();
        return toReturn;
    }

}
