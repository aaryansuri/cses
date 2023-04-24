#include <iostream>
#include <vector>
#include <set>
#include <algorithm>
using namespace std;

class Pair {
    public:
        long long ele;
        int pos;
        Pair(long long ele, int pos) {
            this->ele = ele;
            this->pos = pos;
        }
        bool operator < (const Pair& p) const {
            if (ele == p.ele) return pos < p.pos;
            return ele < p.ele;
        }
};

int main() {
    int n, k;
    cin >> n >> k;
    long long arr[n];
    for(int i = 0; i < n; i++) cin >> arr[i];

    if(k == 1) {
        for(int i = 0; i < n; i++) cout << arr[i] << " ";
        return 0;
    }

    if(k == 2) {
        for(int i = 1; i < n; i++) cout << min(arr[i-1], arr[i]) << " ";
        return 0;
    }

    auto comp = [] (const Pair& p1, const Pair& p2) {
        if (p1.ele == p2.ele) return p1.pos < p2.pos;
        return p1.ele < p2.ele;
    };
    set<Pair, decltype(comp)> left(comp), right(comp);

    vector<Pair> firstKSorted;
    for(int i = 0; i < k; i++) firstKSorted.emplace_back(arr[i], i);

    sort(firstKSorted.begin(), firstKSorted.end(), comp);

    for(int i = 0; i < k/2 + k%2; i++) {
        left.insert(firstKSorted[i]);
    }

    for(int i = k/2 + k%2; i < k; i++) {
        right.insert(firstKSorted[i]);
    }

    cout << left.rbegin()->ele << " ";

    for(int i = 1; i < n - k + 1; i++) {
        Pair previous(arr[i-1], i - 1);

        if(left.find(previous) != left.end()) {
            left.erase(previous);
        } else {
            right.erase(previous);
        }

        Pair curr(arr[k-1+i], k-1+i);

        if(left.rbegin()->ele < curr.ele) {
            right.insert(curr);
        } else {
            left.insert(curr);
        }

        while (left.size() < k/2 + k%2) {
            left.insert(*right.begin());
            right.erase(right.begin());
        }

        while (left.size() > k/2 + k%2) {
            right.insert(*left.rbegin());
            left.erase(prev(left.end()));
        }

        cout << left.rbegin()->ele << " ";
    }

    return 0;
}
