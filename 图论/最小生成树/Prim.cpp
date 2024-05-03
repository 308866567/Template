#include <iostream>
#include<vector>
#include<queue>
#include<cstring>
#include<unordered_map>
#include<algorithm>
using namespace std;
typedef int ll;
const ll N = 510,INF=0x3f3f3f3f;
ll n,m;
ll g[N][N];
ll dist[N];//dist[i] 表示 i 节点到连通部分的最短距离。
bool st[N];//中转节点
//
ll prim()//返回最小生成树的边权和
{	
	memset(dist,INF,sizeof dist);
	ll res=0;
	for(int i=0;i<=n;i++)
	{
		int t=-1;
		for(int j=1;j<=n;j++)//选取一个离连通部分最近的节点t
		{
			if(!st[j]&&(t==-1||dist[t]>dist[j]))
				t=j;
		}
		if(i&&dist[t]==INF) //找不到可以联通的节点
			return INF;
		if(i)//i>0时 
			res+=dist[t];
		st[t]=1;
		for(int j=1;j<=n;j++)//i=0时,将连通t附近的节点
		{
			dist[j]=min(dist[j],g[t][j]);//i>0时,更新离连通部分的距离
		}
	}
	return res;
}
int main()
{ 
	freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	ios::sync_with_stdio(0),cin.tie(0),cout.tie(0);
	cin>>n>>m;
	ll a,b,c;
	memset(g,INF,sizeof g);
	for(int i=1;i<=m;i++){
		cin>>a>>b>>c;
		g[a][b]=g[b][a]=min(g[a][b],c);
	}
	ll t=prim();
	if(t==INF) cout<<("impossible");
	else cout<<t;
	return 0;
}
