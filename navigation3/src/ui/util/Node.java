package ui.util;

import dao.*;
import java.math.BigDecimal;
import java.util.Map;

//从数据库提取点的信息
public class Node {
	public int n_id;
	public String n_name;
	public double n_x;
	public double n_y;

	// 由点编号获取信息
	public	Node(int id) {
		N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
		Map<String, Object> info = mapDAO.findPoint(id).get(0);
		n_name = (String) info.get("n_name");
		n_id = id;
		n_x = getDouble(info.get("n_x"));
		n_y = getDouble(info.get("n_y"));
	}

	double getDouble(Object x) {
		BigDecimal t = (BigDecimal) x;
		return t.doubleValue();
	}

	int getInt(Object x) {
		BigDecimal t = (BigDecimal) x;
		return t.intValue();
	}

	public void update() {
		if (n_id == 0) {
			add();
			return;
		}
		N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
		// 修改点
		mapDAO.updatePoint(n_id, n_name, n_x, n_y);
	}

	public void add() {
		N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
		// 添加点
		mapDAO.addPoint(n_name, n_x, n_y);
		Map<String, Object> info = mapDAO.getId(n_name, n_x, n_y).get(0);
		n_id = getInt(info.get("n_id"));
		// System.out.println(n_id);
	}
}
