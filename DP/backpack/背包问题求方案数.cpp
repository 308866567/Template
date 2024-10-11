#include <bits/stdc++.h>
// https://www.acwing.com/problem/content/description/11/
//  #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e4 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
ll n, m;
ll v[N], w[N];
ll c[N]; // 容量--方案数
ll f[N];
void solve()
{
    cin >> n >> m;
    rep(i, 1, n)
    {
        cin >> v[i] >> w[i];
    }
    // 初始化,求不超容量的
    rep(i, 0, m)
        c[i] = 1;
    /*
    //刚好装满的方案数
    rep(i,1,m) f[i]=-10000;
    f[0]=0,c[0]=1;
    */
    rep(i, 1, n)
    {
        rrep(j, m, v[i])
        {
            if (f[j - v[i]] + w[i] > f[j])
            {
                c[j] = c[j - v[i]];
            }
            else if (f[j - v[i]] + w[i] == f[j])
            {
                c[j] = (c[j] + c[j - v[i]]) % mod;
            }
            f[j] = max(f[j], f[j - v[i]] + w[i]);
        }
    }
    cout << c[m] % mod;
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