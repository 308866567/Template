#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
#define rep(i, start, end) for (ll i = start; i <= end; i++)
#define rrep(i, start, end) for (ll i = start; i >= end; i--)
#define inf 0x3f3f3f3f
//快读
inline int read() {
	int x=0,f=1; char ch=getchar();
	while(ch<'0' || ch>'9') { if(ch=='-') f=-1; ch=getchar(); }
	while(ch>='0'&&ch<='9') { x=(x<<3)+(x<<1)+(ch^48); ch=getchar(); }
	return x * f;
}
//读取行
inline string getline1(){
    string x;
    getline(cin,x,'\n');//读回车
    char * t;gets(t);//保留回车
    return x;
}
//字符串插入,增加,查找,删除,复制,修改,批量初始化     

int main()
{
#ifdef LOCAL
	freopen("/home/xiaobingdu/Downloads/in.txt", "r", stdin);
#endif
	ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);
	return 0;
}

