import java.util.*;

public class pr10 {

    // FIFO Algorithm
    public static void fifo(int[] pages, int framesCount) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> memory = new HashSet<>();
        int pageFaults = 0;

        System.out.println("\nFIFO Page Replacement Simulation:");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Page\tFrames\t\tStatus");
        System.out.println("-------------------------------------------------------------");

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

        System.out.println("-------------------------------------------------------------");
        System.out.println("Total Page Faults (FIFO): " + pageFaults);
    }

    // Optimal Algorithm
    public static void optimal(int[] pages, int framesCount) {
        List<Integer> memory = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("\nOptimal Page Replacement Simulation:");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Page\tFrames\t\tStatus");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (!memory.contains(page)) {
                if (memory.size() < framesCount) {
                    memory.add(page);
                } else {
                    int farthest = -1, replaceIndex = -1;

                    for (int j = 0; j < memory.size(); j++) {
                        int currentPage = memory.get(j);
                        int nextUse = Integer.MAX_VALUE;

                        for (int k = i + 1; k < pages.length; k++) {
                            if (pages[k] == currentPage) {
                                nextUse = k;
                                break;
                            }
                        }

                        if (nextUse > farthest) {
                            farthest = nextUse;
                            replaceIndex = j;
                        }
                    }

                    memory.set(replaceIndex, page);
                }
                pageFaults++;
                System.out.printf("%d\t%s\tFault\n", page, memory);
            } else {
                System.out.printf("%d\t%s\tHit\n", page, memory);
            }
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("Total Page Faults (Optimal): " + pageFaults);
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
        optimal(referenceString, frames);

        sc.close();
    }
}
