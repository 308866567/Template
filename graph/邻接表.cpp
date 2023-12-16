#include <iostream>
#include<cstring>
#include <cstring>
using namespace std;
typedef long long ll;
const long long N = 1e6 + 1;
//e,w存储终点和权值,索引是idx
//ne存储同起点下一条边的索引
//h[a]存储起点a最后一条边的索引
//h需要初始化为-1
ll h[N], w[N], e[N], ne[N], idx;
void add(ll a, ll b, ll c) {
	e[idx] = b, w[idx] = c, ne[idx] = h[a];
	h[a] = idx++;
	/*
	memset(h, -1, sizeof (h));
	for (int i = h[u]; i != -1; i = ne[i])//i是边索引
	{
	int j = e[i]; //终点
	 */
}
//dfs
bool st[N];
void dfs(ll u) {
	st[u] = 1;
	for (int i = h[u]; i != -1; i = ne[i]) { //i是边索引
		int j = e[i]; //终点
		if (st[j])
			continue;
		dfs(j);
	}
	return ;
}
//bfs
#include <queue>
void bfs() {
	int d[N];//存储节点的层次
	memset(d, -1, sizeof d);
	queue<int> q;
	d[1] = 0;//根节点为第0层
	q.push(1);

	while (q.size()) {
		int t = q.front();
		q.pop();
		for (int i = h[t]; i != -1; i = ne[i]) {
			int j = e[i];
			if (d[j] == -1) { //防止重复遍历
				d[j] = d[t] + 1;
				q.push(j);
			}
		}
	}
}
int main() {
	//freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
	//  ios::sync_with_stdio(0);
	//  cin.tie(0);
	// cout.tie(0);
	ll n, m;
	cin >> n >> m;
	//m条边
	ll a, b, c;
	for (int i = 0; i < m; i++) {
		cin >> a >> b >> c;
		add(a, b, c);//单向边a->b
	}
	return 0;
}
