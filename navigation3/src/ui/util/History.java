package ui.util;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.TableItem;

import dao.HistoryDAO;

public class History {
	HistoryDAO DAO = new HistoryDAO();
	public int h_id;
	public int u_id = 2;
	public int n_id_start;
	public int n_id_end;
	public int u_times = 1;


	public History(int start, int end,int u_id) {
		n_id_start = start;
		n_id_end = end;
		this.u_id=u_id;
	}

	// 由表格的一行构造
	public History(TableItem col) {
		if (null == col.getData("start"))
			n_id_start = -1;
		else
			n_id_start = (int) col.getData("start");
		if (null == col.getData("end"))
			n_id_end = -1;
		else
			n_id_end = (int) col.getData("end");
	}

	public	void add() {
		DAO.addHistory(this);
	}
}
