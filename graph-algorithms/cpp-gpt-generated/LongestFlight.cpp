#include <iostream>
#include <queue>
#include <vector>
#include <cstring>
#include <algorithm>
using namespace std;
  
int n, m, a, b, in[100005], dis[100005], pre[100005];
vector <int> v[100005], ans;
  
int main() {
    cin >> n >> m;
    for (int i = 0; i < m; i++){
        cin >> a >> b;
        v[a].push_back(b);
        in[b]++;
    }
    for (int i = 1; i <= n; i++){
        dis[i] = -1e9;
    }
    queue <int> q;
    dis[1] = 0;
    for (int i = 1; i <= n; i++){
        if (in[i] == 0){
            q.push(i);
        }
    }
    while (!q.empty()){
        int now = q.front();
        q.pop();
        for (int i:v[now]){
            if (dis[i] < dis[now]+1){
                pre[i] = now;
                dis[i] = dis[now]+1;
            }
            in[i]--;
            if (in[i] == 0) q.push(i);
        }
    }
    if (dis[n] < 0) cout << "IMPOSSIBLE\n";
    else{
        ans.push_back(n);
        while (ans.back() != 1){
            ans.push_back(pre[ans.back()]);
        }
        reverse(ans.begin(), ans.end());
        cout << ans.size() << "\n";
        for (int i:ans){
            cout << i << " ";
        }
    }
}