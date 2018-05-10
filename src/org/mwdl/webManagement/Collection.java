package org.mwdl.webManagement;

/**
 * Represents a collection landing pages, by holding all the info needed to create one
 *
 * @author Jon Wiggins
 * @version 6/9/17
 */

public class Collection {

    public int collectionNumber;
    public String title;
    public String urlTitle;
    public String publisherLink;
    public String publisher;
    public String text;
    public String img;
    public String des;
    public String browse;
    public boolean isActive;
    public String note;
    public String refinedPublisher;
    public int imgH;
    public int imgW;


    /**
     * Create a new Collection
     *
     * @param collectionNumber Collection number as an int
     * @param isActive is active boolean
     * @param note Notes about the collection
     * @param title The Title of the collection
     * @param publisher The collection's publisher
     * @param text The Article Text of the collection
     * @param img The name of the image typically following the format: collection%collectionNumber%.jpg
     * @param height The height of the image as an int, can be 0
     * @param width The width of the image as an int, can be 0
     * @param des A article of the image
     */
    public Collection(int collectionNumber, boolean isActive, String note, String title, String publisher, String text, String img, int height, int width, String des){
        this.collectionNumber = collectionNumber;
        this.isActive = isActive;
        this.note = note;
        this.title = title;
        this.publisher= publisher;
        this.publisherLink = publisher;
        this.img= img;
        this.imgH = height;
        this.imgW = width;
        this.des= des;

        //Normalize the article text by removing the special markers
        this.text = text.replace("%comma%",",").replace("%newline%","\n");

        refinedPublisher = publisher
                .replace(" ","")
                .replace(".","")
                .replace("-","")
                .replace("(","")
                .replace(")","")
                .replace(",","");

        //Removes any special things from the title
        // I don't think any titles have <p> or <b>, but just to be sure
        this.urlTitle = title
                //pretty sure these ones aren't even needed tbh
                .replaceAll("%comma%","")
                .replaceAll("</p>","")
                .replaceAll("<p>","")
                .replaceAll("<b>","")
                .replaceAll("</b>","");

        //Remove any character that is not a letter or number
        // This normalizes the tile to just raw text for the title
        urlTitle = urlTitle.replaceAll("[^a-zA-Z0-9]", "");

    }

    /**
     * Just used to print out Collection objects with a bit of formatting for debugging
     */
    public String toString(){
        return "Number: "+collectionNumber +"\nisActive: " + isActive + "\nNote: "+ note + "\nTitle: "+ title +"\nURLTitle: " +urlTitle +"\nPublisher: "+ publisherLink + "\nText: " + text + "\nImage: " + img + "\nDescription: " + des;
    }

    /**
     * Note that the exlibiris browse link is just a search link with the title of the collection in the link
     *
     * @return the Exlibirs browse link
     */
    public String getExlibirisLink(){
        return "http://utah-primoprod.hosted.exlibrisgroup.com/primo_library/libweb/action/dlSearch.do?vid=MWDL&institution=MWDL&onCampus=false&search_scope=mw&query=lsr04,exact,"
                + collectionNumber
                + "&indx=1"
                //Note, this variable controls how many objects to return each search
                // currently set to 50
                + "&bulkSize=50";
    }

}
