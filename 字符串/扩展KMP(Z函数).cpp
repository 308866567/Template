#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;

/*用处
匹配所有子串
本质不同子串数
字符串整周期
*/
/*
长度为n的字符串s,起始是1
z[i]表示s与其后缀s[i,n]的最长公共前缀(LCP)的长度
使用Z-box加速
对于i,称区间[i,i+z[i]-1]是i的匹配段,也叫z-box
盒子记作[l,r],一定是s的前缀,
即s[l,r]=s[1,r-l+1]
z[l+1]=z[2]
1<=i
由z[1]~z[i-1]快速计算z[i]
z[6]=z[2]
1.当前i<=r(在盒内),s[i,r]=s[i-l+1,r-l+1]
    - z[i]对应点z[i-l+1]
    - z[i-1+1]<r-l+1,则直接赋值,z[i]=z[i-l+1]
    - z[i-1+1]>=r-l+1,即大于盒子长度,则从r+1往后暴力枚举,
    - 因为r后面是未知区域
2.当前i>r(在盒外),则从i开始暴力枚举
3.求出z[i]后,如果i+z[i]-1>r,更新盒子l=i,r=i+z[i]-1
*/
char t[N], s[N];
int z[N], p[N];
void get_z(char *s, int n)
{
    z[1] = n;
    for (int i = 2, l, r = 0; i <= n; i++)
    {
        // 盒内两种情况赋值z[i]
        if (i <= r)
            z[i] = min(z[i - l + 1], r - i + 1);
        // 不管是否大于盒子长度都进行暴力枚举
        while (s[1 + z[i]] == s[i + z[i]])
            z[i]++;//z[i]会同步盒子的右端点r,而r<=n,所以时间复杂度o(n)
        // 更新盒子,用z[i]更新z[i+1]
        if (i + z[i] - 1 > r)
            l = i, r = i + z[i] - 1;
    }
}

void solve() {

};
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