#include <iostream>
#include <vector>
#include <queue>
#include <limits>

using namespace std;

struct Connection {
    int city;
    long long price;

    Connection(int city, long long price) : city(city), price(price) {}
};

struct Decision {
    int city;
    long long price;
    int citiesTravelled;

    Decision(int city, long long price, int citiesTravelled) : city(city), price(price), citiesTravelled(citiesTravelled) {}
};

struct CompareDecisions {
    bool operator()(const Decision& d1, const Decision& d2) {
        return d1.price > d2.price;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<Connection>> adj(n + 1);

    for (int i = 0; i < m; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        adj[a].emplace_back(b, c);
    }

    vector<long long> distance(n + 1, numeric_limits<long long>::max());
    distance[1] = 0;

    priority_queue<Decision, vector<Decision>, CompareDecisions> queue;

    queue.emplace(1, 0, 0);
    int minCities = numeric_limits<int>::max();
    int maxCities = numeric_limits<int>::min();
    int minimumPriceRoutes = 0;

    while (!queue.empty()) {
        Decision curr = queue.top();
        queue.pop();

        if (distance[curr.city] <= curr.price)
            continue;

        if (curr.city == n) {
            minCities = min(minCities, curr.citiesTravelled);
            maxCities = max(maxCities, curr.citiesTravelled);
            minimumPriceRoutes = (minimumPriceRoutes + 1) % 1000000007;
        }

        for (const Connection& neigh : adj[curr.city]) {
            long long localMinima = distance[curr.city] + neigh.price;

            if (localMinima <= distance[neigh.city]) {
                distance[neigh.city] = localMinima;
                queue.emplace(neigh.city, localMinima, curr.citiesTravelled + 1);
            }
        }
    }

    cout << distance[n] << " " << minimumPriceRoutes << " " << minCities << " " << maxCities << "\n";

    return 0;
}
