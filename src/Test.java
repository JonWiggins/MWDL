import org.mwdl.data.DataFetcher;
import org.mwdl.webManagement.Collection;

import javax.xml.crypto.Data;
import java.util.ArrayList;

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
        for(Collection c : DataFetcher.getAllActiveCollections())
            System.out.println(c);
    }
}
