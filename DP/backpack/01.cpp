#include <iostream>
/*
	https://www.luogu.com.cn/problem/P1048
	采药
*/
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
using namespace std;
typedef long long ll;
const ll N = 1e3 + 10;
ll n, v;
struct node
{
	ll v, w;
} a[N];
void slove1()//二维解法
{
	ll dp[N][N]={0};
	//物品只能用一次
	for (int i = 1; i <= n; i++) //物品
	{
		for (int j = 0; j <= v; j++)
		{
			dp[i][j] = dp[i - 1][j];
			if (j >= a[i].v)
				dp[i][j] = max(dp[i][j], dp[i - 1][j - a[i].v] + a[i].w);
		}
	}
	cout << dp[n][v];
	return ;
}
void slove2()//一维
{
	ll dp[N]={0};
	//物品只能用一次
	for (int i = 1; i <= n; i++) //物品
	{
		for (int j = v; j >= a[i].v; j--) //倒序遍历,保证dp[j]实际为dp[i-1][j]
		{
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
	slove2();
	return 0;
}
