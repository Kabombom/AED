import time

def create_list_of_primes(max_prime):
    list_of_primes = []
    for i in range (max_prime):
        list_of_primes.append(True)
    return list_of_primes


def sundaram_sieve(list_of_primes):              
    for i in range(1, len(list_of_primes)):
        j = i
        if list_of_primes[i]:
            while(i + j + (2 * i * j) < len(list_of_primes)):
                list_of_primes[i + j + (2 * i * j)] = False
                j = j + 1
    print(2)
    for k in range(1, len(list_of_primes)):
        if (k * 2 + 1) > len(list_of_primes):
            break
        if list_of_primes[k]:
            print(k * 2 + 1)


if __name__ == '__main__':
    max_prime = int(raw_input())
    list_of_primes = create_list_of_primes(max_prime)
    start = time.time() * 1000
    sundaram_sieve(list_of_primes)
    end = time.time() * 1000
    print(end - start)
