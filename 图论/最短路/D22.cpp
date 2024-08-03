#include<iostream>
#include<cstring>
#include<algorithm>
#include<queue>
using namespace std;
typedef pair<int,int> PII; //first存距离，second存结点编号 
const int N = 2e5+10;
int n,m;
int h[N],w[N],e[N],ne[N],idx; // 邻接表的存储 
int dist[N]; // 第 i 个点到起点的最短距离
bool st[N]; // 代表第 i 个点的最短路是否确定、是否需要更新 

void add(int a,int b,int c)
{
	e[idx] = b,w[idx] = c,ne[idx] = h[a],h[a] = idx++;
}

int dijkstra()
{
	memset(dist,0x3f,sizeof(dist)); // 将所有距离初始化正无穷
	dist[1] = 0;  // 第一个点到起点的距离为 0  
	
	priority_queue<PII, vector<PII>, greater<PII>> heap; // 小根堆
	heap.push({0,1}); //把 1 号点放入堆中 
	
	while(heap.size()) // 堆不空
	{
	   	PII t = heap.top(); //找到当前距离最小的点
		heap.pop();
		
		int ver = t.second,distance = t.first; // ver为编号，distance为距离
		if(st[ver]) continue;   // 重边的话不用更新其他点了
		st[ver] = true;   //标记 t 已经确定最短路
		
		for(int i = h[ver]; i!=-1; i=ne[i])  // 用 t 更新其他点的最短距离
		{
			int j = e[i];
			if(dist[j] > distance + w[i])
			{
				dist[j] = distance + w[i];
				heap.push({dist[j],j}); //入堆
			}
		}
	}  
	 
	if(dist[n] == 0x3f3f3f3f) return -1;  // 说明 1 和 n 是不连通的，不存在最短路 
	return dist[n];
}
int main()
{
	memset(h,-1,sizeof(h));
	cin >> n >> m;
	while(m--)
	{
		int a,b,c;
		scanf("%d%d%d",&a,&b,&c);
		add(a,b,c);
	}
	cout << dijkstra(); 
	return 0;
}
