#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;

ll n, m;
ll a[N];
ll b[N];
class segmentTree
{
	struct node
	{
		ll l, r;
		ll gcd, sum;
	} tr[4 * N];
	ll *arr;
	segmentTree(ll *t)
	{
		arr = t;
		build(0, 1, n);
	}
	ll gcd(ll a, ll b)
	{
		return b ? gcd(b, a % b) : a;
	}
	// gcd(ai,ai+1,ai+2,ai+3)
	//=gcd(ai,ai+1-ai,ai+2-ai+1,ai+3-ai+2)
	//=gcd(ai,gcd(bi+1,bi+2,bi+3))
	//=gcd(sum(b1...bi),gcd(bi+1...bi+3))
	void push_up(node &p, node l, node r)
	{
		p.sum=l.sum+r.sum;
		p.gcd=gcd(l.gcd,r.gcd);
	}
	void build(ll p, ll l, ll r)
	{
		tr[p] = {l, r};
		if (l == r)
		{
			tr[p].sum=arr[l];
			tr[p].gcd=arr[l];
			return;
		}
		ll mid = l + r >> 1;
		build(p << 1, l, mid);
		build(p << 1 | 1, mid + 1, r);
		push_up(tr[p], tr[p << 1], tr[p << 1 | 1]);
	}
};
void solve()
{
	cin >> n >> m;
	rep(i, 1, n)
	{
		cin >> a[i];
		b[i] = a[i] - a[i - 1];
	}
	char ch;
	rep(i, 1, m)
	{
		cin >> ch;
		ll l, r, v;
		if (ch == 'C')
		{
			cin >> l >> r >> v;
		}
		else
		{
			cin >> l >> r;
		}
	}
}

int main()
{
#ifdef LOCAL
	freopen("/home/xiaobingdu/code/c++/in.txt", "r", stdin);
#endif
#ifdef LOCAL_WIN
	freopen("C:/Users/30886/Desktop/in.txt", "r", stdin);
#endif
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	ll t = 1;
	// cin >> t;
	while (t--)
	{
		solve();
	}
	return 0;
}
