#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>

using namespace std;

int dfs(int x, int* subordinates, const vector<vector<int>>& adj) {
    int ord = 0;
    for (int sub : adj[x]) {
        ord += 1 + dfs(sub, subordinates, adj);
    }
    subordinates[x] = ord;
    return ord;
}

int main() {
    int n;
    cin >> n;

    vector<vector<int>> adj(n + 1);
    for (int i = 2; i <= n; i++) {
        int num;
        cin >> num;
        adj[num].push_back(i);
    }

    int subordinates[n + 1];
    memset(subordinates, -1, sizeof(subordinates));

    dfs(1, subordinates, adj);

    for (int i = 1; i <= n; i++) {
        cout << subordinates[i] << " ";
    }
    cout << endl;

    return 0;
}
