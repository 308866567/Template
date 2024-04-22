#include <iostream>
#include <bits/stdc++.h>
using namespace std;
class uf
{
public:
    int n; // n个节点
    int m; // m个操作
    vector<int> parent;
    void slove()
    {
        cin >> n >> m;
        parent.resize(n + 1);
        for (int i = 0; i <= n; i++)
        {
            parent[i] = i; // 初始时都为自己
        }
        int z, x, y;
        for (int i = 0; i < m; i++)
        {
            cin >> z >> x >> y;
            if (z == 1)
                un(x, y, 1);
            else
            {
                if (un(x, y, 0))
                {
                    cout << "Y\n";
                }
                else
                    cout << "N\n";
            }
        }
    }
    int un(int a, int b, bool z) // 连通
    {
        int t1 = find(a);
        int t2 = find(b);
        if (t1 == t2)
            return 1;
        if (z)
            parent[t1] = t2;
        return 0;
    }
    int find(int x)
    {
        if (parent[x] != x) // 根节点的父节点是其本身
            parent[x] = find(parent[x]);
        return parent[x];
    }
    int find2(int x)
    {
        if (parent[x] == x)
            return x;
        return parent[x] = find(parent[x]);
    }
    void merge(int x, int y)
    {
        parent[find(x)] = find(y);
    }
};
int main()
{
    // freopen("C:\\Users\\30886\\Desktop\\in.txt", "r", stdin);
    // ios::sync_with_stdio(0);
    // cin.tie(0);
    uf a;
    a.slove();
    return 0;
}