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
        boolean next = true;
        int iteration = 30;        
        while (next) {
            if (--iteration == 0)
                next = false;
            if (!(Assignment3.P.nextFloat() <  q))
                continue;
            
            Assignment3.empty.Wait("P decrement empty to ");
            Assignment3.mutex.Wait("P decrement mutex to ");
            
            /* add next produced to the buffer */
            Assignment3.buffer.set(Assignment3.shared_index, 1);
            ++Assignment3.shared_index;
            
            System.out.printf("P produces a new item  - P:%-10s C:%-10s %s\n\n",
                    Assignment3.producer.getState(), 
                    Assignment3.consumer.getState(), 
                    Assignment3.buffer);   
            
            Assignment3.mutex.Signal("P increment mutex to ");
            Assignment3.full.Signal("P increment full to  ");
        }
    }
}

