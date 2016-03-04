#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
using namespace std;

int find_subsequence(int values[], int size, int value) {
    for (int i = 0; i < size; i++) {
        int soma = 0;
        int j = i;
        while (j < size && soma < value) {
            soma += values[j];
            if (soma == value) {
                return (i + 1);
            }
            j++;
        }
    }
    return -1;
}

int main() {
    int size, value, subsequenceAt;
    scanf("%d %d", &size, &value);
    int values[size];
    for (int k = 0; k < size; k++) {
        values[k] = rand() % 100 + 1;
    }
    
    double duration;
    while (size != 0 && value != 0) {
        clock_t start = clock();
        subsequenceAt = find_subsequence(values, size, value);
        clock_t end = clock();
        duration = (double) (end - start) / CLOCKS_PER_SEC;

        if (subsequenceAt != -1) {
            printf("SUBSEQUENCIA NA POSICAO %d\n", subsequenceAt);
        } else {
            printf("SUBSEQUENCIA NAO ENCONTRADA\n");
        }
        scanf("%d %d", &size, &value);
        for (int k = 0; k < size; k++) {
            values[k] = rand() % 100 + 1;
        }
        std::cout << "Time is "
                  << duration
                  << "\n";
    }
    return 0;
}
