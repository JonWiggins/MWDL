package org.mwdl.webManagement;


import org.mwdl.data.ProjectConstants;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.sort;

/**
 * Writes Collection Pages
 *
 * @author Jonathan Wiggins
 * @version 7/13/17
 */

public class CollectionPageMaker {

    /**
     * Given an ArrayList of Collections, writes the Collection List pages
     *  As well was full and AMP pages for each individual collections
     *
     * @param toWrite an ArrayList of Collections to write
     *                Assumes that each Collection is already filled with the required information
     */
    public static void writeGivenCollectionPages(ArrayList<Collection> toWrite){

        writeAlphabeticalCollectionPage(toWrite);
        writeInitialCollectionPage(toWrite);
        writePartnerSortedCollectionPage(toWrite);

        for (Collection element : toWrite){
            writeFullCollection(element);
            writeAMPCollection(element);
        }

    }


    /**
     * Writes a the given collection page
     *
     * @param toWrite a Collection to write
     */
    private static void writeFullCollection(Collection toWrite) {

        String FileLocAndName = ProjectConstants.CollectionPageDirectory + toWrite.urlTitle + ".php";

        try {

            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<script type=\"text/javascript\">\n" +
                    "\tif (screen.width <= 800) {\n" +
                    "  \t\twindow.location = \"../ampcollections/"+ toWrite.urlTitle +".php\";\n" +
                    "\t}\n" +
                    "</script>");
            writer.println("<!-- Mobile Links -->");
            writer.println("<link rel=\"amphtml\" href=\"../ampcollections/" + toWrite.urlTitle + ".php\">");
            writer.println("<link rel=\"alternate\" media=\"only screen and (max-width: 640px)\" href=\"../ampcollections/" + toWrite.urlTitle + ".php\">");
            writer.println("<!-- Collection #" + toWrite.collectionNumber + " -->");
            writer.println("<!-- Collection Title -->");
            writer.println("<title>" + toWrite.title + "</title>");

            writer.println("<?php include(\"../includes/collectionmenuhead.php\");?>");
            writer.println(" ");
            writer.println("<!-- Share Links -->");
            writer.println("<li class=\"mdl-menu__item\"><a class=\"mdl-navigation__link\"\n" +
                    "                                  href=\"http://www.facebook.com/sharer/sharer.php?u=mwdl.org/collections/" + toWrite.urlTitle + ".php&t=" + toWrite.title + "\"\n" +
                    "                                  target=\"_blank\"><imageName src=\"../images/facebook.png\" alt=\"facebook\"\n" +
                    "                                                       height=\"20\" width=\"20\"> Facebook</a></li>");
            writer.println("<li class=\"mdl-menu__item\"><a class=\"mdl-navigation__link\"\n" +
                    "                                  href=\"http://www.twitter.com/intent/tweet?url=mwdl.org/collections/" + toWrite.urlTitle + ".php&via=@MountainWestDL&text=" + toWrite.title + "\"\n" +
                    "                                  target=\"_blank\"><imageName src=\"../images/twitter.png\" alt=\"twitter\"\n" +
                    "                                                       height=\"20\" width=\"20\"> Twitter</a></li>");
            writer.println("<li class=\"mdl-menu__item\"><a class=\"mdl-navigation__link\"\n" +
                    "                                  href=\"http://www.tumblr.com/share/link?url=mwdl.org/collections/" + toWrite.urlTitle + ".php\"\n" +
                    "                                  target=\"_blank\"><imageName src=\"../images/tumblr.png\"\n" +
                    "                                                       alt=\"tumblr\" height=\"20\" width=\"20\"> Tumblr</a></li>");
            writer.println("</ul>");
            writer.println("</div>");

            writer.println("<div class=\"imageAndDes\">");
            writer.println("<!-- Image (if any)-->");
            if (toWrite.imageName != null)
                writer.println(
                        "<imageName src=\"../images/collection_images/" +
                                toWrite.imageName +
                        "\" alt=\"" + toWrite.des + "\""+
                        "width=\"" + toWrite.imageWidth + "\" height=\"" + toWrite.imageHeight +"\""
                         + "align= \"right\" style=\"max-width: 250px; height: auto; margin: 1%; display: block; \">"
                );

            writer.println("<!-- Image Description -->");
            writer.println(toWrite.des.replace("&amp;","&"));
            writer.println("</div>");
            writer.println("<!-- Collection Title-->");
            writer.println("<h4>" + toWrite.title + "</h4>");
            writer.println("<!-- Collection Publisher-->");
            writer.println("<h6> Published by <a href=\"../partners/" + toWrite.refinedPublisher + ".php\">"+ toWrite.publisher+"</a></h6>");
            writer.println(" ");
            writer.println("<!-- Collection Description -->");
            if (toWrite.text != null) writer.println(toWrite.text);
            writer.println("<hr>");
            writer.println("<h6>");
            writer.println("<!-- Browse Link -->");
            if (toWrite.getExlibirisLink() != null)
                writer.println("<a href=\""+toWrite.getExlibirisLink()+"\">Browse all record in " + toWrite.title+"</a>");
            writer.println("</h6>");
            writer.println("<p></p>");
            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\"); ?>");

            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate regular page for collection number " + toWrite.collectionNumber + ", " + toWrite.title);
            e.printStackTrace();
        }

    }

    /**
     * Writes the given collection as an AMP page
     *
     * @param toWrite a Collection to write
     */
    private static void writeAMPCollection(Collection toWrite) {

        String FileLocAndName = ProjectConstants.AMPCollectionPageDirectory + toWrite.urlTitle + ".php";

        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include(\"../includes/ampheader.php\");?>");
            writer.println("<!-- Collection #" + toWrite.collectionNumber + " -->");
            writer.println("<!-- Collection Title -->");
            writer.println("<title>" + toWrite.title + "</title>");
            writer.println("<!-- Desktop Version Link -->");
            writer.println("<link rel=\"canonical\" href=\"../collections/" + toWrite.urlTitle + ".php\">");
            writer.println("<?php include(\"../includes/ampstyle.php\");?>");
            writer.println(" ");
            writer.println("<!-- Collection Title -->");
            writer.println("<h3>" + toWrite.title + "</h3>");
            writer.println("<!-- Collection Publisher -->");
            writer.println("<h6> Published by <a href=\"../partners/" + toWrite.refinedPublisher + ".php\">"+ toWrite.publisher +"</a></h6>");
            writer.println("<!-- Collection Image -->");
            writer.println("<div class=amp-imageName-fill>");
            if (toWrite.imageName != null)
                writer.println(
                        "<amp-imageName src=\"../images/collection_images/" +
                        "alt=\"" + toWrite.des +"\"" +
                        "width=\"" + toWrite.imageWidth + "\" height =\"" + toWrite.imageHeight +"\" " +
                        " layout = \"responsive\"></amp-imageName>"
                );
            writer.println("</div>");
            writer.println("<!-- Image Description -->");
            if (toWrite.des != null) writer.println(toWrite.des);
            writer.println("<!-- Article Text -->");
            if (toWrite.text != null) writer.println(toWrite.text);
            writer.println("<hr>");
            writer.println("<!-- Browse Collection -->");
            writer.println("<h6>");
            if (toWrite.getExlibirisLink() != null)
                writer.println("<a href=\"" + toWrite.getExlibirisLink() + "\">Browse all record in " + toWrite.title+"</a>");
            writer.println("</h6>");
            writer.println("<?php include(\"../includes/ampfooter.php\");?>");

            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate amp page for collection number " + toWrite.collectionNumber + ", " + toWrite.title);
            e.printStackTrace();

        }

    }

    /**
     * Write the initial collections page, wherein the collections are sorted in the order they are given in
     *   the collection data csv file
     * Saves the files as collections.php
     *
     * @param collections an ArrayList of all the active collections
     */
    private static void writeInitialCollectionPage(ArrayList<Collection> collections) {

        String FileLocAndName = ProjectConstants.CollectionPageDirectory + "collections.php";

        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Collections</title>");
            writer.println("<?php include(\"../includes/clistmenuhead.php\");?>");
            writer.println("<h3>Collections in the Mountain West Digital Library</h3>");
            writer.println("<a href=\"aCollections.php\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect\">Sort Alphabetically</a>\n");
            writer.println("&nbsp;");
            writer.println("<a href=\"pCollections.php\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect\"> Sort by Partner</a>\n");
            writer.println("<hr>");
            writer.println("<table class=\"mdl-data-table mdl-js-data-table mdl-shadow--2dp\">");
            writer.println("<thread>");
            writer.println("<tr>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Collection Title</th>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Partner</th>");
            writer.println("</tr>");
            writer.println("</thread>");
            writer.println("<tbody>");

            for (Collection c : collections) {
                String urlTitle = c.urlTitle;
                String title = ellipsize(c.title.replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ","), 55);
                String publisherName = c.publisher.replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ",").replace("Published by ", "");
                String urlPub= c.refinedPublisher;
                writer.println("<!-- Collection #"+ c.collectionNumber+" -->");

                writer.println("<tr>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"" + urlTitle + ".php\">" + title + "</a></td>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"../partners/" + urlPub + ".php\">"+ publisherName +"</a></td>");
                writer.println("</tr>");
            }
            writer.println("</tbody>");
            writer.println("</table>");
            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\");?>");


            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate collections list page");
        }

    }

    /**
     * Write the Collections page that sorts the collections by their names Alphabetically
     * Saves the file to aCollections.php
     *
     * @param collections An ArrayList of all the active collections
     */
    private static void writeAlphabeticalCollectionPage(ArrayList<Collection> collections){

        String FileLocAndName =  ProjectConstants.CollectionPageDirectory + "aCollections.php";

        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Collections</title>");
            writer.println("<?php include(\"../includes/clistmenuhead.php\");?>");
            writer.println("<h3>Collections in the Mountain West Digital Library</h3>");

            writer.println("<a href=\"#\" class=\"mdl-button mdl-js-button mdl-button--raised \">Sort Alphabetically</a>\n");
            writer.println("&nbsp;");
            writer.println("<a href=\"pCollections.php\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect\"> Sort by Partner</a>\n");
            writer.println("<hr>");

            writer.println("<table class=\"mdl-data-table mdl-js-data-table mdl-shadow--2dp\">");
            writer.println("<thread>");
            writer.println("<tr>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Collection Title</th>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Partner</th>");
            writer.println("</tr>");
            writer.println("</thread>");
            writer.println("<tbody>");

            //prepare and sort the array
            HashMap<String, Collection> nameToCollection = new HashMap<>();

            ArrayList<String> collectionNames = new ArrayList<>();

            for (Collection c : collections) {
                nameToCollection.put(c.title, c);
                collectionNames.add(c.title);
            }

            sort(collectionNames);

            ArrayList<Collection> sorted = new ArrayList<>();

            for(String s : collectionNames){
                sorted.add(nameToCollection.get(s));
            }

            for (Collection c : sorted) {
                String urlTitle = c.urlTitle;
                String title = ellipsize(c.title.replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ","), 55);
                String publisherName = c.publisher.replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ",").replace("Published by ", "");
                String urlPub= c.refinedPublisher;
                writer.println("<!-- Collection #"+ c.collectionNumber+" -->");
                writer.println("<tr>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"" + urlTitle + ".php\">" + title + "</a></td>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"../partners/" + urlPub+".php\">"+ publisherName +"</a></td>");
                writer.println("</tr>");
            }
            writer.println("</tbody>");
            writer.println("</table>");
            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\");?>");


            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate collections list page");
        }

    }

    /**
     * Writes the Collections page wherein the collections are sorted by their Partner
     * Saves the files as pCollections.php
     *
     * @param collections An ArrayList of all the active collections
     */
    private static void writePartnerSortedCollectionPage(ArrayList<Collection> collections){

        String FileLocAndName =  ProjectConstants.CollectionPageDirectory + "pCollections.php";

        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Collections</title>");
            writer.println("<?php include(\"../includes/clistmenuhead.php\");?>");
            writer.println("<h3>Collections in the Mountain West Digital Library</h3>");


            writer.println("<a href=\"aCollections.php\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect \">Sort Alphabetically</a>\n");
            writer.println("&nbsp;");
            writer.println("<a href=\"#\" class=\"mdl-button mdl-js-button mdl-button--raised\">Sort by Partner</a>\n");
            writer.println("<hr>");

            writer.println("<table class=\"mdl-data-table mdl-js-data-table mdl-shadow--2dp\">");
            writer.println("<thread>");
            writer.println("<tr>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Collection Title</th>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Partner</th>");
            writer.println("</tr>");
            writer.println("</thread>");
            writer.println("<tbody>");

            ArrayList<String> partnerNames = new ArrayList<>();

            for(Collection c: collections){
                partnerNames.add(c.publisher);
            }
            sort(partnerNames);

            ArrayList<Collection> sorted = new ArrayList<>();

            for(String n : partnerNames){
                for(Collection c : collections){
                    if(c.publisher.equals(n)){
                        sorted.add(c);
                        collections.remove(c);
                        break;
                    }
                }
            }

            for (Collection c : sorted) {
                String urlTitle = c.urlTitle;
                String title = ellipsize(c.title.replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ","), 55);
                String publisherName = c.publisher.replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ",").replace("Published by ", "");
                String urlPub= c.refinedPublisher;
                writer.println("<!-- Collection #"+ c.collectionNumber+" -->");
                writer.println("<tr>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"" + urlTitle + ".php\">" + title + "</a></td>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"../partners/" + urlPub+".php\">"+ publisherName +"</a></td>");
                writer.println("</tr>");
            }
            writer.println("</tbody>");
            writer.println("</table>");
            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\");?>");


            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate collections list page");
        }

    }

    /**
     * Ellipsize a string after a certain length
     *
     * @param input a String to ellipsize
     * @param maxLength the maximum length of the string
     * @return the String ellipsized to maxLength
     */
    private static String ellipsize(String input, int maxLength) {

        String ellip = "...";
        if (input == null || input.length() <= maxLength
                || input.length() < ellip.length()) {
            return input;
        }
        return input.substring(0, maxLength - ellip.length()).concat(ellip);

    }

}
