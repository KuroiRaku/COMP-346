/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

/**
 *
 * @author r_guye
 */
public class Consumer extends Thread {
    
    private float r; // r = 1 - q
    
    public Consumer(float r) {
        assert(r <= 1 && r >= 0);
        this.r = r;
    }
    
    public void run() {
        boolean next = true;
        int iteration = 30;
        while (next) {
            if (--iteration == 0)
                next = false;
            //r = 1-q
            if ((Assignment3.C.nextFloat() >  r))
                continue;
            
            Assignment3.full.Wait("C decrement full to  ");
            Assignment3.mutex.Wait("C decrement mutex to ");
            
            /* remove an item from buffer to next_consumed */ 
            --Assignment3.shared_index;
            Assignment3.buffer.set(Assignment3.shared_index, 0);
            
            System.out.printf("C consumes an item     - P:%-10s C:%-10s %s\n\n",
                    Assignment3.producer.getState(), 
                    Assignment3.consumer.getState(), 
                    Assignment3.buffer);      
            
            Assignment3.mutex.Signal("C increment mutex to ");
            Assignment3.empty.Signal("C increment empty to ");
        }
    }
}
