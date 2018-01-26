package org.mwdl.scripts.webManagement;

import org.mwdl.scripts.data.DataFetcher;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TODO - Fill in class desc
 *
 * @author Jonathan Wiggins
 * @version 12/18/17
 */
public class LineUpdater {

    static int collectionToUpdate;

    /**
     * Given a Collection number, asks for missing info,
     * prints out the line with the new info added
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<String> inactiveCollections= new ArrayList<>();
        try {
            inactiveCollections = DataFetcher.getInactiveCollectionLines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> linesToUpdate = new ArrayList<>();

        for(String s : inactiveCollections)
            if(!s.contains("Could not ret")
                    && !s.contains("too few elements")
                    && !s.contains("- Collection in the "))
                linesToUpdate.add(s);

        for(String c : linesToUpdate)
            System.out.println(c);

        System.out.println(linesToUpdate.size());

        ArrayList<Collection> collectionsToUpdate = DataFetcher.createCollectionsFromLines(linesToUpdate);


    }


    public static Image askForImage() {
        System.out.println("Collection has null image, please input image name (Ex. collection" + collectionToUpdate + ".png)");
        Scanner s = new Scanner(System.in);
        String imageName = s.next();
        System.out.print("Enter the title for this image: ");
        String des = s.next();
        return new Image(imageName,des);
    }

    public static String askForArticleText() {
        System.out.println("Collection has not Article Test, please input article text: ");
        Scanner s = new Scanner(System.in);
        return s.next();
    }

    public static String askForBrowseLink(){
        System.out.println("Collection has a null Browse Link, please enter link");
        Scanner s = new Scanner(System.in);
        return s.next();
    }

    public static class Image{
        String imageName;
        String imageDes;
        public Image(String name, String des){
            this.imageName = name;
            this.imageDes = des;
        }

        public String toString(){
            return "";
        }
    }

}
