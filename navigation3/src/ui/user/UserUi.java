package ui.user;

import dao.*;
import ui.manager.ManageUi;
import ui.util.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class UserUi {

	protected Shell shell;
	Display display;
//-------------------------------全局变量---------------------------------
	// 用于记录用户当前选取的点
	Label start = null;
	Label end = null;
//	Combo label_start = null;
	private Combo combo_start;
//	Combo label_end = null;
	private Combo combo_end;
	boolean flag = true;
	int start_id;
	int end_id;
	int b_sid;
	int b_eid;
	int u_id = 3;// 默认

	// 标点的默认颜色
	Color pointColor = null;

	// 用于记录查询结果
	ArrayList<Integer> path = null;
	Map<String, ArrayList<Double>> line = null;

	// 数据库操作

	NodeInfoDAO infoDAO = new NodeInfoDAO();
	UserDao dao = new UserDao();

	Composite composite_left = null;
	// 右面板
	Composite composite_right = null;
	// 右面板布局
	StackLayout stackLayout_right = null;
	// 标点的大小
	int pointSize = 10;

// ----------------------------------------------------------------
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UserUi window = new UserUi();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
//		createContents();
		login();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	void inUser(Composite composite_login) {
		composite_login.dispose();

		createContents();
		shell.layout();
	}

	private void login() {
		shell = new Shell();
		shell.setSize(626, 477);
		shell.setText("登录");
		SwtUtils.centerWin(shell);

		Composite composite_login = new Composite(shell, SWT.NONE);
		composite_login.setBounds(0, 0, shell.getSize().x, shell.getSize().y);
		composite_login.setLayout(null);

		Label lblNewLabel = new Label(composite_login, SWT.NONE);
		lblNewLabel.setBounds(178, 138, 76, 20);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("账号:");

		Label lblNewLabel_1 = new Label(composite_login, SWT.NONE);
		lblNewLabel_1.setBounds(178, 197, 76, 20);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setText("密码:");

		Text text_pwd = new Text(composite_login, SWT.BORDER | SWT.PASSWORD);
		text_pwd.setBounds(260, 194, 136, 26);

		Text text_uname = new Text(composite_login, SWT.BORDER);
		text_uname.setBounds(260, 135, 136, 26);
		Button button_1 = new Button(composite_login, SWT.CHECK);
		button_1.setBounds(241, 243, 89, 20);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text_uname.getText();
				String pwdString = text_pwd.getText();
				if (name == "" || pwdString == "") {
					SwtUtils.pop_upFrame(shell, "消息提示", "账号或密码不能为空");
					return;
				}
				if (e.doit) {
					dao.updateRemember(1, name);
					return;
				}
				dao.updateRemember(0, name);
			}
		});
		button_1.setText("记住密码");

		text_uname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("lost");
				String name = text_uname.getText();
				if (name == null || name.length() == 0) {
					SwtUtils.pop_upFrame(shell, "提示", "输入用户名");
					return;
				}
				// 判断用户是否记住密码 记住则直接登录
				Map<String, Object> map = dao.selectUser(name);
				if (map == null || map.size() == 0) {
					SwtUtils.pop_upFrame(shell, "提示", "输入正确用户名");
					return;
				}
				u_id = bigToInt(map.get("u_id"));// 记录用户id
				int re = bigToInt(map.get("remember"));// 获取用户记录密码标志
				String pwd1 = String.valueOf(map.get("u_pwd"));// 获取用户密码
				if (re == 1) {// 记住密码 从数据库自动获取密码 自动登录进入主界面
					text_pwd.setText(pwd1);
					button_1.setSelection(true);
					// 自动进入主界面
					inUser(composite_login);
				}
			}
		});

		Button label_dlogin = new Button(composite_login, SWT.NONE);
		label_dlogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		label_dlogin.setBounds(200, 352, 76, 38);
		label_dlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				u_id = 3;// 游客登录默认id
				// 进入主界面
				inUser(composite_login);
			}
		});
		label_dlogin.setAlignment(SWT.CENTER);
		label_dlogin.setText("跳过登录");

		Button button_2 = new Button(composite_login, SWT.NONE);
		button_2.setBounds(307, 352, 80, 38);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// 进入管理员界面
				ManageUi t = new ManageUi();
				shell.setVisible(false);
				t.open();
			}
		});
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_2.setText("管理员面板");
		
		Button button_3 = new Button(composite_login, SWT.NONE);
		button_3.setBounds(307, 279, 89, 38);
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String name = text_uname.getText();
				String pwd = text_pwd.getText();
				dao.addUser(name, pwd);
				SwtUtils.pop_upFrame(shell, "提示", "注册完成");
			}
		});
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_3.setText("注册");
		//登录按钮
		Button button = new Button(composite_login, SWT.NONE);
		button.setBounds(200, 279, 76, 38);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text_uname.getText();
				String pwd = text_pwd.getText();
				Map<String, Object> map = dao.selectUser(name);
				int i = 0;
				if (map == null || map.size() == 0) {
					if (i <= 0) {
						SwtUtils.pop_upFrame(shell, "消息提示", "账号或密码错误");
						return;
					}
					map = dao.selectUser(name);
					u_id = bigToInt(map.get("u_id"));
					// 自动进入主界面
					inUser(composite_login);
				} else {// 已注册
					u_id = bigToInt(map.get("u_id"));
					String pwd1 = String.valueOf(map.get("u_pwd"));
					if (!pwd1.equals(pwd)) {
						SwtUtils.pop_upFrame(shell, "消息提示", "密码错误");
						return;
					}
					// 进入主界面
					inUser(composite_login);
				}

			}
		});
		button.setText("登录");

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell.setSize(800, 800);
		shell.setText("导航系统");
//		shell.setLocation(0, 0);
		SwtUtils.centerWin(shell);
//		shell.setMaximized(true);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		// 设置标点的默认颜色
		pointColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);

		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		sashForm.setTouchEnabled(true);
		sashForm.setToolTipText("左右滑动调整大小");
		sashForm.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		sashForm.setSashWidth(10);// 中间分隔栏的大小

		// 左面板-地图
		composite_left = new Composite(sashForm, SWT.NONE);
		// 右面板
		composite_right = new Composite(sashForm, SWT.NONE);
		// 设置左右面板分配比例
		sashForm.setWeights(new int[] { 3, 2 });
		// 初始化

////////////////////////////////////////////////////////////
		TTS voice = new TTS();
		voice.speak("欢迎");
		// 弹幕导致地图bug
		CLabel huan = new CLabel(composite_right, SWT.NONE);
		setBarrage.labelToBarrage(huan, "欢迎");

////////////////////////////////////////////////////////////

//		initMapPoint(composite_left);
		// 进行分配
		shell.layout();
		initMap(composite_left);
		initBuild(composite_left);
		composite_left.layout();
		// 窗口大小变化时,重新绘制路线
		composite_left.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (path != null) {
					Dijkstra d = new Dijkstra();
//					System.out.println("131绘制路线");
					d.draw(composite_left, line);
//					d.draw(composite_left, path);
				}
			}
		});

		stackLayout_right = new StackLayout();

		combo_start = new Combo(composite_right, SWT.NONE);
		combo_start.setBounds(26, 142, 90, 17);
//		combo_start.setText("起点:");

		combo_end = new Combo(composite_right, SWT.NONE);
		combo_end.setBounds(166, 142, 85, 17);
//		combo_end.setText("终点:");

		// 查询按钮
		Button button = new Button(composite_right, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button != 1)
					return;
				composite_left.redraw();// 清除之前的绘画轨迹
				Dijkstra d = new Dijkstra();
				path = d.dijk(composite_left, start_id, end_id);
				line = d.getLine(path);
				if (path == null)
					SwtUtils.pop_upFrame(shell, "提示", "路线不存在");
				else {
					History t = new History(b_sid, b_eid, u_id);
					t.add();
					table.removeAll();
					initTable(table);
					d.draw(composite_left, line);
				}
			}
		});
		button.setBounds(96, 197, 80, 27);
		button.setText("查询");

		setRight(composite_right);

		table = new Table(composite_right, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 249, 286, 223);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		// 添加列
		TableColumn col0 = new TableColumn(table, SWT.NONE);
		col0.setWidth(123);
		col0.setText("起点");

		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setWidth(171);
		col1.setText("终点");

		initTable(table);

		Label label = new Label(composite_right, SWT.NONE);
		label.setText("起点");
		label.setBounds(26, 127, 61, 17);

		Label label_1 = new Label(composite_right, SWT.NONE);
		label_1.setText("终点");
		label_1.setBounds(166, 127, 61, 17);
		initcombo(combo_start);
		initcombo(combo_end);
	}

	void initcombo(Combo combo) {
		List<Map<String, Object>> name = infoDAO.selectName();
		for (Map<String, Object> x : name) {
			combo.add(x.get("i_landmark").toString());
		}
	}

	// 历史记录的构建
	void initTable(Table table) {
		List<Map<String, Object>> list = infoDAO.selectSD(u_id);

		// 循环设置tableItem,从数据库获取值,设置输入文本框
		// 放置控件
		TableEditor editor = null;

		// 遍历边
		for (Map<String, Object> l : list) {
			// 创建编辑器
			editor = new TableEditor(table);
			editor.horizontalAlignment = SWT.CENTER;// 单元格居中对齐
			editor.grabHorizontal = true;// 自动填充
			// 创建item对象
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0, (String) l.get("s"));// 起点
			tableItem.setText(1, (String) l.get("e"));// 终点
			tableItem.setData("start", l.get("s_id"));
			tableItem.setData("end", l.get("e_id"));

			tableItem.setData("b_sid", l.get("b_sid"));
			tableItem.setData("b_eid", l.get("b_eid"));
		}
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = (TableItem) e.item;
//				System.out.println(item.getText(0) + "  " + item.getText(1));
				start_id = bigToInt(item.getData("start"));
				end_id = bigToInt(item.getData("end"));
				combo_start.setText("");
				combo_end.setText("");
				b_sid = bigToInt(item.getData("b_sid"));
				b_eid = bigToInt(item.getData("b_eid"));

				composite_left.redraw();// 清除之前的绘画轨迹
				Dijkstra d = new Dijkstra();
				path = d.dijk(composite_left, start_id, end_id);
				line = d.getLine(path);
				if (path == null)
					SwtUtils.pop_upFrame(shell, "提示", "路线不存在");
				else {
					History t = new History(b_sid, b_eid, u_id);
					t.add();
					d.draw(composite_left, line);
				}
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		shell.layout();
	}

	public int bigToInt(Object big) {
		BigDecimal decimalValue = (BigDecimal) big;
		return decimalValue.intValue();
	}

	void setRight(Composite composite_right) {
		// 设置系统时间和天气信息
		Composite composite = new Composite(composite_right, SWT.NONE);
		composite.setBounds(10, 10, 295, 111);
		setTime time = new setTime();
		Label label_time = new Label(composite, SWT.NONE);
		label_time.setBounds(10, 10, 263, 17);
		time.labelShowTime(label_time);

		setWeather weather = new setWeather();
		Label label_weather = new Label(composite, SWT.NONE);
		label_weather.setBounds(10, 50, 263, 17);
		label_weather.setText("天气");
		// TODO 天气
		weather.labelShowWeather(label_weather);
	}

	// 把一个容器设置为地图
	void initMap(Composite composite_left) {

		// 设置背景图片
		SwtUtils.autoImage2(composite_left, "/image/t.jpg");
		composite_left.setLayout(new MyLayout());
	}

	// 从数据库读取,创建建筑物容器
	void initBuild(Composite composite) {
		List<Map<String, Object>> list = infoDAO.selectAll();
		for (Map<String, Object> map : list) {
			double cx, cy;// 父容器大小
			cx = composite.getSize().x;
			cy = composite.getSize().y;
			Build build = new Build(map);
			Composite composite1 = new Composite(composite, SWT.NONE);
			setBuild(composite1, build, cx, cy);
			composite1.setData("build", build);
		}
	}

	Composite t = null;// 存储弹窗引用
	private Table table;
	// 每个建筑物的初始化

	void setBuild(Composite composite1, Build build, double cx, double cy) {

		// 颜色
		composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
//		composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		// 大小
		composite1.setBounds((int) (build.lx * cx), (int) (build.ly * cy), (int) (build.rx * cx),
				(int) (build.ry * cy));
		// 布局
		composite1.setLayoutData(new MyLayoutData(build.lx, build.ly, build.rx, build.ry));
		composite1.addMouseTrackListener(new MouseTrackAdapter() {
			// 事件
			// 鼠标进入 设置建筑物边框,并显示悬浮信息
			@Override
			public void mouseEnter(MouseEvent e) {
				// 建筑物轮廓显示
				SwtUtils.drawBorder(composite1, 3);
				t = out(composite1, build.image, build.landmark, build.detail);
			}

			// 鼠标离开建筑物
			@Override
			public void mouseExit(MouseEvent e) {
				composite1.redraw();
				t.dispose();
			}
		});
		// 左键点击添加
		composite1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Build build = (Build) composite1.getData("build");
				// 只有左键才选择点
				if (e.button != 1)
					return;
				// 左键选择
				// 起点和终点轮流设置
				if (flag) {
					flag = false;
					combo_start.setText(build.landmark);
					start_id = build.n_id;
					b_sid = build.id;
				} else {
					flag = true;
					combo_end.setText(build.landmark);
					end_id = build.n_id;
					b_eid = build.id;
				}
			}
		});
	}

	/**
	 * 悬浮信息框
	 * 
	 * @param composite_1  需要悬浮信息框的面板
	 * @param path         地标图片相对路径
	 * @param locationName 地标名
	 * @param detail       地标简介
	 * @return 返回Composite
	 */
	Shell out(Composite com, Image image, String locationName, String detail) {
		Shell box = new Shell(shell, SWT.ON_TOP);
		int w_box = 200;
		int h_box = 275;
		box.setSize(w_box, h_box);
		box.setLocation(1500, 0);
		Point p_build = com.getLocation();// 建筑物位置相对于shell的
		Point p_shell = shell.getLocation();// shell位置,相对于整个屏幕的
		Point size_build = com.getSize();// 建筑物的大小
		Point size_map = com.getParent().getSize();

		// 建筑物位置和shell大小进行比较判断象限
		// 第一象限
		if (p_build.x > size_map.x / 2 && p_build.y < size_map.y / 2) {
//			System.out.println("1");
			p_build.x -= size_build.x + w_box;
		}
		// 第二象限
		else if (p_build.x < size_map.x / 2 && p_build.y < size_map.y / 2) {
//			System.out.println("2");
			p_build.x += size_build.x;

		}
		// 第三象限
		else if (p_build.x < size_map.x / 2 && p_build.y > size_map.y / 2) {
//			System.out.println("3");
			p_build.x += size_build.x;
		}

		// 第四象限
		else if (p_build.x > size_map.x / 2 && p_build.y > size_map.y / 2) {
//			System.out.println("4");
			p_build.x -= size_build.x + w_box;
		}
		p_build.x += p_shell.x;
		p_build.y += p_shell.y;
		box.setLocation(p_build);

		// 图片
		Label label_image = new Label(box, SWT.NONE);
		label_image.setBounds(10, 10, 180, 113);
		label_image.setImage(image);
		SwtUtils.autoImage(label_image);
		// 地点名
		Label label_Name = new Label(box, SWT.NONE);
		label_Name.setBounds(10, 129, 180, 20);
		label_Name.setText(locationName);
		// 文字简介
		Label label_detail = new Label(box, SWT.NONE);
		label_detail.setBounds(10, 164, 180, 65);
		label_detail.setText(detail);
		// 了解详情按钮
//		Button btn_detail = new Button(composite, SWT.NONE);
//		btn_detail.setBounds(10, 235, 98, 30);
//		btn_detail.setText("了解详情");
		box.setVisible(true);
		// TODO 悬浮框不能使用按键,悬浮框到边界处无法完全显示
		return box;
	}
}
