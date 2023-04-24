#include <iostream>
#include <algorithm>
#include <map>
#include <vector>

using namespace std;

class Movie {
public:
    int start;
    int end;

    Movie(int start, int end) {
        this->start = start;
        this->end = end;
    }

    bool operator<(const Movie &other) const {
        return end < other.end || (end == other.end && start < other.start);
    }
};

int main() {
    int n, k;
    cin >> n >> k;

    vector<Movie> movies;

    for (int i = 0; i < n; i++) {
        int start, end;
        cin >> start >> end;
        movies.emplace_back(start, end);
    }

    sort(movies.begin(), movies.end());

    map<int, int> cnt;
    for (int i = 0; i < k; i++) {
        cnt[0]++;
    }

    int watchables = 0;
    for (int i = 0; i < n; i++) {
        int currStart = movies[i].start;
        auto it = cnt.upper_bound(currStart);
        if (it == cnt.begin()) {
            continue;
        }
        it--;
        int lowerEndedMovie = it->first;
        cnt[lowerEndedMovie]--;
        if (cnt[lowerEndedMovie] == 0) {
            cnt.erase(lowerEndedMovie);
        }
        cnt[movies[i].end]++;
        watchables++;
    }

    cout << watchables << endl;

    return 0;
}
