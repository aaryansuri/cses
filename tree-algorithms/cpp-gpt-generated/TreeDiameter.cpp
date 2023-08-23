#include <iostream>
#include <vector>

using namespace std;

struct Neigh {
    int b, maxDistance;

    Neigh(int b) : b(b), maxDistance(-1) {}
};

void dfs(int x, vector<bool> &visited, vector<vector<Neigh>> &adj) {
    visited[x] = true;
    int maxDist = 0;
    for (Neigh &neigh : adj[x]) {
        if (!visited[neigh.b]) {
            dfs(neigh.b, visited, adj);
            maxDist = max(maxDist, neigh.maxDistance);
        }
    }
    for (Neigh &neigh : adj[x]) {
        if (visited[neigh.b]) {
            neigh.maxDistance = max(neigh.maxDistance, maxDist + 1);
        }
    }
}

int main() {
    int n;
    cin >> n;

    vector<vector<Neigh>> adj(n + 1);

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].emplace_back(b);
        adj[b].emplace_back(a);
    }

    vector<bool> visited(n + 1, false);
    dfs(1, visited, adj);

    int maxDist = 0;
    for (int i = 1; i <= n; i++) {
        maxDist = max(maxDist, adj[i][0].maxDistance);
    }

    cout << maxDist << endl;

    return 0;
}
