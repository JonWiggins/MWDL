package org.mwdl.scripts.webManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mwdl.scripts.data.DataFetcher;

/**
 * Used to strip the information off of the old MWDL website and store it in a spreadsheet
 * Note that the spreadsheet has been updated since this has been run, so if you run this you will lose tons of work
 * Note that it is lots of spaghetti because the people who made the side did not have a uniform design
 *  so about 5 different types of pages have to be accounted for
 *
 * pls no use
 *
 *
 * @author Jonathan Wiggins
 * @version 6/5/17
 */


@Deprecated //okay but actually please don't use

public class PartnerMaker {

    private static ArrayList<PartnerPage> partnerArrayList;
    private static PrintWriter csv;
    private static PrintWriter mapCSV;


    public static void main(String[] args) {

        System.exit(0); // Somebody touca ma spaghet

        int count = 101;
        int stopNumber = 369;

        partnerArrayList = new ArrayList<>();
        String FileLocAndName = "partnerData.csv";
        try {
            csv = new PrintWriter(FileLocAndName, "UTF-8");

            csv.append("Partner Number");
            csv.append(",");
            csv.append("Passed Inspection");
            csv.append(",");
            csv.append("Note");
            csv.append(",");
            csv.append("Name");
            csv.append(",");
            csv.append("URL Name");
            csv.append(",");
            csv.append("Link");
            csv.append(",");
            csv.append("Text");
            csv.append(",");
            csv.append("Partner Image");
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

        String mapLocAndName = "partnerMapData.csv";
        try {
            mapCSV = new PrintWriter(mapLocAndName, "UTF-8");

            mapCSV.append("Partner Name");
            mapCSV.append(",");
            mapCSV.append("Partner Link");
            mapCSV.append("\n");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("Could not create CSV collection File");
        }


        while (stopNumber >= count) {
            try {
                boolean isActive = true;
                String note = null, image = null, ampImage = null, imageDes = null, browseLink = null;
                Document doc = Jsoup.connect("http://mwdl.org/partners/" + count + ".php").get();

                //get the title => search for title tag, store contents
                System.out.println("===========New Partner: " + count + " =========");
                String partnerName = doc.title()
                        .replace(" - Partner of the Mountain West Digital Library", "")
                        .replace("<p>", "")
                        .replace("</p>", "");
                System.out.println("Partner Name: " + partnerName);
                String urlPartnerName = partnerName
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
                        .replace("-", "")
                        .replace("/", "");
                System.out.println("Doc Name: " + urlPartnerName + ".php");

                //get the link => Search for "Website"
                String currentLink = doc.select("p").first().toString();

                if (!(currentLink.contains("Website"))) {
                    //This collection is out of order or redacted
                    System.err.println("Partner " + count + " appears to have an issue.");
                    System.err.println("Reason: First \"p\" tag does not contain \"Website\"");
                } else {
                    currentLink = currentLink
                            .replace("<p>", "")
                            .replace("</p>", "");
                }
                System.out.println("Link to partner Page: " + currentLink);
                //get accompanying text => everything between here and an image tag
                String currentText = doc.select("p").toString()
                        .replace("&amp;", "&")
                        .replace(currentLink, "")
                        .replace("<p>Search within the collection:</p>", "");

                String[] articleContents = currentText.split("</p>");

                for (String s : articleContents) {
                    System.out.println(s);
                }
                if (articleContents.length > 1) {
                    String articleText = "";
                    String tempText = "";
                    for (int i = 0; i < articleContents.length; i++) {
                        articleContents[i] = articleContents[i].concat("</p>");
                        if (articleContents[i].contains("img src")) {
                            //this is the articles image
                            //remove the border, and save this and the amp version
                            Element borderRemover = Jsoup.parseBodyFragment(articleContents[i]);

                            try {
                                image = borderRemover.select("img").removeAttr("border").toString().replace(">", "").replace("></a>", "");
                                if (!(image == null)) {
                                    //grab the description
                                    try {
                                        int a = 2;
                                        imageDes = borderRemover.select("a").last().removeAttr("a").removeAttr("ref").toString();
                                    } catch (NullPointerException e) {

                                    }
                                    System.out.println("Image Description: " + imageDes);

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
                                            .concat(" layout = \"responsive\"></amp-img></a>");
                                    image = image.concat("align=\"right\" style=\"max-width:250px; height:auto; margin: 3%; display:block; \">");
                                }
                                System.out.println("Image & Link: " + image);
                                System.out.println("AMP Image & Link: " + ampImage);
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("Does Partner " + count + " not have an image or description?");
                            }
                        } else if (articleContents[i].contains("all records")) {
                            //browse all link selected
                            browseLink = articleContents[i].replace("<p>", "");
                            System.out.println("Browse all Link: " + browseLink);
                        } else if (articleContents[i].contains("Search within the collection:")) {
                            //it is the search bar
                            //ignore it
                        } else {
                            //it is not an image, a des, a browse, or a search bar
                            //it must be part of the text
                            tempText = tempText.concat(articleContents[i]);
                        }
                    }

                    //test to see if the article text contain the image des, if it does, remove it
                    if (tempText.contains("<a href")) {
                        //its does
                        Element desGrabber = Jsoup.parseBodyFragment(tempText);
                        String rawDes = desGrabber.select("a").toString();
                        imageDes = desGrabber.select("a").removeAttr("ref").toString();
                        articleText = tempText.replace(rawDes, "");
                        System.out.println("Image Description: " + imageDes);
                    } else {
                        articleText = tempText.replace("<p>Search within the records from this partner:</p>", "");

                        //refine the article text, remove the "search within" stuff
                        //and the image description, marked by the <strong> tag

                        if (articleText.contains("<strong>")) {
                            Element desGrabber = Jsoup.parseBodyFragment(articleText);
                            imageDes = desGrabber.select("strong").toString();
                            articleText = articleText.replace(imageDes, "");
                            imageDes = imageDes
                                    .replace("<strong>", "")
                                    .replace("</strong>", "");
                        }
                    }
                    //clean up the text, remove blank p tags, the collections from this partner, ect
                    articleText = articleText
                            .replace("<p></p>", "")
                            .replace("<p>Collections from this partner</p>", "")
                            .replace("<p>See also these related partners:</p>", "");
                    System.out.println("Article Text: " + articleText);
                    //see if the page is missing any data
                    if (partnerName.contains("Partner of the Mountain West Digital Library")) {
                        // this page is removed
                        if (note == null) note = count + ".php has been marked for review (Null partner)";
                        else note = note.concat(" (Null Partner)");
                        System.err.println(note);
                    }
                    if (image == null) {
                        // try one more time to capture the image
                        try {
                            image = doc.body().select("img").last().removeAttr("border").toString().replace("</a>", "");

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
                            image = image.concat("align=\"right\" style=\"max-width:250px; height:auto; margin: 3%; display:block; \">");

                            System.out.println("Image & Link: " + image);
                            System.out.println("AMP Image & Link: " + ampImage);


                        } catch (Exception e) {
                            note = count + ".php has been marked for review (Null image)";
                            System.err.println(note);
                            isActive = false;
                        }

                    }
                    if (image.contains("mountainWestDigitalLibrary-mainLogo.png")) {
                        //the image captured is the Logo
                        image = null;
                        ampImage = null;
                        if (note == null) note = count + ".php has been marked for review (Null image)";
                        else note = note.concat(" (Null image)");
                        System.err.println(note);
                        isActive = false;

                    }
                    if (imageDes == null) {
                        try {
                            //try to get it again, it may be inside a span tag
                            imageDes = doc.select("strong").first()
                                    .toString()
                                    .replace("<strong>", "")
                                    .replace("</strong>", "");
                        } catch (NullPointerException e) {

                            // this page is removed
                            if (note == null) note = count + ".php has been marked for review (Null image description)";
                            else note = note.concat(" (Null image description)");
                            System.err.println(note);
                            isActive = false;
                        }

                    }
                    if (browseLink == null) {
                        // this page is removed
                        if (note == null) note = count + ".php has been marked for review (Null Browse Link)";
                        else note = note.concat(" (Null browse link)");
                        System.err.println(note);
                        isActive = false;
                    }

                    if (articleText == null) {
                        if (note == null) note = count + ".php has been marked for review (Null Article Text)";
                        else note = note.concat(" (Null Article Text)");
                        System.err.println(note);
                        isActive = false;
                    }

                    if (articleText.replace("<p>Collections from this partner</p>", "").length() < 30) {
                        if (note == null) note = count + ".php has been marked for review (Article Text Too Short)";
                        else note = note.concat(" (Article Text Too Short)");
                        System.err.println(note);
                        isActive = false;
                    }

                    try {
                        ArrayList<Collection> a = DataFetcher.getAllActiveCollectionsFromPartner(count);
                        System.out.println("Active Collections: " + a.size());

                        if (a.size() == 0) {
                            if (note == null) note = count + ".php has been marked for review (0 Collections)";
                            else note = note.concat(" (0 Collections)");
                            System.err.println(note);
                            isActive = false;
                        }

//                for(Collection p : a){
//                    System.out.println(p.getTitle());
//                }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //Now that you have the info needed, write it all to a new html doc, and then to a new amphtml doc
                    partnerArrayList.add(new PartnerPage(count, isActive, note, partnerName, urlPartnerName, currentLink, articleText, image, ampImage, imageDes, browseLink));

                } else {
                    note = "Partner " + count + " has too few elements to be parsed.";
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
        for (PartnerPage a : partnerArrayList) {
            writeCSV(a);
            writeMapCSV(a);
        }
        csv.close();
        mapCSV.close();
    }

    public static void writeMapCSV(PartnerPage toWrite){
        if(toWrite.isActive() && !toWrite.getName().contains("- Partner") && toWrite.getName() != null) {
            mapCSV.append(toWrite.getName().replace("%comma%",","));
            mapCSV.append(",");
            mapCSV.append("http://test.mwdl.org/NewSite/partners/").append(toWrite.getUrlName()).append(".php");
            mapCSV.append("\n");
        }
    }

    public static void writeCSV(PartnerPage toWrite) {

        csv.append(String.valueOf(toWrite.getPartnerNumber())).append(",");
        csv.append(String.valueOf(toWrite.isActive())).append(",");

        if (toWrite.getNote() == null) {
            csv.append("").append(",");
        } else {
            csv.append(toWrite.getNote()).append(",");
        }

        try {
            csv.append(toWrite.getName().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getUrlName().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getLink().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getDescription().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getImage().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getAmpImage().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getImageDes().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",");

        try {
            csv.append(toWrite.getBrowseLink().replace("\n", "%newline%").replace("\r", "%return%").replace(",", "%comma%"));
        } catch (NullPointerException e) {
        }

        csv.append(",").append("\n");
    }
}
