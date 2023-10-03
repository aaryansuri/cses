#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

const int MAXN = 200005;

void dfsSum(int x, int parent, vector<vector<int>>& adj, int* paths) {
    int sum = 0;
    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        dfsSum(neigh, x, adj, paths);
        sum += paths[neigh];
    }
    paths[x] += sum;
}

void dfs(int x, int parent, vector<vector<int>>& adj, vector<vector<int>>& ancestors, int m, int d, int* distance, int* paths) {
    distance[x] = d;

    for (int neigh : adj[x]) {
        if (neigh == parent) continue;

        ancestors[neigh][0] = x;
        for (int j = 1; j <= m; j++) {
            ancestors[neigh][j] = ancestors[neigh][j - 1] == 0 ? 0 : ancestors[ancestors[neigh][j - 1]][j - 1];
        }
        dfs(neigh, x, adj, ancestors, m, d + 1, distance, paths);
    }
}

int LCA(int a, int b, int* distance, vector<vector<int>>& ancestors, int m) {
    int u = a, v = b;

    if (distance[u] > distance[v]) {
        swap(u, v);
    }

    int k = distance[v] - distance[u];
    int col = 0;

    while (k != 0) {
        if (k & 1) {
            v = ancestors[v][col];
        }
        col++;
        k >>= 1;
    }

    if (v == u) return u;

    for (int j = m; j >= 0; j--) {
        if (ancestors[u][j] != ancestors[v][j]) {
            u = ancestors[u][j];
            v = ancestors[v][j];
        }
    }

    return ancestors[u][0];
}

void pathSaveLCA(int a, int b, vector<vector<int>>& ancestors, int* distance, int m, int* paths) {
    int lca = LCA(a, b, distance, ancestors, m);
    paths[a] += 1;
    paths[b] += 1;
    paths[lca] -= 1;
    paths[ancestors[lca][0]] -= 1;
}

int main() {
    int n, q;
    cin >> n >> q;

    vector<vector<int>> adj(n + 1);
    vector<vector<int>> ancestors(n + 1, vector<int>(log2(n) + 1));
    int distance[MAXN];
    int paths[MAXN] = {0};

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    dfs(1, -1, adj, ancestors, log2(n), 0, distance, paths);

    for (int i = 0; i < q; i++) {
        int a, b;
        cin >> a >> b;
        pathSaveLCA(a, b, ancestors, distance, log2(n), paths);
    }

    dfsSum(1, -1, adj, paths);

    for (int i = 1; i <= n; i++) {
        if (i > 1) cout << " ";
        cout << paths[i];
    }
    cout << endl;

    return 0;
}
