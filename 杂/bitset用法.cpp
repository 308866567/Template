#include <iostream>
#include <bitset>
using namespace std;
bitset<10> a;

//赋值
void init() {
	//从右往左
	//使用二进制字符串进行赋值
	string arr="1011";
	bitset<10>a1(arr);
	cout<<a1<<"\n";
	cout<<a1[0]<<"\n";
	 // 通过整数进行赋值
	bitset<10>a2(9);
	cout<<a2<<"\n";
	//通过另一个bitset进行赋值
	//需要长度相同
	bitset<10>a3=a2;
	cout<<a3<<"\n";
}
//修改
void update() {
	//[0]在最右边
	//下标访问进行单点修改
	a[0]=1;
	cout<<a<<"\n";
	//直接用位运算符
	cout<<(a<<1)<<"\n";
}
//查询
void find () {
	/*
	  下面是一些常用方法的概述：
	  
	  bitset< N >：创建一个具有N个位的std::bitset对象，N必须是一个常量表达式。
	  
	  size()：返回std::bitset对象中位的数量。
	  
	  count()：返回被设置为1的位的数量。
	  
	  test(pos)：测试给定位置pos处的位是否被设置为1，返回一个布尔值。
	  
	  set()：将所有位设置为1。
	  
	  set(pos)：将给定位置pos处的位设置为1。
	  
	  reset()：将所有位设置为0。
	  
	  reset(pos)：将给定位置pos处的位设置为0。
	  
	  flip()：将所有位取反。
	  
	  flip(pos)：将给定位置pos处的位取反。
	  
	  to_string()：返回一个字符串表示std::bitset对象的位集合。
	  
	  to_ulong()：将std::bitset对象转换为unsigned long类型的值。
	  
	  to_ullong()：将std::bitset对象转换为unsigned long long类型的值。
	  
	  any()：any方法返回一个布尔值，指示位集合中是否至少有一个位被设置为1。如果至少有一个位为1，则返回true；否则返回false。
	  
	  all()：all方法返回一个布尔值，指示位集合中的所有位是否都被设置为1。如果所有位都为1，则返回true；否则返回false。
	  
	  none()方法返回一个布尔值，指示位集合中是否没有任何位被设置为1。如果所有位都为0，则返回true；否则返回false。
	  
	  _Find_first():用于查找位集合中第一个被设置为1的位的位置。如果找到，则返回该位置的索引；否则返回npos。该方法的返回类型是size_type。需要注意的是，返回的位置索引是从0开始计数的。
	  
	 */
}

int main() {
	init();

	return 0;
}
