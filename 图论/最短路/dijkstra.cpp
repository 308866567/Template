#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;

// 二维数组版本
class dijkstra
{
public:
	unordered_map<ll, unordered_map<ll, ll>> g;
	typedef pair<ll, ll> pii; // 距离,中转点
	ll dist[N];
	bool st[N];
	ll n;
	dijkstra()
	{
		rep(i, 0, N - 2)
		{
			dist[i] = INF;
		}
		memset(st, 0, sizeof st);
	}
	ll dijk(ll s, ll ee)
	{
		priority_queue<pii, vector<pii>, greater<pii>> q; // 小根堆
		q.push({0, s});									  // 存储e到其他点的距离,每次以最近点进行更新
		while (!q.empty())
		{
			pii minn = q.top();
			q.pop();
			if (st[minn.second])
				continue;
			st[minn.second] = 1;
			for (auto it : g[minn.second])
			{
				ll j = it.first;
				if (dist[j] > minn.first + it.second)
				{
					dist[j] = minn.first + it.second;
					q.push({dist[j], j});
				}
			}
		}
		if (dist[ee] >= dist[0])
			return 0;
		return dist[ee];
	}
	void add(ll a, ll b, ll c)
	{
		if (g[a].count(b) == 0)
			g[a][b] = INF;
		g[a][b] = min(g[a][b], c);
	}
	void init()
	{
	}
};

// 邻接表版本
class dijkstra2
{
public:
	ll h[N], w[N], e[N], ne[N], idx;
	typedef pair<ll, ll> pii; // 距离,中转点
	ll dist[N];
	bool st[N];
	ll n;
	dijkstra2()
	{
		rep(i, 0, N - 2)
		{
			h[i] = -1;
		}
		rep(i, 0, N - 2)
		{
			dist[i] = INF;
		}
		memset(st, 0, sizeof st);
	}
	ll dijk(ll s, ll ee)
	{
		priority_queue<pii, vector<pii>, greater<pii>> q; // 小根堆
		q.push({0, s});									  // 存储e到其他点的距离,每次以最近点进行更新
		while (!q.empty())
		{
			pii minn = q.top();
			q.pop();
			if (st[minn.second])
				continue;
			st[minn.second] = 1;
			for (ll i = h[minn.second]; i != -1; i = ne[i])
			{
				ll j = e[i];
				if (dist[j] > minn.first + w[i])
				{
					dist[j] = minn.first + w[i];
					q.push({dist[j], j});
				}
			}
		}
		if (dist[ee] >= dist[0])
			return -1;
		return dist[ee];
	}
	void add(ll a, ll b, ll c)
	{
		e[idx] = b, w[idx] = c;
		ne[idx] = h[a], h[a] = idx++;
	}
	void init()
	{
		ll n, m;
		cin >> n;
		cin >> m;
		rep(i, 1, m)
		{
			ll a, b, c;
			cin >> a >> b >> c;
			add(a, b, c);
		}
		dijk(1, n);
	}
};
void solve()
{
}

int main()
{
#ifdef LOCAL
	freopen("/home/xiaobingdu/code/c++/in.txt", "r", stdin);
#endif
#ifdef LOCAL_WIN
	freopen("C:/Users/30886/Desktop/in.txt", "r", stdin);
#endif
	// ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	ll t = 1;
	// cin >> t;
	while (t--)
	{
		solve();
	}
	return 0;
}