import java.util.Scanner;

public class Main {
    static void fillMatrice(Scanner input, int[][] matrice, int numberOfLines, int numberOfColumns) {
        String strInput;
        String [] values;
        for (int i = 0; i < numberOfLines; i++) {
            strInput = input.nextLine();
            values = strInput.split("\\s+");
            for (int j = 0; j < numberOfColumns; j++) {
                matrice[i][j] = Integer.parseInt(values[j]);
            }
        }
    }

    static int compareMatriceLines(int[] line1, int[] line2) {
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

    static void printMatrice(int[][] matrice, int numberOfLines, int numberOfColumns) {
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

    static void insertionSort(int[][] matrice, int numberOfLines, int numberOfColumns) {
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

    public static void main(String[] args) {
        int numberOfLines, numberOfColumns;
        Scanner input = new Scanner(System.in);
        String strInput;
        String [] sizes;
        strInput = input.nextLine();
        sizes = strInput.split("\\s+");
        numberOfLines = Integer.parseInt(sizes[0]);
        numberOfColumns = Integer.parseInt(sizes[1]);
        int[][] matrice = new int[numberOfLines][numberOfColumns];
        fillMatrice(input, matrice, numberOfLines, numberOfColumns);
        insertionSort(matrice, numberOfLines, numberOfColumns);
        printMatrice(matrice, numberOfLines, numberOfColumns);
    }

}