import java.util.Scanner;

public class SJF {
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the number of process:");
		
		int input = scanner.nextInt();
		int processID[]=new int[input];
		int arrivalTime[]=new int[input];
		int burstTime[]=new int[input];
		int completionTime[]=new int[input];
		int turnAroundTime[]=new int[input];
		int waitingTime[]=new int[input];
		int check[]=new int[input];
		
		int st=0;
		int total=0;
		float averageWaitingTime=0;
		float averageTurnAroundTime=0;
		
		for(int i=0; i<input; i++)
		{
			processID[i]=i;
			check[i]=0;
			
			System.out.println("Plese enter the process " +(i+1)+" arrival time:");
			arrivalTime[i] =scanner.nextInt();
			
			System.out.println("Please enter the process "+ (i+1)+ " burst time");
			burstTime[i]=scanner.nextInt();
		}
		
		boolean x=true;
		while(true)
		{
			int k = input;
			int min = 999;
			
			if (total == input)
				break;
			
			for(int i=0; i<input; i++) 
			{
				if((arrivalTime[i] <= st) && (check[i]==0) && (burstTime[i]<min))
				{
					min=burstTime[i];
					k=i;
				}
			}
			

			if(k==input)
				st++;
			else
			{
				completionTime[k]=st+burstTime[k];
				st+=burstTime[k];
				turnAroundTime[k]=completionTime[k]-arrivalTime[k];
				waitingTime[k]=turnAroundTime[k]-burstTime[k];
				check[k]=1;
				total++;
			}
		}
		
		System.out.println("processID  Arrival    Burst  Complete  Turn-Around     Wait");
		for(int i=0; i<input; i++)
		{
			averageWaitingTime+=waitingTime[i];
			averageTurnAroundTime+= turnAroundTime[i];
			System.out.println(processID[i] + " \t     " + arrivalTime[i] + " \t       " + burstTime[i] + " \t  " + completionTime[i] + " \t    " +turnAroundTime[i]+"  \t         "+ waitingTime[i]);
		}
		
		System.out.println("average turn around time is " + (float)(averageTurnAroundTime/input));
		System.out.println("average waiting time is "+(float)(averageWaitingTime/input));
		
		scanner.close();
		
	}

}
