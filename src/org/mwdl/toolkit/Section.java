package org.mwdl.toolkit;

import java.util.ArrayList;

/**
 * TODO - Fill in class desc
 *
 * @author Jonathan Wiggins
 * @version 7/17/18
 */
public class Section {

    public Section superSection;
    public ArrayList<Section> subsections;

    public String title;
    public ArrayList<String> images;
    public String description;

    public Section(){

    }

}
