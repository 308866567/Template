#include <bits/stdc++.h>
#include <algorithm>
typedef long long ll;
using namespace std;
class segmentTree
{
public:
    const static ll N = 1e6 + 10;
    struct Info
    {
        ll sum, mx, lmx, rmx;
    };
    friend Info operator+(Info a, Info b)
    {
        // 子节点更新父节点
        Info c = {0, 0, 0, 0};
        c.lmx = max(a.lmx, a.sum + b.lmx);
        c.rmx = max(b.rmx, b.sum + a.rmx);
        c.mx = max({a.mx, b.mx, a.rmx + b.lmx});
        return c;
    }
    struct node
    {
        ll l, r;
        Info info;
    } tr[4 * N];
    ll *arr;
    segmentTree(ll *a)
    {
        arr = a;
    }
    void push_up(node &p, node l, node r)
    {
        // 子节点更新父节点
        p.info = l.info + r.info;
    }
    void push_down(ll p)
    {
    }
    void build(ll p, ll l, ll r)
    {
        tr[p] = {l, r};
        if (l == r)
        {
            // 初始化
            tr[p].info = {arr[l], arr[l], arr[l], arr[l]};
            return;
        }
        ll mid = l + r >> 1;
        build(p << 1, l, mid);
        build(p << 1 | 1, mid + 1, r);
        push_up(tr[p], tr[p << 1], tr[p << 1 | 1]);
    }
    // 点修
    void update(ll p, ll x, ll k)
    {
        if (tr[p].l == tr[p].r)
        {
            // 叶子节点修改
            tr[p].info = {k, k, k, k};
            return;
        }
        ll mid = tr[p].l + tr[p].r >> 1;
        push_down(p);
        // 修改
        if (x <= mid)
            update(p << 1, x, k);
        else
            update(p << 1 | 1, x, k);
        push_up(tr[p], tr[p << 1], tr[p << 1 | 1]);
    }
    // 区查
    node query(ll p, ll x, ll y)
    {
        if (tr[p].l >= x && tr[p].r <= y)
        {
            //[x,y]的子区间
            return tr[p];
        }
        ll mid = tr[p].l + tr[p].r >> 1;
        push_down(p);
        // 统计子区间
        if (y <= mid)
            return query(p << 1, x, y);
        if (x > mid)
            return query(p << 1 | 1, x, y);
        node t;
        push_up(t, query(p << 1, x, y), query(p << 1 | 1, x, y));
        return t;
    }
};
int main()
{
    ll n, m;
    ll a[(ll)1e6 + 10];
    // scanf("%d%d", &n, &m);
    // for (int i = 1; i <= n; i++)
    //     scanf("%d", &a[i]);
    // build(1, 1, n);
    // while (m--)
    // {
    //     int k, x, y;
    //     scanf("%d%d%d", &k, &x, &y);
    //     if (k == 1)
    //     {
    //         if (x > y)
    //             swap(x, y);
    //         printf("%d\n", query(1, x, y).mx);
    //     }
    //     else
    //         change(1, x, y);
    // }
    return 0;
}