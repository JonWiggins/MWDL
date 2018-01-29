package org.mwdl.scripts.webManagement;

import org.mwdl.scripts.data.DataFetcher;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents a partner landing page by holding all the info needed to create one
 *
 * @author Jonathan Wiggins
 * @version 6/23/17
 */

public class PartnerPage {

    private String name;
    private String urlName;
    private ArrayList<Collection> activeCollections;
    private String link;
    private String description;
    private String image;
    private String imageDes;
    private String browseLink;
    private int partnerNumber;
    private boolean isActive;
    private String note;
    private String ampImage;

    public PartnerPage(int number, boolean isActive, String note, String name, String urlName, String partnerLink, String des, String img, String ampImg, String imgDes, String bLink){

        this.partnerNumber = number;
        this.isActive = isActive;
        this.note = note;
        this.name = name;
        this.urlName = urlName;
        this.link = partnerLink;
        this.description = des;
        this.image = img;
        this.ampImage = ampImg;
        this.imageDes = imgDes;
        this.browseLink = bLink;
        try {
            activeCollections = DataFetcher.getAllActiveCollectionsFromPartner(partnerNumber);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setActiveCollections(ArrayList<Collection> collectionsList){
        activeCollections = collectionsList;

    }
    public ArrayList getCollections(){
        return activeCollections;
    }
    public String getName(){
        return name;
    }

    public String getImageDes() {
        return imageDes;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getBrowseLink() {
        return browseLink;
    }

    public int getPartnerNumber() {
        return partnerNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getNote() {
        return note;
    }

    public String getUrlName() {
        return urlName;
    }

    public String getAmpImage() {
        return ampImage;
    }
}
