/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author r_guye
 */
public class CPU {
    private Process currentProcess; // reference of the process alocated to CPU
    private int     idleTime,       // time CPU not working starting at time 0
                    runningTime,    // time CPU is running a process
                    quantum,        // time allowed for process to run for rr
                    ID;             // ID of the CPU
    private String  status;         // idle or running

    public CPU(int ID) {
        this.ID = ID;
        this.currentProcess = null;
        this.idleTime = 0;
        this.runningTime = 0;
        this.quantum = 0;
        this.status = "idle";
    }

    // remove completed process and upload new one using algorithm
    // place process onto waiting queue if it has I/O request
    // replace process with one with a shorter burst time if using SJF / SRTF
    // replace process with another if it has reached quantum limit using RR
    public void update(int TIME_UNIT, String scheduler, int quantum)
    {
        // update all CPUs, inferring that it is impossible to have an IO request at time 0
        if (status.equals("running"))
        {
            // process is running
            // If using SJF / SRTF, check and replace
            // If it is already shortest, do nothing
            // If using RR, check quantum and replace.
            // If there are no process in ready queue, reset quantum
            if (!Assignment2.ready_queue.isEmpty())
            {
                schedulingActive(scheduler);
                currentProcess.setStatus("running");
            }
            else
            {
                if (currentProcess.getQuantum() > quantum)
                {
                    currentProcess.setQuantum(0);
                }
            }
        }
        else // CPU is idle
        {
            if (!Assignment2.ready_queue.isEmpty())
            {
                schedulingIdle(scheduler);
                
                currentProcess.setStatus("running");
                System.out.println("> Process " + currentProcess.getID() + 
                                   " has left the ready queue and is now running"
                                           + " on CPU " + ID);
                status = "running";
            }
        }
    }
    
    void schedulingActive(String scheduler)
    {
        if (scheduler.equals("SJF") || scheduler.equals("SRTF")) 
                {
                    Process shortestProcess = currentProcess;
                    for(Process p : Assignment2.ready_queue) {
                        if (shortestProcess.getRemainingExecTime() > p.getRemainingExecTime())
                        {
                            shortestProcess = p;
                        }
                    }
                    if (shortestProcess != currentProcess)
                    {
                        currentProcess.setStatus("ready");
                        Assignment2.ready_queue.add(currentProcess);
                        System.out.println("> Process " + currentProcess.getID() + 
                            " from CPU " + ID + " has returned on the ready queue"
                               + " in exchange of Process " + shortestProcess.getID());                        
                        currentProcess = shortestProcess;
                        Assignment2.ready_queue.remove(shortestProcess);
                    }
                }
                else if (scheduler.equals("RR"))
                {
                    if (currentProcess.getQuantum() == quantum)
                    {
                        currentProcess.setStatus("ready");
                        currentProcess.setQuantum(0);
                        Assignment2.ready_queue.add(currentProcess);
                        System.out.println("> Process " + currentProcess.getID() + 
                            " from CPU " + ID + " has returned on the ready queue"
                               + " in exchange of Process " + Assignment2.ready_queue.get(0).getID());                        
                        currentProcess = Assignment2.ready_queue.remove(0);
                    }
                }
    }
    
    void schedulingIdle(String scheduler)
    {
                // Using FCFS or RR
                if (scheduler.equals("FCFS") || scheduler.equals("RR")) 
                {
                    currentProcess = Assignment2.ready_queue.remove(0);
                }
                else // Using SJF or SRTF
                {
                    Process shortestProcess = Assignment2.ready_queue.get(0);
                    for(Process p : Assignment2.ready_queue) {
                        if (shortestProcess.getRemainingExecTime() > p.getRemainingExecTime())
                        {
                            shortestProcess = p;
                        }
                    }
                    currentProcess = shortestProcess;
                    Assignment2.ready_queue.remove(shortestProcess);
                }
        
    }
    
    

    public void run(int TIME_UNIT)
    {
        if (status.equals("running"))
        {
            ++runningTime;
            currentProcess.run(TIME_UNIT, this);
        }
        else ++idleTime;
    }
    
    public void setIldeTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIldeTime() {
        return idleTime;
    }

    public int getID() {
        return ID;
    }
    
    public Process getCurrentProcess() {
        return currentProcess;
    }

    public int getIdletime() {
        return idleTime;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public String getStatus() {
        return status;
    }

    public void setCurrentProcess(Process currentProcess) {
        this.currentProcess = currentProcess;
    }

    public void setIdletime(int idleTime) {
        this.idleTime = idleTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCPUUtilization()
    {
        float percentage = ((float)runningTime/(runningTime+idleTime))*100;
        return String.format("%.2f%%",percentage);
    }
    
    @Override
    public String toString() {
        return "CPU " + ID + "{" + "currentProcess=" + currentProcess 
                + ",\n idleTime=" + idleTime 
                + ", runningTime=" + runningTime 
                + ", status=" + status + "}";
    }
}
