#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define inf 0x3f3f3f3f
ll n, m;
ll d[N]; // 存储节点的入度
ll h[N], e[N], ne[N], w[N], idx = 0;
void add(ll a, ll b, ll c)
{
    ne[++idx] = h[a];
    h[a] = idx;
    w[idx] = c;
    e[idx] = b;
    return;
}
void slove()
{
    cin >> n >> m;
    memset(h, -1, sizeof h);
    for (ll i = 1, a, b, c; i <= m; i++)
    {
        cin >> a >> b >> c;
        add(a, b, c);
        d[b]++;
    }
    topSort(); 
    return;
}
void topSort()
{
    queue<ll> q;
    // 添加入度为0的节点
    vector<ll> ans;
    rep(i, 1, n)
    {
        if (d[i] == 0)
        {
            q.push(i);
            ans.emplace_back(i);
        }
    }
    while (!q.empty())
    {
        ll t = q.front();
        cout<<t<<' ';
        q.pop();
        for (ll i = h[t]; i; i = ne[i])
        {
            ll j = e[i];
            d[j]--;
            if (d[j] == 0)
            {
                q.push(j);
                ans.emplace_back(j);
            }
        }
    }
    if (ans.size() == n)
    {
        for (ll i : ans)
        {
            cout << i << ' ';
        }
    }
    else
    {
        cout << -1;
    }
}
int main()
{
#ifdef LOCAL
    freopen("/home/xiaobingdu/Downloads/in.txt", "r", stdin);
#endif
    ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
    ll t = 1;
    // cin >> t;
    while (t--)
    {
        slove();
    }
    return 0;
}
