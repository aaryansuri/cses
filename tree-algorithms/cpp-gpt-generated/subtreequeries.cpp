#include <iostream>
#include <vector>

using namespace std;

const int MAXN = 200005;


int id = 0;


void dfsForSubTree(int x, int parent, vector<vector<int>>& adj, vector<int>& subTreeSizes, vector<int>& lookup, vector<int>& reverseLookUp) {
    id++;
    int size = 0;
    lookup[id] = x;
    reverseLookUp[x] = id;
    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        dfsForSubTree(neigh, x, adj, subTreeSizes, lookup, reverseLookUp);
        size += 1 + subTreeSizes[neigh];
    }
    subTreeSizes[x] = size;
}

long sum(vector<long>& BIT, int k) {
    long sum = 0;
    while (k >= 1) {
        sum += BIT[k];
        k -= k & -k;
    }
    return sum;
}

void update(vector<long>& BIT, int k, int v, int n) {
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
    vector<vector<int>> adj(n + 1, vector<int>());
    vector<long> BIT(n + 1);
    vector<int> lookup(n + 1);
    vector<int> reverseLookUp(n + 1);

    for (int i = 1; i <= n; i++) {
        cin >> values[i];
    }

    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    dfsForSubTree(1, -1, adj, subTreeSizes, lookup, reverseLookUp);

    for (int i = 1; i <= n; i++) {
        update(BIT, reverseLookUp[i], values[i], n);
    }

    string result = "";

    for (int i = 0; i < q; i++) {
        int type;
        cin >> type;
        if (type == 1) {
            int s, x;
            cin >> s >> x;
            update(BIT, reverseLookUp[s], x - values[s], n);
            values[s] = x;
        } else {
            int s;
            cin >> s;
            int subTreeSize = subTreeSizes[s];
            result += to_string(sum(BIT, reverseLookUp[s] + subTreeSize) - sum(BIT, reverseLookUp[s] - 1)) + "\n";
        }
    }

    cout << result;

    return 0;
}