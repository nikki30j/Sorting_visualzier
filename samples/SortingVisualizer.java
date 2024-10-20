import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SortingVisualizer extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 910;
    private static final int SCREEN_HEIGHT = 750;
    private static final int ARR_SIZE = 130;
    private static final int RECT_SIZE = 7;

    private int[] arr = new int[ARR_SIZE];
    private int[] Barr = new int[ARR_SIZE];
    private boolean complete = false;

    public SortingVisualizer() {
        randomizeAndSaveArray();
        loadArr();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer sortingVisualizer = new SortingVisualizer();
        frame.add(sortingVisualizer);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        sortingVisualizer.intro();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        visualize(g, -1, -1, -1);
    }

    private void visualize(Graphics g, int x, int y, int z) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        int j = 0;
        for (int i = 0; i <= SCREEN_WIDTH - RECT_SIZE; i += RECT_SIZE) {
            if (complete) {
                g.setColor(new Color(100, 180, 100)); // sorted color
            } else if (j == x || j == z) {
                g.setColor(new Color(100, 180, 100)); // comparison color
            } else if (j == y) {
                g.setColor(new Color(165, 105, 189)); // swap color
            } else {
                g.setColor(new Color(170, 183, 184)); // unsorted color
            }

            g.fillRect(i, SCREEN_HEIGHT - arr[j], RECT_SIZE, arr[j]);
            j++;
        }
    }

    private void inplaceHeapSort(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int childIndex = i;
            int parentIndex = (childIndex - 1) / 2;

            while (childIndex > 0) {
                if (input[childIndex] > input[parentIndex]) {
                    int temp = input[parentIndex];
                    input[parentIndex] = input[childIndex];
                    input[childIndex] = temp;
                } else {
                    break;
                }

                visualize(getGraphics(), parentIndex, childIndex, -1);
                delay(40);

                childIndex = parentIndex;
                parentIndex = (childIndex - 1) / 2;
            }
        }

        for (int heapLast = input.length - 1; heapLast >= 0; heapLast--) {
            int temp = input[0];
            input[0] = input[heapLast];
            input[heapLast] = temp;

            int parentIndex = 0;
            int leftChildIndex = 2 * parentIndex + 1;
            int rightChildIndex = 2 * parentIndex + 2;

            while (leftChildIndex < heapLast) {
                int maxIndex = parentIndex;

                if (input[leftChildIndex] > input[maxIndex]) {
                    maxIndex = leftChildIndex;
                }
                if (rightChildIndex < heapLast && input[rightChildIndex] > input[maxIndex]) {
                    maxIndex = rightChildIndex;
                }
                if (maxIndex == parentIndex) {
                    break;
                }

                int temp2 = input[parentIndex];
                input[parentIndex] = input[maxIndex];
                input[maxIndex] = temp2;

                visualize(getGraphics(), maxIndex, parentIndex, heapLast);
                delay(40);

                parentIndex = maxIndex;
                leftChildIndex = 2 * parentIndex + 1;
                rightChildIndex = 2 * parentIndex + 2;
            }
        }
        complete = true;
    }

    private void selectionSort() {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
                visualize(getGraphics(), i, minIndex, -1);
                delay(1);
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        complete = true;
    }

    private void randomizeAndSaveArray() {
        Random random = new Random();
        for (int i = 0; i < ARR_SIZE; i++) {
            Barr[i] = random.nextInt(SCREEN_HEIGHT);
        }
    }

    private void loadArr() {
        System.arraycopy(Barr, 0, arr, 0, ARR_SIZE);
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void intro() {
        System.out.println("==============================Sorting Visualizer==============================\n\n"
                + "Visualization of different sorting algorithms in Java. A sorting algorithm puts the elements of a list in a certain order.\n"
                + "The sorting algorithms covered are Selection Sort, Insertion Sort, Bubble Sort, Merge Sort, Quick Sort, and Heap Sort.\n"
                + "The list size is fixed to 130 elements. You can randomize the list and select any sorting algorithm.\n\n"
                + "Press ENTER to show controls...");

        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        showControls();
    }

    private void showControls() {
        System.out.println("\nAvailable Controls inside Sorting Visualizer:\n"
                + "    Use 0 to Generate a different randomized list.\n"
                + "    Use 1 to start Selection Sort Algorithm.\n"
                + "    Use 6 to start Heap Sort Algorithm.\n"
                + "    Use q to exit out of Sorting Visualizer\n\n"
                + "PRESS ENTER TO START SORTING VISUALIZER...\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "0":
                randomizeAndSaveArray();
                loadArr();
                break;
            case "1":
                loadArr();
                selectionSort();
                break;
            case "6":
                loadArr();
                inplaceHeapSort(arr);
                break;
            case "q":
                System.exit(0);
                break;
        }
        repaint();
    }
}
