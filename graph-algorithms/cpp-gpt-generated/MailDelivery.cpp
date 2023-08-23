#include <iostream>
#include <vector>
#include <set>
using namespace std;

void dfs(int node, vector<int>& path, vector<set<int>>& adj) {
    while (!adj[node].empty()) {
        auto it = adj[node].begin();
        int nextNode = *it;
        adj[node].erase(it);
        adj[nextNode].erase(node);
        dfs(nextNode, path, adj);
    }
    path.push_back(node);
}

int main() {
    int n, m;
    cin >> n >> m;

    vector<set<int>> adj(n + 1);

    for (int i = 0; i < m; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].insert(b);
        adj[b].insert(a);
    }

    bool isImpossible = false;
    for (int i = 1; i <= n; i++) {
        if (adj[i].size() % 2 == 1) {
            isImpossible = true;
            break;
        }
    }

    if (isImpossible) {
        cout << "IMPOSSIBLE" << endl;
        return 0;
    }

    vector<int> path;
    dfs(1, path, adj);

    if (path.size() != m + 1) {
        cout << "IMPOSSIBLE" << endl;
        return 0;
    }

    for (int i : path) {
        cout << i << " ";
    }
    cout << endl;

    return 0;
}
