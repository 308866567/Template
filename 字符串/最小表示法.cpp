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
char s[N];
int get_min(char *s)//时间复杂度2n
{
    //字符串起始下标为1
    ll n = strlen(s + 1);
    // 破环成链
    for (ll i = 1; i <= n; i++)
        s[n + i] = s[i];
    /*
    i,j表示字符串s的循环同构串的开头
     */
    ll i = 1, j = 2, k = 0;
    while (i <= n && j <= n)
    {
        //比较当前i,j两个循环同构串
        for(k=0;k<n&&s[i+k]==s[j+k];k++);
        //不相等时,相等位置字符比较大的那个循环同构串
        //更新开头到不相等字符位置的下一个字符
        s[i+k]>s[j+k]?i=i+k+1:j=j+k+1;
        if(i==j) j++;
    }
    //返回最小表示法的开头,T=[i,n]+[1,i-1]
    return min(i,j);
}
void solve()
{
    cin >> s + 1;
    cout << get_min(s);
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
