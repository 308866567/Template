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
查找子串
字符串周期
统计每个前缀的出现次数
字符串压缩
*/

/*
暴力解法n*m
双指针
p的指针j直接回退到1，i+1
丢弃了已经匹配的信息
*/
ll bf()
{
}
/*
当不匹配时，设s和p已经匹配上的子串为ts和tp
kmp是让i指针不动
j指针回跳到恰当位置再继续
思路为在ts中从头找到一个子串与tp头相等的
1.取模式串的最长相等的前后缀
2.通过模式串p前后缀的自我匹配长度，计算next函数
next[i]表示p[1,i]中相等前后缀的最长长度，小于p[1,i]的长度，真子串
next[1]=0
*/
const int N = 1000010;
int m, n;
char S[N], P[N];
int ne[N];
ll next()
{
    // 字符串下标都是从1开始
    ne[1] = 0;
    /*
    i=2是因为最长相等前后缀的起点不能为字符串开头，开头下标是1
    j=0是因为第一个比较的字符是s[2]和p[1]，然后因为预判，j=1-1=0
    i指向p后缀的结尾，真后缀范围[2,m]
    j指向p前缀的结尾，真前缀范围[1,m-1]
    当前缀和后缀假设往后判断一位不匹配时，此时用ne[i]换一个前后缀
    j往左跳，改变前缀的结尾，i不变
    此时相等前后缀长度减少，意味着i对应的后缀开头减少，但没有变量记录后缀开头
    而前缀开头始终为1，后缀结尾本来也是固定为m，但for循环改变了后缀结尾位置
    */
    for (ll i = 2, j = 0; i <= n; i++)
    {
        /*
        i表示假设的后缀结尾位置，i在循环里固定
        j+1表示假设的前缀结尾位置
        左跳j指针，跳到起始0或者下一位可以匹配上
        回跳一次：跳到[1,ne[j]]后继续判断
        当前缀和后缀假设往后判断一位不匹配时，此时用ne[i]换一个前后缀
        因为[1,j]已经完全相等，所以[1,ne[j]]也是[1,i]的相等前后缀
        */
        while (j != 0 && P[i] != P[j + 1])
            j = ne[j];
        if (P[i] == P[j + 1])
            j++;
        // 当前比较过s[i]了，记录ne[i]的值
        ne[i] = j;
    }
}



void kmp()
{
    cin >> S+1 >> P+1;
    m = strlen(S+1), n = strlen(P+1);
    // 计算next函数
    ne[1] = 0;
    for(int i = 2, j = 0; i <= n; i ++){
        while(j && P[i] != P[j+1]) j = ne[j];
        if(P[i] == P[j+1]) j ++; 
        ne[i] = j;
    }
    // KMP匹配
    for(int i = 1, j = 0; i <= m; i ++){
        while(j && S[i] != P[j+1]) j = ne[j];
        if(S[i] == P[j+1]) j ++;
        if(j == n) printf("%d\n", i-n+1);
    }
    for(int i = 1; i <= n; i ++)
        printf("%d ", ne[i]);
    return;
}
void solve();
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