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
    
    public static void main(String[] args) {
        
        CpuScheduler scheduler = null;

        // read file
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter 0 for FCFS, 1 for SJF, 2 for SRTF, and 3 for RR");
            int choice = input.nextInt();
            switch (choice) {
                case 0:
                    scheduler = new FCFS("FCFS");
                    ;
                    break;
                case 1:
                    scheduler = new SJF("SJF");
                    break;
                case 2:
                    scheduler = new SRTF("SRTF");
                    break;
                case 3:
                    System.out.println("Please enter number of quantum");
                    int quantum = input.nextInt();
                    scheduler = new RR("RR with q = " + quantum, quantum);
                    break;
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.trim().startsWith("//") || data.isEmpty()) continue;
                String[] arrOfStr = data.split("	"); 
                if ("numOfCPUs:".equals(arrOfStr[0]))
                {
                    int numOfCPUs = Integer.parseInt(arrOfStr[1]);
                    // Create list of CPUs
                    for (int i = 0; i < numOfCPUs; ++i) scheduler.getCPUs().add(new CPU(i));
                }
                else
                {   // Create list of processors
                    ArrayList<Integer> IORequestTimeArrayList = new ArrayList<Integer>();
                    if (arrOfStr.length != 3)
                    {
                        for (int j = 3; j < arrOfStr.length; ++j)
                            IORequestTimeArrayList.add(Integer.parseInt(arrOfStr[j]));
                    }
                    scheduler.getNew_queue().add(
                            new Process(arrOfStr[0],          // ID
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

        int TIME_UNIT    = 0;                               // Current time unit
        int numProcesses = scheduler.getNew_queue().size(); // Total number of processes to process

        System.out.printf("Time   ");
        for (CPU c : scheduler.getCPUs()) {
            System.out.printf("CPU %d   ",c.getID());
        }
        System.out.println("Ready Queue\t        I/O Queue");
        System.out.println("-------------------------------------------------------------------------");
        
        /***************************  MAIN LOOP  ******************************/
        // Loop increments TIME_UNIT by 1 every iteration
        // Stops when all processes has terminated
        // CPU Scheduler manages the cpus and processes by updating all these components
        while (numProcesses != scheduler.getTerminated_queue().size())
        //while (TIME_UNIT != 5)
        {
            // Scheduler performs updating operations before any processes runs
            scheduler.update(TIME_UNIT++);

            // Running all processes
            if (numProcesses != scheduler.getTerminated_queue().size())
                scheduler.run(TIME_UNIT);
            
        }
        scheduler.getStatistics();
    }
}
