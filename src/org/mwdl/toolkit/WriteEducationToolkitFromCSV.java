package org.mwdl.toolkit;

import org.mwdl.data.DataFetcher;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO - Fill in class desc
 *
 * @author Jonathan Wiggins
 * @version 8/13/18
 */
public class WriteEducationToolkitFromCSV {

    public static void main(String[] args) {
        try {
            ArrayList<Section> sections = DataFetcher.getAllEducationSections();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
