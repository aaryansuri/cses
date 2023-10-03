#include <iostream>
#include <vector>

using namespace std;

int maxD = 0;
int nodeEnd = 0;

void dfs(int x, int parent, const vector<vector<int>>& adj, int distance) {
    if (distance > maxD) {
        nodeEnd = x;
        maxD = distance;
    }

    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        dfs(neigh, x, adj, distance + 1);
    }
}

void dfs(int x, int parent, const vector<vector<int>>& adj, int distance, vector<int>& distances) {
    distance++;

    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        dfs(neigh, x, adj, distance, distances);
        distances[neigh] = distance;
    }
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

    dfs(1, 0, adj, 0);
    int oneEnd = nodeEnd;
    maxD = 0;
    dfs(oneEnd, 0, adj, 0);
    int anotherEnd = nodeEnd;

    vector<int> oneEndDistance(n + 1);
    dfs(oneEnd, 0, adj, 0, oneEndDistance);

    vector<int> anotherEndDistance(n + 1);
    dfs(anotherEnd, 0, adj, 0, anotherEndDistance);

    for (int i = 1; i <= n; i++) {
        cout << max(oneEndDistance[i], anotherEndDistance[i]) << " ";
    }

    cout << endl;

    return 0;
}
