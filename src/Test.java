import org.mwdl.scripts.data.DataFetcher;
import org.mwdl.scripts.webManagement.Collection;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Jonathan Wiggins
 * @version 6/28/17
 */

public class Test {
    public static void main(String[] args) {
        try {


            for(String c : DataFetcher.getInactiveCollectionLines())
                System.out.println(c);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
