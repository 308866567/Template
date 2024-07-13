#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
char a[N], b[N];
ll la, lb;
ll z[N];
ll p[N];

// b 的 zz 函数数组 zz，即 bb 与 bb 的每一个后缀的 LCP 长度。
// bb 与 aa 的每一个后缀的 LCP 长度数组 pp。
void get_z(char *s, ll len)
{
    z[1] = len;
    for (ll i = 2, l, r = 0; i <= len; i++)
    {
        if (i <= r)
            z[i] = min(z[1 + i - l], r - i + 1);
        while (s[1 + z[i]] == s[i + z[i]])
            z[i]++;
        if (i + z[i] - 1 > r)
            l = i, r = i + z[i] - 1;
    }
}
void get_p(char *a, ll la, char *b, ll lb)
{
    // b跟a的所有后缀匹配
    // z[i]是b的
    // i扫描a
    for (ll i = 1, l, r = 0; i <= la; i++)
    {
        // 3.赋值,根据已知的z[i]
        if (i <= r)
            p[i] = min(z[1 + i - l], r - i + 1);
        // 1.暴力枚举
        while (1 + p[i] <= lb && i + p[i] <= la && b[1 + p[i]] == a[i + p[i]])
            p[i]++;
        // 2.盒子更新,根据p[i]值
        if (i + p[i] - 1 > r)
            l = i, r = i + p[i] - 1;
    }
}
void solve()
{
    cin >> a + 1;
    cin >> b + 1;
    la = strlen(a + 1);
    lb = strlen(b + 1);
    get_z(b, lb);
    get_p(a, la, b, lb);
    long long ans1 = 0, ans2 = 0;
    for (int i = 1; i <= lb; i++)
        ans1 ^= 1LL * i * (z[i] + 1);
    for (int i = 1; i <= la; i++)
        ans2 ^= 1LL * i * (p[i] + 1);
    cout << ans1 << endl
         << ans2;
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
