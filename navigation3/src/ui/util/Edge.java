package ui.util;

import dao.*;
import java.math.BigDecimal;
import java.util.Map;

import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class Edge {
	public int e_id;
	public int start;
	public int end;// 终点
	public int len;// 距离

	// 由map构造出边
		public Edge(Map<String, Object> x,int X) {
			if (x.get("e_id") == null)
				e_id = -1;
			else
				e_id = getInt(x.get("e_id"));
			start = getInt(x.get("n_start"));
			end = getInt(x.get("n_end"));
			if (x.get("n_distance") == null)
				len = -1;
			else
				len = getInt(x.get("n_distance"));
		}
	
	// 由map构造出边
	public Edge(Map<String, Object> x) {
		if (x.get("e_id") == null)
			e_id = -1;
		else
			e_id = getInt(x.get("e_id"));
		start = getInt(x.get("a"));
		end = getInt(x.get("b"));
		if (x.get("距离") == null)
			len = -1;
		else
			len = getInt(x.get("距离"));
	}

	// 由表格的一行构造
	public Edge(TableItem col, int start) {
		if (null == col.getData("e_id"))
			e_id = -1;
		else
			e_id = (int) col.getData("e_id");
		this.start = start;
		end = Integer.valueOf(col.getText(0));

		Text dist = (Text) col.getData("距离");
		// TODO 文本框输入非数字会报错
		if ("" == dist.getText())
			len = -1;
		else
			len = Integer.valueOf(dist.getText());
	}

	// 转int
	int getInt(Object x) {
		if(x==null) return -1;
		BigDecimal t = (BigDecimal) x;
		return t.intValue();
	}
	
	public void del() {
		N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
		Object temp=mapDAO.findEdgeId(end, start, len);
		int t=getInt(temp);
		mapDAO.delEdge(e_id);
		if(t==-1) return ;
		mapDAO.delEdge(t);
	}
	
	public void update() {
		if (len == -1)
			return;
		if (e_id == -1) {
			add();
		} else {
			N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
			// 修改边
			mapDAO.updateEdge(start, end, len, e_id);
			Object temp=mapDAO.findEdgeId(end, start, len);
			int t=getInt(temp);
			if(t==-1) return ;
			mapDAO.updateEdge(end, start, len, t);
		}
	}

	public void add() {
		if (len == -1)
			return;
		N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
		// 添加边
		mapDAO.addEdge(start, end, len);
		//添加反向边
		mapDAO.addEdge(end, start, len);
	}
}
