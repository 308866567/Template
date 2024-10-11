// https://www.acwing.com/problem/content/description/10/
#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 500;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
class dp
{
public:
    int f[N][N];
    int v[N];
    int b[N];    // 子节点个数
    int a[N][N]; // 子节点数组
    int w[N];
    int m; // 背包容量
    int n;
    int root;
    void in()
    {
        cin >> n >> m;
        for (int i = 1; i <= n; i++)
        {
            int p;
            cin >> v[i] >> w[i] >> p;
            if (p == -1)
                root = i;
            else
                a[p][b[p]++] = i;
        }
        dfs(root);
        cout << f[root][m];
        return;
    }
    int dfs(int u)
    {
        for (int i = v[u]; i <= m; i++)
        {
            f[u][i] = w[u]; // 初始化
        }
        for (int i = 0; i < b[u]; i++)
        {
            int s = a[u][i];
            dfs(s);
            // 当前子节点s更新父节点u
            //01背包一维,从大到小更新父节点容量,防止重复使用子节点
            for (int j = m; j >= v[u]; j--) 
            {
                // 选子节点,更新,枚举子节点的占用体积
                for (int k = 0; k <= j - v[u]; k++)
                {
                    f[u][j] = max(f[u][j], f[u][j - k] + f[s][k]);
                }
            }
        }
        return 0;
    }
} x;
ll n;
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
        // solve();
        x.in();
    }
    return 0;
}