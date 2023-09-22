#include <iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
const long long inf=2147483647;
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>

struct edge
{
	ll e, v, w;
} e[N];


ll n, m, s;
ll dist[N];
ll backup[N];
void bellman()
{
	for(int i=1;i<=n;i++)
	{
		dist[i]=inf;
	}
	dist[s] = 0;
	//迭代次数是有实际意义的，
//	我们迭代1次，那我们求的最短距离就是最多不经过1条边的最短距离。
	for (int i = 1; i <= n; i++)//i条边的最短路
	{
		memcpy(backup, dist, sizeof dist);
		for (int j = 1; j <= m; j++)
		{
			//需要用更新前的dist数组
			dist[e[j].v] = min(backup[e[j].e] + e[j].w, dist[e[j].v]);
		}
	}
	for (int i = 1; i <= n; i++)
	{
			cout << dist[i] << ' ';
	}
	return ;
}
//
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> m >> s;
	for (int i = 1; i <= m; i++)
	{
		cin >> e[i].e >> e[i].v >> e[i].w;
	}
	bellman();
	return 0;
}
