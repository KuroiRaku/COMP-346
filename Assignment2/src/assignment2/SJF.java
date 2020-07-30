/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;

/**
 *
 * @author Le Cherng
 */
public class SJF extends CpuScheduler {

    public SJF(String name) {
        super(name);
    }

    @Override
    // go over every cpu and processes and updates according to their states
    // time starts at 0
    public void update(int time) {

        // temporary list used to remove processes from new queue
        ArrayList<Process> temp_Processes = new ArrayList<Process>();
        // check if new processes arrived and add to the ready queue
        for (Process p : new_queue) {
            if (p.getArrivalTime() == time) {
                p.wake_up();
                ready_queue.add(p);
                temp_Processes.add(p);
            }
        }
        // removing them from new process queue
        for (Process p : temp_Processes) {
            new_queue.remove(p);
        }

        // update all cps
        // remove terminated or waiting processes
        // add ready process for free cpu
        for (CPU c : CPUs) {
            if (c.isRunning()) {    // process in cpu waiting to be cleared
                if (c.getCurrentProcess().isTerminated()) {
                    remove(c, terminated_queue);
                    if (!ready_queue.isEmpty()) // free cpu to use
                    {
                        select(c, time);
                    }
                } else if (c.getCurrentProcess().isWaiting()) {
                    remove(c, wait_queue);
                    if (!ready_queue.isEmpty()) // free cpu to use
                    {
                        select(c, time);
                    }
                }
            } else {     // cpu is idle
                if (!c.isRunning() && !ready_queue.isEmpty()) // free cpu to use
                {
                    select(c, time);
                }
            }
        }

        // Print output
        log(time);
    }

    @Override
    // select Process p from the ready queue and places to a free CPU
    public void select(CPU c, int time) {

        Process shortestProcess = ready_queue.get(0);
        for (Process p : ready_queue) {
            if (shortestProcess.getRemainingExecTime() > p.getRemainingExecTime()) {
                shortestProcess = p;
            }
        }
        c.setCurrentProcess(shortestProcess);
        if (shortestProcess.getStartTime() == -1) {
            shortestProcess.setStartTime(time); // set first time execution
        }
        ready_queue.remove(shortestProcess);
        shortestProcess.setStatusRunning();
        c.setStatusRunning();
    }
}
