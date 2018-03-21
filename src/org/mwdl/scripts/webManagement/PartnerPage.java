package org.mwdl.scripts.webManagement;

import org.mwdl.scripts.data.DataFetcher;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents a partner landing page by holding all the info needed to create one
 *
 * oop ftw amirite?
 *
 * @author Jonathan Wiggins
 * @version 6/23/17
 */

public class PartnerPage {

    public String name;
    public ArrayList<Collection> activeCollections;
    public String link;
    public String description;
    public String image;
    public String imageH;
    public String imageW;
    public String imageDes;
    public int partnerNumber;
    public boolean isActive;
    public String note;

    public String urlName;
    public String browseLink;

    /**
     * Holds the information needed to make a landing page for a partner
     *
     * Should be read in from newPartnerData.csv which is in the following format
     *  Partner Number, Passed Inspection, Note, Name, Link, Text, Image Name, Image Height, Image Length, Image Description
     */
    public PartnerPage(int number, boolean isActive, String note, String name, String link, String text, String image, String imageH, String imageW, String imageDes){
        this.partnerNumber = number;
        this.isActive = isActive;
        this.note = note;
        this.name = name.replace("%comma%",",");
        this.link = link;
        this.description = text.replace("%comma%",",").replace("%newline%","\n");
        this.image = image;
        this.imageH = imageH;
        this.imageW = imageW;
        this.imageDes = imageDes;

        try {
            activeCollections = DataFetcher.getAllActiveCollectionsFromPartner(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        browseLink = getExlibirisLink();

        urlName = name.replaceAll("%comma%","");
        urlName = urlName.replaceAll("[^a-zA-Z0-9]", "");
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
