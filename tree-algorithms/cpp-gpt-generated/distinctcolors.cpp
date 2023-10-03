#include <iostream>
#include <vector>
#include <set>

using namespace std;

set<int> dfsForColors(int x, int parent, const vector<vector<int>>& adj, const vector<int>& colors, vector<int>& distinctColors) {
    set<int> dis;
    dis.insert(colors[x]);

    for(int neigh : adj[x]) {
        if(neigh == parent) continue;
        set<int> subTree = dfsForColors(neigh, x, adj, colors, distinctColors);
        if (subTree.size() > dis.size()) {
            swap(subTree, dis);
        }

        dis.insert(subTree.begin(), subTree.end());
    }

    distinctColors[x] = dis.size();
    return dis;
}

int main() {
    int n;
    cin >> n;

    vector<int> colors(n + 1);
    vector<vector<int>> adj(n + 1, vector<int>());
    adj[0].push_back(0); // Dummy element to match the 1-based indexing in Java

    for(int i = 1; i <= n; i++) {
        cin >> colors[i];
    }

    for(int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    vector<int> distinctColors(n + 1);

    set<int> result = dfsForColors(1, 0, adj, colors, distinctColors);

    for(int i = 1; i <= n; i++) {
        cout << distinctColors[i] << " ";
    }

    return 0;
}
