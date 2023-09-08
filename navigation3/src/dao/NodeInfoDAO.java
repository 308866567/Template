package dao;
/**
 * 地点信息数据操作类
 * @author huan
 */

import java.util.List;
import java.util.Map;

import oracle.net.aso.s;


public class NodeInfoDAO {
	
	Dbhelper dbhelper = new Dbhelper();

	
	public Map<String, Object> findByName(String name){
		String sql="select i_id from n_node_info where i_landmark=?";
		return dbhelper.select(sql, name);
	}
	
	/**
	 * 历史记录起始点以及对应建筑物的名称 
	 * @return
	 */
	public List<Map<String, Object>> selectSD(int u_id){
//		String sql="select c.b_sid,c.s_id,d.s,c.b_eid,c.e_id,d.e from (select b.n_id_start b_sid,b.start_id s_id,b.n_id_end b_eid,i.n_id e_id,i.i_id iid  from \r\n"
//				+ "(select n_id_start,n_id start_id,n_id_end from n_node_info i join n_User_history h on h.n_id_start = i.i_id) b \r\n"
//				+ "join n_node_info i on b.n_id_end=i.i_id) c cross join (select a.start_mark s,i.i_landmark e,i.i_id iid  from (select i_landmark start_mark,n_id_end end_id \r\n"
//				+ "from n_node_info i join n_User_history h on h.n_id_start = i.i_id) a join n_node_info i on a.end_id=i.i_id) d where c.iid=d.iid";
//		System.out.println(sql);
		
		String sql="select  sum(A.u_times) 次数,A.u_id, A.n_id_start b_sid,A.起点绑定点id s_id,A.起点名称 s, A.n_id_end b_eid,B.终点绑定点id e_id,B.终点名称 e from(\r\n"
				+ "select n_User_history.*,n_node_info.i_landmark 起点名称,n_node_info.n_id 起点绑定点id from n_User_history  join n_node_info on n_id_start=i_id\r\n"
				+ ") A join\r\n"
				+ "(select h_id,n_node_info.i_landmark 终点名称,n_node_info.n_id 终点绑定点id from n_User_history  join n_node_info on n_id_end=i_id\r\n"
				+ ")B on A.h_id=B.h_id   where u_id=?\r\n"
				+ "group by A.u_id, A.n_id_start,A.起点绑定点id,A.起点名称,A.n_id_end,B.终点绑定点id,B.终点名称\r\n"
				+ "ORDER BY 次数 DESC";
//		System.out.println(sql);
		return dbhelper.finds(sql,u_id);
	}
	
	/**
	 * 设置地点信息
	 * @param lx
	 * @param ly
	 * @param rx
	 * @param ry
	 * @return
	 */
	public int setCompositeSize(double lx,double ly,double rx,double ry) {
		String sql="INSERT INTO n_node_info (i_id, i_lx, i_ly, i_rx, i_ry) "
				+ "VALUES (seq_n_node_info_id.nextval,?, ?, ?, ?)";
		return dbhelper.update(sql, lx,ly,rx,ry);
	}
	
	/**
	 * 查找所有的建筑名
	 * @return
	 */
	public List<Map<String, Object>> selectName(){
		String sql="select i_landmark from n_node_info";
		return dbhelper.finds(sql);
	}

	
	public int setCompositeSize(byte []bt,String name,String detail, double lx,double ly,double rx,double ry,int id) {
		String sql="INSERT INTO n_node_info (i_id, i_image, i_landmark, i_detail, i_lx, i_ly, i_rx, i_ry,n_id)\r\n"
				+ "VALUES (seq_n_node_info_id.nextval, ?, ?, ?, ?, ?, ?, ?,?)";
		return dbhelper.update(sql,bt,name,detail, lx,ly,rx,ry,id);
	}
	
	/**
	 * 添加地点信息
	 * @param bt
	 * @param name
	 * @param detail
	 * @param lx
	 * @param ly
	 * @param rx
	 * @param ry
	 * @return
	 */
	public int setCompositeSize(byte []bt,String name,String detail, double lx,double ly,double rx,double ry) {
		String sql="INSERT INTO n_node_info (i_id, i_image, i_landmark, i_detail, i_lx, i_ly, i_rx, i_ry)\r\n"
				+ "VALUES (seq_n_node_info_id.nextval, ?, ?, ?, ?, ?, ?, ?)";
		return dbhelper.update(sql,bt,name,detail, lx,ly,rx,ry);
	}
	
	/**
	 * 通过地点编号查询地点信息
	 * @param id
	 * @return
	 */
	public Map<String,Object> selectById(String id){
		String sql="select i_id, i_image, i_landmark, i_detail, i_lx, i_ly, i_rx, i_ry from n_node_info where i_id=?";
		
//		String sql="select i_image from n_node_info where i_id=?";
		return dbhelper.select(sql, id);
	}
	
	/**
	 * 根据建筑id删除信息
	 * @param id
	 * @return
	 */
	public int delete(String id){
		String sql="delete from n_node_info where i_id=?";
		return dbhelper.update(sql, id);
	}
	
	/**
	 * 查询所有地点
	 * @return
	 */
	public List<Map<String, Object>> selectAll(){
		String sql="select i_id, i_image, i_landmark, i_detail, i_lx, i_ly, i_rx, i_ry,n_id from n_node_info";
		return dbhelper.finds(sql);
	}
}
