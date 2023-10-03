#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAXN = 100005;

int id = 0;

void dfs(int x, int parent, vector<vector<int>>& adj, vector<int>& subTreeSizes, vector<int>& lookup, vector<int>& values, vector<long long>& pathSum) {
    id++;
    int size = 0;
    lookup[x] = id;
    pathSum[id] = values[x] + pathSum[lookup[parent]];
    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        dfs(neigh, x, adj, subTreeSizes, lookup, values, pathSum);
        size += 1 + subTreeSizes[neigh];
    }
    subTreeSizes[x] = size;
}

long long sum(vector<long long>& BIT, int k) {
    long long sum = 0;
    while (k >= 1) {
        sum += BIT[k];
        k -= k & -k;
    }
    return sum;
}

void update(vector<long long>& BIT, int k, int n, long long v) {
    while (k <= n) {
        BIT[k] += v;
        k += k & -k;
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n, q;
    cin >> n >> q;

    vector<int> values(n + 1);
    vector<int> subTreeSizes(n + 1);
    vector<long long> pathSum(n + 1);
    vector<int> lookup(n + 1);
    vector<long long> BIT(n + 1);

    for (int i = 1; i <= n; i++) {
        cin >> values[i];
    }

    vector<vector<int>> adj(n + 1);

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    dfs(1, 0, adj, subTreeSizes, lookup, values, pathSum);

    for (int i = 0; i < q; i++) {
        int type;
        cin >> type;
        if (type == 1) {
            int s, x;
            cin >> s >> x;
            update(BIT, lookup[s], n, x - values[s]);
            update(BIT, lookup[s] + subTreeSizes[s] + 1, n, -(x - values[s]));
            values[s] = x;
        } else {
            int s;
            cin >> s;
            cout << sum(BIT, lookup[s]) + pathSum[lookup[s]] << "\n";
        }
    }

    return 0;
}
