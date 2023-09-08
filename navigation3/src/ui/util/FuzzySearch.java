package ui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模糊查询
 * @author huan
 *
 */
public class FuzzySearch {
	String reg;//正则表达式
	String[] result;//模糊匹配结果集
	
	
	public FuzzySearch(String keyWords) {
		reg=".*"+keyWords+".*";
	}
	
	/**
	 * 模糊查询获取查询结果集
	 * @param data
	 * @return
	 */
	public String[] search(String[] data) {
		List<String> list = new ArrayList<>();
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		for (String string : data) {
			Matcher matcher = pattern.matcher(string);
			if (matcher.matches()) {
				list.add(string);
			}
		}
		result = new String[list.size()];
		for (int i=0;i<list.size();i++) {
			result[i]=list.get(i);
		}
		return result;
	}
}
