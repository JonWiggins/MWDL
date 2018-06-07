package org.mwdl.interview;

import java.util.ArrayList;


public class MergeLists {


    /**
     * NVM, this is too long
     */


    /**
     * Task: Create a method that merges three arraylists into one
     * - Take 3 ArrayList objects a parameters
     * - Assume that each of the List are already sorted
     * - Return an ArrayList that contains all the elements from all three Lists
     * - Note that the list are not necessarily the same size
     *
     */


    public ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c){
        ArrayList<Integer> toReturn = new ArrayList<>();


        return toReturn;
    }

    public class OrderKeeper{
        ArrayList<Integer> first;
        ArrayList<Integer> second;
        ArrayList<Integer> third;

        int firstIndex;
        int secondIndex;
        int thirdIndex;

        public OrderKeeper(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c){

            first = a;
            second = b;
            third = c;

            firstIndex = 0;
            secondIndex = 0;
            thirdIndex = 0;

        }

        /**
         * rearrange the three lists
         */
        public void reOrder(){

        }


    }

}
