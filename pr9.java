import java.util.*;

public class pr9 {
    // FIFO Algorithm
    public static void fifo(int[] pages, int framesCount) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> memory = new HashSet<>();
        int pageFaults = 0;

        System.out.println("\nFIFO Page Replacement Simulation:");
        System.out.println("------------------------------------------------------------");
        System.out.println("Page\tFrames\t\tStatus");
        System.out.println("------------------------------------------------------------");

        for (int page : pages) {
            if (!memory.contains(page)) {
                if (memory.size() < framesCount) {
                    memory.add(page);
                    queue.add(page);
                } else {
                    int removed = queue.poll();
                    memory.remove(removed);
                    memory.add(page);
                    queue.add(page);
                }
                pageFaults++;
                System.out.printf("%d\t%s\tFault\n", page, memory);
            } else {
                System.out.printf("%d\t%s\tHit\n", page, memory);
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("Total Page Faults (FIFO): " + pageFaults);
    }

    // LRU Algorithm
    public static void lru(int[] pages, int framesCount) {
        List<Integer> memory = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("\nLRU Page Replacement Simulation:");
        System.out.println("------------------------------------------------------------");
        System.out.println("Page\tFrames\t\tStatus");
        System.out.println("------------------------------------------------------------");

        for (int page : pages) {
            if (!memory.contains(page)) {
                if (memory.size() < framesCount) {
                    memory.add(page);
                } else {
                    memory.remove(0); // Remove least recently used
                    memory.add(page);
                }
                pageFaults++;
                System.out.printf("%d\t%s\tFault\n", page, memory);
            } else {
                memory.remove((Integer) page);
                memory.add(page); // Move this page to most recently used
                System.out.printf("%d\t%s\tHit\n", page, memory);
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("Total Page Faults (LRU): " + pageFaults);
    }

    // Main Function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter length of reference string: ");
        int n = sc.nextInt();

        int[] referenceString = new int[n];
        System.out.println("Enter reference string (space-separated pages):");
        for (int i = 0; i < n; i++) {
            referenceString[i] = sc.nextInt();
        }

        fifo(referenceString, frames);
        lru(referenceString, frames);

        sc.close();
    }
}
