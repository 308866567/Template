#include<bits/stdc++.h>
using namespace std;
#define ll long long
ll mod; //宏定义一个mod
//快速幂优化
ll quick_pow(ll a,ll b)
{
	ll ans=1;
	while(b){
		if(b&1) ans=(ans*a)%mod;
		b>>=1;
		a=(a*a)%mod;
	}
	return ans;
}

//逆元函数 公式为 (a/b)%mod=(a*b^(mod-2))%mod
ll inv(ll a,ll b)
{
	return (a*quick_pow(b,mod-2))%mod;
}

/***
注意事项：逆元函数的使用，a必须能整除b，并且mod为质数
**/

int main()
{
	ll a,b;
	while(cin>>a>>b>>mod){
		cout<<inv(a,b)<<endl;
	}
	return 0;
}
