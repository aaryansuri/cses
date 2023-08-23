#include <bits/stdc++.h>
#define rep(i,j,k) for (int i=j; i<=k; i++)
#define pb push_back
#define liyuu_my_wife ios::sync_with_stdio(0), cin.tie(0);
using namespace std;

const int N = 2e5+10, LG = 30;
vector<int> G[N], rG[N], uG[N];
int n, q;

int comp[N];
void mark_comp (int u, int c)
{
    comp[u] = c;
    for (auto v: uG[u])
        if (!comp[v]) mark_comp(v,c);
}

int cyclen[N], ts[N], vis[N];
void find_cyc (int u)
{
    while(1)
    {
        vis[u] = 1;
        u = G[u][0];
        if (vis[u]) break;
    }
    int len = 0, till = u;
    while (1)
    {
        ts[u] = len++;
        u = G[u][0];
        if (u == till)
            break;
    }
    while (1)
    {
        cyclen[u] = len;
        u = G[u][0];
        if (u == till) break;
    }
}

int rt[N], d[N], par[N][LG];
void dfs (int u, int _rt)
{
    rt[u] = _rt;
    rep(i,1,LG-1)
        par[u][i] = par[par[u][i-1]][i-1];
    for (auto v: rG[u])
    {
        if (!cyclen[v])
            d[v] = d[u]+1, par[v][0] = u, dfs(v,_rt);
    }
}

int dist (int u, int v)
{
    return (ts[v]-ts[u]+cyclen[u])%cyclen[u];
}

int jump (int u, int len)
{
    for (int i=0; len>0; len>>=1,i++)
        if (len&1)
            u = par[u][i];
    return u;
}

signed main()
{
    liyuu_my_wife
    cin >> n >> q;
    rep(i,1,n)
    {
        int v;
        cin >> v;
        G[i].pb(v);
        rG[v].pb(i);
        uG[i].pb(v);
        uG[v].pb(i);
    }
    rep(i,1,n)
        if (!comp[i])
        {
            mark_comp(i,i);
            find_cyc(i);
        }
    rep(i,1,n)
        if (cyclen[i])
            dfs(i,i);

    while (q--)
    {
        int u, v;
        cin >> u >> v;
        if (comp[u] != comp[v])
        {
            cout << -1 << '\n';
            continue;
        }
        if (cyclen[u])
        {
            if (cyclen[v])
                cout << dist(u,v) << '\n';
            else
                cout  << -1 << '\n';
        }
        else
        {
            if (cyclen[v])
                cout << d[u] + dist(rt[u],v) << '\n';
            else
            {
                if (jump(u,d[u]-d[v]) == v)
                    cout << d[u]-d[v] << '\n';
                else
                    cout << -1 << '\n';
            }
        }
    }
}