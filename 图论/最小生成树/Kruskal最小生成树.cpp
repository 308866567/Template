#include <iostream>
#include<cstring>
#include<algorithm>
using namespace std;
typedef int ll;
const long long N = 1e5 + 10, M = 2e5 + 10, INF = 0x3f3f3f3f;
ll n, m;
ll p[N];//x的父节点
struct Edge//存储边
{
	ll a, b, w;
	bool operator <(const Edge &t)const
	{
		return w < t.w;
	}
} edges[M];
//判断是否会产生回路的方法为：使用并查集
//在最小生成树的为一个集合
//如果边上的这两个顶点在一个集合中，说明两个顶点已经连通，这条边不要
ll find(ll x)
{
	if (p[x] != x)
		p[x] = find(p[x]);
	return p[x];
}
ll kruskal()//无向图
{
	sort(edges, edges + m);
	for (int i = 1; i <= n; i++) p[i] = i;
	ll res = 0, cnt = 0;//最小生成树的树边权重之和
	ll a, b, w;
	for (int i = 0; i < m; i++)//权值从小到大
	{
		a = edges[i].a;
		b = edges[i].b;
		w = edges[i].w;
		a = find(a), b = find(b);
		//如果这个边与之前选择的所有边不会组成回路，就选择这条边
		if (a != b)
		{
			p[a] = b;//a->b
			res += w;
			cnt++;
		}
	}
	//直到具有 n 个顶点的连通网筛选出来 n-1 条边为止
	if (cnt < n - 1)
		return INF;
	return res;
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0);
	cin.tie(0);
	cout.tie(0);
	cin >> n >> m;
	ll a, b, w;
	for (int i = 0; i < m; i++)
	{
		cin >> a >> b >> w;
		edges[i] = {a, b, w};
	}
	ll t = kruskal();
	if (t == INF)
		cout << "impossible";
	else
		cout << t << "\n";
	return 0;
}
