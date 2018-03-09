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
            for(Collection c : DataFetcher.getAllActiveCollectionsFromPartner("Brigham Young University"))
                System.out.println(c.title);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
