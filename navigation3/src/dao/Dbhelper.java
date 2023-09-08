package dao;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleDriver;
import oracle.sql.BLOB;
/**
 * 数据库操作的帮助类
 *
 */
public class Dbhelper {
	private  Connection conn;//调试器与其调试的目标VM之间的连接。
	private  PreparedStatement stmt;//用于执行静态SQL语句并返回其生成的结果的对象。
	private  ResultSet rSet;//表示数据库结果集的数据表，通常通过执行查询数据库的语句生成。
	static int i=0;
	//驱动注册
	static {
		OracleDriver driver = new OracleDriver();
	}
	
	public Connection getConn() {
		try {
			//获取连接对象
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","a");
//			conn = DriverManager.getConnection(MyProperties.getInstance().getProperty("url"),MyProperties.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;//返回连接对象
	}
	
	/**
	 * 获取连接对象 指定用户
	 * @param user
	 * @param password
	 * @return
	 */
	public Connection getConn(String user,String password) {
		try {
			//获取连接对象
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1512:orcl",user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;//返回连接对象
	}
	
	/**
	 * 关闭所有的资源
	 * @param conn
	 * @param stmt
	 * @param rSet
	 */
	public void closeAll(Connection conn,Statement stmt,ResultSet rSet) {
		i++;
//		System.out.println(i);
		//释放资源 单独处理
		//关闭结果集对象
		if (null!=rSet) {
			try {
				rSet.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		//关闭语句对象
		if (null!=stmt) {
			try {
				stmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		//关闭连接对象
		if (null!=conn) {
			try {
				conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * @param sql 查询的sql语句 返回多条记录
	 * @param params sql语句的占位符需要的参数 传入数值的顺序必须和sql语句中占位符?顺序一致
	 * @return
	 * @throws IOException 
	 */
	public List<Map<String, Object>> finds(String sql,Object ...params) {
		List<Map<String, Object>> list = new ArrayList<>();//存整个表
		Map<String, Object> map = null;//存储一条记录
		try {
			conn = getConn();
			stmt = conn.prepareStatement(sql);
			//PreparedStatement prepareStatement​(String sql) 创建一个 PreparedStatement对象，用于将参数化SQL语句发送到数据库.
			//设置参数 传入数值的顺序必须和sql语句中占位符?顺序一致
			setParams(stmt,params);
			//执行查询
			rSet = stmt.executeQuery();//执行sql查询
			List<String> columnNames=getColumnNames(rSet);
			//对结果集进行处理
			while(rSet.next()) {
				map = new HashMap<>();
				//列名作为键,循环所有列
				for(String name:columnNames) {
					//根据列名获取值
					Object object=rSet.getObject(name);
					if (null==object) {
						map.put(name, null);
						continue;
					}
					String typeName=object.getClass().getName();
					if ("oracle.sql.BLOB".equals(typeName)) {//图片处理
						//将图片保存到字节数组中
						BLOB blob = (BLOB)object;
						InputStream in=blob.getBinaryStream();//InputStream getBinaryStream() 以流的 BLOB检索此 Blob实例指定的 BLOB值。 
						byte[] bt=new byte[(int)blob.length()];
						in.read(bt);
						map.put(name, bt);
					}else {
						map.put(name, rSet.getObject(name));
					}
				}
				list.add(map);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询语句出错,dbhelper finds()");
		} catch (IOException e) {
			e.printStackTrace();
			this.closeAll(conn, stmt, rSet);
		}finally {
			this.closeAll(conn, stmt, rSet);
		}
		return list;
	}
	
	/**
	 * 更新多条语句    多条语句的存储用class会不会好处理一点?
	 * @param sqls
	 * @param params
	 * @return
	 */
	public int update(List<String>sqls,List<List<Object>>params) {
		int result = 0;
		try {
			//默认情况下,connection处于自动提交模式,禁用后需要显示调用commit方法
			conn=getConn();
			conn.setAutoCommit(false);
			for (int i = 0; i < sqls.size(); i++) {
				String sql=sqls.get(i);
				//获取当前sql语句对应的参数集合
				List<Object> list = params.get(i);
				//获取预编译对象
				stmt = conn.prepareStatement(sql);
				//设置参数
				setParams(stmt, list.toArray());
				//执行更新操作
				result = stmt.executeUpdate();
				if (result<=0) {//执行失败
					conn.rollback();//事务回滚
					result = 0;
				}
			}
			//事务提交
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				result = 0;
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			System.out.println("事务处理异常:"+e.getMessage());
			e.printStackTrace();
			closeAll(conn, stmt, rSet);
		}finally {
			//conn.setAutoCommit(ture);//还原事务提交状态  设置?池时需要还原状态
			closeAll(conn, stmt, rSet);
		}
		return result;
	}
	
	/**
	 * 查询 select * from tb_name where id=?
	 * @param sql
	 * @param params
	 * @return
	 * @throws IOException 
	 */
	public Map<String,Object> select(String sql,Object...params) {
		List<Map<String, Object>> list = finds(sql, params);
		if (null==list||list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 更新操作 delete insert update 单条sql语句
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql,Object...params) {
		int result = 0;
		try {
			conn =getConn();
			stmt = conn.prepareStatement(sql);
			setParams(stmt, params);
			result = stmt.executeUpdate();//返回受影响的语句条数
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("update error");
			closeAll(conn, stmt, rSet);
		}finally {
			closeAll(conn, stmt, rSet);
		}
		return result;
	}
	
	/**
	 * 获取所有列名
	 * @param rSet2
	 * @return
	 * @throws SQLException
	 */
	private List<String> getColumnNames(ResultSet rSet2) throws SQLException {
		List<String> names=new ArrayList<>();
		ResultSetMetaData metaData = rSet2.getMetaData();//可用于获取有关ResultSet对象中列的类型和属性的信息
		int count=metaData.getColumnCount();//获取总列数
//		System.out.println(count);
		for(int i=1;i<=count;i++) {
			String name = metaData.getColumnName(i);//从1开始 获取列名
//			System.out.println(name.toLowerCase());
			names.add(name.toLowerCase());//列名转换为小写
		}
		return names;
	}
	/**
	 * 设置参数
	 * @param stmt2 当前预编译对象
	 * @param params 传入的参数
	 * @throws SQLException
	 */
	private void setParams(PreparedStatement stmt2, Object[] params) throws SQLException {
		//设置参数 传入参数值的顺序必须和sql语句中占位符?顺序一致
		if (params!=null && params.length>0) {
			//循环数组
			for(int i=0;i<params.length;i++) {
				//占位符从1开始,数组下标从0开始
				stmt2.setObject(i+1, params[i]);
				//void setObject​(int parameterIndex, Object x) 使用给定对象设置指定参数的值。  
			}
		}
	}
	public int delete(String sql,Object...params) {
		int result = 0;
		try {
			conn = getConn();
			stmt = conn.prepareStatement(sql);
			setParams(stmt, params);
			result = stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			closeAll(conn, stmt, rSet);
		}finally {
			closeAll(conn, stmt, rSet);
		}
		return result;
	}
}
