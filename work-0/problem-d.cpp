#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
using namespace std;


int find_subsequence(int values[], int size, int value) {
    int soma = 0;
    int i = 0;
    int j = 0;
    while (soma != value && i < size) {
        while (soma < value && j < size) {
            soma += values[j];
            if (soma == value) {
                return (i + 1);
            }
            j++;
        }
        soma -= values[i];
        i++;
    }
    if (soma == value) {
        return (i + 1);
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
    while (size != 0) {
        clock_t start = clock() * 1000.0;
        subsequenceAt = find_subsequence(values, size, value);
        clock_t end = clock() * 1000.0;
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
    }
    std::cout << "Time is "
              << duration
              <<"\n";
    return 0;
}
