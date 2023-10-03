#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

void dfsForHeight(int x, vector<int>& distance, int d, vector<vector<int>>& adj, int parent) {
    distance[x] = d;
    for (int child : adj[x]) {
        if (child == parent) continue;
        dfsForHeight(child, distance, d + 1, adj, x);
    }
}

void build(vector<vector<int>>& ancestors, int m, int n) {
    for (int j = 1; j <= m; j++) {
        for (int i = 1; i <= n; i++) {
            ancestors[i][j] = ancestors[i][j - 1] == -1 ? -1 : ancestors[ancestors[i][j - 1]][j - 1];
        }
    }
}

int LCA(int u, int v, vector<int>& distance, vector<vector<int>>& ancestors, int m) {
    if (distance[u] > distance[v]) {
        swap(u, v);
    }

    int k = distance[v] - distance[u];

    for (int j = m; j >= 0; j--) {
        if ((k & (1 << j)) != 0) {
            v = ancestors[v][j];
        }
    }

    if (u == v) return u;

    for (int j = m; j >= 0; j--) {
        if (ancestors[u][j] != ancestors[v][j]) {
            u = ancestors[u][j];
            v = ancestors[v][j];
        }
    }

    return ancestors[u][0];
}

int main() {
    int n, q;
    cin >> n >> q;

    int m = static_cast<int>(log2(200000)) + 1;

    vector<vector<int>> ancestors(n + 1, vector<int>(m + 1, 0));
    vector<vector<int>> adj(n + 1);

    for (int i = 2; i <= n; i++) {
        int u;
        cin >> u;
        ancestors[i][0] = u;
        adj[i].push_back(u);
        adj[u].push_back(i);
    }

    ancestors[1][0] = -1;
    build(ancestors, m, n);
    vector<int> distance(n + 1);
    dfsForHeight(1, distance, 0, adj, 0);

    for (int i = 0; i < q; i++) {
        int u, v;
        cin >> u >> v;
        cout << LCA(u, v, distance, ancestors, m) << endl;
    }

    return 0;
}
