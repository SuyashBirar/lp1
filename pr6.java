import java.util.*;

// Class to represent a process
class Process {
    int pid;             // Process ID
    int arrivalTime;     // Arrival Time
    int burstTime;       // Burst Time
    int remainingTime;   // Remaining Time
    int completionTime;  // Completion Time
    int turnaroundTime;  // Turnaround Time
    int waitingTime;     // Waiting Time

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

// Base abstract class for Scheduling Algorithms
abstract class SchedulingAlgorithm {
    protected Process[] processes;
    protected int n;

    public SchedulingAlgorithm(int n) {
        this.n = n;
        this.processes = new Process[n];
    }

    public abstract void schedule();

    protected void printResults() {
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        System.out.println("----------------------------------------");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + 
                               p.completionTime + "\t" + p.turnaroundTime + "\t" + p.waitingTime);
        }
    }

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

// --------------------- SJF Preemptive (Shortest Remaining Time First) ---------------------
class SJFPreemptive extends SchedulingAlgorithm {
    public SJFPreemptive(int n) {
        super(n);
    }

    @Override
    public void schedule() {
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time

        int completed = 0, currentTime = 0, minRemaining;
        boolean[] isCompleted = new boolean[n];

        while (completed != n) {
            int shortest = -1;
            minRemaining = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time that has arrived
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && !isCompleted[i] &&
                    processes[i].remainingTime < minRemaining && processes[i].remainingTime > 0) {
                    minRemaining = processes[i].remainingTime;
                    shortest = i;
                }
            }

            if (shortest == -1) {
                currentTime++;
                continue;
            }

            // Execute the process for 1 unit of time
            processes[shortest].remainingTime--;
            currentTime++;

            // If the process is completed
            if (processes[shortest].remainingTime == 0) {
                isCompleted[shortest] = true;
                completed++;

                processes[shortest].completionTime = currentTime;
                processes[shortest].turnaroundTime = processes[shortest].completionTime - processes[shortest].arrivalTime;
                processes[shortest].waitingTime = processes[shortest].turnaroundTime - processes[shortest].burstTime;
            }
        }

        printResults();
        calculateAverages();
    }
}

// --------------------------- Round Robin Scheduling ---------------------------
class RoundRobin extends SchedulingAlgorithm {
    private int quantum;

    public RoundRobin(int n, int quantum) {
        super(n);
        this.quantum = quantum;
    }

    @Override
    public void schedule() {
        Queue<Process> queue = new LinkedList<>();
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time

        int currentTime = 0;
        int completed = 0;
        boolean[] isInQueue = new boolean[n];

        queue.add(processes[0]);
        isInQueue[0] = true;
        currentTime = processes[0].arrivalTime;

        while (!queue.isEmpty()) {
            Process current = queue.poll();

            // Execute the process for quantum or remaining time, whichever is smaller
            int execTime = Math.min(current.remainingTime, quantum);
            current.remainingTime -= execTime;
            currentTime += execTime;

            // Add newly arrived processes to queue
            for (int i = 0; i < n; i++) {
                if (!isInQueue[i] && processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0) {
                    queue.add(processes[i]);
                    isInQueue[i] = true;
                }
            }

            // If process is not finished, add it back to queue
            if (current.remainingTime > 0) {
                queue.add(current);
            } else {
                current.completionTime = currentTime;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
                completed++;
            }

            // If queue is empty but not all processes are completed, jump to next process arrival
            if (queue.isEmpty() && completed < n) {
                for (int i = 0; i < n; i++) {
                    if (!isInQueue[i] && processes[i].remainingTime > 0) {
                        queue.add(processes[i]);
                        isInQueue[i] = true;
                        currentTime = Math.max(currentTime, processes[i].arrivalTime);
                        break;
                    }
                }
            }
        }

        printResults();
        calculateAverages();
    }
}

// --------------------------- Main Class ---------------------------
public class pr6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("CPU Scheduling Simulator");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time for Process P" + (i + 1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter Burst Time for Process P" + (i + 1) + ": ");
            int bt = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt);
        }

        System.out.println("\nSelect Scheduling Algorithm:");
        System.out.println("1. Shortest Job First (Preemptive)");
        System.out.println("2. Round Robin (Preemptive)");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        SchedulingAlgorithm scheduler = null;

        switch (choice) {
            case 1 -> scheduler = new SJFPreemptive(n);
            case 2 -> {
                System.out.print("Enter Time Quantum: ");
                int tq = sc.nextInt();
                scheduler = new RoundRobin(n, tq);
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        scheduler.processes = processes;
        scheduler.schedule();
    }
}

