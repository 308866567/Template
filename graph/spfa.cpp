#include <iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;

#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>

ll h[N], w[N], e[N], ne[N], idx;
void add(ll a, ll b, ll c)
{
	e[idx] = b, w[idx] = c, ne[idx] = h[a];
	h[a] = idx++;
}
//
ll n, m;

void spfa(){
	
}
int main()
{
	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	cin >> n >> m;
	for (int i = 1; i <= m; i++)
	{
		ll a, b, c;
		cin >> a >> b >> c;
		add(a, b, c);
	}
	return 0;
}

