#include <iostream>
#include <algorithm>
using namespace std;

int maxPagesIdp(int prices[], int pages[], int n, int x) {
    int dp[n + 1][x + 1];
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= x; j++) {
            if (i == 0 || j == 0) {
                dp[i][j] = 0;
            } else {
                dp[i][j] = dp[i - 1][j];
                if (j - prices[i - 1] >= 0) {
                    dp[i][j] = max(dp[i][j], pages[i - 1] + dp[i - 1][j - prices[i - 1]]);
                }
            }
        }
    }
    return dp[n][x];
}

int main() {
    int n, x;
    cin >> n >> x;
    int prices[n], pages[n];
    for (int i = 0; i < n; i++) {
        cin >> prices[i];
    }
    return 0;
}