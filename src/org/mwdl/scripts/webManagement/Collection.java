package org.mwdl.scripts.webManagement;

import java.util.ArrayList;

/**
 * @author Jon Wiggins
 * @version 6/9/17
 */

public class Collection {

    public String title;
    public String urlTitle;
    public String publisherLink;
    public String text;
    public String img;
    public String des;
    public String browse;
    public String ampImage;
    public int collectionNumber;
    public boolean isActive;
    public String note;
    public String urlPublisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getPublisherLink() {
        return publisherLink;
    }

    public void setPublisherLink(String publisherLink) {
        this.publisherLink = publisherLink;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }
    public String getAmpImg(){
        return ampImage;
    }
    public int getCollectionNumber() { return collectionNumber;}
    public boolean getActive(){
        return isActive;
    }
    public String getNote(){
        return note;
    }

    public String toString(){
        return "Number: "+collectionNumber +"\nisActive: " + isActive + "\nNote: "+ note + "\nTitle: "+ title +"\nURLTitle: " +urlTitle +"\nPublisher: "+ publisherLink + "\nText: " + text + "\nImage: " + img +"\nAMPImage: " + ampImage + "\nDescription: " + des + "\nBrowse Link: " + browse;
    }

    public String getPlainPublisher(){
        return publisherLink.replaceAll("<.*?>","").replace("Published by ","").replace("%comma%",",");
    }

    public String getUrlPublisher(){
        return urlPublisher;
    }



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

        urlPublisher = getPlainPublisher()
                .replace(" ","")
                .replace(".","")
                .replace("-","")
                .replace("(","")
                .replace(")","");
    }


}
