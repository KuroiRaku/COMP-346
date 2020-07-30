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

    enum processStatus {
        NEW,
        READY,
        RUNNING,
        WAITING,
        TERMINATED
    }

    private processStatus status;
    private String ID;
    private int arrivalTime,
            totalExecTime;
    private ArrayList<Integer> IORequestTime;

    private int startTime, // first time it enters CPU starts at -1
            execTime, // time being spend processing on CPU
            waitTime, // total time the process waits on the ready queue || turnaroundTime - totalExecTime
            exitTime, // time when process exits CPU
            ioWaitTime, // time used for the waiting_queue, up to 2 unit time
            quantum;        // time allowed for process to run for rr

    public Process(String ID, int arrivalTime, int totalExecTime, ArrayList<Integer> IORequestTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.totalExecTime = totalExecTime;
        this.IORequestTime = IORequestTime;
        this.status = processStatus.NEW;
        this.startTime = -1;
        this.execTime = 0;
        this.waitTime = 0;
        this.exitTime = -1;
        this.ioWaitTime = 0;
        this.quantum = 0;
    }

    public void run(int time, CPU cpu) {

        ++execTime; // simulating process work
        ++quantum;

        if (execTime == totalExecTime) {
            terminate(time);
        } else if (!IORequestTime.isEmpty()) {
            if (execTime == IORequestTime.get(0)) {
                yield();
            }
        }
    }

    public void wake_up() {
        //A process that previously yielded completes its I/O request, 
        // and is ready to perform CPU operations. 
        // wake_up() is also called when a process in the NEW state becomes runnable.
        status = processStatus.READY;
        ioWaitTime = 0;
    }

    public void yield() {
        // A process completes its CPU operations and yields the processor to perform an I/O request.
        status = processStatus.WAITING;
        IORequestTime.remove(0);
    }

    public void preempt() {
        // When using a Round-Robin or Static Priority scheduling algorithm, 
        // a CPU-bound process may be preempted before it completes its CPU operations.
        status = processStatus.READY;
    }

    public void terminate(int time) {
        // A process exits or is killed.
        status = processStatus.TERMINATED;
        exitTime = time;
    }

    public void setStatusRunning() {
        status = processStatus.RUNNING;
    }

    public boolean isTerminated() {
        return (status == processStatus.TERMINATED);
    }

    public boolean isWaiting() {
        return (status == processStatus.WAITING);
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getQuantum() {
        return quantum;
    }

    public void incrementWaitTime() {
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

    public int getTotalExecTime() {
        return totalExecTime;
    }

    public ArrayList<Integer> getIORequestTime() {
        return IORequestTime;
    }

    public processStatus getStatus() {
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
        return totalExecTime + waitTime;
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

    public void setStatus(processStatus status) {
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
                + ",\n   status=" + status
                + ", startTime=" + startTime
                + ", execTime=" + execTime
                + ", waitTime=" + waitTime
                + ", turnaroundTime=" + getTurnaroundTime()
                + ", exitTime=" + exitTime
                + ",\n   ioWaitTime=" + ioWaitTime
                + ", quantum=" + quantum
                + ", responseTime=" + getResponseTime() + "}";
    }

}
