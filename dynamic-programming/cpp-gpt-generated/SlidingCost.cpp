#include <bits/stdc++.h>
using namespace std;

struct Pair {
    long long ele, pos;
    Pair(long long ele, int pos) : ele(ele), pos(pos) {}
    bool operator<(const Pair& other) const {
        if (ele != other.ele) return ele < other.ele;
        return pos < other.pos;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n, k;
    cin >> n >> k;
    long long arr[n];

    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }

    if (k == 1) {
        for (int i = 0; i < n; i++) {
            cout << 0 << " ";
        }
        return 0;
    }

    if (k == 2) {
        for (int i = 1; i < n; i++) {
            cout << max(arr[i - 1], arr[i]) - min(arr[i - 1], arr[i]) << " ";
        }
        return 0;
    }

    auto comp = [](const Pair& p1, const Pair& p2) {
        if (p1.ele != p2.ele) return p1.ele < p2.ele;
        return p1.pos < p2.pos;
    };
    set<Pair, decltype(comp)> left(comp), right(comp);

    vector<Pair> firstKSorted;
    for (int i = 0; i < k; i++) {
        firstKSorted.emplace_back(arr[i], i);
    }

    sort(firstKSorted.begin(), firstKSorted.end(), comp);
    long long leftSum = 0, rightSum = 0;

    for (int i = 0; i < k / 2 + k % 2; i++) {
        left.insert(firstKSorted[i]);
        leftSum += firstKSorted[i].ele;
    }

    for (int i = k / 2 + k % 2; i < k; i++) {
        right.insert(firstKSorted[i]);
        rightSum += firstKSorted[i].ele;
    }

    long long median;
    if (k % 2 == 0) {
        median = (left.rbegin()->ele + right.begin()->ele) / 2;
    } else {
        median = left.rbegin()->ele;
    }

    long long windowMedian = (left.size() * median - leftSum) + (rightSum - right.size() * median);
    cout << windowMedian << " ";

    for (int i = 1; i < n - k + 1; i++) {
        Pair previous(arr[i - 1], i - 1);

        if (left.find(previous) != left.end()) {
            left.erase(previous);
            leftSum -= previous.ele;
        } else {
            right.erase(previous);
            rightSum -= previous.ele;
        }

        Pair curr(arr[k - 1 + i], k - 1 + i);

        if (left.rbegin()->ele < curr.ele) {
            right.insert(curr);
            rightSum += curr.ele;
        } else {
            left.insert(curr);
            leftSum += curr.ele;
        }

        while (left.size() < k / 2 + k % 2) {
            left.insert(*right.begin());
            leftSum += right.begin()->ele;
            rightSum -= right.begin()->ele;
            right.erase(right.begin());
        }

        while (left.size() > k / 2 + k % 2) {
                right.insert(*--left.end());
                rightSum += left.rbegin()->ele;
                leftSum -= left.rbegin()->ele;
                left.erase(--left.end());
        }

            if (k % 2 == 0) {
                median = (left.rbegin()->ele + right.begin()->ele) / 2;
            } else {
                median = left.rbegin()->ele;
            }

            windowMedian = (left.size() * median - leftSum) + (rightSum - right.size() * median);
            cout<<windowMedian<<" ";
        }
        return 0;
}
