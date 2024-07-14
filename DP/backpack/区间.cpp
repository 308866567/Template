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
void solve()
{
	cin >> n;
    for(ll len=2;len<=n;len++){//区间长度
        for(ll l=1;l+len-1<=n;l++){//区间起点
            ll r=l+len+1;
            for(ll k=l;k<r;k++){//枚举分割点
                // f[l][r]=min(f[l][r],f[l][k]+f[k+1][r]+代价)
            }
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
