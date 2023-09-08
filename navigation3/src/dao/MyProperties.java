package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyProperties extends Properties{

	private static MyProperties instance= new MyProperties();
	
	private MyProperties() {
		try {
			//根据类路路径加载配置文件获取流信息
			InputStream in=this.getClass().getClassLoader().getResourceAsStream("db.properties");
			this.load(in);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static MyProperties getInstance() {
		return instance;
	}
}
