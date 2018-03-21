package org.mwdl.scripts.webManagement;
import org.mwdl.scripts.data.DataFetcher;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Generates a csv file that can be uploaded to Google Drive for the interactive custom map of our partners
 *
 * @author Jonathan Wiggins
 * @version 9/15/17
 */

public class GeneratePartnerMap {

    //TODO if you are making this version of the map for the testing website, set this to false
    // otherwise set it to true
    private static boolean isForLiveSite = true;


    public static void main(String[] args) {
        try {
            ArrayList<PartnerPage> partners = DataFetcher.getAllActivePartners();

            String FileLocAndName = "partnerMapData.csv";
            PrintWriter csv = new PrintWriter(FileLocAndName,"UTF-8");
            csv.append("Partner Name, Partner Link \n");

            for(PartnerPage current : partners){
                if(isForLiveSite)
                    csv.append("\"" + current.name + "\"" + ",http://mwdl.org/partners/"+current.urlName + ".php\n");
                else
                    csv.append("\"" + current.name + "\"" + ",http://test.mwdl.org/partners/"+current.urlName + ".php\n");

            }

            csv.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
