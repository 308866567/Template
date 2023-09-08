#include <iostream>
#include<vector>
#include<algorithm>
using namespace std;
typedef long long ll;
const long long N = 1e6 + 10, M = 2e5;
int n, m;
int k, T, S, D;
struct edge
{
	int a, b, w;
	bool operator <(const edge& ee)const
	{
		return w < ee.w;
	}
} ed[M];
vector<int> e[N];//即为重构树
int d[N];
int p[N];
int a[N];
int down[N];
int fa[N][18];
int find(int x)
{
	return p[x] == x ? p[x] : p[x] = find(p[x]);
}
int kruskal()
{
	sort(ed + 1, ed + 1 + m);
	for (int i = 1; i <= 2 * n; i++)
		p[i] = i;
	int cnt = n;
	for (int i = 1; i <= m; i++)
	{
		int a = find(ed[i].a), b = find(ed[i].b);
		if (a ^ b)//a!=b
		{
			d[++cnt] = ed[i].w;
			p[a] = p[b] = cnt;
			e[cnt].push_back(a);
			e[cnt].push_back(b);
		}
	}
	return cnt;
}
