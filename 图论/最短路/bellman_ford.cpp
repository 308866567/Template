#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
const long long inf = 2147483647;
struct edge
{
	ll e, v, w;
} e[N];

ll n, m, s = 1; // 节点数,边数,起点
ll dist[N];
ll backup[N];
void bellman()
{
	memset(dist, 0x3f, sizeof dist);
	dist[s] = 0;
	// 我们迭代1次，那我们求的最短距离就是最多不经过1条边的最短距离。
	for (int i = 1; i <= n; i++) // 起点最多经过i条边的最短路
	{
		memcpy(backup, dist, sizeof dist);
		for (int j = 1; j <= m; j++) // 用边起点为中转节点,更新s到边终点的距离
		{
			edge t = e[j];
			// 需要用更新前的dist数组
			dist[t.v] = min(backup[t.e] + t.w,
							dist[t.v]);
		}
	}
	// for (int i = 1; i <= n; i++)
	// {
	// 		cout << dist[i] << ' ';
	// }
	cout << dist[n] << ' ';
	return;
}
//
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
#ifdef LOCAL
	freopen("/home/xiaobingdu/code/c++/in.txt", "r", stdin);
#endif
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> m;
	for (int i = 1; i <= m; i++)
	{
		cin >> e[i].e >> e[i].v >> e[i].w;
	}
	bellman();
	return 0;
}
