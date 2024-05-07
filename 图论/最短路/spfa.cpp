#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
/*
给定一个 n 个点 m条边的有向图，
图中可能存在重边和自环， 边权可能为负数。
请你求出 1号点到 n 号点的最短距离，
如果无法从 1 号点走到 n号点，则输出 impossible。
数据保证不存在负权回路。
*/
/*
Bellman_ford算法会遍历所有的边，
但是有很多的边遍历了其实没有什么意义，
我们只用遍历那些到源点距离变小的点所连接的边即可，
只有当一个点的前驱结点更新了，该节点才会得到更新；
因此考虑到这一点，
我们将创建一个队列每一次加入距离被更新的结点。
有负权回路请你不要用SPFA否则会死循环
如果只有正权边,可以把队列换成优先队列,
每次取出当前距离最小的节点进行扩展
*/
ll h[N], w[N], e[N], ne[N], idx;
void add(ll a, ll b, ll c)
{
	e[++idx] = b, w[idx] = c, ne[idx] = h[a], h[a] = idx;
}
ll n, m;
queue<ll> q;
bool v[N];//标记当前节点是否在队列里
ll dist[N];
void spfa()
{
	memset(dist, 0x3f, sizeof dist);
	memset(v, 0, sizeof v);
	dist[1] = 0;
	v[1] = 1;
	q.push(1);
	while (!q.empty())
	{
		ll x = q.front();
		q.pop();
		v[x] = 0;
		for (ll i = h[x]; i != -1; i = ne[i])
		{
			ll y = e[i];
			// cout<<x<<"-"<<y<<"\n";
			ll z = w[i];
			if (dist[y] > dist[x] + z)
			{
				dist[y] = dist[x] + z;
				//当前已经加入队列的结点，
				// 无需再次加入队列，
				// 即便发生了更新也只用更新数值即可，
				// 重复添加降低效率
				if (!v[y])
				{
					q.push(y);
					v[y] = 1;
				}
			}
		}
	}
}
int main()
{
#ifdef LOCAL
	freopen("/home/xiaobingdu/code/c++/in.txt", "r", stdin);
#endif
	memset(h, -1, sizeof h);
	cin >> n >> m;
	for (int i = 1; i <= m; i++)
	{
		ll a, b, c;
		cin >> a >> b >> c;
		add(a, b, c);
	}
	spfa();
	int t = dist[n];
	if (t == 0x3f3f3f3f)
		puts("impossible");
	else
		printf("%d\n", t);
	return 0;
}
