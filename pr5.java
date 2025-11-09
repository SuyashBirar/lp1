import java.util.*;

// Base class for Scheduling algorithms
abstract class SchedulingAlgorithm {
    protected int n; // Number of processes
    protected Process[] processes; // Array to store processes

    public SchedulingAlgorithm(int n) {
        this.n = n;
        this.processes = new Process[n];
    }

    // Abstract method to be implemented by each scheduling algorithm
    public abstract void schedule();

    // Method to print process details
    protected void printResults() {
        System.out.println("Process\tAT\tBT\tPriority\tCT\tTAT\tWT");
        System.out.println("-------------------------------------------------------------");
        for (Process p : processes) {
            System.out.println(p);
        }
    }

    // Method to calculate average turnaround and waiting time
    protected void calculateAverages() {
        double avgTAT = 0, avgWT = 0;
        for (Process p : processes) {
            avgTAT += p.turnaroundTime;
            avgWT += p.waitingTime;
        }
        System.out.println("\nAverage Turnaround Time: " + (avgTAT / n) + " ms");
        System.out.println("Average Waiting Time: " + (avgWT / n) + " ms");
    }
}

// Class to represent a process
class Process {
    int processID;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int remainingTime;
    int priority; // Added for Priority Scheduling

    public Process(int processID, int arrivalTime, int burstTime) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = 0; // default
    }

    @Override
    public String toString() {
        return "P" + processID + "\t" + arrivalTime + "\t" + burstTime + "\t" + priority + "\t\t" +
                completionTime + "\t" + turnaroundTime + "\t" + waitingTime;
    }
}

// FCFS Scheduling Algorithm (First-Come-First-Serve)
class FCFS extends SchedulingAlgorithm {
    public FCFS(int n) {
        super(n);
    }

    @Override
    public void schedule() {
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime)
                currentTime = p.arrivalTime;

            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            currentTime = p.completionTime;
        }

        printResults();
        calculateAverages();
    }
}

// Priority Scheduling Algorithm (Non-Preemptive)
class PriorityScheduling extends SchedulingAlgorithm {
    public PriorityScheduling(int n) {
        super(n);
    }

    @Override
    public void schedule() {
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        boolean[] completed = new boolean[n];
        int completedCount = 0;

        while (completedCount < n) {
            Process highestPriority = null;
            int highestPriorityIndex = -1;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && processes[i].arrivalTime <= currentTime) {
                    if (highestPriority == null ||
                        processes[i].priority < highestPriority.priority) {
                        highestPriority = processes[i];
                        highestPriorityIndex = i;
                    }
                }
            }

            if (highestPriority == null) {
                currentTime++;
                continue;
            }

            currentTime += highestPriority.burstTime;
            highestPriority.completionTime = currentTime;
            highestPriority.turnaroundTime = highestPriority.completionTime - highestPriority.arrivalTime;
            highestPriority.waitingTime = highestPriority.turnaroundTime - highestPriority.burstTime;
            completed[highestPriorityIndex] = true;
            completedCount++;
        }

        printResults();
        calculateAverages();
    }
}

// Main class
public class pr5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Process Scheduling Simulator");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];
        System.out.println("\nSelect Scheduling Algorithm:");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Priority Scheduling (Non-Preemptive)");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        boolean priorityMode = (choice == 2);

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time for Process P" + (i + 1) + ": ");
            int arrivalTime = sc.nextInt();
            System.out.print("Enter Burst Time for Process P" + (i + 1) + ": ");
            int burstTime = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);

            if (priorityMode) {
                System.out.print("Enter Priority (lower number = higher priority): ");
                processes[i].priority = sc.nextInt();
            }
        }

        SchedulingAlgorithm algorithm;
        switch (choice) {
            case 1 -> algorithm = new FCFS(n);
            case 2 -> algorithm = new PriorityScheduling(n);
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        algorithm.processes = processes;
        algorithm.schedule();
    }
}

