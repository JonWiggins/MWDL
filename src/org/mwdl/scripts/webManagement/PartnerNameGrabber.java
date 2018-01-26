package org.mwdl.scripts.webManagement;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class PartnerNameGrabber {
    /**
     * take the php array of partners, create a map the maps numerical
     * @param args
     */
    public static void main(String[] args) {
        try {

            HashMap<Integer, String> numtoName = new HashMap<>();

            //TODO add file loc
            File f = new File("");
            Scanner s = new Scanner(System.in);
            String nextLine;
            while(s.hasNextLine()){
                nextLine = s.nextLine().replace("/*", "").replace("*/","");
                String[] line = nextLine.split("\"");
                numtoName.put(Integer.valueOf(line[1].substring(3)),line[3]);
                System.out.println(numtoName.get(Integer.valueOf(line[1].substring(3))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
