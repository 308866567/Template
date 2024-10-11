#include <bits/stdc++.h>
#include <iostream>
using namespace std;
typedef long long ll;
const ll N = 1e3 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
ll n, m;
ll v[N], w[N];
ll f[N][N];
ll p[N][N]; // 记录上一步的位置
void solve()
{
    cin >> n >> m;
    rep(i, 1, n)
    {
        cin >> v[i] >> w[i];
    }
    rrep(i, n, 1)//倒序可以处理相等时保证字典序最小
    {
        rep(j, 0, m)
        {
            f[i][j] = f[i + 1][j];
            p[i][j] = j;
            if (j >= v[i])
            {
                //相等时也更新,因为选了当前这个i字典序更大
                if (f[i + 1][j - v[i]] + w[i] >= f[i][j])
                {
                    p[i][j] = j - v[i];
                }
                f[i][j] = max(f[i][j], f[i + 1][j - v[i]] + w[i]);
            }
        }
    }
    // cout << f[1][m] << "\n";
    ll j = m;
    // 不加
    rep(i, 1, n)
    {
        //如果没选第i个,那么f[i][j]==f[i+1][j]
        if (j >= v[i] && f[i][j] == f[i + 1][j - v[i]] + w[i])
        {
            cout << i << " ";
            j -= v[i];
        }
    }
    // 加记录路径数组
    j = m;
    rep(i, 1, n)
    {
        // 如果没选中的话,j的值不变
        if (p[i][j] < j)
        {
            // cout << i << " ";
            j = p[i][j];
        }
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
    // ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
    ll t = 1;
    // cin >> t;
    while (t--)
    {
        solve();
    }
    return 0;
}

//  "-Wl,--stack=134217728",
// ulimit -s 134217728
#include <sys/resource.h>
void add()
{
    const rlim_t kStackSize = 64 * 1024 * 1024; // min stack size = 16 MB
    struct rlimit rl;
    int result;
    result = getrlimit(RLIMIT_STACK, &rl);
    if (result == 0)
    {
        if (rl.rlim_cur < kStackSize)
        {
            rl.rlim_cur = kStackSize;
            result = setrlimit(RLIMIT_STACK, &rl);
            if (result != 0)
            {
                fprintf(stderr, "setrlimit returned result = %d\n", result);
            }
        }
    }
}