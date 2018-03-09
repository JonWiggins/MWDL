package org.mwdl.scripts.webManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * Used to strip the information off of the old MWDL website and store it in a spreadsheet
 * Note that the spreadsheet has been updated since this has been run, so if you run this you will lose tons of work
 * Note that it is lots of spaghetti because the people who made the side did not have a uniform design
 *  so about 5 different types of pages have to be accounted for
 *
 * pls no use
 *
 * Large parts of this program had to be commented out b/c of changes to the Collection class after it was Deprecated
 * * I should probably just delete this so 10 years from now no one can find it and use how bad it is to blackmail me

 *
 * @author Jonathan Wiggins
 * @version 3/9/18
 */


@Deprecated //okay but actually don't use this

public class CollectionMaker {

    private static ArrayList<Collection> collectionArrayList;
    private static PrintWriter csv;


    public static void main(String[] args) {

        System.exit(0); // Somebody touca ma spaghet

        int count = 1001;
        int stopNumber = 2337;

        collectionArrayList = new ArrayList<>();
        String FileLocAndName = "collectionData.csv";
        try {
            csv = new PrintWriter(FileLocAndName,"UTF-8");

            csv.append("Collection Number");
            csv.append(",");
            csv.append("Passed Inspection");
            csv.append(",");
            csv.append("Note");
            csv.append(",");
            csv.append("Title");
            csv.append(",");
            csv.append("URL Title");
            csv.append(",");
            csv.append("Publisher");
            csv.append(",");
            csv.append("Article Text");
            csv.append(",");
            csv.append("Article Image");
            csv.append(",");
            csv.append("AMP Image Version");
            csv.append(",");
            csv.append("Image Description");
            csv.append(",");
            csv.append("Browse Link");
            csv.append("\n");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("Could not create CSV collection File");
        }


        while (stopNumber >= count) {
            //for each php for a collection, make a corresponding html and amphtml
            //do this by reading the php, to get the following info:
            // - title of collection
            // - publisher
            // - accompanying text
            // - size of image (gotten from image (gotten from collection id number)
            // - Description of the image
            // - link to browse the collection

            try {
                boolean isActive = true;
                String note = null, image = null, ampImage = null, imageDes = null, browseLink = null;
                Document doc = Jsoup.connect("http://mwdl.org/collections/" + count + ".php").get();

                //get the title => search for title tag, store contents
                //System.out.println("===========New Article: " +count+" =========");
                String currentTitle = doc.title()
                        .replace(" - Collection in the Mountain West Digital Library", "")
                        .replace("<p>", "")
                        .replace("</p>", "");
               //System.out.println("Title: " + currentTitle);
                String urlTitle = currentTitle
                        .replace("!", "")
                        .replace(" ", "")
                        .replace(":", "")
                        .replace(".", "")
                        .replace(",", "")
                        .replace("(", "")
                        .replace(")", "")
                        .replace("'", "")
                        .replace("\"", "")
                        .replace("?", "")
                        .replace("-","")
                        .replace("/","");
               // System.out.println("Doc Name: " + urlTitle + ".php");

                //get the publisher => Search for "Published by"
                String currentPublisher = doc.select("p").first().toString();

                if(! (currentPublisher.contains("Published by"))){
                    //This collection is out of order or redacted
                    System.err.println("Collection " + count + " appears to have an issue.");
                    System.err.println("Reason: First \"p\" tag does not contain \"Published by\"");
                }else{
                    currentPublisher = currentPublisher
                            .replace("<p>", "")
                            .replace("</p>", "");
                }
                //System.out.println("Link to partner Page: " + currentPublisher);
                //get accompanying text => everything between here and an image tag
                String currentText = doc.select("p").toString()
                        .replace(currentPublisher, "")
                        .replace("<p>Search within the collection:</p>", "")
                        .replace("&amp;", "&");
                String[] articleContents = currentText.split("</p>");
                if(articleContents.length > 1) {
                    String articleText = "";
                    String tempText = "";
                    for (int i = 0; i < articleContents.length; i++) {
                        articleContents[i] = articleContents[i].concat("</p>");
                        if(articleContents[i].contains("img src")){
                            //this is the articles image
                            //remove the border, and save this and the amp version
                            Element borderRemover = Jsoup.parseBodyFragment(articleContents[i]);

                            try {
                                image = borderRemover.select("img").removeAttr("border").toString().replace(">","").replace("></a>","");
                                if(!(image == null)) {
                                    //grab the description
                                    try {
                                        imageDes = borderRemover.select("a").last().removeAttr("a").removeAttr("ref").toString();
                                    } catch (NullPointerException e) {

                                    }
                                    //System.out.println("Image Description: " + imageDes);

                                    if(!image.contains("height")){
                                        image = image.concat(" height =\"250\"");
                                    }
                                    if(!image.contains("width")){
                                        image = image.concat(" width=\"250\"");
                                    }
                                    ampImage = image
                                            .replace("img", "amp-img")
                                            .replace("<p>", "")
                                            .replace("border=\"2\"", "")
                                            .replace("../images", "../images")
                                            .concat(" layout = \"responsive\"></amp-img>");
                                    image = image.concat(" align= \"right\" style=\"max-width: 250px; height: auto; margin: 1%; display: block; \">");
                                }
                                //System.out.println("Image & Link: " + image);
                                //System.out.println("AMP Image & Link: " + ampImage);
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("Does Article " + count + " not have an image or description?");
                            }
                        }else if(articleContents[i].contains("all records")){
                            //browse all link selected
                            browseLink = articleContents[i].replace("<p>", "");
                            //System.out.println("Browse all Link: " + browseLink);
                        }else if(articleContents[i].contains("Search within the collection:")){
                            //it is the search bar
                            //ignore it
                        }else{
                            //it is not an image, a des, a browse, or a search bar
                            //it must be part of the text
                            tempText = tempText.concat(articleContents[i]);
                        }
                    }

                    //test to see if the article text contain the image des, if it does, remove it
                    if(tempText.contains("<a href")){
                        //it does
                        Element desGrabber = Jsoup.parseBodyFragment(tempText);
                        String rawDes = desGrabber.select("a").toString();
                        imageDes = desGrabber.select("a").removeAttr("ref").toString().replace("&amp;", "&");
                        articleText="";
                        String[] text = tempText.split("<p>");
                        for(String a : text){
                            if(!a.contains(rawDes.replace("&amp;","&"))){
                                articleText = articleText.concat(a);
                            }
                        }

                    }else{
                        articleText = tempText;
                    }
                    //System.out.println("Article Text: " + articleText);
                    //see if the page is missing any data
                    if (currentPublisher.contains("../partners/.php")
                            || !currentPublisher.contains("<a href")
                            || currentPublisher.contains(".php\"></a>")) {
                        // this page is removed
                        if(note == null) note = count + ".php has been marked for review (Null partner)";
                        else note = note.concat(" (Null AnalyticsPartner)");
                        isActive = false;
                        System.err.println(note);
                    }

                    //remove the extra ref from the text of the collection
                    if(articleText.contains(" ref=")){
                        articleText = articleText.replaceAll(" ref=\"[^\"]*\"", "");
                    }


                    if(image == null){
                        // try one more time to capture the image
                        try {
                            image = doc.body().select("img").last().removeAttr("border").toString().replace("</a>","");

                            if(!image.contains("height")){
                                image = image.concat(" height =\"250\"");
                            }
                            if(!image.contains("width")){
                                image = image.concat(" width=\"250\"");
                            }
                            ampImage = image
                                    .replace("img", "amp-img")
                                    .replace("<p>", "")
                                    .replace("border=\"2\"", "")
                                    .replace("../images", "../images")
                                    .concat(" layout = \"responsive\"></amp-img>");
                            image = image.concat(" align= \"right\" style=\"max-width: 250px; height: auto; margin: 1%; display: block; \">");

                            //System.out.println("Image & Link: " + image);
                            //System.out.println("AMP Image & Link: " + ampImage);


                        }catch(Exception e){
                            note = count + ".php has been marked for review (Null image)";
                            System.err.println(note);
                            isActive = false;
                        }

                    }

                    if(image.contains("mountainWestDigitalLibrary-mainLogo.png")){
                        //the image captured is the Logo
                        image = null;
                        ampImage = null;
                        if(note == null) note = count + ".php has been marked for review (Null image)";
                        else note = note.concat(" (Null image)");
                        System.err.println(note);
                        isActive = false;

                    }
                    if(imageDes == null){
                        // this page is removed
                        if(note == null) note = count + ".php has been marked for review (Null image description)";
                        else note = note.concat(" (Null image description)");
                        System.err.println(note);
                        isActive = false;
                    }
                    if(browseLink == null){
                        // this page is removed
                        if(note == null) note = count + ".php has been marked for review (Null Browse Link)";
                        else note = note.concat(" (Null browse link)");
                        System.err.println(note);
                        isActive = false;
                    }
                    if(articleText.length() <20 || articleText == null){
                        if(note == null) note = count + ".php has been marked for review (Null Article Text)";
                        else note = note.concat(" (Null Article Text)");
                        System.err.println(note);
                        isActive = false;
                    }

                    //Now that you have the info needed, write it all to a new html doc, and then to a new amphtml doc
                    //collectionArrayList.add(new Collection(count, isActive, note, currentTitle, urlTitle, currentPublisher, articleText, image, ampImage, imageDes, browseLink));

                }else{
                    note = "Article " + count +" has too few elements to be parsed.";
                    csv.append(String.valueOf(count)).append(",").append(String.valueOf(false)).append(",");
                    csv.append(note).append("\n");
                    System.err.println(note);
                }
            } catch (org.jsoup.HttpStatusException e) {
                csv.append(String.valueOf(count)).append(",").append(String.valueOf(false)).append(",");
                csv.append("Could not retrieve collection: " + count + ".php").append("\n");
                System.err.println("Could not retrieve collection: " + count + ".php");
            } catch (IOException e) {
                e.printStackTrace();
            }

            count++;
        }
        for(Collection a : collectionArrayList){
            writeCSV(a);
        }
        csv.close();
    }

    public static void writeCSV(Collection toWrite){

        csv.append(String.valueOf(toWrite.collectionNumber)).append(",");
        csv.append(String.valueOf(toWrite.isActive)).append(",");

        if(toWrite.note == null){
            csv.append("").append(",");
        }else{
            csv.append(toWrite.note).append(",");
        }

        try {
            csv.append(toWrite.title.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        try{
        csv.append(toWrite.urlTitle.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        try{
        csv.append(toWrite.publisherLink.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        try{
        csv.append(toWrite.text.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        try{
        csv.append(toWrite.img.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        try{
        //csv.append(toWrite.ampImage.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        try{
        csv.append(toWrite.des.replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
        }catch(NullPointerException e){}

        csv.append(",");

        //Commented because we don't use getBrowse anymore
        // Browse links are generated, not stored
//        try{
//        csv.append(toWrite.getBrowse().replace("\n", "%newline%").replace("\r", "%return%").replace(",","%comma%"));
//        }catch(NullPointerException e){}

        csv.append(",").append("\n");
    }
}
