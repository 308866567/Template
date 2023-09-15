#include <iostream>
using namespace std;
typedef long long ll;
const ll N = 1e5 + 10;
//洛谷p1776
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>

struct node
{
	ll v, w;//花费,价值
}
a[N];
ll idx = 0;
//
ll n, W;

void slove1()//二进制优化
{
	ll dp[N] = {0};
	for (int i = 1; i <= n; i++)
	{
		ll v, w, m;
		cin >> w >> v >> m;
		ll k = 1;
		while (k <= m)
		{
			idx++;
			a[idx].v = k * v;
			a[idx].w = k * w;
			m -= k;
			k *= 2;
		}
		if (m > 0)
		{
			idx++;
			a[idx].v = m * v;
			a[idx].w = m * w;
		}
	}
	for (int i = 1; i <= idx; i++)
	{
		for (int j = W; j >= a[i].v; j--)
		{
			dp[j] = max(dp[j], dp[j - a[i].v] + a[i].w);
		}
	}
	cout << dp[W];
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> W;
	slove1();
	return 0;
}
