#include <cstring>
#include <iostream>
#include <algorithm>
#include <unordered_map>
#include<queue>
typedef long long ll;
const ll N = 2e5;
using namespace std;
unordered_map<int, unordered_map<int, int>>g;//邻接表
ll dist[N];
int n, m;
int x, y, z;
bool st[N];
struct node
{
	ll v, w; //终点,距离
	node(ll a, ll b)
	{
		v = a;
		w = b;
	}
	bool operator<(const node &x) const
	{
		return w > x.w;
	}
};
ll dijkstra()
{
	priority_queue <node> q;
	memset(dist, 0x3f, sizeof(dist));//找最小值,初始化为正无穷
	dist[1] = 0; //起点为0
	q.push(node(1, 0));
	while (!q.empty())
	{
		node h = q.top(); //t.u是当前距离最近的点
		q.pop();
		ll t = h.v;
		if (st[t]) continue;
		st[t] = 1;
		dist[t] = h.w;
		//以t为中转更新t的后继
		for (auto it : g[t])
		{
			int j = it.first;
			if (dist[j] > dist[t] + g[t][j])
			{
				dist[j] = dist[t] + g[t][j];
				q.push(node(j, dist[j]));
			}
		}
		
	}
	if (dist[n] >= 1e9)
		return -1;
	return dist[n];
}
int main()
{
	//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0);
	cin.tie(0);
	cout.tie(0);
	cin >> n >> m;
	for (int i = 0; i < m; i++)
	{
		cin >> x >> y >> z;
		if (g[x][y] == 0)
			g[x][y] = z;
		else
			g[x][y] = min(g[x][y], z);
	}
	cout << 	dijkstra();
	
	return 0;
}
