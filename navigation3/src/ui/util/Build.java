package ui.util;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import dao.NodeInfoDAO;

/**
 *处理数据库信息为建筑物信息类 
 *
 */
public class Build {
	public	String landmark, detail;
	public	byte[] bt = null;
	public	Image image = null;
	//左上角坐标,建筑大小和容器大小的比 TODO  rx,ry改名称
	public	double lx, ly, rx, ry;
	public	int id;
	public	int n_id;
	NodeInfoDAO infoDAO = new NodeInfoDAO();

	public String[] selectName(){
		List<Map<String, Object>> list = infoDAO.selectName();
		String[] names=new String[list.size()];
		int i=0;
		for (Map<String, Object> map : list) {
			names[i]=(String)map.get("i_landmark");
		}
		return names;
	}
	
	public Build() {}
	
	public Build(Map<String, Object> map) {
		id = bigToInt(map.get("i_id"));
		n_id= bigToInt(map.get("n_id"));
		landmark = (String) map.get("i_landmark");
		detail = (String) map.get("i_detail");
		lx = bigToDou(map.get("i_lx"));
		ly = bigToDou(map.get("i_ly"));
		rx = bigToDou(map.get("i_rx"));
		ry = bigToDou(map.get("i_ry"));
		if (map.get("i_image") != null) {
			byte[] bt = (byte[]) map.get("i_image");
			ByteArrayInputStream in = new ByteArrayInputStream(bt);
			ImageData data = new ImageData(in);
			image = new Image(Display.getDefault(), data);
		} else {
			image = new Image(Display.getDefault(), "src/image/zanwu.png");// 获取图片
		}
	}

	/**
	 * 坐标数据处理 BigDecimal转double
	 * 
	 * @param big
	 * @return
	 */
	public double bigToDou(Object big) {
		BigDecimal decimalValue = (BigDecimal) big;
		return decimalValue.doubleValue();
	}

	public int bigToInt(Object big) {
		BigDecimal decimalValue = (BigDecimal) big;
		return decimalValue.intValue();
	}

	void add() {

	}

	int update() {
		return infoDAO.setCompositeSize(lx, ly, rx, ry);
	}

	int del() {
		return infoDAO.delete(String.valueOf(id));
	}
}



