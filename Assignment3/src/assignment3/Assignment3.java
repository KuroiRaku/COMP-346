/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

/**
 *
 * @author Le Cherng
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author r_guye
 */
public class Assignment3 {
    
    public static final int BUFFER_SIZE = 10;
    public static int shared_index = 0;
    public static int[] buffer = new int[BUFFER_SIZE];
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore full = new Semaphore(0);        
    public static Semaphore empty = new Semaphore(BUFFER_SIZE); 
    public static Random P = new Random(System.currentTimeMillis());
    public static Random C = new Random(System.currentTimeMillis());
    private static float q = 0.66f;
    
    public static Consumer consumer = new Consumer( 1 - q );
    public static Producer producer = new Producer(q);
    
    public static void main(String[] args) {
        Arrays.fill(buffer, 0);

        consumer.start();
        producer.start();
        try
        {
            consumer.join();
            producer.join();
            // Some final stats after all the child threads terminated...
            System.out.println("System terminates normally.");
        }
       catch(InterruptedException e)
       {
            System.out.println("Caught InterruptedException: " + e.getMessage());
            System.exit(1);
       }
       catch(Exception e)
       {
            System.out.println("Caught exception: " + e.getClass().getName());
            System.out.println("Message : " + e.getMessage());
            System.out.println("Stack Trace : ");
            e.printStackTrace();
        }
    }
    
}

