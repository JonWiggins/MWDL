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

    //Generate this from getExlibrisLink()
    @Deprecated
    public String publisherLink;
    public String text;
    public String img;
    public String des;
    public String browse;

    //Generate this tag from img and imgH/imgW
    @Deprecated
    public String ampImage;
    public boolean isActive;
    public String note;
    public String refinedPublisher;
    public int imgH;
    public int imgW;




    /**
     * This method is deprecated because it makes use of the browse link, which should be generated rather than stored
     * See getExlibirisLink()
     *
     */
    @Deprecated
    public Collection(int collectionNumber, boolean isActive, String note, String title, String urTitle, String publisherLink, String text, String img, String ampImage, String des, String browse){
        this.collectionNumber = collectionNumber;
        this.isActive = isActive;
        this.note = note;
        this.title = title;
        this.urlTitle= urTitle;
        this.publisherLink = publisherLink;
        this.text = text;
        this.img= img;
        this.ampImage = ampImage;
        this.des= des;
        this.browse= browse;

        refinedPublisher = getPlainPublisher()
                .replace(" ","")
                .replace(".","")
                .replace("-","")
                .replace("(","")
                .replace(")","");
    }

    /**
     * Use this one kid
     *
     * @param collectionNumber
     * @param isActive
     * @param note
     * @param title
     * @param publisherLink
     * @param text
     * @param img
     * @param height
     * @param width
     * @param des
     */
    public Collection(int collectionNumber, boolean isActive, String note, String title, String publisherLink, String text, String img, int height, int width, String des){
        this.collectionNumber = collectionNumber;
        this.isActive = isActive;
        this.note = note;
        this.title = title;
        this.publisherLink = publisherLink;
        this.text = text;
        this.img= img;
        this.imgH = height;
        this.imgW = width;
        this.des= des;

        refinedPublisher = getPlainPublisher()
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
        return "Number: "+collectionNumber +"\nisActive: " + isActive + "\nNote: "+ note + "\nTitle: "+ title +"\nURLTitle: " +urlTitle +"\nPublisher: "+ publisherLink + "\nText: " + text + "\nImage: " + img +"\nAMPImage: " + ampImage + "\nDescription: " + des;
    }

    public String getPlainPublisher(){
        return publisherLink.replaceAll("<.*?>","").replace("Published by ","").replace("%comma%",",");
    }

    public String getRefinedPublisher(){
        return refinedPublisher;
    }

    /**
     * Note that the exlibiris browse link is just a search link with the title of the collection in the link
     *
     * @return the Exlibirs browse link
     */
    public String getExlibirisLink(){
        return "http://utah-primoprod.hosted.exlibrisgroup.com/primo_library/libweb/action/dlSearch.do?vid=MWDL&institution=MWDL&onCampus=false&search_scope=mw&query=lsr04,exact,"
                + collectionNumber
                + "&indx=1&"
                //Note, this variable controls how many objects to return each search
                // currently set to 50
                + "bulkSize=50";
    }

}
