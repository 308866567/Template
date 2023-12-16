#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
#include <unordered_map>
#include <algorithm>
#include <cmath>
#include <map>
#include <set>
using namespace std;

typedef long long ll;
#define rep(i, a, n) for (ll i = a; i <= n; i++)
#define rem(i, a, n) for (ll i = a; i >= n; i--)

const ll N = 1e6 + 10;

//公式法求
//a个里选b个分为两种情况
//c(a,b)=c(a-1,b-1)+c(a-1,b)
// c(0,b)=0 c(a,0)=1 c(0,0)=1
void slove1() {
	int mod = 1e9 + 7;
	ll f[2002][2002];
	for (int i = 0; i <= 2000; i++) {
		for (int j = 0; j <= i; j++) {
			if (!j) f[i][j] = 1;
			else f[i][j] = (f[i - 1][j - 1] + f[i - 1][j]) % mod;
		}
	}
}


class combination {
		ll mod;
//费马小定理,快速幂,求组合数2
//通过计算乘法逆元，我们可以得到组合数的分母的倒数
//当b与m互质时,b的乘法逆元为b的m-2次方
		ll qmi(ll a, ll b) {
			ll ans = 1;
			while (b > 0) {
				if ((b & 1) != 0) ans = ans * a % mod;
				a = a * a % mod;
				b >>= 1;
			}
			return ans;
		}
//当b与m互质时,b的乘法逆元为b的m-2次方
		ll inverse(ll b) {
			return qmi(b, mod - 2);
		}

	public:
		combination(ll x) {
			mod = x;
		}
//c(a,b)=a!*inverse(b!)*inverse((a-b)!)
		ll cal(ll a, ll b) {
			ll now = 1;
			ll ans = 1;
			for (int i = 1; i <= a; i++) {
				now = now * i % mod;
				if (i == b) {
					ans = ans * inverse(now) % mod;
				} if (i == a - b) {
					ans = ans * inverse(now) % mod;
				}  if (i == a) {
					ans = ans * now % mod;
				}
			}
			return ans;
		}
};

//
int main() {
	//freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	//freopen("E:\\in.txt", "r", stdin);
	//freopen("D:\\in.txt", "r", stdin);
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	combination t=combination(1e9+7);
	ll n;
	cin>>n;
	ll a,b;
	while(n--){
		cin>>a>>b;
		cout<<t.cal(a,b)<<"\n";
	}
	return 0;
}
