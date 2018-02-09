package org.mwdl.scripts.webManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Jonathan Wiggins
 * @version 7/3/17
 */

public class PartnerPageMaker {

    public static void writeFullPartner(PartnerPage toWrite){
        String FileLocAndName = "partners/"+toWrite.getUrlName() + ".php";
        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");
            writer.println("<?php include(\"../includes/header.php\"); ?>");
            writer.println("<?php include(\"../includes/linkImports.php\");?>");
            writer.println("<script type=\"text/javascript\">\n" +
                    "\tif (screen.width <= 800) {\n" +
                    "  \t\twindow.location = \"../amppartners/"+ toWrite.getUrlName() +".php\";\n" +
                    "\t}\n" +
                    "</script>");
            writer.println("<!-- Mobile Links -->");
            writer.println("<link rel=\"amphtml\" href=\"../amppartners/" + toWrite.getUrlName() + ".php\">");
            writer.println("<link rel=\"alternate\" media=\"only screen and (max-width: 640px)\" href=\"../amppartner/" + toWrite.getUrlName() + ".php\">");
            writer.println("<!-- Title -->");
            writer.println("<title> "+ toWrite.getName() +" </title>");
            writer.println("<?php include(\"../includes/partnermenuhead.php\");?>");
            writer.println("<div class=\"imageAndDes\">");
            writer.println("<!-- Image -->");
            writer.println(toWrite.getImage());
            writer.println("<!-- Image Description -->");
            writer.println(toWrite.getImageDes());
            writer.println("</div>");
            writer.println("<!-- Partner #" + toWrite.getPartnerNumber() + " -->");
            writer.println("<!-- Partner Name -->");
            writer.println("<h3>"+toWrite.getName()+"</h3>");
            writer.println("<!-- Website Link -->");
            writer.println("<h6>"+toWrite.getLink()+"</h6>");
            writer.println("<p></p>");
            writer.println("<!-- Article Text -->");
            writer.println("<p>" + toWrite.getDescription() + "</p>");
            writer.println("<hr>");
            writer.println("<h6>" + toWrite.getBrowseLink() + "</h6>");
            writer.println("<!-- List all Active Collections -->");

            ArrayList<Collection> collections = toWrite.getCollections();
            for(Collection collection : collections)
                writer.println("<li><a href = \"../collections/"+collection.urlTitle + ".php\">" + collection.urlTitle.replace("%comma%","") +"</a></li>");

            writer.println("</div>");
            writer.println("</div>");
            writer.println("<?php include(\"../includes/footer.php\"); ?>");

            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate regular page for collection number" + toWrite.getPartnerNumber() + ", "+toWrite.getName());
        }
    }

    public static void writeAMPPartner(PartnerPage toWrite){
        String FileLocAndName = "amppartners/"+toWrite.getUrlName() + ".php";
        try{
            PrintWriter writer = new PrintWriter(FileLocAndName, "UTF-8");


            writer.println("<?php include(\"../includes/ampheader.php\");?>");
            writer.println("<!-- Partner #" + toWrite.getPartnerNumber() + " -->");
            writer.println("<!-- Partner Name -->");
            writer.println("<title>" + toWrite.getName() + "</title>");
            writer.println("<!-- Desktop Version Link -->");
            writer.println("<link rel=\"canonical\" href=\"../partners/" + toWrite.getUrlName() + ".php\">");
            writer.println("<?php include(\"../includes/amppstyle.php\");?>");
            writer.println(" ");
            writer.println("<!-- Partner Title -->");
            writer.println("<h3>" + toWrite.getName() + "</h3>");
            writer.println("<!-- Partner Website -->");
            writer.println("<h6>" + toWrite.getLink() + "</h6>");
            writer.println("<!-- Partner Image -->");
            writer.println("<div class=amp-img-fill>");
            if (toWrite.getAmpImage() != null) writer.println(toWrite.getAmpImage());
            writer.println("</div>");
            writer.println("<!-- Image Description -->");
            if (toWrite.getImageDes() != null) writer.println(toWrite.getImageDes());
            writer.println("<!-- Article Text -->");
            if (toWrite.getDescription() != null) writer.println(toWrite.getDescription());
            writer.println("<hr>");
            writer.println("<!-- Browse Collections -->");
            writer.println("<h6>");
            if (toWrite.getBrowseLink() != null) writer.println(toWrite.getBrowseLink());
            writer.println("</h6>");
            writer.println("<!-- List all Active Collections -->");

            ArrayList<Collection> collections = toWrite.getCollections();
            for(Collection collection : collections)
                writer.println("<li><a href = \"../ampcollections/"+collection.urlTitle + ".php\">" + collection.urlTitle.replace("%comma%","") +"</a></li>");

            writer.println("<?php include(\"../includes/ampfooter.php\");?>");

            //close writer
            writer.close();
        } catch (IOException e) {
            // do something
            System.err.println("Could not generate amp page for partner number" + toWrite.getPartnerNumber() + ", " + toWrite.getName());

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
