import java.util.Scanner;

public class Main {
    private static long[][] matrice;

    private static long getMax() {
        long max = matrice[0][0];
        for (int i = 1; i < matrice.length; i++) {
            if (max < matrice[i][0]) {
                max = matrice[i][0];
            }
        }
        return max;
    }

    private static long getDigit(long number, long exponential) {
        return ((number/exponential)%10);
    }

    private static void fillMatrice(Scanner input, int numberOfLines, int numberOfColumns) {
        String strInput;
        String [] values;
        for (int i = 0; i < numberOfLines; i++) {
            if (input.hasNext()) {
                strInput = input.nextLine();
                values = strInput.split("\\s+");
                for (int j = 0; j < numberOfColumns; j++) {
                    matrice[i][j] = Long.parseLong(values[j]) + 1000;
                }
            }
        }
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (j != 0) {
                    matrice[i][0] *= 10000;
                    matrice[i][0] += matrice[i][j];
                }
            }
        }
    }

    private static void printMatrice() {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                if (j < matrice[0].length - 1) {
                    System.out.print(matrice[i][j]-1000 + " ");
                }
                else {
                    System.out.println(matrice[i][j]-1000);
                }
            }
        }
    }

    private static void countSort(long exponential)  {
        int i;
        int count[] = new int[10];
        long [][] temporaryMatrice = new long[matrice.length][matrice[0].length];

        for (i = 0; i < matrice.length; i++) {
            count[ ((int)(getDigit(matrice[i][0], exponential))) ]++;
        }

        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (i = matrice.length - 1; i >= 0; i--)  {
            temporaryMatrice[count[ ((int)(getDigit(matrice[i][0], exponential))) ] - 1] = matrice[i];
            count[ ((int)(getDigit(matrice[i][0], exponential))) ]--;
        }

        for (i = 0; i < matrice.length; i++) {
            matrice[i] = temporaryMatrice[i];
        }
    }

    private static void radixsort()  {
        long max = getMax();
        long exponential = 1;

        while (max / exponential > 0) {
            countSort(exponential);
            exponential *= 10;
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
        matrice = new long[numberOfLines][numberOfColumns];
        fillMatrice(input, numberOfLines, numberOfColumns);
        long startTime = System.nanoTime();
        radixsort();
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (j != 0) {
                    matrice[i][0] /= 10000;
                }
            }
        }
    }
}
