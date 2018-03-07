package org.mwdl.scripts.webManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Write the given partner's webpages
 *
 * @author Jonathan Wiggins
 * @version 7/3/17
 */

public class PartnerPageMaker {

    public static void writeFullPartner(PartnerPage toWrite){
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
            writer.println("<h6>"+toWrite.link+"</h6>");
            writer.println("<p></p>");
            writer.println("<!-- Article Text -->");
            writer.println("<p>" + toWrite.description + "</p>");
            writer.println("<hr>");
            if (toWrite.browseLink != null)
                writer.println("<a href=\"" + toWrite.browseLink + "\">Browse all records from " + toWrite.name + "</a>");
            writer.println("<!-- List all Active Collections -->");

            ArrayList<Collection> collections = toWrite.activeCollections;
            for(Collection collection : collections)
                writer.println("<li><a href = \"../collections/"+collection.urlTitle + ".php\">" + collection.urlTitle.replace("%comma%","") +"</a></li>");

            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\"); ?>");

            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate regular page for partner " + toWrite.name);
        }
    }

    public static void writeAMPPartner(PartnerPage toWrite){
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
            writer.println("<h6>" + toWrite.link + "</h6>");
            writer.println("<!-- Partner Image -->");
            writer.println("<div class=amp-img-fill>");
            //<amp-img src="../images/partner_images/partner140.jpg" alt="American West Center" width="392" height="53" layout = "responsive"></amp-img>
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
                writer.println(toWrite.browseLink);
            writer.println("</h6>");
            writer.println("<!-- List all Active Collections -->");

            ArrayList<Collection> collections = toWrite.activeCollections;
            for(Collection collection : collections)
                writer.println("<li><a href = \"../ampcollections/"+collection.urlTitle + ".php\">" + collection.urlTitle.replace("%comma%","") +"</a></li>");

            writer.println("<?php include(\"../includes/ampfooter.php\");?>");

            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate amp page for partner number " + toWrite.partnerNumber + ", " + toWrite.name);

        }
    }

    public static void writePartnersPage(){
        String FileLocAndName = "partners/partners.php";
        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");

            writer.println("<?php include (\"../includes/header.php\");?>");
            writer.println("<?php include (\"../includes/linkImports.php\");?>");
            writer.println("<title>Partners</title>");
            writer.println("<?php include(\"../includes/plistmenuhead.php\");?>");
            writer.println("<h3>Mountain West Digital Library Partners</h3>");
            writer.println("<iframe src=\"https://www.google.com/maps/d/embed?mid=1XKbCNWPNF-r84SYoyhuiV2Y5s5Y\" height=\"700\" style=\"max-height:auto; max-width:auto;\"></iframe>");
            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\");?>");
            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate partners list page");
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

}
