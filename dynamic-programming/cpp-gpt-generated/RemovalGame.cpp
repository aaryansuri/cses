#include <iostream>
#include <vector>

using namespace std;

struct Pair {
    long first;
    long second;

    Pair(long a, long b) : first(a), second(b) {}

    Pair() : first(0), second(0) {}
};

long maxScore(vector<long>& arr) {
    int n = arr.size();

    Pair dp[n][n];

    for(int i = 0; i < n; i++) {
        dp[i][i] = Pair(arr[i], 0);
    }

    for(int d = 1; d < n; d++) {
        for(int i = 0; i < n - d; i++) {
            int l = i;
            int r = i + d;

            if(arr[l] + dp[l + 1][r].second >= arr[r] + dp[l][r - 1].second) {
                dp[l][r] = Pair(arr[l] + dp[l + 1][r].second, dp[l + 1][r].first);
            } else {
                dp[l][r] = Pair(arr[r] + dp[l][r - 1].second, dp[l][r - 1].first);
            }
        }
    }

    return dp[0][n - 1].first;
}

int main() {
    int n;
    cin >> n;

    vector<long> arr(n);
    for(int i = 0; i < n; i++) {
        cin >> arr[i];
    }

    cout << maxScore(arr) << endl;
    return 0;
}
