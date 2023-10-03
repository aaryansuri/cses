#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

const int MAX = 1000000;
const int MOD = 1000000007;

long exponentiation(long a, long b, int mod) {
    if(b == 0) return 1;
    long sq = exponentiation(a, b / 2, mod) ;
    if(b % 2 == 0) {
        return (sq * sq) % mod;
    }
    return (a * ((sq * sq) % mod)) % mod;
}

void calFactorialAndInverse(vector<long>& factorial, vector<long>& inverseFactorial, int mod) {
    factorial[0] = 1;
    inverseFactorial[0] = 1;

    for(int i = 1; i < factorial.size(); i++) {
        factorial[i] = (i * factorial[i - 1]) % mod;
        inverseFactorial[i] = exponentiation(factorial[i], mod - 2, mod);
    }
}

int main() {
    string input;
    cin >> input;
    int n = 1000000;

    vector<long> factorial(n + 1);
    vector<long> inverseFactorial(n + 1);

    unordered_map<char, int> duplicates;

    for(char c : input) {
        duplicates[c]++;
    }

    calFactorialAndInverse(factorial, inverseFactorial, MOD);

    long ans = factorial[input.length()];

    for(const auto& entry : duplicates) {
        if(entry.second >= 2) {
            ans = (ans * inverseFactorial[entry.second]) % MOD;
        }
    }

    cout << ans << endl;

    return 0;
}
