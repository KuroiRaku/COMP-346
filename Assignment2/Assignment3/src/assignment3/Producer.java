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
public class Producer extends Thread {
    
    private float q; // probability rate
    
    public Producer(float q) {
        assert(q <= 1 && q >= 0);
        this.q = q;
    }
    
    public void run() {
        while (true) {
            if (!(Assignment3.P.nextFloat() <  q))
                continue;
            Assignment3.empty.Wait();
            Assignment3.mutex.Wait();
            
            /* add next produced to the buffer */
            System.out.printf("+ Produce item - mutex(%d), full(%d), empty(%d),"
                    + " P(%s), C(%s): ", 
                    Assignment3.mutex.getValue(), Assignment3.full.getValue(), 
                    Assignment3.empty.getValue(), Assignment3.producer.getState(), 
                    Assignment3.consumer.getState());   
            Assignment3.buffer[Assignment3.shared_index] = 1;
            ++Assignment3.shared_index;
            for (int i = 0; i < Assignment3.buffer.length; i++)
                System.out.print(Assignment3.buffer[i] + " ");
            if (Assignment3.shared_index == Assignment3.BUFFER_SIZE)
                 System.out.println("(FULL)");
            else
            System.out.println(""); 
            
            Assignment3.mutex.Signal();
            Assignment3.full.Signal();
        }
    }
}

