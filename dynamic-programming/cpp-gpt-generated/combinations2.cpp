#include <iostream>
#include <vector>

using namespace std;

const int MOD = 1e9 + 7;

int n, x;
vector<int> coins;
vector<vector<int>> memo;

int dp(int i, int j) {
    if (j == 0) {
        return 1;
    }
    if (i == n || j < 0) {
        return 0;
    }
    if (memo[i][j] != -1) {
        return memo[i][j];
    }
    int ans = (dp(i, j - coins[i]) + dp(i + 1, j)) % MOD;
    memo[i][j] = ans;
    return ans;
}

int main() {
    cin >> n >> x;

    coins.resize(n);
    for (int i = 0; i < n; i++) {
        cin >> coins[i];
    }

    memo.assign(n, vector<int>(x + 1, -1));

    cout << dp(0, x) << endl;

    return 0;
}
