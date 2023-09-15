#include <iostream>
/*
https://www.acwing.com/activity/content/problem/content/1000/
*/
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;

#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
struct node
{
	ll v, w, s;
} a[N];
ll n, v;
void slove1()
{
	ll dp[N];
	for (int i = 1; i <= n; i++)
	{
		for (int j = v; j >= a[i].v;  j--)
		{
			for (int k = 1; k <= a[i].s; k++)
			{
				if (j >= a[i].v * k)
					dp[j] = max(dp[j], dp[j - a[i].v * k] + a[i].w * k);
			}
		}
	}
	cout << dp[v];
}
void slove2()//二进制优化
{
	ll dp[N];
	ll cnt = 0; //物品个数
	ll A, B, C;
	for (int i = 1; i <= n; i++)
	{
		cin >> A >> B >> C;
		ll k = 1;
		//相同物品合并成一个
		//1 2 4
		while (k <= C)
		{
			cnt++;
			a[cnt].v = A * k;
			a[cnt].w = B * k;
			C -= k;
			k *= 2;
		}
		if (C > 0)
		{
			cnt++;
			a[cnt].v = A * C;
			a[cnt].w = B * C;
		}
	}
	//转化为01背包
	for (int i = 1; i <= cnt; i++)
	{
		for (int j = v; j >= a[i].v;  j--)
		{
			dp[j]=max(dp[j],dp[j-a[i].v]+a[i].w);
		}
	}
	cout << dp[v];
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> v;
	slove2();
	return 0;
}
