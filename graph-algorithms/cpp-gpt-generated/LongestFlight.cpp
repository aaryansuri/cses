#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int dfs(int city, vector<vector<int>>& adj, vector<int>& parentCities, vector<int>& dp) {
    if (city == parentCities.size() - 1)
        return 1;

    int maxDistance = -1;

    for (int neigh : adj[city]) {
        if (dp[neigh] == -1)
            continue;

        if (dp[neigh] > 0) {
            if (dp[neigh] + 1 >= maxDistance) {
                parentCities[neigh] = city;
                maxDistance = dp[neigh] + 1;
            }
        } else {
            int distance = dfs(neigh, adj, parentCities, dp);
            if (distance > 0) {
                if (distance + 1 >= maxDistance) {
                    maxDistance = distance + 1;
                    parentCities[neigh] = city;
                }
            }
        }
    }

    dp[city] = maxDistance;

    return maxDistance;
}

int main() {
    int n, m;
    cin >> n >> m;

    vector<vector<int>> adj(n + 1);
    for (int i = 0; i < m; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
    }

    vector<int> parentCities(n + 1, 0);
    vector<int> dp(n + 1, 0);

    int longest = dfs(1, adj, parentCities, dp);
    if (longest == -1) {
        cout << "IMPOSSIBLE" << endl;
        return 0;
    }

    vector<int> path;
    int curr = n;
    while (curr != 1) {
        path.push_back(curr);
        curr = parentCities[curr];
    }
    path.push_back(curr);

    reverse(path.begin(), path.end());
    string answer;
    for (int i = 0; i < path.size(); i++) {
        answer += to_string(path[i]);
        if (i != path.size() - 1)
            answer += " ";
    }
    cout << longest << endl;
    cout << answer << endl;

    return 0;
}
