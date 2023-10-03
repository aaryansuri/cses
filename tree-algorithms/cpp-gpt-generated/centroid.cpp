#include <iostream>
#include <vector>

using namespace std;

int dfsFindCentroid(int x, int parent, vector<vector<int>>& adj, vector<int>& subTreeSize, int n) {
    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        if (subTreeSize[neigh] >= n / 2) return dfsFindCentroid(neigh, x, adj, subTreeSize, n);
    }
    return x;
}

void dfs1(int x, int parent, vector<vector<int>>& adj, vector<int>& subTreeSize) {

    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        dfs1(neigh, x, adj, subTreeSize);
        subTreeSize[x] += 1 + subTreeSize[neigh];
    }
}

int main() {
    int n;
    cin >> n;

    vector<vector<int>> adj(n + 1);

    for (int i = 1; i <= n; i++) {
        adj[i].clear();
    }

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    vector<int> subTreeSize(n + 1, 0);

    dfs1(1, 0, adj, subTreeSize);

    cout << dfsFindCentroid(1, 0, adj, subTreeSize, n) << endl;

    return 0;
}
