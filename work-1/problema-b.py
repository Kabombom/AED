import math
import time

def is_prime(number):
    if(number % 2) == 0:
        return False
    i = 3
    while(i <= math.sqrt(number)):
        if(number % i) == 0:
            return False
        i = i + 2
    return True


def list_of_primes(max_prime):
    list_primes = []
    list_primes.append(2)
    for j in range(2, max_prime):
        if (is_prime(j)):
            list_primes.append(j)
    return list_primes


if __name__ == '__main__':
    max_prime = int(raw_input())
    start = time.time() * 1000
    list_primes = list_of_primes(max_prime)
    end = time.time() * 1000
    print (end - start)
