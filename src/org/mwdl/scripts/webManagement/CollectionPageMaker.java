package org.mwdl.scripts.webManagement;


import org.mwdl.scripts.data.DataFetcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.sort;

/**
 * Given a collection object, writes a collection landing page
 *
 * @author Jonathan Wiggins
 * @version 7/13/17
 */

public class CollectionPageMaker {

    public static void writeFullCollection(Collection toWrite) {
        String FileLocAndName = "collections/" + toWrite.getUrlTitle() + ".php";
        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<script type=\"text/javascript\">\n" +
                    "\tif (screen.width <= 800) {\n" +
                    "  \t\twindow.location = \"../ampcollections/"+ toWrite.getUrlTitle() +".php\";\n" +
                    "\t}\n" +
                    "</script>");
            writer.println("<!-- Mobile Links -->");
            writer.println("<link rel=\"amphtml\" href=\"../ampcollections/" + toWrite.getUrlTitle() + ".php\">");
            writer.println("<link rel=\"alternate\" media=\"only screen and (max-width: 640px)\" href=\"../ampcollections/" + toWrite.getUrlTitle() + ".php\">");
            writer.println("<!-- Collection #" + toWrite.getCollectionNumber() + " -->");
            writer.println("<!-- Collection Title -->");
            writer.println("<title>" + toWrite.getTitle() + "</title>");

//            writer.println("<!-- Schema MetaData Script -->");
//            writer.println("<script type=\"application/ld+json\">{");
//            writer.println("\"@context\": \"http://schema.org\",");
//            writer.println("\"@type\": \""+toWrite.getMetaAtt1()+"\",");
//            writer.println("\"mainEntityOfPage\": \"mwdl.org/collections/"+ toWrite.getUrlTitle()+".php\",");
//            writer.println("\"headline\": \""+toWrite.getTitle()+"\",");
//            writer.println("} </script>");

            writer.println("<?php include(\"../includes/collectionmenuhead.php\");?>");
            writer.println(" ");
            writer.println("<!-- Share Links -->");
            writer.println("<li class=\"mdl-menu__item\"><a class=\"mdl-navigation__link\"\n" +
                    "                                  href=\"http://www.facebook.com/sharer/sharer.php?u=mwdl.org/collections/" + toWrite.getUrlTitle() + ".php&t=" + toWrite.getTitle() + "\"\n" +
                    "                                  target=\"_blank\"><img src=\"../images/facebook.png\" alt=\"facebook\"\n" +
                    "                                                       height=\"20\" width=\"20\"> Facebook</a></li>");
            writer.println("<li class=\"mdl-menu__item\"><a class=\"mdl-navigation__link\"\n" +
                    "                                  href=\"http://www.twitter.com/intent/tweet?url=mwdl.org/collections/" + toWrite.getUrlTitle() + ".php&via=@MountainWestDL&text=" + toWrite.getTitle() + "\"\n" +
                    "                                  target=\"_blank\"><img src=\"../images/twitter.png\" alt=\"twitter\"\n" +
                    "                                                       height=\"20\" width=\"20\"> Twitter</a></li>");
            writer.println("<li class=\"mdl-menu__item\"><a class=\"mdl-navigation__link\"\n" +
                    "                                  href=\"http://www.tumblr.com/share/link?url=mwdl.org/collections/" + toWrite.getUrlTitle() + ".php\"\n" +
                    "                                  target=\"_blank\"><img src=\"../images/tumblr.png\"\n" +
                    "                                                       alt=\"tumblr\" height=\"20\" width=\"20\"> Tumblr</a></li>");
            writer.println("</ul>");
            writer.println("</div>");

            writer.println("<div class=\"imageAndDes\">");
            writer.println("<!-- Image (if any)-->");
            if (toWrite.getImg() != null) writer.println(toWrite.getImg());
            writer.println("<!-- Image Description -->");
            writer.println(toWrite.getDes().replace("&amp;","&"));
            writer.println("</div>");
            writer.println("<!-- Collection Title-->");
            writer.println("<h4>" + toWrite.getTitle() + "</h4>");
            writer.println("<!-- Collection Publisher-->");
            writer.println("<h6> Published by <a href=\"../partners/" + toWrite.getUrlPublisher() + ".php\">"+ toWrite.getPlainPublisher()+"</a></h6>");
            writer.println(" ");
            writer.println("<!-- Collection Description -->");
            if (toWrite.getText() != null) writer.println(toWrite.getText());
            writer.println("<hr>");
            writer.println("<h6>");
            writer.println("<!-- Browse Link -->");
            if (toWrite.getBrowse() != null) writer.println(toWrite.getBrowse());
            writer.println("</h6>");
            writer.println("<p></p>");
            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\"); ?>");

            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate regular page for collection number" + toWrite.getCollectionNumber() + ", " + toWrite.getTitle());
        }
    }

    public static void writeAMPCollection(Collection toWrite) {
        String FileLocAndName = "ampcollections/" + toWrite.getUrlTitle() + ".php";
        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include(\"../includes/ampheader.php\");?>");
            writer.println("<!-- Collection #" + toWrite.getCollectionNumber() + " -->");
            writer.println("<!-- Collection Title -->");
            writer.println("<title>" + toWrite.getTitle() + "</title>");
            writer.println("<!-- Desktop Version Link -->");
            writer.println("<link rel=\"canonical\" href=\"../collections/" + toWrite.getUrlTitle() + ".php\">");

//            writer.println("<!-- Schema MetaData Script -->");
//            writer.println("<script type=\"application/ld+json\">{");
//            writer.println("\"@context\": \"http://schema.org\",");
//            writer.println("\"@type\": \""+toWrite.getMetaAtt1()+"\",");
//            writer.println("\"mainEntityOfPage\": \"mwdl.org/collections/"+ toWrite.getUrlTitle()+".php\",");
//            writer.println("\"headline\": \""+toWrite.getTitle()+"\",");
//            writer.println("} </script>");

            writer.println("<?php include(\"../includes/ampstyle.php\");?>");
            writer.println(" ");
            writer.println("<!-- Collection Title -->");
            writer.println("<h3>" + toWrite.getTitle() + "</h3>");
            writer.println("<!-- Collection Publisher -->");
            writer.println("<h6> Published by <a href=\"../partners/" + toWrite.getUrlPublisher() + ".php\">"+ toWrite.getPlainPublisher()+"</a></h6>");
            writer.println("<!-- Collection Image -->");
            writer.println("<div class=amp-img-fill>");
            if (toWrite.getAmpImg() != null) writer.println(toWrite.getAmpImg());
            writer.println("</div>");
            writer.println("<!-- Image Description -->");
            if (toWrite.getDes() != null) writer.println(toWrite.getDes());
            writer.println("<!-- Article Text -->");
            if (toWrite.getText() != null) writer.println(toWrite.getText());
            writer.println("<hr>");
            writer.println("<!-- Browse Collection -->");
            writer.println("<h6>");
            if (toWrite.getBrowse() != null) writer.println(toWrite.getBrowse());
            writer.println("</h6>");
            writer.println("<?php include(\"../includes/ampfooter.php\");?>");

            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate amp page for collection number" + toWrite.getCollectionNumber() + ", " + toWrite.getTitle());

        }
    }

    public static void writeCollectionsPage(){
        writeAlphaCPage();
        writeInitPage();
        writePCPage();
    }

    public static void writeInitPage() {
        String FileLocAndName = "collections/collections.php";
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

            ArrayList<Collection> collectionArrayList = DataFetcher.getAllActiveCollections();

            for (Collection c : collectionArrayList) {
                String urlTitle = c.getUrlTitle();
                String title = ellipsize(c.getTitle().replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ","), 55);
                String publisherName = c.getPlainPublisher().replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ",").replace("Published by ", "");
                String urlPub= c.getUrlPublisher();
                writer.println("<!-- Collection #"+ c.getCollectionNumber()+" -->");

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
            // do something
            System.err.println("Could not generate collections list page");
        }
    }


    public static void writeAlphaCPage(){
        String FileLocAndName = "collections/aCollections.php";
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
            ArrayList<Collection> collectionArrayList = DataFetcher.getAllActiveCollections();
            HashMap<String, Collection> nameToCollection = new HashMap<>();

            ArrayList<String> collectionNames = new ArrayList<>();

            for (Collection c : collectionArrayList) {
                nameToCollection.put(c.getTitle(), c);
                collectionNames.add(c.getTitle());
            }
            sort(collectionNames);

            ArrayList<Collection> sorted = new ArrayList<>();

            for(String s : collectionNames){
                sorted.add(nameToCollection.get(s));
            }

            for (Collection c : sorted) {
                String urlTitle = c.getUrlTitle();
                String title = ellipsize(c.getTitle().replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ","), 55);
                String publisherName = c.getPlainPublisher().replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ",").replace("Published by ", "");
                String urlPub= c.getUrlPublisher();
                writer.println("<!-- Collection #"+ c.getCollectionNumber()+" -->");
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
            // do something
            System.err.println("Could not generate collections list page");
        }
    }

    public static void writePCPage(){
        String FileLocAndName = "collections/pCollections.php";
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

            ArrayList<Collection> collectionArrayList = DataFetcher.getAllActiveCollections();
            ArrayList<String> partnerNames = new ArrayList<>();

            for(Collection c: collectionArrayList){
                partnerNames.add(c.getPlainPublisher());
            }
            sort(partnerNames);

            ArrayList<Collection> sorted = new ArrayList<>();

            for(String n : partnerNames){
                for(Collection c : collectionArrayList){
                    if(c.getPlainPublisher().equals(n)){
                        sorted.add(c);
                        collectionArrayList.remove(c);
                        break;
                    }
                }
            }

            for (Collection c : sorted) {
                String urlTitle = c.getUrlTitle();
                String title = ellipsize(c.getTitle().replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ","), 55);
                String publisherName = c.getPlainPublisher().replace("%newline%", "\n").replace("%return%", "/r").replace("%comma%", ",").replace("Published by ", "");
                String urlPub= c.getUrlPublisher();
                writer.println("<!-- Collection #"+ c.getCollectionNumber()+" -->");
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
            // do something
            System.err.println("Could not generate collections list page");
        }
    }

    public static String ellipsize(String input, int maxLength) {
        String ellip = "...";
        if (input == null || input.length() <= maxLength
                || input.length() < ellip.length()) {
            return input;
        }
        return input.substring(0, maxLength - ellip.length()).concat(ellip);
    }




    public static void thing() throws FileNotFoundException {
        //prepare and sort the array
        ArrayList<Collection> collectionArrayList = DataFetcher.getAllActiveCollections();
        HashMap<String, Collection> nameToCollection = new HashMap<>();

        ArrayList<String> collectionNames = new ArrayList<>();

        for (Collection c : collectionArrayList) {
            nameToCollection.put(c.getTitle(), c);
            collectionNames.add(c.getTitle());
        }
        sort(collectionNames);

        for (String n : collectionNames) {
            System.out.println(n);
        }
    }

    public static void thiner() throws FileNotFoundException {
        //prepare and sort the array
        ArrayList<Collection> collectionArrayList = DataFetcher.getAllActiveCollections();
        HashMap<String, Collection> partnerToCollection = new HashMap<>();

        ArrayList<String> partnerNames = new ArrayList<>();

        for (Collection c : collectionArrayList) {
            partnerToCollection.put(c.getTitle(), c);
            partnerNames.add(c.getTitle());
        }
        sort(partnerNames);

        for (String n : partnerNames) {
            System.out.println(n);
        }
    }
}