#include <iostream>
#include <vector>

using namespace std;

long long towers(int n) {

    vector<vector<long long>> dp(n+1, vector<long long>(2, 0));

    dp[1][1] = 1;
    dp[1][0] = 1;

    for(int i = 2; i <= n; i++) {
        dp[i][0] = (dp[i-1][1] + 4 * dp[i-1][0]) % 1000000007;
        dp[i][1] = (dp[i-1][0] + 2 * dp[i-1][1]) % 1000000007;
    }

    return (dp[n][0] + dp[n][1]) % 1000000007;
}

int main() {

    int n;
    cin >> n;

    string output = "";
    vector<int> input(n);

    for(int i = 0; i < n; i++) {
        cin >> input[i];
    }

    for(int num : input) {
        output += to_string(towers(num)) + "\n";
    }

    cout << output;

    return 0;
}
