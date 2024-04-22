#include <iostream>
#include <climits>
#include <bits/stdc++.h>
using namespace std;
const int INF = 1e9;
class G
{
public:
    int n, m;                  // 节点数,边数
    int k;                     // k次询问
    vector<vector<int>> graph; // u到v的最短距离
    vector<vector<int>> path;  // u到v的中转节点
    void in()                  // 输入
    {
        cin >> n >> m >> k;
        int u, v, w;
        graph.resize(n + 1);
        path.resize(n + 1);
        for (int i = 1; i <= n; i++)
        {
            path[i].resize(n + 1, -1);   // 节点序号从1开始
            graph[i].resize(n + 1, INF); // 无穷大的权值表示没有直接的边
            graph[i][i] = 0;
        }
        for (int i = 0; i < m; i++) // p条边
        {
            cin >> u >> v >> w;
            graph[u][v] = min(w,graph[u][v]);
        }
        floyd();
        slove();
    }
    void slove() // 处理询问
    {
        int x, y;
        for (int i = 0; i < k; i++)
        {
            cin >> x >> y;
            if (graph[x][y] > INF / 2)
                cout << "impossible\n";
            else
                cout << graph[x][y] << "\n";
        }
    }
    void floyd()
    {
        for (int mid = 1; mid <= n; mid++) // 穷举每个中转节点
            for (int u = 1; u <= n; u++)
                for (int v = 1; v <= n; v++)
                // if (u != v && mid != u && mid != v) // 含有中转节点和自身不更新
                {
                    graph[u][v] = min(graph[u][mid] + graph[mid][v], graph[u][v]);
                    path[u][v] = mid;
                }
    }
};
int main()
{
    // freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
    G g;
    g.in();
    return 0;
}
