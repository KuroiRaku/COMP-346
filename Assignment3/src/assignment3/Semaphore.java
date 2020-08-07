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
public class Semaphore {
    private int value;
    
    public Semaphore(int value)
    {
             this.value = value;
    }
    public Semaphore()
    {
            this(0);
    }
    public synchronized void Wait(String output)
    {
        while (this.value <= 0)
        {
            try
            {
                if (!Assignment3.consumer.isAlive() || !Assignment3.producer.isAlive()) {
                    System.out.printf("\nEND STATE - mutex:%d, full:%d, empty:%-2d   "
                        + " P:TERMINATED C:TERMINATED\n\n",
                        Assignment3.mutex.getValue(), Assignment3.full.getValue(), 
                        Assignment3.empty.getValue());
                    System.exit(0);
                }
                else
                    wait();
            }
            catch(InterruptedException e)
            {
                System.out.println ("Semaphore::Wait() - caught InterruptedException: " + e.getMessage() );
                e.printStackTrace();
            }
        }
        this.value--;    
        System.out.printf(output + value + " - P:%-10s C:%-10s\n",
                    Assignment3.producer.getState(), 
                    Assignment3.consumer.getState());
    }

    public synchronized void Signal(String output)
    {
        ++this.value;
        System.out.printf(output + value + " - P:%-10s C:%-10s\n",
                    Assignment3.producer.getState(), 
                    Assignment3.consumer.getState()); 
        notify();
    }
    
    public int getValue() {
        return value;
    }
}
