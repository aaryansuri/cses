#include <iostream>
#include <vector>
using namespace std;

const int MAX = 1e6+10;

int main() {
    int n;
    cin >> n;

    vector<int> values(n);
    for (int i = 0; i < n; i++) {
        cin >> values[i];
    }

    vector<vector<int>> divisorOf(MAX);

    for (int i = 2; i < MAX; i++) {
        if (divisorOf[i].size() == 0) {
            divisorOf[i].push_back(i);
            for (int j = 2 * i; j < MAX; j += i) {
                divisorOf[j].push_back(i);
            }
        }
    }

    vector<int> valuesDivisibleBy(MAX);
    vector<int> primeDivisorOf(MAX);

    for (int i = 0; i < n; i++) {
        vector<int>& divisorOfL = divisorOf[values[i]];
        for (int mask = 1; mask < (1 << divisorOfL.size()); mask++) {
            int combination = 1;
            int primeDivisor = 0;
            for (int pos = 0; pos < divisorOfL.size(); pos++) {
                if (mask & (1 << pos)) {
                    combination *= divisorOfL[pos];
                    primeDivisor++;
                }
            }
            valuesDivisibleBy[combination]++;
            primeDivisorOf[combination] = primeDivisor;
        }
    }

    long long totalPairs = (static_cast<long long>(n) * (n - 1)) / 2;
    long long validPairs = 0;

    for (int i = 0; i < MAX; i++) {
        if (primeDivisorOf[i] % 2 == 1) {
            validPairs += (static_cast<long long>(valuesDivisibleBy[i]) * (valuesDivisibleBy[i] - 1)) / 2;
        } else {
            validPairs -= (static_cast<long long>(valuesDivisibleBy[i]) * (valuesDivisibleBy[i] - 1)) / 2;
        }
    }

    cout << totalPairs - validPairs << endl;

    return 0;
}
