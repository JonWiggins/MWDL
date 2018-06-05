package org.mwdl.webManagement;

import org.mwdl.data.DataFetcher;
import org.mwdl.data.ProjectConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Run this to create all the Partner landing pages from the active partners given by the partnerData csv
 *
 * It will write all of the Partner landing pages into the directory given by ProjectConstants
 *
 * @author Jonathan Wiggins
 * @version 5/9/18
 */

public class WritePartnerFromCSV {

    public static void main(String[] args) {

        ArrayList<Partner> toWrite = DataFetcher.getAllActivePartners();

        System.out.println("Found " + toWrite.size() + " active partners in" + ProjectConstants.PartnerDataCSV + ".");
        PartnerPageMaker.writeGivenPartnerPages(toWrite);
        System.out.println("Completed writing Partners");
    }
}