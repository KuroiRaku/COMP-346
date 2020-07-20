import java.util.Scanner;

public class FCFS {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the number of CPUs:");
		int input = scanner.nextInt();
		
		int processID[]= new int[input];
		int arrivalTime[]= new int[input];
		int burstTime[]= new int[input];
		int completionTime[]= new int[input];
		int turnAroundTime[]= new int[input];
		int waitingTime[]= new int[input];
		int tmp; 
		float totalWaitingTime=0;
		float totalTurnAroundTime=0;
		
		for (int i=0; i<input; i++) 
		{
			processID[i]=i;

			System.out.println("Plese enter the process " +(i+1)+" arrival time:");
			arrivalTime[i] =scanner.nextInt();
			
			System.out.println("Please enter the process "+ (i+1)+ " burst time");
			burstTime[i]=scanner.nextInt();
			
		}
		
		//sort based on arrivalTime:
		for(int i=0; i<input; i++)
		{
			for(int j=0; j<input-(i+1); j++)
			{
				if(arrivalTime[j] > arrivalTime[j+1])
				{
					tmp=arrivalTime[j];
					arrivalTime[j]=arrivalTime[j+1];
					arrivalTime[j+1]=tmp;
					tmp=burstTime[j];
					burstTime[j]=burstTime[j+1];
					burstTime[j+1]=tmp;
					tmp = processID[j];
					
					processID[j]=processID[j+1];
					processID[j+1]=tmp;
					
				}
			}
		}
		
		//the Completion Times:
		for(int i=0; i<input; i++) 
		{
			if(i==0)
			{
				completionTime[i]=arrivalTime[i] + burstTime[i];
			} 
			else 
			{
				if(arrivalTime[i]>completionTime[i-1])
				{
					completionTime[i] = arrivalTime[i]+burstTime[i];
				}
				else
					completionTime[i] = completionTime[i-1]+burstTime[i];
			}
			
			turnAroundTime[i] = completionTime[i] - arrivalTime[i];
			waitingTime[i]= turnAroundTime[i] - burstTime[i];
			totalWaitingTime += waitingTime[i];			
			totalTurnAroundTime += turnAroundTime[i];		
		}
		System.out.println("processID  Arrival      Burst  Complete  Turn-Around     Wait");
		for(int i=0; i<input; i++)
		{
			System.out.println(processID[i] + " \t     " + arrivalTime[i] + " \t " + burstTime[i] + " \t " + completionTime[i] + " \t    " +turnAroundTime[i]+"  \t    "+ waitingTime[i]);
		}
		scanner.close();
		System.out.println("average waiting time:" + (totalWaitingTime/input));
		System.out.println("average turn around time:" + (totalTurnAroundTime/input));

	}
}


