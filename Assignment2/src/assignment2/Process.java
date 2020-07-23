/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;

/**
 *
 * @author r_guye
 */
public class Process {
    private String ID;
    private int arrivalTime, 
                totalExecTime;
    private ArrayList<Integer> IORequestTime;
    
    public static String newChanges;  // output changes done after execution
    
    private String status;      // new, ready, running, waiting, *terminated
    private int startTime,      // first time it enters CPU starts at -1
                execTime,       // time being spend processing on CPU
                waitTime,       // total time the process waits on the ready queue || turnaroundTime - totalExecTime
                exitTime,       // time when process exits CPU
                ioWaitTime,     // time used for the waiting_queue, up to 2 unit time
                quantum;        // time allowed for process to run for rr

    public Process(String ID, int arrivalTime, int totalExecTime, ArrayList<Integer> IORequestTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.totalExecTime = totalExecTime;
        this.IORequestTime = IORequestTime;
        this.status = "new";
        this.startTime = -1;
        this.execTime = 0;
        this.waitTime = 0;
        this.exitTime = -1;
        this.ioWaitTime = 0;
        this.newChanges = "";
        this.quantum = 0;
    }
    
    public void run(int TIME_UNIT, CPU cpu)
    {
        if (startTime == -1) startTime = TIME_UNIT; // set first time execution
        ++execTime; // simulating process work
        ++quantum;
        
        if (execTime == totalExecTime)
        {
            status = "terminated";
            exitTime = TIME_UNIT + 1;
            newChanges += "> Process " + ID + 
                   " has terminated and is now heading to"
                           + " the terminated queue\n";                  
            Assignment2.terminated_queue.add(this);
            cpu.setCurrentProcess(null);
            cpu.setStatus("idle");
        }
        else if (!IORequestTime.isEmpty())
        {
            if (execTime == IORequestTime.get(0))
            {
                status = "waiting";
                IORequestTime.remove(0);
                Assignment2.waiting_queue.add(this);
                newChanges += "> Process " + ID + 
                                   " has left CPU " + ID + " and is now waiting"
                                           + " on waiting queue\n";   
                cpu.setCurrentProcess(null);
                cpu.setStatus("idle");                
            }
        }
    }

    public static void setNewChanges(String newChanges) {
        Process.newChanges = newChanges;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public static String getNewChanges() {
        return newChanges;
    }

    public int getQuantum() {
        return quantum;
    }
    
    public void incrementWaitTime()
    {
        ++waitTime;
    }
        
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getResponseTime() {  // called when terminated
        return startTime - arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getID() {
        return ID;
    }

    public int getArrivaltime() {
        return arrivalTime;
    }

    public int getTotalExecTime() {
        return totalExecTime;
    }

    public ArrayList<Integer> getIORequestTime() {
        return IORequestTime;
    }

    public String getStatus() {
        return status;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getExecTime() {
        return execTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getTurnaroundTime() {
        return  totalExecTime + waitTime;
    }

    public int getExitTime() {
        return exitTime;
    }

    public int getIoWaitTime() {
        return ioWaitTime;
    }
    
    public int getRemainingExecTime() {
        return totalExecTime - execTime;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setArrivaltime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setTotalExecTime(int totalExecTime) {
        this.totalExecTime = totalExecTime;
    }

    public void setIORequestTime(ArrayList<Integer> IORequestTime) {
        this.IORequestTime = IORequestTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setExitTime(int exitTime) {
        this.exitTime = exitTime;
    }

    public void setIoWaitTime(int ioWaitTime) {
        this.ioWaitTime = ioWaitTime;
    }
    
    public void increaseIoWaitTime() {
        ++ioWaitTime;
    }

    @Override
    public String toString() {
        return "Processor{" + "ID=" + ID 
                + ", arrivalTime=" + arrivalTime 
                + ", totalExecTime=" + totalExecTime 
                + ", IORequestTime=" + IORequestTime 
                + ", status=" + status 
                + ",\n startTime=" + startTime 
                + ", execTime=" + execTime 
                + ", waitTime=" + waitTime 
                + ", turnaroundTime=" + getTurnaroundTime() 
                + ", exitTime=" + exitTime 
                + ", ioWaitTime=" + ioWaitTime 
                + ", quantum=" + quantum                
                + ", responseTime=" + getResponseTime() + "}";
    }
    
    
}
