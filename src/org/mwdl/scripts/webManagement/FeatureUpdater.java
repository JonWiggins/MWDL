package org.mwdl.scripts.webManagement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mwdl.scripts.data.DataFetcher;

import java.io.IOException;


/**
 * This class gets the html for the homepage from the internet, splices out the card for Featured Collection and
 * Featured Partner, and rewrites the homepage to include a given partner and collection
 *
 * @author Jonathan Wiggins
 * @version 1/22/18
 */
public class FeatureUpdater {

    public static void main(String[] args) {
        String homePageURL = "http://test.mwdl.org/NewSite/index.php";

        int collectionToFeatureNumber = 1534;
        int partnerToFeatureNumber = 101;


        try {

            Document doc = Jsoup.connect(homePageURL).get();

            //select collection


            //select partner


            //new collection text
            Collection ctoInsert = DataFetcher.fetchCollection(collectionToFeatureNumber);

            String collectionDiv = "";

            collectionDiv.concat("<!-- Image (if any)-->");
            if (ctoInsert.getImg() != null) collectionDiv.concat(ctoInsert.getImg());
            collectionDiv.concat("<!-- Image Description -->");
            collectionDiv.concat(ctoInsert.getDes().replace("&amp;","&"));
            collectionDiv.concat("</div>");
            collectionDiv.concat("<!-- Collection Title-->");
            collectionDiv.concat("<h4>" + ctoInsert.getTitle() + "</h4>");
            collectionDiv.concat("<!-- Collection Publisher-->");
            collectionDiv.concat("<h6> Published by <a href=\"../partners/" + ctoInsert.getUrlPublisher() + ".php\">"+ ctoInsert.getPlainPublisher()+"</a></h6>");
            collectionDiv.concat(" ");
            collectionDiv.concat("<!-- Collection Description -->");
            if (ctoInsert.getText() != null) collectionDiv.concat(ctoInsert.getText());
            collectionDiv.concat("<hr>");
            collectionDiv.concat("<h6>");
            collectionDiv.concat("<!-- Browse Link -->");
            if (ctoInsert.getBrowse() != null) collectionDiv.concat(ctoInsert.getBrowse());
            collectionDiv.concat("</h6>");


            //new partner text
            PartnerPage ptoInsert = DataFetcher.fetchPartner(partnerToFeatureNumber);

            String partnerDiv = "";

            partnerDiv.concat("<!-- Image -->");
            partnerDiv.concat(ptoInsert.getImage());
            partnerDiv.concat("<!-- Image Description -->");
            partnerDiv.concat(ptoInsert.getImageDes());
            partnerDiv.concat("</div>");
            partnerDiv.concat("<!-- Partner #" + ptoInsert.getPartnerNumber() + " -->");
            partnerDiv.concat("<!-- Partner Name -->");
            partnerDiv.concat("<h3>"+ptoInsert.getName()+"</h3>");
            partnerDiv.concat("<!-- Website Link -->");
            partnerDiv.concat("<h6>"+ptoInsert.getLink()+"</h6>");
            partnerDiv.concat("<p></p>");
            partnerDiv.concat("<!-- Article Text -->");
            partnerDiv.concat("<p>" + ptoInsert.getDescription() + "</p>");



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
