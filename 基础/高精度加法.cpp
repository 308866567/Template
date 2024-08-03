#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
ll n;
class Solution
{
public:
	vector<int> add(vector<int> &a, vector<int> &b)
	{
		vector<int> c;
		int la = a.size() - 1, lb = b.size() - 1;
		int lc = max(a.size(), b.size());
		int add = 0;
		for (int i = 0; i < lc; i++)
		{
			int ta = 0;
			int tb = 0;
			if (la - i >= 0)
				ta = a[la - i];
			if (lb - i >= 0)
				tb = b[lb - i];
			int t = ta + tb + add;
			add = t / 10;
			c.push_back(t % 10);
		}
		if (add > 0)
			c.push_back(add);
		reverse(c.begin(), c.end());
		return c;
	}
	vector<int> plusOne(vector<int> &digits)
	{
		vector<int> b({1});
		return add(digits, b);
	}
	int p = 2;
	string addBinary(string a, string b)
	{
		string c;
		int la = a.size(), lb = b.size();
		int lc = max(a.size(), b.size());
		int add = 0;
		for (int i = 1; i <= lc; i++)
		{
			int ta = 0;
			int tb = 0;
			if (la - i >= 0)
				ta = a[la - i] - '0';
			if (lb - i >= 0)
				tb = b[lb - i] - '0';
			int t = ta + tb + add;
			add = t/p;
			t %= p;
			c += t + '0';
		}
		if (add > 0)
		{
			c += (add + '0');
		}
		reverse(c.begin(), c.end());
		return c;
	}
};
void solve()
{
	Solution s;
	string a="5",b="1";
	cout<<s.addBinary(a,b);
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
