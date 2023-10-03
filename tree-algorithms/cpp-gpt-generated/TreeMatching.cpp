#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

using namespace std;

int dfs(int x, int parent, bool nextPickup, vector<set<int>>& adj, vector<vector<int>>& dp) {
    if (dp[x][nextPickup ? 1 : 0] != -1)
        return dp[x][nextPickup ? 1 : 0];

    if (nextPickup) {
        int maxVal = 0;
        int pickUpAll = 0;

        for (int neigh : adj[x]) {
            if (neigh == parent)
                continue;
            pickUpAll += dfs(neigh, x, true, adj, dp);
        }

        for (int neigh : adj[x]) {
            if (neigh == parent)
                continue;
            int pickup = 1 + dfs(neigh, x, false, adj, dp);
            maxVal = max(maxVal, pickUpAll - dp[neigh][1] + pickup);
        }

        dp[x][1] = maxVal;

        return maxVal;
    } else {
        int ans = 0;

        for (int neigh : adj[x]) {
            if (neigh == parent)
                continue;
            ans += max(dfs(neigh, x, true, adj, dp), dfs(neigh, x, false, adj, dp));
        }

        dp[x][0] = ans;

        return ans;
    }
}

int main() {
    int n;
    cin >> n;

    vector<set<int>> adj(n + 1);

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].insert(b);
        adj[b].insert(a);
    }

    vector<vector<int>> dp(n + 1, vector<int>(2, -1));

    cout << max(dfs(1, 0, false, adj, dp), dfs(1, 0, true, adj, dp)) << endl;

    return 0;
}
