#include <bits/stdc++.h>
// #include<iostream>
using namespace std;
typedef long long ll;
const ll N = 5e3 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define INF 0x3f3f3f3f
const ll mod = 1e9 + 7;
ll n;
ll V,M;//容量v，承重m
ll f[N][N];
void solve()
{
    cin>>n>>V>>M;
    ll v,m,w;
    rep(i,1,n){
        cin>>v>>m>>w;
        rrep(j,V,v){
            rrep(k,M,m){
                f[j][k]=max(f[j][k],f[j-v][k-m]+w);
            }
        }
    }
}
//if(j-ti<=di)
//dp[j]=max(dp[j],dp[j-ti]+pi)
//睿抗第5题
//【2024年睿抗机器人开发者大赛（RAICOM）CAIP-编程技能赛-本科组省赛 -  CSDN App】
//http://t.csdnimg.cn/SESDj
ll dp[N];
void solve2(){
    memset(dp,0,sizeof dp);
    cin>>n;
    ll t,d,p;
    ll ans=0;
    rep(i,1,n){
        cin>>t>>d>>p;
        rrep(j,5002,0){
            if(j<=d&&j-t>=0){
                dp[j]=max(dp[j],dp[j-t]+p);
            }
            ans=max(ans,dp[j]);
        }
        rep(j,0,10){
            cout<<dp[j]<<" ";
        }
        cout<<"\n";
    }
    cout<<ans<<"\n";
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
	cin >> t;
	while (t--)
	{
		solve2();
	}
	return 0;
}
