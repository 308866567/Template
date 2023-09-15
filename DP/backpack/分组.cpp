#include <iostream>
/*
https://www.acwing.com/problem/content/9/
*/
using namespace std;
typedef long long ll;
const ll N = 1e2 + 10;

#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
struct node
{
	ll v, w, s;
} a[N][N];
ll s[N];
ll n, v;
void slove1()//容积放在外层,可压缩为1维
{
	ll dp[N][N] = {0}; //前i组,j容积max
	//无后效性
	for (int i = 1; i <= n; i++)
	{
		for (int j = 0; j <= v;  j++) 
		{
			dp[i][j] = dp[i - 1][j];
			for (int k = 1; k <= s[i]; k++)
			{
				if (j >= a[i][k].v)
				{
					dp[i][j] = max(dp[i][j], dp[i-1][j - a[i][k].v] + a[i][k].w);
				}
			}
		}
	}
	cout << dp[n][v];
}
void slove2()//容积放在内层,需要枚举所有容积
{

	ll dp[N][N] = {0}; //前i组,j容积max
	//无后效性
	for (int i = 1; i <= n; i++)
	{
		for (int k = 1; k <= s[i]; k++)
		{
			for (int j = 0; j <= v;  j++) 
			{
				dp[i][j]=max(dp[i][j],dp[i-1][j]);
				if (j >= a[i][k].v)
				{
					dp[i][j] = max(dp[i][j], dp[i-1][j - a[i][k].v] + a[i][k].w);
				}
			}
		}
	}
	cout << dp[n][v];
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> v;
	for (int i = 1; i <= n; i++) //n组
	{
		cin >> s[i];
		for (int j = 1; j <= s[i]; j++)
		{
			cin >> a[i][j].v >> a[i][j].w;
		}
	}
	slove1();
	return 0;
}
