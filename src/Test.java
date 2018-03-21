import org.mwdl.scripts.data.DataFetcher;
import org.mwdl.scripts.webManagement.Collection;
import org.mwdl.scripts.webManagement.PartnerPage;
import org.mwdl.scripts.webManagement.PartnerPageMaker;

import javax.xml.crypto.Data;
import javax.xml.datatype.DatatypeFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Just use this to test methods in a *closed* environment
 *
 *
 * I frequently used this class to run DataFetcher methods, ie, how many in/active collections are there?
 *
 * @author Jonathan Wiggins
 * @version 6/28/17
 */

public class Test {

    public static void main(String[] args) {
        try {

            ArrayList<Integer> asdf = new ArrayList<>();

            asdf.add(1060);
            asdf.add(1059);
            asdf.add(1100);
            asdf.add(1099);
            asdf.add(1101);
            asdf.add(1100);
            asdf.add(1307);
            asdf.add(1306);
            asdf.add(1308);
            asdf.add(1546);
            asdf.add(1545);

            for(int element : asdf){
                System.out.println(element + " " + DataFetcher.fetchCollection(element).urlTitle);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       // System.out.println("Brigham Young University Master's Theses on Mormonism: ".replaceAll("[^a-zA-Z0-9]", ""));
    }
}
