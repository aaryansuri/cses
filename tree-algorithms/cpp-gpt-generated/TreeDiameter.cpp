#include <iostream>
#include <vector>

using namespace std;

int maxDiameter = 0;

int dfs(int x, int parent, const vector<vector<int>>& adj) {
    int h1 = 0, h2 = 0;

    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        int neighHeight = 1 + dfs(neigh, x, adj);
        if (neighHeight > h2) {
            if (neighHeight > h1) {
                h2 = h1;
                h1 = neighHeight;
            } else {
                h2 = neighHeight;
            }
        }
        maxDiameter = max(maxDiameter, h1 + h2);
    }

    return h1;
}

int main() {
    int n;
    cin >> n;

    vector<vector<int>> adj(n + 1);

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    dfs(1, 0, adj);

    cout << maxDiameter << endl;

    return 0;
}
