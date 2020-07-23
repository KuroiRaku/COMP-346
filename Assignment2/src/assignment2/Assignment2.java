/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 *
 * @author r_guye
 */


public class Assignment2 {
    
    public static ArrayList<Process> Processes = new ArrayList<Process>();        // List of processes
    public static ArrayList<CPU> CPUs = new ArrayList<CPU>();                     // List of CPUs
    public static ArrayList<Process> ready_queue = new ArrayList<Process>();      // ready queue
    public static ArrayList<Process> waiting_queue = new ArrayList<Process>();    // ready queue
    public static ArrayList<Process> terminated_queue = new ArrayList<Process>(); // queue with completed process
    
    public static void main(String[] args) {
        
        // Select algorithm
        //String scheduler = "FCFS";
        //String scheduler = "SJF";               
        //String scheduler = "SRTF";      
        String scheduler = "RR";
        int quantum = 3;



        
        // read file
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.trim().startsWith("//") || data.isEmpty()) continue;
                String[] arrOfStr = data.split("	"); 
                if ("numOfCPUs:".equals(arrOfStr[0]))
                {
                    int numOfCPUs = Integer.parseInt(arrOfStr[1]);
                    // Create list of CPUs
                    for (int i = 0; i < numOfCPUs; ++i) CPUs.add(new CPU(i));
                }
                else
                {   // Create list of processors
                    ArrayList<Integer> IORequestTimeArrayList = new ArrayList<Integer>();
                    if (arrOfStr.length != 3)
                    {
                        for (int j = 3; j < arrOfStr.length; ++j)
                            IORequestTimeArrayList.add(Integer.parseInt(arrOfStr[j]));
                    }
                    Processes.add(new Process(arrOfStr[0], // ID
                            Integer.parseInt(arrOfStr[1]),    // arrivalTime
                            Integer.parseInt(arrOfStr[2]),    // totalExecTime
                            IORequestTimeArrayList            // IORequestTime
                    ));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        int TIME_UNIT    = 0,                     // Current time unit 
            numProcesses = Processes.size();      // Total number of processes to process
        
        System.out.println(">>> STARTING POINT");
        System.out.println("{");
        for(Process p : Processes) {
            System.out.println(p);
        }
        
        for(CPU c : CPUs) {
            System.out.println(c);
        }
        System.out.println("}\n");
        ////////////////////////////////////////////////////////////////////////
        // MAIN LOOP
        ////////////////////////////////////////////////////////////////////////
        while (numProcesses != terminated_queue.size()) // Stops when all processes has terminated
        //while (TIME_UNIT < 15)
        {
            //Disposable list to remove the processes
            ArrayList<Integer> temp_Processes = new ArrayList<Integer>();
            ArrayList<Integer> temp_Waiting_Queue = new ArrayList<Integer>();  
            
            System.out.println(">>> AT UNIT TIME " + TIME_UNIT + "\n");
            // put newly arrived processes in ready queue
            for (int i = 0; i < Processes.size(); ++i)
            {
                if (Processes.get(i).getArrivaltime() == TIME_UNIT)
                {
                    System.out.println("> Process " + Processes.get(i).getID() + 
                                       " arrived and has entered the ready queue.");
                    Processes.get(i).setStatus("ready");
                    ready_queue.add(Processes.get(i));
                    temp_Processes.add(i);
                }
            }
            // removing them from Processes
            for (int i : temp_Processes) {
                Processes.remove(i);
            }
            
            // update waiting queue
            for (int j = 0; j < waiting_queue.size(); ++j)
            {
                waiting_queue.get(j).increaseIoWaitTime();
                if (waiting_queue.get(j).getIoWaitTime() > 2)
                {
                    waiting_queue.get(j).setIoWaitTime(0);
                    waiting_queue.get(j).setStatus("ready");
                    System.out.println("> Process " + waiting_queue.get(j).getID() + 
                                       " finished its I/O request and has entered the ready queue.");
                    ready_queue.add(waiting_queue.get(j));
                    temp_Waiting_Queue.add(j);
                }
            }
            // removing them from ready_queue
            for (int j : temp_Waiting_Queue) {
                waiting_queue.remove(j);
            }
            
            // update and run all CPUs           
            for(CPU c : CPUs) {
                // remove completed process and upload new one using algorithm
                // place process onto waiting queue if it has I/O request
                // replace process with one with a shorter burst time if using SJF / SRTF
                // replace process with another if it has reached quantum limit using RR
                c.update(TIME_UNIT, scheduler, quantum);
                c.run(TIME_UNIT); // Run all CPUs, whether they are idling or processing
            }       
            
            // update waitTime for processes in the ready queue
            for (Process p : ready_queue) {
                p.incrementWaitTime();
            }
            
            System.out.println("\n>>> AT THE END OF UNIT " + TIME_UNIT);
            
            System.out.println(Process.newChanges);
            Process.newChanges = "";
            
            System.out.println("> Processes that has yet started:");
            for(Process p : Processes) {
                System.out.println(p);
            }
            System.out.println("> CPU state:");
            for(CPU c : CPUs) {
                System.out.println(c);
            }
            System.out.println("\n");
            
            System.out.println("> Contents inside the ready queue:");
            System.out.println("{");
            for(Process p : ready_queue) {
                System.out.println(p);
            }            
            System.out.println("}");
            System.out.println("> Contents inside the waiting queue:");
            System.out.println("{");
            for(Process p : waiting_queue) {
                System.out.println(p);
            }            
            System.out.println("}");
            System.out.println("> Contents inside the terminated queue:");
            System.out.println("{");
            for(Process p : terminated_queue) {
                System.out.println(p);
            }            
            System.out.println("}\n");  
            
            ++TIME_UNIT;  // increase time unit
        }
        ////////////////////////////////////////////////////////////////////////
        System.out.println("\n>>> TERMINATING RESULT\n"); 
        System.out.println("\nProcesses Results:");
        int totalWaitTime = 0;
        for(Process p : terminated_queue) {
            System.out.println(p);
            totalWaitTime += p.getWaitTime();
        }
        
        System.out.println("\nCPU Results:");
        for(CPU c : CPUs) {
            System.out.println(c);
            System.out.println("CPU utilization for CPU " + c.getID() +
                    " is " + c.getCPUUtilization());
        }

        float avgWaitTime = (float)totalWaitTime/numProcesses;
        System.out.printf("\nAverage Wait Time is: %f\n",avgWaitTime);
    }
    
}
