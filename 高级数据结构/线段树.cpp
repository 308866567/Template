#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
ll n;
class segmentTree
{
    const static ll N = 1e6 + 10;
    struct node
    {
        ll l, r;
        ll sum, add;
    } tr[4 * N];
    inline ll lc(ll p) { return p << 1; }
    inline ll rc(ll p) { return p << 1 | 1; }
    inline void push_up(ll p)
    {
        tr[p].sum = tr[lc(p)].sum + tr[rc(p)].sum;
    }
    inline void push_down(ll p)
    {
        if (tr[p].add > 0)
        {
            tr[lc(p)].add = tr[p].add;
            tr[lc(p)].sum += tr[p].add * (tr[lc(p)].r - tr[lc(p)].l + 1);
            tr[rc(p)].add = tr[p].add;
            tr[rc(p)].sum = tr[p].add * (tr[rc(p)].r - tr[rc(p)].l + 1);
            tr[p].add = 0;
        }
    }
    ll *w; // w为区间单点值数组
    void build(ll p, ll l, ll r)
    {
        tr[p] = {l, r, w[l], 0};
        if (l == r)
            return;
        ll m = l + r >> 1;
        build(lc(p), l, m);
        build(rc(p), m + 1, r);
        push_up(p);
    }
    // 区间[x,y]
    void update(ll p, ll x, ll y, ll k)
    {
        if (tr[p].l >= x && tr[p].r <= y)
        {
            tr[p].sum += k * (tr[p].r - tr[p].l + 1);
            tr[p].add += k;
            return;
        }
        ll m = tr[p].l + tr[p].r >> 1;
        push_down(p);
        if (x <= m)
            update(lc(p), x, y, k);
        if (y > m)
            update(rc(p), x, y, k);
        push_up(p);
    }
    // 查询[x,y]
    ll query(ll p, ll x, ll y)
    {
        if (tr[p].l >= x && tr[p].r <= y)
        {
            return tr[p].sum;
        }
        ll m = tr[p].l + tr[p].r >> 1;
        push_down(p);
        ll sum = 0;
        if (x <= m)
            sum += query(lc(p), x, y);
        if (y > m)
            sum += query(rc(p), x, y);
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
