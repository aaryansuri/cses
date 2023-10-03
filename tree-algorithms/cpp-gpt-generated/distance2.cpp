#include <iostream>
#include <vector>

using namespace std;

void sumOfDistancesForRoot(int x, int parent, vector<vector<int>>& adj, vector<long long>& sumFromRoot1, vector<int>& childFromRoot1) {
    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        sumOfDistancesForRoot(neigh, x, adj, sumFromRoot1, childFromRoot1);
        sumFromRoot1[x] += 1 + sumFromRoot1[neigh] + childFromRoot1[neigh];
        childFromRoot1[x] += childFromRoot1[neigh];
        childFromRoot1[x] += 1;
    }
}

void sumOfDistancesForAll(int x, int parent, vector<vector<int>>& adj, vector<int>& childFromRoot1, vector<long long>& distances, int n) {
    for (int neigh : adj[x]) {
        if (neigh == parent) continue;
        distances[neigh] = distances[x] - childFromRoot1[neigh] + (n - childFromRoot1[neigh] - 2);
        sumOfDistancesForAll(neigh, x, adj, childFromRoot1, distances, n);
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

    vector<long long> sumFromRoot1(n + 1, 0);
    vector<int> childFromRoot1(n + 1, 0);

    sumOfDistancesForRoot(1, 0, adj, sumFromRoot1, childFromRoot1);

    vector<long long> distances(n + 1, 0);

    distances[1] = sumFromRoot1[1];
    sumOfDistancesForAll(1, 0, adj, childFromRoot1, distances, n);

    for (int i = 1; i <= n; i++) {
        cout << distances[i] << " ";
    }

    return 0;
}
