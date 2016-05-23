import java.util.Scanner;

public class Main {
    private static int[][] matrice;

    private static void fillMatrice(Scanner input, int numberOfLines, int numberOfColumns) {
        String strInput;
        String [] values;
        for (int i = 0; i < numberOfLines; i++) {
            if (input.hasNext()) {
                strInput = input.nextLine();
                values = strInput.split("\\s+");
                for (int j = 0; j < numberOfColumns; j++) {
                    matrice[i][j] = Integer.parseInt(values[j]);
                }
            }
        }
    }

    private static int compareMatriceLines(int[] line1, int[] line2) {
        int i = 0;
        for (int columnElement : line1) {
            if (columnElement > line2[i]) {
                return 0;
            }
            else if (columnElement < line2[i]){
                return 1;
            }
            else {
                i++;
            }
        }
        return 2;
    }

    private static void printMatrice(int numberOfLines, int numberOfColumns) {
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (j < numberOfColumns - 1) {
                    System.out.print(matrice[i][j] + " ");
                }
                else {
                    System.out.println(matrice[i][j]);
                }
            }
        }
    }

    private static void insertionSort(int numberOfLines) {
        int j;
        for (int i = 1; i < numberOfLines; i++) {
            j = i -1;
            int[] key = matrice[i];

            while (j >= 0 && compareMatriceLines(matrice[j], key) == 0) {
                matrice[j + 1] = matrice[j];
                j--;
            }
            matrice[j + 1] = key;
        }
    }

    private static void swapLines(int index1, int index2) {
        int []saveLine = matrice[index1];
        matrice[index1] = matrice[index2];
        matrice[index2] = saveLine;
    }

    private static int partition(int left, int right) {
        int []pivot = matrice[left];
        int low = left + 1;
        int high = right;

        while (high > low) {
            while (low <= high && compareMatriceLines(matrice[low], pivot) != 0) {
                low++;
            }

            while (low <= high && compareMatriceLines(matrice[high], pivot) == 0) {
                high--;
            }

            if (high > low) {
                swapLines(high, low);
            }
        }

        while (high > left && compareMatriceLines(matrice[high], pivot) != 1) {
            high--;
        }

        if (compareMatriceLines(pivot, matrice[high]) == 0) {
            matrice[left] = matrice[high];
            matrice[high] = pivot;
            return high;
        }
        else {
            return left;
        }
    }


    private static void quickSort(int left, int right) {
        if (left < right) {
            int partitionIndex = partition(left, right);
            quickSort(left, partitionIndex-1);
            quickSort(partitionIndex+1, right);
        }
    }

    public static void main(String[] args) {
        int numberOfLines, numberOfColumns;
        Scanner input = new Scanner(System.in);
        String strInput;
        String [] sizes;
        strInput = input.nextLine();
        sizes = strInput.split("\\s+");
        numberOfLines = Integer.parseInt(sizes[0]);
        numberOfColumns = Integer.parseInt(sizes[1]);
        matrice = new int[numberOfLines][numberOfColumns];
        fillMatrice(input, numberOfLines, numberOfColumns);
        if (numberOfLines <= 10) {
            long timeStart = System.nanoTime();
            insertionSort(numberOfLines);
            long timeEnd = System.nanoTime();
            System.out.println(timeEnd - timeStart);
        }
        else {
            long timeStart = System.nanoTime();
            quickSort(0, numberOfLines - 1);
            long timeEnd= System.nanoTime();
            System.out.println(timeEnd - timeStart);
        }
    }
}
