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
    
    enum cpuStatus {
        IDLE,
        RUNNING
    }
    private cpuStatus status;
    
    private Process currentProcess; // reference of the process alocated to CPU
    private int     idleTime,       // time CPU not working starting at time 0
                    runningTime,    // time CPU is running a process
                    quantum,        // time allowed for process to run for rr
                    ID;             // ID of the CPU

    public CPU(int ID) {
        this.ID = ID;
        this.currentProcess = null;
        this.idleTime = 0;
        this.runningTime = 0;
        this.quantum = 0;
        this.status = cpuStatus.IDLE;
    }
    
    // Clear out the process on the cpu
    // Should be because it has terminated or finshed its quantum time
    public void clearProcess()
    {
        currentProcess = null;
        status = cpuStatus.IDLE;
    }

    public void run(int time)
    {
        if (isRunning())
        {
            ++runningTime;
            currentProcess.run(time, this);
        }
        else ++idleTime;
    }
    
    public boolean isRunning() {
        return (status == cpuStatus.RUNNING);
    }
    
    public void setStatusRunning() {
        status = cpuStatus.RUNNING;
    }
    
    public void setStatusIdle() {
        status = cpuStatus.IDLE;
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

    public int getRunningTime() {
        return runningTime;
    }

    public cpuStatus getStatus() {
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

    public void setStatus(cpuStatus status) {
        this.status = status;
    }

    public String getCPUUtilization()
    {
        float percentage = ((float)runningTime/(runningTime+idleTime))*100;
        return String.format("%.2f%%",percentage);
    }
    
    @Override
    public String toString() {
        return "CPU{" + "ID=" + ID 
                + ", currentProcess=" + currentProcess 
                + ", idleTime=" + idleTime 
                + ", runningTime=" + runningTime 
                + ", status=" + status + "}";
    }
}
