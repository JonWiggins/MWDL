import org.mwdl.data.DataFetcher;
import org.mwdl.webManagement.Collection;

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
        //also 1955
        System.out.println(DataFetcher.fetchCollection(1924));
    }
}
