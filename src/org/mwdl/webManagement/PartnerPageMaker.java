package org.mwdl.webManagement;

import org.mwdl.data.ProjectConstants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

import static java.util.Collections.sort;

/**
 * Writes Partner Pages
 *
 * @author Jonathan Wiggins
 * @version 5/9/18
 */

public class PartnerPageMaker {

    /**
     * Creates regular and AMP page files for all of the given Partner objects
     * Also generates the partner map page file
     *
     * @param toWrite An ArrayList of Partner objects to write
     *                Assumes each is already filled with the required information
     */
    public static void writeGivenPartnerPages(ArrayList<Partner> toWrite){

        for(Partner element : toWrite){
            writeAMPPartnerPage(element);
            writeNormalPartnerPage(element);
        }

        writePartnersMapPage();
        writePartnersListPage(toWrite);

    }

    /**
     * Given a Partner object holding the needed info to create a partner's landing page
     *  Creates the Page by writing the needed html/php to a file titled after the given
     *  Partner objects urlName in the partners folder in the cwd
     *
     * @param toWrite a Partner object to write
     *                Assumes the object already holds the requisite information
     */
    private static void writeNormalPartnerPage(Partner toWrite){

        String FileLocAndName = ProjectConstants.PartnerPageDirectory + toWrite.urlName + ".php";

        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");
            writer.println("<?php include(\"../includes/header.php\"); ?>");
            writer.println("<meta name=\"description\" content=\"" + toWrite.name+ ", a Partner of the MWDL\">");
            writer.println("<?php include(\"../includes/linkImports.php\");?>");
            writer.println("<script type=\"text/javascript\">\n" +
                    "\tif (screen.width <= 800) {\n" +
                    "  \t\twindow.location = \"../amppartners/"+ toWrite.urlName +".php\";\n" +
                    "\t}\n" +
                    "</script>");
            writer.println("<!-- Mobile Links -->");
            writer.println("<link rel=\"amphtml\" href=\"../amppartners/" + toWrite.urlName + ".php\">");
            writer.println("<link rel=\"alternate\" media=\"only screen and (max-width: 640px)\" href=\"../amppartner/" + toWrite.urlName + ".php\">");
            writer.println("<!-- Title -->");
            writer.println("<title> "+ toWrite.name +" </title>");
            writer.println("<?php include(\"../includes/partnermenuhead.php\");?>");
            writer.println("<div class=\"imageAndDes\">");
            writer.println("<!-- Image -->");
            if (toWrite.imageName != null)
                writer.println(
                        "<img src=\"../images/partner_images/" +
                                toWrite.imageName +
                                "\" alt=\"" + toWrite.imageDes + "\""+
                                "width=\"" + toWrite.imageWidth + "\" height=\"" + toWrite.imageHeight +"\""
                                + "align= \"right\" style=\"max-width: 250px; height: auto; margin: 1%; display: block; \">"
                );
            writer.println("<!-- Image Description -->");
            writer.println(toWrite.imageDes);
            writer.println("</div>");
            writer.println("<!-- Partner #" + toWrite.partnerNumber + " -->");
            writer.println("<!-- Partner Name -->");
            writer.println("<h3>"+toWrite.name+"</h3>");
            writer.println("<!-- Website Link -->");
            writer.println("<h6><a href=\"" + toWrite.link + "\">"+ toWrite.name+" Website </a></h6>");
            //writer.println("<p></p>");
            writer.println("<!-- Article Text -->");
            writer.println("<p>" + toWrite.article + "</p>");
            writer.println("<hr>");
            if (toWrite.browseLink != null)
                writer.println("<a href=\"" + toWrite.browseLink + "\">Browse all records from " + toWrite.name + "</a>");
            writer.println("<!-- List all Active Collections -->");

            ArrayList<Collection> collections = toWrite.activeCollections;
            for(Collection collection : collections)
                writer.println("<li><a href = \"../collections/"+collection.urlTitle + ".php\">" + collection.title.replace("%comma%","") +"</a></li>");

            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\"); ?>");

            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate regular page for partner " + toWrite.partnerNumber + ", " + toWrite.name);
        }

    }

    /**
     * Given a Partner object, writes the AMP Page
     *
     * @param toWrite a Partner to write
     *                Assumes that ParterPage hold all the needed information
     */
    private static void writeAMPPartnerPage(Partner toWrite){

        String FileLocAndName = ProjectConstants.AMPPartnerPageDirectory + toWrite.urlName + ".php";

        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");


            writer.println("<?php include(\"../includes/ampheader.php\");?>");
            writer.println("<meta name=\"description\" content=\"" + toWrite.name+ ", a Partner of the MWDL\">");
            writer.println("<!-- Partner #" + toWrite.partnerNumber + " -->");
            writer.println("<!-- Partner Name -->");
            writer.println("<title>" + toWrite.name + "</title>");
            writer.println("<!-- Desktop Version Link -->");
            writer.println("<link rel=\"canonical\" href=\"../partners/" + toWrite.urlName + ".php\">");
            writer.println("<?php include(\"../includes/amppstyle.php\");?>");
            writer.println(" ");
            writer.println("<!-- Partner Title -->");
            writer.println("<h3>" + toWrite.name + "</h3>");
            writer.println("<!-- Partner Website -->");
            writer.println("<h6><a href=\"" + toWrite.link + "\">"+ toWrite.name+" Website </a></h6>");
            writer.println("<!-- Partner Image -->");
            writer.println("<div class=amp-img-fill>");
            if (toWrite.imageName != null)
                writer.println("<amp-img src=\"../images/partner_images/" +
                        toWrite.imageName +
                        "\" alt=\"" + toWrite.imageDes +"\"" +
                        " width=\"" + toWrite.imageWidth + "\"" +
                        " height=\""+ toWrite.imageHeight +"\"" +
                        " layout = \"responsive\"></amp-img>"

                );
            writer.println("</div>");
            writer.println("<!-- Image Description -->");
            if (toWrite.imageDes != null)
                writer.println(toWrite.imageDes);
            writer.println("<!-- Article Text -->");
            if (toWrite.article != null)
                writer.println(toWrite.article);
            writer.println("<hr>");
            writer.println("<!-- Browse Collections -->");
            writer.println("<h6>");
            if (toWrite.browseLink != null)
                writer.println("<a href=\"" + toWrite.browseLink + "\">Browse all records from " + toWrite.name + "</a>");
            writer.println("</h6>");
            writer.println("<!-- List all Active Collections -->");

            ArrayList<Collection> collections = toWrite.activeCollections;
            for(Collection collection : collections)
                writer.println("<li><a href = \"../ampcollections/"+collection.urlTitle + ".php\">" + collection.urlTitle.replace("%comma%","") +"</a></li>");

            writer.println("<?php include(\"../includes/ampfooter.php\");?>");

            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate amp page for partner " + toWrite.partnerNumber + ", " + toWrite.name);

        }

    }

    /**
     * Write the partners map page
     * Note that this method writes the same page every time, the page does not change when the website
     *  is regenerated, however rewriting this page into the new partner folder means that you can simply
     *  overwrite the old folder without worrying about losing this page.
     */
    private static void writePartnersMapPage(){

        String FileLocAndName = ProjectConstants.PartnerPageDirectory + "partners.php";

        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<meta name=\"description\" content=\"A Map of MWDL Partners\">");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Partners</title>");
            writer.println("<?php include(\"../includes/plistmenuhead.php\");?>");
            writer.println("<h3>Mountain West Digital Library Partners</h3>");

            writer.println("<a href=\"partnerslist.php\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect\">View List</a>\n");
            writer.println("<hr>");

            //This is a static link to the custom google map, it is setup in the MWDL Google Drive
            //If you are looking for it, it is in /MWDL/Technical/Website/MWDL Partner Map
            // You cannot edit it here, you must open it through google.com/maps/d/ while logged into the MWDL
            // google account
            // I created it by using the GeneratePartnerMap class, and saving the output csv file onto the drive
            // to a file named Partner Map Data
            // You can then import the spreadsheet into the google map
            writer.println("<iframe src=\"https://www.google.com/maps/d/embed?mid=1XKbCNWPNF-r84SYoyhuiV2Y5s5Y\" height=\"700\" style=\"max-height:auto; max-width:auto;\"></iframe>");

            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\");?>");
            //close writer
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not generate partners map page");
        }

    }


    private static void writePartnersListPage(ArrayList<Partner> partnersToWrite){

        //Get an ArrayList of Partners in sorted order by name

        //Do this by getting an array of the names, sorting it, then adding the Partner objects
        // to a list in the order of the sorted names
        ArrayList<String> partnerNames = new ArrayList<>();

        for(Partner element : partnersToWrite){
            partnerNames.add(element.name);
        }

        sort(partnerNames);

        ArrayList<Partner> sorted = new ArrayList<>();

        for(String name : partnerNames){
            for(Partner element : partnersToWrite){
                if(element.name.equals(name))
                    sorted.add(element);
            }
        }

        String FileLocAndName = ProjectConstants.PartnerPageDirectory + "partnerslist.php";

        try {
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<meta name=\"description\" content=\"A List of the Partners of MWDL\">");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Partners</title>");
            writer.println("<?php include(\"../includes/plistmenuhead.php\");?>");
            writer.println("<h3>Mountain West Digital Library Partners</h3>");

            writer.println("<a href=\"partners.php\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect\">View Map</a>\n");
            writer.println("<hr>");

            writer.println("<table class=\"mdl-data-table mdl-js-data-table mdl-shadow--2dp\">");
            writer.println("<thread>");
            writer.println("<tr>");
            writer.println("<th class=\"mdl-data-table__cell--non-numeric\">Partner Name</th>");
            writer.println("</tr>");
            writer.println("</thread>");
            writer.println("<tbody>");

            for(Partner element : sorted){

                writer.println("<!-- Partner #" + element.partnerNumber + " -->");
                writer.println("<tr>");
                writer.println("<td class=\"mdl-data-table__cell--non-numeric\"> <a href = \"" + element.urlName + ".php\">" + element.name + "</a></td>");
                writer.println("</tr>");
            }

            writer.println("</tbody>");
            writer.println("</table>");

            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\");?>");
            //close writer
            writer.close();


        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("Could not generate partners map page");
        }


    }


}
