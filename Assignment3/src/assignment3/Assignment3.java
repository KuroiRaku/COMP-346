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
import java.util.Scanner;

/**
 *
 * @author r_guye
 */
public class Assignment3 {
    
    public static final int BUFFER_SIZE = 10;
    public static int shared_index = 0;
    public static ArrayList<Integer> buffer;
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore full = new Semaphore(0);        
    public static Semaphore empty = new Semaphore(BUFFER_SIZE); 
    public static Random P = new Random(System.currentTimeMillis());
    public static Random C = new Random(System.currentTimeMillis());
    public static Consumer consumer;
    public static Producer producer;
    
    public static void main(String[] args) {
        //input
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter number for q");
        float q = input.nextFloat();
        
        //0.7 
        //initializing consumer and producer
        consumer = new Consumer( 1 - q );
        producer = new Producer(q);
    
        buffer = new ArrayList<Integer>(BUFFER_SIZE);
        for (int i = 0; i <BUFFER_SIZE; i++)
            buffer.add(0);
        
        System.out.printf("START STATE - mutex:%d, full:%d, empty:%-2d   "
                    + " P:%s C:%s\n\n",
                Assignment3.mutex.getValue(), Assignment3.full.getValue(), 
                Assignment3.empty.getValue(), Assignment3.producer.getState(), 
                Assignment3.consumer.getState());
        
        consumer.start();
        producer.start();
        try
        {
            consumer.join();
            producer.join();
            // Some final stats after all the child threads terminated...
            System.out.printf("\nEND STATE - mutex:%d, full:%d, empty:%-2d   "
                        + " P:%s C:%s\n\n",
                    Assignment3.mutex.getValue(), Assignment3.full.getValue(), 
                    Assignment3.empty.getValue(), Assignment3.producer.getState(), 
                    Assignment3.consumer.getState()); 
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

