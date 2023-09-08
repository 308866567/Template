package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ui.util.SwtUtils;

public class Dijkstra {
	N_mapDAO mapDAO = new N_mapDAO();// 数据库操作

	public ArrayList<Integer> dijk(Composite map, int start, int end) {
		List<Map<String, Object>> edges = null;
		ArrayList<Integer> path = new ArrayList<Integer>();// 存储路径

		int n = mapDAO.getPointSize();// 总节点个数
		int[] dist = new int[n + 2];// 最短距离
		int[] zhong = new int[n + 2];
		Arrays.fill(dist, (int) 2e6);// 初始化无穷大,注意数值越界变成负数
		// 起点初始化
		dist[start] = 0;
		Queue<node> q = new PriorityQueue<>();
		q.add(new node(start, 0));
		while (!q.isEmpty()) {
			node t = q.poll();
			// t.w表示start到t.v的距离
			// t.v表示start到v有条边
			// dist[t.v]表示start到v的距离
			if (t.w > dist[t.v] || t.w >= (int) 2e6)
				continue;
			// 以t.v为中转节点更新
			edges = mapDAO.distEdge(t.v);// 从数据库获取边集
			for (Map<String, Object> edge : edges)// 更新t.v相邻的节点j
			{
				Object b = edge.get("b");
				Object len = edge.get("最短距离");
				if (len == null)
					continue;
				int j = Integer.valueOf(b.toString());// 终点
				int d = Integer.valueOf(len.toString());// 距离
				// t.v->j=w[i]
				if (t.w + d < dist[j]) {
					dist[j] = t.w + d;
					zhong[j] = t.v;// 记录中转节点
					q.add(new node(j, dist[j]));
				}
			}
		}
		// 计算路径,TODO 需要判断能否到达
		int p = end;
		while (p != start) {
			if (p == 0)
				return null;// 防止死循环 TODO 加入无法到达的返回值
			path.add(p);
			p = zhong[p];
		}
		path.add(start);
		Collections.reverse(path);// 反转路径
//		draw(map, path);
		return path;
	}

	/**
	 * //由点的相对坐标获取点的绝对坐标
	 */
	public Point getPoint(Control map, Map<String, Object> point) {
		double weight = map.getSize().x;
		double height = map.getSize().y;
		BigDecimal X = (BigDecimal) point.get("n_x");
		BigDecimal Y = (BigDecimal) point.get("n_y");
		int x = (int) (weight * X.doubleValue());
		int y = (int) (height * Y.doubleValue());
		Point t = new Point(x, y);
		return t;
	}

	// 获取最短路上的所有点的相对坐标
	public Map<String, ArrayList<Double>> getLine(ArrayList<Integer> path) {
		Map<String, ArrayList<Double>> line=new HashMap<String, ArrayList<Double>>();
		if (path == null) {
			return null;
		}
		ArrayList<Double> X = new ArrayList<Double>();
		ArrayList<Double> Y = new ArrayList<Double>();
		line.put("x",X);
		line.put("y",Y);
		int size = path.size();
		for (int i = 0; i < size ; i++) {
			List<Map<String, Object>> a = mapDAO.findPoint(path.get(i));
			BigDecimal x = (BigDecimal) a.get(0).get("n_x");
			BigDecimal y = (BigDecimal) a.get(0).get("n_y");
			X.add(x.doubleValue());
			Y.add(y.doubleValue());
		}
		return line;
	}
	
	// 绘制最短路
		public void draw(Composite map, Map<String, ArrayList<Double>> line) {
			if (line == null) {
				return;
			}
			double weight = map.getSize().x;
			double height = map.getSize().y;
			ArrayList<Double> X = line.get("x");
			ArrayList<Double> Y =  line.get("y");
			int size = X.size();
			for (int i = 0; i < size -1; i++) {
				Point a = new Point((int)(weight*X.get(i)), (int)(height*Y.get(i)));
				Point b = new Point((int)(weight*X.get(i+1)), (int)(height*Y.get(i+1)));
				SwtUtils.drawRedLine(map,a,b );
			}
		}
	// 绘制最短路
	public void draw(Composite map, ArrayList<Integer> path) {
		if (path == null) {
			return;
		}
		int size = path.size();
		for (int i = 0; i < size - 1; i++) {
			List<Map<String, Object>> a = mapDAO.findPoint(path.get(i));
			List<Map<String, Object>> b = mapDAO.findPoint(path.get(i + 1));
			SwtUtils.drawRedLine(map, getPoint(map, a.get(0)), getPoint(map, b.get(0)));
		}
	}

	// 辅助节点
	public class node implements Comparable<node> {
		int v, w;// 终点,路线长度

		node(int a, int b) {
			v = a;
			w = b;

		}

		// 排序比较器
		@Override
		public int compareTo(node o) {
			return Integer.compare(w, o.w);// boolean转int
		}
	}
}