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
char t[N];
ll lt;
ll z[N];
void get_z(char *s, ll len)
{
    z[1] = len;
    for (ll i = 2, l, r = 0; i <= len; i++)
    {
        if(i<=r) z[i]=min(z[1+i-l],r-i+1);
        while (s[1 + z[i]] == s[i + z[i]])
            z[i]++;
        if (i + z[i] - 1 > r)
            l = i, r = i + z[i] - 1;
    }
}  
void solve()
{
    cin >> n;
    cin >> t + 1;

    // t匹配s(n,k)
    // 构造s(n,k)?
    // 10进制转k进制字符串
    for (int k = 2; k <= 16; k++)
    {
    }
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
