package dao;

import java.util.Map;

public class UserDao {

	Dbhelper dbhelper = new Dbhelper();
	

	
	/**
	 * 添加用户
	 * @param name
	 * @param pwd
	 * @return
	 */
	public int addUser(String name,String pwd) {
		String sql="INSERT INTO n_user (u_id, u_name, u_pwd) VALUES (seq_n_user_id.nextval, ?, ?)";
		return dbhelper.update(sql, name,pwd);
	}
	
	/**
	 * 查询指定用户信息
	 * @param name
	 * @return
	 */
	public Map<String,Object> selectUser(String name){
		String sql="select u_id, u_name, u_pwd,Remember from n_user where u_name=?";
		return dbhelper.select(sql, name);
	}
	
	/**
	 * 
	 * @param r
	 * @return
	 */
	public int updateRemember(int r,String name) {
		String sql="update n_user set Remember=? where u_name=?";
		return dbhelper.update(sql, r,name);
	}
}
