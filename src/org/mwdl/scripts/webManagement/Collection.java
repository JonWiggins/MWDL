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
    public String text;
    public String img;
    public String des;
    public String browse;
    public String ampImage;
    public boolean isActive;
    public String note;
    public String refinedPublisher;




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

    public Collection(int collectionNumber, boolean isActive, String note, String title, String urTitle, String publisherLink, String text, String img, String ampImage, String des){
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

        refinedPublisher = getPlainPublisher()
                .replace(" ","")
                .replace(".","")
                .replace("-","")
                .replace("(","")
                .replace(")","");
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
        return "http://utah-primoprod.hosted.exlibrisgroup.com/primo_library/libweb/action/search.do?tab=default_tab&mode=Advanced&scp.scps=scope:(mw)&vid=MWDL&indx=1&dum=true&srt=rank&frbg=&fn=search&ct=search&vl(1UI1)=exact&vl(35820410UI1)=lsr13&vl(freeText1)="
                +title.replace(" ","+");
    }

}
