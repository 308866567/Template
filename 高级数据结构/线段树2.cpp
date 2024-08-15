#include <bits/stdc++.h>

using namespace std;
 
using i64 = long long;
 
const int N = 100010;
const i64 inf = 1e16;

int a[N];

struct Info {
  int lc, rc, ls, rs, len, ans;
};

Info operator+(Info a, Info b) {
  Info c = {0, 0, 0, 0, 0};
  c.ans = max(a.ans, b.ans);
  if (a.rc == 0 && b.lc == 0) c.ans = max(c.ans, a.rs + b.ls);
  c.lc = a.lc, c.rc = b.rc;
  c.ls = a.ls;
  if (a.ls == a.len && a.rc == b.lc) c.ls += b.ls;
  c.rs = b.rs;
  if (b.rs == b.len && a.rc == b.lc) c.rs += a.rs;
  c.len = a.len + b.len;
  return c;
}

struct SegTree {

  struct Node {
    int l, r;  
    Info info;
  } tr[N << 2];
  
  void pull(int u) {
    tr[u].info = tr[u << 1].info + tr[u << 1 | 1].info;
  }
  
  void build(int u, int l, int r) {
    tr[u] = {l, r};
    if (l == r) {
      // int lc, rc, ls, rs, len, ans;
      tr[u].info = {a[l], a[l], 1, 1, 1, a[l] == 0};
      return;
    }
    int mid = l + r >> 1;
    build(u << 1, l, mid);
    build(u << 1 | 1, mid + 1, r);
    pull(u);
  }
  //单点修改
  void modify(int u, int x) {
    if (tr[u].l == tr[u].r) {
      //
      tr[u].info.lc ^= 1;
      tr[u].info.rc ^= 1;
      tr[u].info.ans ^= 1;
      return;
    }
    int mid = tr[u].l + tr[u].r >> 1;
    if (x <= mid) modify(u << 1, x);
    if (x > mid) modify(u << 1 | 1, x);
    pull(u);
  }
  
  Info query(int u, int l, int r) {
    if (tr[u].l >= l && tr[u].r <= r) return tr[u].info;
    int mid = tr[u].l + tr[u].r >> 1;
    if (r <= mid) return query(u << 1, l, r);
    if (l > mid) return query(u << 1 | 1, l, r);
    return query(u << 1, l, r) + query(u << 1 | 1, l, r);
  }
} t;

signed main() {
  cin.tie(0)->sync_with_stdio(false);
  int n, m;
  cin >> n >> m;
  t.build(1, 1, n);
  while (m--) {
    int op, x;
    cin >> op;
    if (op == 1) {
      cin >> x;
      t.modify(1, x);
    } else {
      int l, r;
      cin >> l >> r;
      cout << t.query(1, l, r).ans << '\n';
    }
  }
  return 0; 
}