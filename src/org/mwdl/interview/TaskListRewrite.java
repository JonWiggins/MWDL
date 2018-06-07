package org.mwdl.interview;

import java.util.ArrayList;
import java.util.Hashtable;

public class TaskListRewrite {

    /**
     * Given Code: A bad class
     * - Note that FamilyList holds the elements for both lists
     */
    public static class ListKeeperBad{
        ArrayList<String> MomsList;
        ArrayList<String> DadsList;
        ArrayList<String> FamilyList;

        /**
         * Init lists
         */
        public ListKeeperBad(){
            MomsList = new ArrayList<>();
            DadsList = new ArrayList<>();
            FamilyList = new ArrayList<>();
        }

        /**
         * Adds to the given list
         */
        public void addToList(String listName, String toAdd){
            if(listName.equalsIgnoreCase("Mom")) {
                MomsList.add(toAdd);
                FamilyList.add(toAdd);
            }else if(listName.equalsIgnoreCase("Dad")) {
                DadsList.add(toAdd);
                FamilyList.add(toAdd);
            }else
                FamilyList.add(toAdd);
        }

        /**
         * Return a list for a given individual
         */
        public ArrayList<String> returnList(String listName){
            if(listName.equalsIgnoreCase("Mom")) {
                return MomsList;
            }else if(listName.equalsIgnoreCase("Dad")) {
                return DadsList;
            }else
                return FamilyList;
        }

        /**
         * returns all of the list items in all lists
         */
        public ArrayList<String> getAllListItems(){
            return FamilyList;
        }

    }

    /*
     * There is nothing syntactically or functionally wrong with this code
     *  - Ask Applicant what is wrong with this code from a programming perspective, think about what may happen when
     *     this class is used to store large amounts of list items, or lists
     *      - Class must be rewritten each time a new family member is added
     *      - All items are stored in memory twice
     *
     * Task: Rewrite that class
     * - Do not change any of the method headers
     * - Use a backing data structure to store and access data in a better manner
     * - Make it such that changes to the class are not needed to add a new family member
     */


    /**
     * One possible solution
     * There are a couple ways they could go with this, any way that can handle an arbitrary amount of lists is good
     *
     * - Note that this method decreases the storage use
     * - Note that this method increases access time, especially for getting all of the elements
     */
    public static class ListKeeperGood{
        Hashtable<String, ArrayList<String>> listNameToContents;

        /**
         * Init
         */
        public ListKeeperGood(){
            listNameToContents = new Hashtable<>();
        }

        /**
         * Adds to the given list
         */
        public void addToList(String listName, String toAdd){
            if(listNameToContents.containsKey(listName))
                listNameToContents.get(listName).add(toAdd);
            else{
                ArrayList<String> newList = new ArrayList<>();
                newList.add(toAdd);
                listNameToContents.put(listName, newList);
            }

        }

        /**
         * Return a list for a given individual
         */
        public ArrayList<String> returnList(String listName){
            if(listNameToContents.containsKey(listName))
                return listNameToContents.get(listName);
            else
                return getAllListItems();
        }

        /**
         * returns all of the list items in all lists
         */
        public ArrayList<String> getAllListItems(){
            ArrayList<String> toReturn = new ArrayList<>();
            for(ArrayList<String> element : listNameToContents.values())
                toReturn.addAll(element);
            return toReturn;
        }
    }

}
