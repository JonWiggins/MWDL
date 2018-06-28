package org.mwdl.webManagement;
import org.mwdl.data.DataFetcher;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * If you view https://www.mwdl.org/partners/partners.php, you will see an interactive Google Map with pins
 *  for each partner, this is what enables that section of the website
 *
 * The custom Google Map is supplied with data from a Google Sheets Document
 * This program creates a csv file with two columns
 *  1. The name of each active MWDL partner
 *  2. The url for each partners page on the MWDL website
 *
 * To update the partner map, after the addition/removal of a partner (in the partnerData csv):
 *  1. Run this Program
 *  2. Upload the resulting document to Google Drive, note the name of the resulting sheet
 *  3. Login to the MWDL Google account
 *  4. Goto google.com/maps/d/
 *  5. Select MWDL Partner Map
 *  6. Select the Parter Map Data Layer
 *  7. Click Delete this layer
 *  8. Click Add Layer
 *  9. Import the previously uploaded sheet
 *  10. Fix any import conflicts, some parters names will not instantly appear on google maps, their names
 *      may need to be edited slightly
 *
 *
 * @author Jonathan Wiggins
 * @version 9/15/17
 */

public class GeneratePartnerMap {

    //TODO if you are making this version of the map for the testing website, set this to false
    private static boolean isForLiveSite = true;


    public static void main(String[] args) {
        try {
            ArrayList<Partner> partners = DataFetcher.getAllActivePartners();

            String FileLocAndName = "partnerMapData.csv";
            PrintWriter csv = new PrintWriter(FileLocAndName,"UTF-8");
            csv.append("Partner Name, Partner Link \n");

            for(Partner current : partners){
                if(isForLiveSite)
                    csv.append("\"" + current.name + "\"" + ",http://mwdl.org/partners/" + current.urlName + ".php\n");
                else
                    csv.append("\"" + current.name + "\"" + ",http://test.mwdl.org/partners/" + current.urlName + ".php\n");

            }

            csv.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
