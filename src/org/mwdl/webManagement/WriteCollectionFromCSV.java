package org.mwdl.webManagement;

import org.mwdl.data.DataFetcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Run this class to generate all of the Collection Landing Pages from the collectionData.csv file in this dir
 *
 * @author Jonathan Wiggins
 * @version 6/20/17
 */

public class WriteCollectionFromCSV {


    public static void main(String[] args) {

        ArrayList<Collection> toWrite = DataFetcher.getAllActiveCollections();

        System.out.println("Found " + toWrite.size() + " active collections in the Collections Data CSV file.");
        CollectionPageMaker.writeGivenCollectionPages(toWrite);
        System.out.println("Completed writing collections");
    }

}