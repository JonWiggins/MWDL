package org.mwdl.webManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Writes Partner Pages
 *
 * @author Jonathan Wiggins
 * @version 5/9/18
 */

public class PartnerPageMaker {

    /**
     * Creates regular and AMP page files for all of the given PartnerPage objects
     * Also generates the partner map page file
     *
     * @param toWrite An ArrayList of PartnerPage objects to write
     *                Assumes each is already filled with the required information
     */
    public static void writeGivenPartnerPages(ArrayList<PartnerPage> toWrite){
        writePartnersMapPage();

        for(PartnerPage element : toWrite){
            writeAMPPartnerPage(element);
            writeNormalPartnerPage(element);
        }
    }

    /**
     * Given a PartnerPage object holding the needed info to create a partner's landing page
     *  Creates the Page by writing the needed html/php to a file titled after the given
     *  PartnerPage objects urlName in the partners folder in the cwd
     *
     * @param toWrite a PartnerPage object to write
     *                Assumes the object already holds the requisite information
     */
    private static void writeNormalPartnerPage(PartnerPage toWrite){
        String FileLocAndName = "partners/"+toWrite.urlName + ".php";
        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");
            writer.println("<?php include(\"../includes/header.php\"); ?>");
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
            if (toWrite.image != null)
                writer.println(
                        "<img src=\"../images/partner_images/" +
                                toWrite.image +
                                "\" alt=\"" + toWrite.imageDes + "\""+
                                "width=\"" + toWrite.imageW + "\" height=\"" + toWrite.imageH +"\""
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
            writer.println("<p></p>");
            writer.println("<!-- Article Text -->");
            writer.println("<p>" + toWrite.description + "</p>");
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
     * Given a PartnerPage object, writes the AMP Page
     *
     * @param toWrite a PartnerPage to write
     *                Assumes that ParterPage hold all the needed information
     */
    private static void writeAMPPartnerPage(PartnerPage toWrite){
        String FileLocAndName = "amppartners/"+toWrite.urlName + ".php";
        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");


            writer.println("<?php include(\"../includes/ampheader.php\");?>");
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
            if (toWrite.image != null)
                writer.println("<amp-img src=\"../images/partner_images/" +
                        toWrite.image +
                        " alt=\"" + toWrite.imageDes +"\"" +
                        " width=\"" + toWrite.imageW + "\"" +
                        " height=\""+ toWrite.imageH +"\"" +
                        " layout = \"responsive\"></amp-img>"

                );
            writer.println("</div>");
            writer.println("<!-- Image Description -->");
            if (toWrite.imageDes != null)
                writer.println(toWrite.imageDes);
            writer.println("<!-- Article Text -->");
            if (toWrite.description != null)
                writer.println(toWrite.description);
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
        String FileLocAndName = "partners/partners.php";
        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Partners</title>");
            writer.println("<?php include(\"../includes/plistmenuhead.php\");?>");
            writer.println("<h3>Mountain West Digital Library Partners</h3>");

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


}
