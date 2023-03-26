#include <bits/stdc++.h>
using namespace std;

struct Customer {
    long long arrival, departure;
    int pos;

    bool operator<(const Customer& other) const {
        if (arrival == other.arrival) {
            return departure < other.departure;
        }
        return arrival < other.arrival;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<Customer> customers(n);
    for (int i = 0; i < n; ++i) {
        cin >> customers[i].arrival >> customers[i].departure;
        customers[i].pos = i;
    }

    sort(customers.begin(), customers.end());

    map<long long, set<long long>> pool;
    vector<long long> roomAnswer(n);

    long long rooms = 0;
    for (const auto& customer : customers) {
        auto lowest = pool.lower_bound(customer.arrival);
        if (lowest == pool.begin()) {
            set<long long>& existing = pool[customer.departure];
            existing.insert(++rooms);
            roomAnswer[customer.pos] = rooms;
        } else {
            --lowest;
            set<long long>& existing = lowest->second;
            long long usePrevious = *existing.begin();
            existing.erase(existing.begin());
            if (existing.empty()) {
                pool.erase(lowest);
            }
            set<long long>& existingDep = pool[customer.departure];
            existingDep.insert(usePrevious);
            roomAnswer[customer.pos] = usePrevious;
        }
    }

    cout << rooms << '\n';
    for (int i = 0; i < n; ++i) {
        cout << roomAnswer[i] << ' ';
    }
    cout << '\n';

    return 0;
}
