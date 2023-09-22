#include <cstring>
#include <iostream>
#include <algorithm>

using namespace std;

const int N = 510; // 最大节点数
int n, m;
int g[N][N];
int dist[N];
bool st[N];

int dijkstra()
{
    memset(dist, 0x3f, sizeof dist);
    dist[1] = 0;
    // 找队列头节点中的最短边head-t
    // 以此最短边更新后继节点的最短路
    // 邻接矩阵可以遍历全部直接加,邻接表则只更新t节点的后继
    for (int i = 0; i < n - 1; i++) // 循环n-1次
    {
        int t = -1;
        for (int j = 1; j <= n; j++)                      // 起点为1
            if (!st[j] && (t == -1 || dist[t] > dist[j])) // 在j没有访问过条件下,一个中转节点只能用一次
                t = j;                                    // t未被赋值或有权值更小的边出现更新t为j

        for (int j = 1; j <= n; j++) // 以t为中间点,更新i-t-j的路程
            dist[j] = min(dist[j], dist[t] + g[t][j]);

        st[t] = true;
    }

    if (dist[n] == 0x3f3f3f3f)
        return -1;
    return dist[n];
}

int main()
{
    scanf("%d%d", &n, &m);

    memset(g, 0x3f, sizeof g);
    while (m--)
    {
        int a, b, c;
        scanf("%d%d%d", &a, &b, &c);

        g[a][b] = min(g[a][b], c);
    }

    printf("%d\n", dijkstra());

    return 0;
}
