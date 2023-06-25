#include <iostream>
#include <vector>
#include <queue>

using namespace std;

void travelThroughCityDFS(int curr, vector<vector<int>>& adj, vector<bool>& visited) {
    if (visited[curr]) return;
    visited[curr] = true;
    for (int neighbour : adj[curr]) {
        travelThroughCityDFS(neighbour, adj, visited);
    }
}

void travelThroughCityBFS(int curr, vector<vector<int>>& adj, vector<bool>& visited) {
    queue<int> q;
    q.push(curr);

    while (!q.empty()) {
        int city = q.front();
        q.pop();

        for (int neighbour : adj[city]) {
            if (!visited[neighbour]) {
                visited[neighbour] = true;
                q.push(neighbour);
            }
        }
    }
}

int main() {
    int n, m;
    cin >> n >> m;

    vector<vector<int>> adj(n + 1);
    vector<bool> visited(n + 1, false);

    for (int i = 0; i < m; i++) {
        int c1, c2;
        cin >> c1 >> c2;
        adj[c1].push_back(c2);
        adj[c2].push_back(c1);
    }

    vector<int> cityLeaders;

    for (int i = 1; i <= n; i++) {
        if (!visited[i]) {
            cityLeaders.push_back(i);
            travelThroughCityBFS(i, adj, visited);
        }
    }

    cout << cityLeaders.size() - 1 << endl;

    for (int i = 0; i < cityLeaders.size() - 1; i++) {
        cout << cityLeaders[i] << " " << cityLeaders[i + 1] << endl;
    }

    return 0;
}
