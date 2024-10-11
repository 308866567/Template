#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e5 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
ll n;
// 离散化
class lisanhua
{
    void solve(vector<ll> &v)
    {
        sort(v.begin(), v.end());
        v.erase(unique(v.begin(), v.end()), v.end());
    }
    ll getid(ll x,vector<ll> &v)
    {
        // 下标0开始
        return lower_bound(v.begin(), v.end(), x) - v.begin() + 1;
    }
};

class segmentTree
{
#define lc(x) tr[x].l
#define rc(x) tr[x].r
    const static ll N = 1e6 + 10;
    struct Info
    {
    };
    friend Info operator+(Info a, Info b)
    {
        return a;
    }
    struct node
    {
        ll l, r;
        ll s;
        Info info;
    } tr[400 * N];
    ll root[N], idx;
    ll *w; // w为区间单点值数组
    void solve()
    {
    }
    void build(ll &p, ll l, ll r)
    {
        p = ++idx;
        if (l == r)
            return;
        ll m = l + r >> 1;
        build(lc(p), l, m);
        build(rc(p), m + 1, r);
    }
    // x为前一版本,y为当前版本
    void insert(ll x, ll &y, ll l, ll r, ll v)
    {
        y = ++idx;
        tr[y] = tr[x];
        // 更新区间内信息
        tr[y].s++;
        if (l == r)
            return;
        ll m = l + r >> 1;
        if (v <= m)
            insert(lc(x), lc(y), l, m, v);
        else
            insert(rc(x), rc(y), m + 1, r, v);
    }
    // 查询[x,y]
    ll query(ll x, ll y, ll l, ll r, ll k)
    {
        if (l == r)
            return l;
        ll m = l + r >> 1;
        ll s = tr[lc(y)].s - tr[lc(x)].s;
        if (k <= s)
            return query(lc(x), lc(y), l, m, k);
        else
            return query(rc(x), rc(y), m + 1, r, k - s);
    }
};

void solve()
{
    cin >> n;
}

int main()
{
#ifdef LOCAL
    freopen("/home/xiaobingdu/code/c++/in.txt", "r", stdin);
#endif
#ifdef LOCAL_WIN
    freopen("C:/Users/30886/Desktop/in.txt", "r", stdin);
#endif
    ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
    ll t = 1;
    // cin >> t;
    while (t--)
    {
        solve();
    }
    return 0;
}
