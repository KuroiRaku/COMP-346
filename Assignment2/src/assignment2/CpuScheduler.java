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
public class CpuScheduler {

    protected String name;                         // name of algorithm to be used
    protected ArrayList<CPU> CPUs;                 // list of CPU
    protected ArrayList<Process> new_queue;        // queue with new processes to come
    protected ArrayList<Process> ready_queue;     // ready queue
    protected ArrayList<Process> wait_queue;       // queue with processes have IO
    protected ArrayList<Process> terminated_queue; // queue with completed processes

    public CpuScheduler(String name) {
        this.name = name;
        CPUs = new ArrayList<CPU>();
        new_queue = new ArrayList<Process>();
        ready_queue = new ArrayList<Process>();
        wait_queue = new ArrayList<Process>();
        terminated_queue = new ArrayList<Process>();
    }

    // go over every cpu and processes and updates according to their states
    // time starts at 0
    public void update(int time) {

    }

    // run all cpus and processes
    public void run(int time) {
        // update waitTime for processes in the ready queue
        for (Process p : ready_queue) {
            p.incrementWaitTime();
        }

        // temporary list used to remove processes from waiting queue
        ArrayList<Process> temp_Wait_Queue = new ArrayList<Process>();

        // update the wait queue 
        // and place those who finished its IO request back to ready queue
        for (Process p : wait_queue) {
            p.increaseIoWaitTime();
            if (p.getIoWaitTime() == 2) {
                p.wake_up();
                ready_queue.add(p);
                temp_Wait_Queue.add(p);
            }
        }
        // removing them from new process queue
        for (Process p : temp_Wait_Queue) {
            wait_queue.remove(p);
        }

        // run all CPUs           
        for (CPU c : CPUs) {
            c.run(time); // Run all CPUs, whether they are idling or processing
        }
    }

    // select which process to pick in the ready queue
    public void select(CPU c, int time) {

    }

    // remove process from cpu and place them to destination
    public void remove(CPU c, ArrayList<Process> destination) {
        destination.add(c.getCurrentProcess());
        c.clearProcess();
    }

    // terminate process and store its information in queue
    public void terminate(CPU target) {
        terminated_queue.add(target.getCurrentProcess());
        target.clearProcess();
    }

    public ArrayList<CPU> getCPUs() {
        return CPUs;
    }

    public void setCPUs(ArrayList<CPU> CPUs) {
        this.CPUs = CPUs;
    }

    public ArrayList<Process> getNew_queue() {
        return new_queue;
    }

    public void setNew_queue(ArrayList<Process> new_queue) {
        this.new_queue = new_queue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Process> getTerminated_queue() {
        return terminated_queue;
    }

    public void setTerminated_queue(ArrayList<Process> terminated_queue) {
        this.terminated_queue = terminated_queue;
    }

    public float getAvgWaitTime() {

        int totalWaitTime = 0;
        for (Process p : terminated_queue) {
            totalWaitTime += p.getWaitTime();
        }
        return (float) totalWaitTime / terminated_queue.size();
    }

    public void log(int time) {
        System.out.printf("%-7d", time);
        for (CPU c : CPUs) {
            if (!c.isRunning()) {
                System.out.printf("%-8s", "(idle)");
            } else {
                System.out.printf("%-8s", c.getCurrentProcess().getID());
            }
        }
        String readyQueueOutput = "[";
        for (int i = 0; i < ready_queue.size() - 1; ++i) {
            readyQueueOutput += ready_queue.get(i).getID() + ", ";
        }
        if (!ready_queue.isEmpty()) {
            readyQueueOutput += ready_queue.get(ready_queue.size() - 1).getID();
        }
        readyQueueOutput += "]";
        System.out.printf("%-20s", readyQueueOutput);

        String waitingQueueOutput = "\t[";
        for (int i = 0; i < wait_queue.size() - 1; ++i) {
            waitingQueueOutput += wait_queue.get(i).getID() + ", ";
        }
        if (!wait_queue.isEmpty()) {
            waitingQueueOutput += wait_queue.get(wait_queue.size() - 1).getID();
        }
        waitingQueueOutput += "]";
        System.out.println(waitingQueueOutput);
    }

    public void getStatistics() {

        System.out.println("\n>>> Result for " + name);
        System.out.println("Process Info");
        System.out.println("PID   arrivalTime   execTime   startTime   exitTime   waitTime   turnaroundTime   responseTime");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (Process p : terminated_queue) {
            System.out.printf("%-6s%-14d%-11d%-12d%-11d%-11d%-17d%d\n",
                    p.getID(),
                    p.getArrivalTime(),
                    p.getExecTime(),
                    p.getStartTime(),
                    p.getExitTime(),
                    p.getWaitTime(),
                    p.getTurnaroundTime(),
                    p.getResponseTime());
        }
        System.out.println("\nCPU Info");
        System.out.println("CID   idleTime   runningTime   CPU utilization");
        System.out.println("----------------------------------------------");
        for (CPU c : CPUs) {
            System.out.printf("%-6d%-11d%-14d%s\n",
                    c.getID(),
                    c.getIldeTime(),
                    c.getRunningTime(),
                    c.getCPUUtilization());
        }

        System.out.printf("\nAverage Wait Time is: %f\n", getAvgWaitTime());
    }
}
