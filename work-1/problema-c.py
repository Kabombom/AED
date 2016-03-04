import time

def create_list(max_prime):
    list_primes = []
    for i in range (max_prime):
        list_primes.append(True)
    return list_primes


def erasthotenes_sieve(list_primes):
    for i in range(2, len(list_primes)):
        j = i
        if list_primes[i]:
            while(j * i < len(list_primes)):
                list_primes[i * j] = False
                j = j + 1


if __name__ == '__main__':
    max_prime = int(raw_input())
    list_primes = create_list(max_prime) 
    start = time.time() * 1000
    erasthotenes_sieve(list_primes)
    end = time.time() * 1000
    print(end - start)
