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
    public synchronized void Wait()
    {
        while (this.value <= 0)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                System.out.println ("Semaphore::Wait() - caught InterruptedException: " + e.getMessage() );
                e.printStackTrace();
            }
        }
        this.value--;    
    }

    public synchronized void Signal()
{
        ++this.value;
        notify();
    }

    public int getValue() {
        return value;
    }
}
