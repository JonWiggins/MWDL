package org.mwdl.toolkit;

import org.mwdl.data.ProjectConstants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO - Fill in class desc
 *
 * @author Jonathan Wiggins
 * @version 7/17/18
 */
public class Parser {

    public static void main(String[] args) {

        Section current = new Section();

        ArrayList<Section> toolkitSections = new ArrayList<>();

        try {
            //TODO replace with toolkit in ProjectConstants
            BufferedReader toolkitData = new BufferedReader(new FileReader(ProjectConstants.CollectionDataCSV));

            while(toolkitData.ready()){
                String currentline = toolkitData.readLine();


            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Recursively parses section to build sections
     *
     * @param line
     * @param currentSection
     * @return
     */
    public static Section parseSection(String line, Section currentSection){

        //if the line starts with a comma, it needs to be added as a subsection to the current section
        if(line.startsWith(",")){

        }
        //if the line starts with a title, current section gets added to the sections list
        // and create a new section for this line
        else{

        }

        return currentSection;
    }

}
