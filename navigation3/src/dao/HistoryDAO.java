package dao;

import java.util.List;
import java.util.Map;

import ui.util.History;

public class HistoryDAO {
//	Dbhelper dbhelper = new Dbhelper();
	
	Dbhelper dbhelper = new Dbhelper();


	public int addHistory(History h) {
		String sql = "insert into n_User_history values(seq_n_history_id.nextval,?,?,?,?)";
		return dbhelper.update(sql,h.u_id,h.n_id_start,h.n_id_end,h.u_times);
	}
	
	public List<Map<String, Object>> findByID(int sid,int eid){
		String sql = "select h_id, u_id, n_id_start, n_id_end, u_times from n_User_history where n_id_start=? and n_id_end=?";
		return dbhelper.finds(sql,sid,eid);
	}
	
	public int updateTimes(int sid,int eid) {
		String sql = "update n_User_history set u_times=u_times+1 where n_id_start=? and n_id_end=?";
		return dbhelper.update(sql,sid,eid);
	}

}