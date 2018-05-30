package org.mwdl.webManagement;

import org.mwdl.data.DataFetcher;

import java.util.ArrayList;

/**
 * Represents a partner landing page by holding all the info needed to create one
 *
 * @author Jonathan Wiggins
 * @version 6/23/17
 */

public class Partner {

    public String name;
    public ArrayList<Collection> activeCollections;
    public String link;
    public String article;
    public String imageName;
    public int imageHeight;
    public int imageWidth;
    public String imageDes;
    public int partnerNumber;
    public boolean isActive;
    public String note;
    public String urlName;
    public String browseLink;

    /**
     * Holds the information needed to make a landing page for a partner
     *
     * Note that if imageWidth is 0, it will be set to 250
     * Note that if imageHeight is 0, it will be set to 250
     *
     * Should be read in from newPartnerData.csv which is in the following format
     *  Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description
     */
    public Partner(int number, boolean isActive, String note, String name, String link, String text, String image, int imageH, int imageW, String imageDes){
        this.partnerNumber = number;
        this.isActive = isActive;
        this.note = note;
        this.name = name.replace("%comma%",",");
        this.link = link;
        this.article = text.replace("%comma%",",").replace("%newline%","<br/>");
        this.imageName = image;
        this.imageHeight = imageH;
        this.imageWidth = imageW;
        this.imageDes = imageDes;


        activeCollections = DataFetcher.getAllActiveCollectionsFromPartner(name);


        browseLink = getExlibirisLink();

        //Normalize the name of the partner for storage in a url
        urlName = name.replaceAll("%comma%","");
        //removes all characters from the String other than a-z and 0-9
        urlName = urlName.replaceAll("[^a-zA-Z0-9]", "");

        //ensure that the imageName size is not 0
        if(imageH == 0)
            this.imageHeight = 250;
        if(imageW == 0)
            this.imageWidth = 250;
    }

    /**
     * Just used to print out Partner objects with a bit of formatting for debugging
     */
    public String toString(){
        return "Number: "+ partnerNumber +
                "\nisActive: " + isActive +
                "\nNote: " + note +
                "\nName: " + name +
                "\nURLName: " + urlName +
                "\nLink: " + link +
                "\nText: " + article +
                "\nImage: " + imageName +
                "\nImage Height: " + imageHeight +
                "\nImage Width: " + imageWidth +
                "\nDescription: " + imageDes;
    }

    /**
     * Note that the exlibiris browse link is just a search link with the name of the partner in it
     *
     * @return the Exlibirs browse link
     */
    public String getExlibirisLink(){
        return "http://utah-primoprod.hosted.exlibrisgroup.com/primo_library/libweb/action/search.do?tab=default_tab&mode=Advanced&scp.scps=scope:(mw)&vid=MWDL&indx=1&dum=true&srt=rank&frbg=&fn=search&ct=search&vl(1UI1)=exact&vl(35820410UI1)=lsr12&vl(freeText1)="
                + this.name.replace(" ", "+");
    }
}
