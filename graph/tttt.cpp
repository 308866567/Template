#include <cstring>
#include <iostream>
#include <algorithm>
typedef long long ll;
using namespace std;
const ll N = 510, M = 10010;
struct Edge
{
	int a, b, c;
} edges[M];
int n, m, k;
int dist[N];//最短距离
int last[N];
void bellman_ford()
{
	memset(dist, 0x3f, sizeof dist);
	dist[1] = 0; //起点
	for(int i=0;i<=k;i++)//边数,最多k条边
	{
		memcpy(last,dist,sizeof dist);//备份
		for(int j=1;j<=m;j++)
		{
			auto e=edges[j];
			dist[e.b]=min(dist[e.b],last[e.a]+e.c);
		}
	}
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	// ios::sync_with_stdio(0);
	// cin.tie(0);
	//cout.tie(0);
	cin >> n >> m >> k;
	for (int i = 1; i <= m; i++)
	{
		int a, b, c;
		cin >> a >> b >> c;
		edges[i] = {a, b, c};
	}
	bellman_ford();
	if (dist[n] > 0x3f3f3f3f / 2) puts("impossible");
	else printf("%d\n", dist[n]);
	return 0;
}
