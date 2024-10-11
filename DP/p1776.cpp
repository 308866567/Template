#include <iostream>
using namespace std;
typedef long long ll;
const ll N = 1e5 + 10;

#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>


ll n, m;
ll f[N], V[N], W[N], C[N];
ll q[N];
//

void slove1()//朴素
{
	for (int i = 1; i <= n; i++)
	{
		for (int j = m; j >= 0; j--)
		{
			for (int k = 0; k <= min(C[i], j / V[i]); k++)
				f[j] = max(f[j], f[j - k * V[i]] + k * W[i]);
		}
	}
	cout << f[m];
}

ll calc(int i, int u, int x)//x含义是p-k的值
{
	return f[u + x * V[i]] - x * W[i];
}
//q维护了f[u+(p-C[i])*V[i]]~f[u+(p-1)*V[i]]这个窗口
void slove2()//单调队列优化
{
	memset(f, 0xcf, sizeof (f));
	f[0] = 0;
	for (int i = 1; i <= n; i++)
	{
		//j=u+p*V[i]
		for (int u = 0; u < V[i]; u++)
		{
			ll l = 1, r = 0;
			ll maxp = (m - u) / V[i];
			for (int p = maxp - 1; p >= max((ll)0, maxp - C[i]); p--)
			{
				//右侧插入维护单调性
				while (l <= r && calc(i, u, q[r]) <= calc(i, u, p))
					r--;
				q[++r] = p;
			}
			//每次更新f[u+p*V[i]]的值
			//候选集合为max(f[u+(p-k)*V[i]]-(p-k)*W[i])+p*W[i]
			//k的范围[0,C[i]]
			//k使用单调队列存储
			for (int p = maxp; p >= 0 ; p--)
			{
				//除去>=j的决策
				//窗口右边界往左移
				while (l <= r && q[l] > p - 1)
					l++;
				//使用
				if (l <= r)//队列有值
					f[u + p * V[i]] = max(f[u + p * V[i]],
					                      calc(i, u, q[l]) + p * W[i]);
				//当前V[i]的系数是p-C[i]-1
				//添加,旧决策<=新决策,旧决策舍去
				if (p - C[i] - 1 >= 0)
				{
					//窗口左边界往左移
					//从r处添加维护单调性
					while (l <= r &&
					       calc(i, u, q[r]) <= calc(i, u, p - C[i] - 1) )
						r--;
					q[++r] = p - C[i] - 1;
				}
			}
		}
	}
	ll ans = 0;
	for (int i = 1; i <= m; i++)
	{
		ans = max(ans, f[i]);
	}
	cout << ans;
}
int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> m;
	for (int i = 1; i <= n; i++)
	{
		cin >> W[i] >> V[i] >> C[i];
	}
	slove2();
	return 0;
}
