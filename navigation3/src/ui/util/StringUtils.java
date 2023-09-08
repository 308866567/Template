package ui.util;

/**
*@author 作者 Lydia
*@company 公司 源辰信息
*@version 创建时间:2023年8月23日下午4:54:44
* 类说明
*/
public class StringUtils {
	
	/**
	 * 将对象转为字符串
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj) {
		if(null==obj) {
			return "";
		}
		return obj.toString();
	}
	
	/**
	 * 字符串空判断
	 * @param params
	 * @return
	 */
	public static boolean isNull(String...params) {
		if(null==params ||params.length<=0) {
			return true;
		}
		for(String  str:params) {
			if(null==str ||"".equals(str)) {
				return true;
			}
		}
		
		return false;
	}
}

