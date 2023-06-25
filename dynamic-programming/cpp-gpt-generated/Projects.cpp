#include <bits/stdc++.h>
using namespace std;

struct Project {
    long long start, end, reward, rewardsAccumulated;

    bool operator<(const Project& other) const {
        if (end != other.end) return end < other.end;
        return start < other.start;
    }
};

int main() {
    int n;
    cin >> n;

    vector<Project> projects(n);

    for (int i = 0; i < n; i++) {
        cin >> projects[i].start >> projects[i].end >> projects[i].reward;
    }

    sort(projects.begin(), projects.end());

    long long maxReward = 0;
    set<Project, function<bool(Project, Project)>> heap([](Project a, Project b) {
        if (a.end != b.end) return a.end < b.end;
        return a.rewardsAccumulated < b.rewardsAccumulated;
    });

    for (auto& project : projects) {
        auto lessThanCurr = heap.lower_bound({0, project.start, 0, 0});

        if (lessThanCurr != heap.begin()) {
            lessThanCurr--;
            project.rewardsAccumulated = project.reward + lessThanCurr->rewardsAccumulated;
        } else {
            project.rewardsAccumulated = project.reward;
        }

        maxReward = max(project.rewardsAccumulated, maxReward);
        project.rewardsAccumulated = maxReward;
        heap.insert(project);
    }

    cout << maxReward << endl;

    return 0;
}
