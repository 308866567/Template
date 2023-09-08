#include <iostream>
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
unordered_map<ll, unordered_map<ll, ll>> g;
//
struct node
{
	ll m, v;
	double w;
	bool operator<(node &t)
	{
		return w > t.w;
	}
} a[N];
ll n, t;

int main()
{
//	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
//	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> t;
	for (int i = 1; i <= n; i++)
	{
		cin >> a[i].m >> a[i].v;
		a[i].w = 1.0 * a[i].v / a[i].m;
	}
	sort(a + 1, a + 1 + n);
	double ans = 0;
	for (int i = 1; i <= n; i++)
	{
		if (t <= 0) break;
		if (t > a[i].m)
		{
			ans += a[i].v;
			t -= a[i].m;
		}
		else
		{
			ans += t * a[i].w;
			t = 0;
		}
	}
	printf("%.2lf",ans);
	return 0;
}
