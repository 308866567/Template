#include <bits/stdc++.h>
using namespace std;
typedef long ll;
const ll N = 1e6 + 10;
string s[N];
ll tree[N][26], idx = 0;
ll ee[N];
void insert(string x)
{
    ll p = 0;
    for (int i = 0; i < x.size(); i++)
    {
        ll j = x[i] - 'a';
        if (tree[p][j] == 0)
        {
            tree[p][j] = ++idx;
        }
        p = tree[p][j];
        // cout << p << ' ';
    }
    ee[p]++;
    // cout << "\n";
}
ll search(string x)
{
    ll ans = 0;
    ll p = 0;
    for (int i = 0; i < x.size(); i++)
    {
        ll j = x[i] - 'a';
        
        if (tree[p][j] == 0)
        {
            break;
        }
        p = tree[p][j];
        if (ee[p] > 0)
        {
            ans += ee[p];
        }
        // cout << p << ' ';
    }
    // cout << "\n";
    return ans;
}
int main()
{
    // freopen("/home/xiaobingdu/Downloads/in.txt", "r", stdin);
    ll n, m;
    cin >> n >> m;
    for (int i = 0; i < n; i++)
    {
        cin >> s[i];
        insert(s[i]);
    }
    string t;
    while (m--)
    {
        cin >> t;
        cout << search(t) << "\n";
    }
    return 0;
}