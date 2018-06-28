package org.mwdl.interview;

import java.util.ArrayList;

public class ReverseLinkedList {

    /**
     * Given Code.
     * Note that this is a static class, but it does not change its functionality, in this case
     *
     * Note the use of non-private fields.
     *  - Using Getters and Setters is not needed
     *
     */
    public static class Node{
        Node next;
        Object data;

        public Node(Object data){
            this.data = data;
        }
    }

    /*
     * Task: Create a method that reverses the ordering of a linked list
     * - A public method
     * - Returns a Node
     * - Takes a Node, the head of a linked list
     * - The Node that is returned is the new head of the list
     *  - Previously the tail
     *
     *  How would you modify this program if it were included ina linked list class
     *  - The method is now void and takes no parameter
     */


    /**
     * Possible solution
     * Note this is Theta(n), an acceptable solution should be
     *
     * @param head the head of a linked list
     * @return The new head of the list
     */
    public static Node reverse(Node head){
        Node current = null;
        Node last;
        Node next = head;

        while(next != null){
            last = current;
            current = next;
            next = next.next;
            current.next = last;
        }
        return current;
    }

    /**
     * Quick Test, should be able to trace such a test by hand
     */
    public static void main(String[] args) {
        int[] toTest = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node head = new Node(1);
        Node current = head;

        for(int element : toTest){
            Node toAdd = new Node(element);

            current.next = toAdd;
            current = toAdd;
        }


        System.out.println("Original");
        Node currentP = head;
        while (currentP != null){
            System.out.println(currentP.data);
            currentP = currentP.next;
        }


        System.out.println("Reversed");
        Node newHead = reverse(head);
        Node newCurrent = newHead;
        while(newCurrent != null){
            System.out.println(newCurrent.data);
            newCurrent = newCurrent.next;
        }
    }


    /**
     * Write a program that pcorints the numbers from 1 to 100.
     * But for multiples of three print “Fizz” instead of the number
     * and for the multiples of five print “Buzz”.
     * For numbers which are multiples of both three and five print “FizzBuzz”
     */
    public static void FizzBuzz(){

    }

}


class FizzFun {

    //Chuanchao
    public static void FizzBuzz(){
        for(int i=1;i<=100;i++){
            if(i%3 !=0 && i%5 !=0)
                System.out.print(i);
            if(i%3==0)
                System.out.print("Fizz");

            if(i%5==0)
                System.out.print("Buzz");
            System.out.println("");
        }
    }

    //Arash
    //Note this is actually wrong because it will print Fizz3 and Fizz6
    public static void FizzBuzz2(){
        for(int i = 1; i <= 100; i++){
            if(i % 3 == 0)
                System.out.print("Fizz");
            if( i % 5 == 0)
                System.out.print("Buzz");
            else
                System.out.print(i);

            System.out.println("");
        }
    }


    //Owen
    public static void FizzBuzz3(){
        int i = 1;
        while(i < 101) {
            if (i % 3 == 0) {
                if (i % 5 == 0)
                    System.out.println("FizzBuzz");
                else
                    System.out.println("Fizz");
            } else if (i % 5 == 0){
                System.out.println("Buzz");
            }else{
                System.out.println(i);
            }

            i+=1;
        }
    }


    /**
     * My quick solution
     */
    public static void FizzBuzz4(ArrayList<relation> toTest) {
        for (int count = 1; count <= 100; count++) {
            String toPrint = "";

            if (count % 3 == 0)
                toPrint = toPrint.concat("Fizz");
            if (count % 5 == 0)
                toPrint = toPrint.concat("Buzz");

            if (toPrint.length() == 0)
                System.out.println(count);
            else
                System.out.println(toPrint);

        }
    }

    public class relation{

        String word ="asdf";
        int target = 5;
    }
}