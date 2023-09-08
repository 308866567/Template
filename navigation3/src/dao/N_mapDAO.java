package dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class N_mapDAO {
	Dbhelper dbhelper = new Dbhelper();
	


	public int getPointSize() {
		String sql = "SELECT max(n_id) FROM n_node";
		List<Map<String, Object>> t = dbhelper.finds(sql);
		String key = "max(n_id)";
		int sum = bigToInt(t.get(0).get(key))+2;
//		System.out.println(sum);
		return sum;
	}

	public int bigToInt(Object big) {
		BigDecimal decimalValue = (BigDecimal) big;
		return decimalValue.intValue();
	}

	// 添加点
	public int addPoint(String name, double x, double y) {
		String sql = "insert into n_node(n_id,n_name,n_x,n_y)" + " values(seq_n_node_id.nextval,?,?,?)";
		return dbhelper.update(sql, name, x, y);

	}

	// 获取新建的点id
	public List<Map<String, Object>> getId(String name, double x, double y) {
		String sql = "select * from n_node where n_name=? and n_x=? and n_y=?";
		return dbhelper.finds(sql, name, x, y);
	}

	// 修改点
	public int updatePoint(int id, String name, double x, double y) {
		String sql = "update n_node set n_name=?,n_x=?,n_y=? where n_id=?";
		return dbhelper.update(sql, name, x, y, id);
	}

	// 删除点
	public int delPoint(int id) {
		if (id == 0)
			return -1;
		String sql = "delete from n_edge where n_start=? or n_end= ?";
		dbhelper.update(sql, id, id);
		sql = "delete from n_node where n_id=?";
		return dbhelper.update(sql, id);
	}

	// 获取一个点的所有信息
	public List<Map<String, Object>> findPoint(int x) {
		String sql = "select * from n_node where n_id=?";
		return dbhelper.finds(sql, x);
	}

	// 返回所有的地点信息,用于用户界面初始化的绘制
	public List<Map<String, Object>> allPoint() {
		String sql = "select * from n_node where n_id!=0";
		return dbhelper.finds(sql, null);
	}

	// 插入一条边
	public int addEdge(Object... params) {
		String sql = "insert into n_edge values(seq_n_edge_id.nextval,?,?,?)";
		return dbhelper.update(sql, params);
	}

	// 查找一条边的id
	public Object findEdgeId(int start, int end, int len) {
		String sql = "select * from n_edge where n_start=? and n_end=? and n_distance=?";
		List<Map<String, Object>> t = dbhelper.finds(sql, start, end, len);
		if (t.size() == 0 || t == null)
			return null;
		return t.get(0).get("e_id");
	}

	// 根据id查找一条边
	public Map<String, Object> findEdge(int e_id) {
		String sql = "select * from n_edge where e_id=?";
		List<Map<String, Object>> t = dbhelper.finds(sql, e_id);
		if (t == null)
			return null;
		return t.get(0);
	}

	// 修改一条边
	public int updateEdge(Object... params) {
		String sql = "update n_edge set n_start=?,n_end=?,n_distance=? where e_id=?";
		return dbhelper.update(sql, params);
	}

	// 由边的id删除一条边
	public int delEdge(int e_id) {
		String sql = "delete from n_edge where e_id=?";
		return dbhelper.update(sql, e_id);
	}

	// 由边的信息删除一条边
	public int daddEdge(int start, int end, int len) {
		String sql = "delete from n_edge where n_start=? and n_end=? and n_distance=?";
		return dbhelper.update(sql, start, end, len);
	}

	// 返回以一个点为起点的所有道路信息,距离短优先
	// 已有的路径显示距离,没有的显示空
	public List<Map<String, Object>> distEdge(int start) {
		String sql = "select a,b,min(n_distance) as 最短距离  from n_edge right join \r\n"
				+ "(SELECT n1.n_id as a, n2.n_id as b \r\n" + "FROM n_node n1\r\n" + "CROSS JOIN n_node n2\r\n"
				+ "WHERE n1.n_id != n2.n_id and n1.n_id=? and n2.n_id != 0)  ttt\r\n"
				+ "on n_start =ttt.a and n_end =ttt.b \r\n" + "group by a,b\r\n" + "order by a,b ";
//		System.out.println(sql);
		List<Map<String, Object>> t = dbhelper.finds(sql, start);
		return t;
	}

	public List<Map<String, Object>> allEdge(int start) {
		String sql = "select e_id,a,b,n_distance as 距离  from n_edge right join \r\n"
				+ "(SELECT n1.n_id as a, n2.n_id as b \r\n" + "FROM n_node n1\r\n" + "CROSS JOIN n_node n2\r\n"
				+ "WHERE n1.n_id != n2.n_id and n1.n_id=? and n2.n_id != 0)  ttt\r\n"
				+ "on n_start =ttt.a and n_end =ttt.b \r\n" + "order by a,b,n_distance ";
//		System.out.println(sql);
		List<Map<String, Object>> t = dbhelper.finds(sql, start);
		return t;
	}
}
