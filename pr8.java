import java.util.Arrays;
import java.util.Scanner;

public class pr8 {

    // Utility function to print table neatly
    static void printAllocation(String title, int[] processSize, int[] allocation, int[] blockSize, int[] remblockSize) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println(title);
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-12s %-18s %-12s %-18s %-18s\n",
                "Process", "Process Size(KB)", "Block No", "Block Size(KB)", "Unused Space(KB)");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < processSize.length; i++) {
            if (allocation[i] != -1) {
                int b = allocation[i];
                int unused = remblockSize[b];
                System.out.printf("%-12s %-18d %-12d %-18d %-18d\n",
                        "P" + (i + 1), processSize[i], (b + 1), blockSize[b], unused);
            } else {
                System.out.printf("%-12s %-18d %-12s %-18s %-18s\n",
                        "P" + (i + 1), processSize[i], "Not Alloc", "-", "-");
            }
        }
    }

    // First Fit Algorithm
    static void firstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        int[] remblockSize = Arrays.copyOf(blockSize, m);
        Arrays.fill(allocation, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (remblockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    remblockSize[j] -= processSize[i];
                    break;
                }
            }
        }

        printAllocation("FIRST FIT ALLOCATION", processSize, allocation, blockSize, remblockSize);
    }

    // Next Fit Algorithm
    static void nextFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        int[] remblockSize = Arrays.copyOf(blockSize, m);
        Arrays.fill(allocation, -1);

        int j = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            boolean allocated = false;
            while (count < m) {
                if (remblockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    remblockSize[j] -= processSize[i];
                    allocated = true;
                    break;
                }
                j = (j + 1) % m;
                count++;
            }
            if (!allocated)
                allocation[i] = -1;
            else
                j = (j + 1) % m;
        }

        printAllocation("NEXT FIT ALLOCATION", processSize, allocation, blockSize, remblockSize);
    }

    // Worst Fit Algorithm
    static void worstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        int[] remblockSize = Arrays.copyOf(blockSize, m);
        Arrays.fill(allocation, -1);

        for (int i = 0; i < n; i++) {
            int worstIdx = -1;
            for (int j = 0; j < m; j++) {
                if (remblockSize[j] >= processSize[i]) {
                    if (worstIdx == -1 || remblockSize[j] > remblockSize[worstIdx])
                        worstIdx = j;
                }
            }
            if (worstIdx != -1) {
                allocation[i] = worstIdx;
                remblockSize[worstIdx] -= processSize[i];
            }
        }

        printAllocation("WORST FIT ALLOCATION", processSize, allocation, blockSize, remblockSize);
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of memory blocks: ");
        int m = sc.nextInt();
        int[] blockSize = new int[m];
        System.out.println("Enter size of each memory block:");
        for (int i = 0; i < m; i++) {
            blockSize[i] = sc.nextInt();
        }

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int[] processSize = new int[n];
        System.out.println("Enter size of each process:");
        for (int i = 0; i < n; i++) {
            processSize[i] = sc.nextInt();
        }

        // Call each required strategy
        firstFit(blockSize, m, processSize, n);
        nextFit(blockSize, m, processSize, n);
        worstFit(blockSize, m, processSize, n);

        sc.close();
    }
}
