#include <iostream>
/*
	https://www.luogu.com.cn/problem/P1616
	疯狂的采药
*/
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
using namespace std;
typedef long long ll;
const ll N = 1e7 + 10;
const ll M = 1e4 + 10;
ll n, v;
struct node
{
	ll v, w;
} a[M];
void slove1()//二维解法,3个for
{
	ll dp[1000][1000] = {0};//会爆空间
	for (int i = 1; i <= n; i++) //物品
	{
		for (int j = a[i].v; j <= v; j++)
		{
			for (int k = 1; k <= n; k++)//无限拿
			{
				//能放入
				if (j >= k*a[i].v)
					dp[i][j] = max(dp[i - 1][j], dp[i ][j - k*a[i].v] +k* a[i].w);
			}
		}
	}
	cout << dp[n][v];
	return ;
}
void slove2()//一维,3个for
{
	ll dp[N] = {0};
	for (int i = 1; i <= n; i++) //物品
	{
		for (int j = a[i].v; j <= v; j++)
		{
			for (int k = 1; k <= n; k++)//无限拿
			{
				//能放入
				if (j >= k * a[i].v)
					dp[j] = max(dp[j], dp[j - k * a[i].v] + k * a[i].w);
			}
		}
	}
	cout << dp[v];
	return ;
}
void slove3()//一维,2个for
{
	ll dp[N] = {0};
	for (int i = 1; i <= n; i++) //物品
	{
		for (int j = a[i].v; j <= v; j++)
		{
			//能放入
			if (j >= a[i].v)
				dp[j] = max(dp[j], dp[j - a[i].v] + a[i].w);
		}
	}
	cout << dp[v];
	return ;
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> v >> n;
	for (int i = 1; i <= n; i++)
	{
		cin >> a[i].v >> a[i].w;
	}
	slove3();
	return 0;
}
