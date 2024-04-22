#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
//堆性质：任意节点都比其子节点大，只在父子孙之间有性质
class Heap
{
public:
    const static ll SIZE = 1e5;
    ll heap[SIZE], n=0;
    void insert(ll val)
    {
        heap[++n] = val;
        // 调整新插入节点的位置,注意是末尾
        up(n);
    }
    void up(ll p)
    {
        //p是子节点
        //这儿是p,p不能为根节点,p/2不能=0
        while (p > 1)
        {
            // 大根堆，比父节点则交换
            if (heap[p] > heap[p / 2])
            {
                swap(heap[p], heap[p / 2]);
                p /= 2;
            }
            else
            {
                break;
            }
        }
    }
    ll GetTop()
    {
        return heap[1];
    }
    void down(ll p)
    {
        ll s = p *2;
        //注意s<=n
        //s最小等于n
        while (s <= n)
        {
            //取子节点的较大者,防止s+1越界
            if(s<n&&heap[s]<heap[s+1]) s++;
            //子节点大就交换
            if (heap[s] > heap[p])
            {
                swap(heap[s],heap[p]);
                p = s, s=p*2;
            }
            else
                break;
        }
    }
    void Remove(ll k){
        heap[k]=heap[n--];
        up(k);
        down(k);
    }
};
int main()
{
    return 0;
}