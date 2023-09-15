#include <iostream>
using namespace std;
typedef long long ll;
const ll N = 1e5 + 10;

#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
template<typename T>
void out(T *a)
{
	for (int i = 0; i <= 10; i++)
	{
		cout << a[i] << ' ';
	}
}
struct node
{
	ll v, w, m; //花费,价值,个数
}
a[N];
ll dp[N] = {0};
ll q[N] = {0};
ll n, W;
ll calc(int i, int d, int k)
{
	return dp[d+k*a[i].v]-k*a[i].w;
}
void slove1()//二进制优化
{
	memset(dp,0xcf,sizeof dp);
	dp[0]=0;
	for (int i = 1; i <= n; i++)
	{
		cin >> a[i].w >> a[i]. v >> a[i].m;
		//枚举余数,更新j%v==d的dp[j]
		for (int d = 0; d < a[i].m; d++)
		{
			ll l = 1, r = 0;
			ll maxp = (W - d) / a[i].v;
			//倒序更新
			//初始化滑动窗口
			for (int k = maxp - 1; k >= max((ll)0, maxp - a[i].m); --k)
			{
				//窗口内只保留最大的g[d+k*v[i]]
				while (l <= r && calc(i, d, q[r]) <= calc(i, d, k))
					r--;
				q[++r] = k;//队列存储决策选k个i物品
			}
			//往0滑动
			for (int k = maxp; k >= 0; --k)
			{
				//排除过时的决策
				while (l <= r && q[l] > k - 1) l++;

				//更新状态
				if (l <= r)
					dp[d + k * a[i].v] = max(dp[d + k * a[i].v], calc(i, d, q[l]) + k * a[i].w);

				//添加决策k
				while (l <= r && calc(i, d, q[r]) <= calc(i, d, k - a[i].m - 1)) r--;
				q[++r] = k - a[i].m - 1;
			}
		}
	}
	ll ans = 0;
	for (int i = 1; i <= W; i++)
	{
		ans = max(ans, dp[i]);
	}
	cout << ans;
	return ;
}

int main()
{
	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> W;
	slove1();
	return 0;
}

