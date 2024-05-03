#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const ll N = 1e6 + 10;
string s[N];
ll tree[N][26], idx = 0;
ll ee[N];
//插入字符串
void insert(string x)
{
    ll p = 0;//从根节点开始
    for (ll i = 0; i < x.size(); i++)
    {
        ll j = x[i] - 'a';//下一节点的key
        if (tree[p][j] == 0)//没有节点
        {
            tree[p][j] = ++idx;//新建节点
        }
        p = tree[p][j];//进入下一节点
    }
    ee[p]++;//标记点p为一个字符串的结尾
}
//查一个字符串是否在字典树里
ll search(string x)
{
    ll ans = 0;
    ll p = 0;
    for (ll i = 0; i < x.size(); i++)
    {
        ll j = x[i] - 'a';
        if (tree[p][j] == 0)//中途断开
        {
            break;
        }
        p = tree[p][j];
        if (ee[p] > 0)//遇到结尾
        {
            ans += ee[p];
        }
    }
    return ans;
}
int main()
{
    ll n,m;
    cin>>n>>m;
    for(ll i=0;i<n;i++){
        cin>>s[i];
        insert(s[i]);
    }
    string t;
    while(m--){
        cin>>t;
        cout<<search(t)<<"\n";
    }
    return 0;
}