package ui.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class setWeather {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			setWeather window = new setWeather();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		Label label_weather = new Label(shell, SWT.NONE);
		label_weather.setText("天气");
		label_weather.setBounds(51, 42, 183, 17);
		labelShowWeather(label_weather);
	}

	public void labelShowWeather(Label label_weather) {
        Map<String, Object> map=getWeather("421002");
        StringBuffer sBuffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
        	Object val = entry.getValue();
        	sBuffer.append(val+" ");
			//System.out.print(val+" ");
		}
        label_weather.setText(sBuffer.toString());
        label_weather.pack();
	}

	public Map<String, Object> getWeather(String cityid){
		URL url=null;
		try {
			url = new URL("http://t.weather.sojson.com/api/weather/city/101250401");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("连接超时");
		}// 构建 API 请求 URL
		URLConnection conn=null;
		try {
			conn = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //设置在打开与此URLConnection引用的资源的通信链接时要使用的指定超时值（以毫秒为单位）。 
		conn.setConnectTimeout(1000);
		Map<String,Object> map = new HashMap<>();
		StringBuffer sBuffer=null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
			      conn.getInputStream(), "UTF-8"));){
			sBuffer = new StringBuffer();
			String line=null;
			while((line=br.readLine())!=null) {
				sBuffer.append(line);
			}
			//所有获取的数据
			String datas = sBuffer.toString();
			//System.out.println(datas);
			//处理后的数据
			map=get(datas, map);
	        //System.out.println(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return map;
	}
	
	private Map<String, Object> get(String datas,Map<String, Object> map) {
		int s=datas.indexOf("\"type\":");
		int e=datas.indexOf(",\"notice\"");
		String data=datas.substring(s+8, e-1);
		map.put("weather", data);
		//System.out.println(datas.substring(s+8, e-1));
		s=datas.indexOf("\"high\"");
		e=datas.indexOf(",\"low\"");
		data=datas.substring(s+11, e-1);
		//System.out.println(data);
		map.put("high", data+"~");
		s=datas.indexOf("\"low\"");
		e=datas.indexOf(",\"ymd\"");
		data=datas.substring(s+10, e-1);
		//System.out.println(data);
		map.put("low", data);
		return map;
	}

}

	
