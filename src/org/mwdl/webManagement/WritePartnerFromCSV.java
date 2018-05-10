package org.mwdl.webManagement;

import org.mwdl.data.DataFetcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Run this to create all the Partner landing pages from the newPartnerData.csv in this dir
 *
 * It will write all of the Partner landing pages into partners/ and amppartners/
 *
 * @author Jonathan Wiggins
 * @version 5/9/18
 */

public class WritePartnerFromCSV {

    public static void main(String[] args) {

        PartnerPageMaker.writeGivenPartnerPages(DataFetcher.getAllActivePartners());
    }
}