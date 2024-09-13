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
ll a[N];
// 模板测试洛谷p2249
class bound
{
    ll *a;
    // 左右查找只有==时情况处有着不同处理
    bound(ll *t)
    {
        a = t;
    }
    // 返回最后一个<=q的下标
    // 如果不存在q,返回小于q的
    // q在mid左边.改右边界
    // 数组有效区间为[1,n]
    // l指向可行解
    ll upper_bound(ll q)
    {
        ll l = 0, r = n + 1, mid;
        while (l + 1 < r)
        {
            mid = l + r >> 1;
            if (a[mid] <= q)
                l = mid;
            else
                r = mid;
        }
        return l;
    }
    // 返回第一个>=q的下标
    // 如果不存在q,返回大于q的
    // q在mid右边,改左边界
    // 数组有效区间为[1,n]
    // r指向可行解
    ll lower_bound(ll q)
    {
        ll l = 0, r = n + 1, mid;
        while (l + 1 < r)
        {
            mid = l + r >> 1;
            if (a[mid] >= q)
                r = mid;
            else
                l = mid;
        }
        return r;
        // 不存在时返回-1
        // return a[r]==q?r:-1;
    }
    // 浮点二分,upper最大化板子改
    // 例,求一个浮点数y的三次方根,-10000<=y<=10000
    // 浮点例题洛谷p1024,可以先求出两个极值点，再在被极值点分成的三个区域二分查找
    double find(double y)
    {
        double l = -100, r = 100, mid;
        double t = 1e-5;
        // l和r靠的足够近
        while (r - l < t)
        {
            double mid = (l + r) / 2;
            // mid对应值小于y
            if (mid * mid * mid <= y)
                l = mid;
            else
                r = mid;
        }
        return l;
        // 不存在时返回-1
        //  return a[l]==q?l:-1;
    }
};
void solve()
{
}

int main()
{
#ifdef LOCAL
    freopen("/home/xiaobingdu/code/c++/in.txt", "r", stdin);
#endif
#ifdef LOCAL_WIN
    freopen("C:/Users/30886/Desktop/in.txt", "r", stdin);
#endif
    // ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
    ll t = 1;
    // cin >> t;
    while (t--)
    {
        solve();
    }
    return 0;
}