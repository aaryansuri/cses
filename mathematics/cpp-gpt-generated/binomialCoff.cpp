#include <iostream>
#include <vector>
using namespace std;

const int MAX = 1000000;
const int MOD = 1000000007;

long binomialCoff(vector<long>& factorial, vector<long>& inverseFactorial, int a, int b) {
    return (((factorial[a] * inverseFactorial[a - b]) % MOD) * inverseFactorial[b]) % MOD;
}

long exponentiation(long a, long b) {
    if (b == 0) return 1;
    long sq = exponentiation(a, b / 2);
    if (b % 2 == 0) {
        return (sq * sq) % MOD;
    }
    return (a * ((sq * sq) % MOD)) % MOD;
}

void calFactorialAndInverse(vector<long>& factorial, vector<long>& inverseFactorial) {
    factorial[0] = 1;
    inverseFactorial[0] = 1;

    for (int i = 1; i < factorial.size(); i++) {
        factorial[i] = (i * factorial[i - 1]) % MOD;
        inverseFactorial[i] = exponentiation(factorial[i], MOD - 2);
    }
}


int main() {
    int n;
    cin >> n;
    vector<long> factorial(MAX + 1);
    vector<long> inverseFactorial(MAX + 1);

    calFactorialAndInverse(factorial, inverseFactorial);

    for (int i = 0; i < n; i++) {
        int a, b;
        cin >> a >> b;
        cout << binomialCoff(factorial, inverseFactorial, a, b) << "\n";
    }

    return 0;
}
