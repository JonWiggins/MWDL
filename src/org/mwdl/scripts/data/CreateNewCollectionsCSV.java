package org.mwdl.scripts.data;

import org.mwdl.scripts.webManagement.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This method is made to be used only once, that means if you are looking at it, and you aren't me
 *  *Don't run it*
 *  Use it instead to document the storage system used in this project
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

     * Wherein:
     *  the title was stored redundantly alongside the URL Title
     *  The Publisher was stored as an old link to the publishers page
     *  The article text was stored in html form
     *  the image was stored as a directly html <img> tag
     *  the amp img, literally just a processed version of the <img> tag was stored redundantly
     *  the browse link was stored in its entirety for not reason, as it can be generated from a generic search
     *   link with the name of the collection attached to the end
     *
     * The new collectionData.csv will store the information from the previous document in the new format
     *
     * Collection Number, Passed Inspection, Note, Title, Publisher, Article Text, Article Image, Image Height, Image Length, Image Description
     *
     * Wherein:
     *  The Title will now be stored in plain text, and the urlTitle will be generated as needed rather than stored
     *  The image will be stored as either the file name in /images/collectionImages/"imageName.extension"
     *  The image size as an int will be stored
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Collection> toReformat = importData();

        
    }

    public static ArrayList<Collection> importData() {
        Scanner s = null;
        ArrayList<Collection> toReturn = new ArrayList<>();
        try {
            s = new Scanner(new File("collectionData.csv")).useDelimiter(",");

            //skip the first line
            s.nextLine();

            while (s.hasNextLine()) {
                int count = s.nextInt();
                boolean isActive = Boolean.valueOf(s.next());
                if (isActive) {
                    String note = s.next();
                    if(note == null) note = " ";
                    String title = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String urlTitle = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String pub = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String text = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String img = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String ampImg = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String des = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");
                    String browse = s.next().replace("%newline%","\n").replace("%return%","\r").replace("%comma%",",");

                    toReturn.add(new Collection(count, isActive, note, title, urlTitle, pub, text, img, ampImg, des, browse));
                } else{
                    String note = s.next();
                    if(note == null) note = " ";
                    //TODO if line is only missing an image or something, this will throw away the entire rest of the line
                    //TODO  try taking the entire line and just catch null for each one
                    toReturn.add(new Collection(count, isActive, note, null, null, null, null, null, null, null, null));
                }
                s.nextLine();

            }
            System.out.println("Successfully loaded " + toReturn.size() +" collections from the csv file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


}
