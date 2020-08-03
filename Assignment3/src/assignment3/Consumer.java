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
        while (true) {
            if (!(Assignment3.C.nextFloat() <  r))
                continue;
            Assignment3.full.Wait();
            Assignment3.mutex.Wait();
            
            /* remove an item from buffer to next_consumed */ 
            System.out.printf("- Consume item - mutex(%d), full(%d), empty(%d),"
                    + " P(%s), C(%s): ", 
                    Assignment3.mutex.getValue(), Assignment3.full.getValue(), 
                    Assignment3.empty.getValue(), Assignment3.producer.getState(), 
                    Assignment3.consumer.getState());   
            --Assignment3.shared_index;
            Assignment3.buffer[Assignment3.shared_index] = 0;
            for (int i = 0; i < Assignment3.buffer.length; i++)
                System.out.print(Assignment3.buffer[i] + " ");
            if (Assignment3.shared_index == 0)
                 System.out.println("(EMPTY)");
            else
                System.out.println(""); 
            
            Assignment3.mutex.Signal();
            Assignment3.empty.Signal();
        }
    }
}
