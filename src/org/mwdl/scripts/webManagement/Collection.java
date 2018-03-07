package org.mwdl.scripts.webManagement;

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


    public Collection(int collectionNumber, boolean isActive, String note, String title, String publisher, String text, String img, int height, int width, String des){
        this.collectionNumber = collectionNumber;
        this.isActive = isActive;
        this.note = note;
        this.title = title;
        this.publisher= publisher;
        this.publisherLink = publisher;
        this.text = text;
        this.img= img;
        this.imgH = height;
        this.imgW = width;
        this.des= des;

        refinedPublisher = publisher
                .replace(" ","")
                .replace(".","")
                .replace("-","")
                .replace("(","")
                .replace(")","");

        this.urlTitle = title.replace(" ","")
                .replace(".","")
                .replace("-","")
                .replace("(","")
                .replace(")","")
                .replace(",","")
                .replace(":","")
                .replace("/","")
                //pretty sure these ones aren't even needed tbh
                .replace("%comma%","")
                .replace("</p>","")
                .replace("<p>","")
                .replace("<b>","")
                .replace("</b>","");

    }

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
