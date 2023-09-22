#include <iostream>
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
using namespace std;
typedef long long ll;
const long long N = 111;
//拓扑排序
ll n;
unordered_map<ll, unordered_map<ll, ll>> g;
//入度为0的先输出，然后子节点入度都-1
void bfs()
{
	queue<int>q;
	for (int i = 1; i <= n; i++)
	{
		if (g[i].empty())
		{
			q.push(i);
			cout << i << ' ';
		}
	}
	while (!q.empty())
	{
		int i = q.front();
		ll size = g[i].size();
		q.pop();
		for (auto it : g[i])
		{
			int j = it.first;
			if (g[j].size() == size + 1)
			{
				q.push(j);
				cout << j << ' ';
			}
		}
	}
	return ;
}
int main()
{
	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0);
	cin.tie(0);
	cout.tie(0);
	cin >> n;
	int x;
	for (int i = 1; i <= n; i++)
	{
		while (cin >> x)
		{
			if (x == 0 )break;
			g[x][i] = 1;//反向建边
		}
	}
	bfs();
	return 0;
}
