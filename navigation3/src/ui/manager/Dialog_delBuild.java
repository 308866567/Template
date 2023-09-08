package ui.manager;

import dao.*;
import ui.util.*;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class Dialog_delBuild extends Dialog {

	protected Object result;
	protected Shell shell;

	Display display;
	// 标签
	Label label_start = null;
	Label label_end = null;
	Label lblid =null;

	// 用于记录用户选取的两个点
	Composite start = null;
	Composite end = null;
	boolean flag = true;

	Composite last = null, now = null;
	boolean flag2 = true;// 判断点

	// 用于记录导航结果

	NodeInfoDAO infoDAO = new NodeInfoDAO();

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public Dialog_delBuild(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1145, 820);
		shell.setText("用户界面");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);
		System.out.println(shell.getLocation());

		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		sashForm.setTouchEnabled(true);
		sashForm.setToolTipText("左右滑动调整大小");
		sashForm.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		sashForm.setSashWidth(10);// 中间分隔栏的大小

		// 左面板-地图
		Composite composite_left = new Composite(sashForm, SWT.NONE);
		// 右面板
		Composite composite_right = new Composite(sashForm, SWT.NONE);
		// 设置左右面板分配比例
		sashForm.setWeights(new int[] { 3, 2 });
		// 进行分配
		shell.layout();

		// 右面板设置
		Composite composite = new Composite(composite_right, SWT.NONE);
		composite.setBounds(53, 144, 350, 367);
		// 建筑物
		initBuildSet(composite);
		
		lblid = new Label(composite, SWT.NONE);
		lblid.setBounds(30, 340, 61, 17);
		lblid.setText("绑定的点id");

		// 左面板设置
		initMap(composite_left);
	}

	void initBuildSet(Composite composite) {

		text_lx = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_lx.setBounds(44, 10, 73, 26);

		text_ly = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_ly.setBounds(44, 57, 73, 26);

		text_rx = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_rx.setBounds(44, 106, 73, 26);

		text_ry = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_ry.setBounds(44, 156, 73, 26);

		Label lblLx = new Label(composite, SWT.NONE);
		lblLx.setBounds(143, 13, 34, 20);
		lblLx.setText("lx");

		Label lblLy = new Label(composite, SWT.NONE);
		lblLy.setBounds(143, 57, 34, 20);
		lblLy.setText("ly");

		Label lblRx = new Label(composite, SWT.NONE);
		lblRx.setBounds(143, 106, 34, 20);
		lblRx.setText("rx");

		Label lblRy = new Label(composite, SWT.NONE);
		lblRy.setBounds(143, 159, 34, 20);
		lblRy.setText("ry");

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(128, 208, 76, 20);
		lblNewLabel.setText("id");

		text_id = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_id.setBounds(44, 208, 73, 26);

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(10, 318, 303, 20);
		label.setText("将鼠标移动到建筑物轮廓上,自动获取位置信息");

		text_name = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text_name.setBounds(44, 240, 73, 26);

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setBounds(128, 243, 76, 20);
		label_1.setText("地标名");

		Button button = new Button(composite, SWT.NONE);
		// 删除建筑
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 由建筑物的id删除
				String id = text_id.getText();
				int i = infoDAO.delete(id);
				if (i <= 0) {
					SwtUtils.pop_upFrame(shell, "消息提示", "删除失败");
					return;
				}
				SwtUtils.pop_upFrame(shell, "消息提示", "删除成功");
				SwtUtils.clearText(text_id, text_lx, text_ly, text_name, text_rx, text_ry);
			}
		});
		button.setBounds(79, 272, 98, 30);
		button.setText("删除");
	}

	// 把一个容器设置为地图
	void initMap(Composite composite_left) {
		// 设置布局
		composite_left.setLayout(new MyLayout());
		// 设置动态适应的背景图片
		SwtUtils.autoImage2(composite_left, "/image/t.jpg");
		// 从数据库创建建筑物
		initBuild(composite_left);
	}

	// 获取选择的建筑物信息设置到全局变量上
	void ShowId(Build build) {
		text_lx.setText("" + build.lx);
		text_ly.setText("" + build.ly);
		text_rx.setText("" + build.rx);
		text_ry.setText("" + build.ry);
		text_id.setText("" + build.id);
		text_name.setText("" + build.landmark);
		lblid.setText(""+build.n_id);
	}

	Composite t = null;// 存储弹窗引用
	private Text text_lx;
	private Text text_ly;
	private Text text_rx;
	private Text text_ry;
	private Text text_id;
	private Text text_name;

	// 从数据库读取,创建建筑物容器
	void initBuild(Composite composite) {
		List<Map<String, Object>> list = infoDAO.selectAll();
		for (Map<String, Object> map : list) {
			double cx, cy;//父容器大小
			cx = composite.getSize().x;
			cy = composite.getSize().y;
			Build build = new Build(map);
			Composite composite1 = new Composite(composite, SWT.NONE);
			// composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			composite1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			composite1.setBounds((int) (build.lx * cx), (int) (build.ly * cy), (int) (build.rx * cx),
					(int) (build.ry * cy));
			composite1.setLayoutData(new MyLayoutData(build.lx, build.ly, build.rx, build.ry));

			composite1.addMouseTrackListener(new MouseTrackAdapter() {

				// 鼠标进入 设置建筑物边框,并显示悬浮信息
				@Override
				public void mouseEnter(MouseEvent e) {
					//设置选中的建筑物
					ShowId(build);
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
		}
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
	Composite out(Composite com, Image image, String locationName, String detail) {
		Shell composite = new Shell(shell, SWT.ON_TOP);
		composite.setBounds(54, 97, 200, 275);
		Point point = com.getLocation();// 建筑物位置
		Point point2 = com.getSize();
		Point point3 = shell.getLocation();
		composite.setBounds(0, 0, 200, 275);// 悬浮框位置
		composite.setBounds(point3.x + point.x + point2.x, point3.y + point.y, 200, 275);// 悬浮框位置
		// composite位置判断
		int x = com.getParent().getSize().x / 2;// 建筑所在的父面板
		int y = com.getParent().getSize().y / 2;
		if (point.x > x && point.y > y) {
			composite.setLocation(point3.x + point.x - 200, point3.x + point.y + point2.y - 275);
		} else if (point.x > x && point.y < y) {
			composite.setLocation(point3.x + point.x - 200, point3.x + point.y);
		} else if (point.x < x && point.y > y) {
			composite.setLocation(point3.x + point.x + point2.x, point3.x + point.y + point2.y - 275);
		}

		// 图片
		Label label_image = new Label(composite, SWT.NONE);
		label_image.setImage(image);
		label_image.setBounds(10, 10, 180, 113);
		SwtUtils.autoImage(label_image);
		// 地点名
		Label label_Name = new Label(composite, SWT.NONE);
		label_Name.setBounds(10, 129, 180, 20);
		label_Name.setText(locationName);
		// 文字简介
		Label label_detail = new Label(composite, SWT.NONE);
		label_detail.setBounds(10, 164, 180, 65);
		label_detail.setText(detail);
		// 了解详情按钮
//		Button btn_detail = new Button(composite, SWT.NONE);
//		btn_detail.setBounds(10, 235, 98, 30);
//		btn_detail.setText("了解详情");
		composite.setVisible(true);
		// TODO 悬浮框不能使用按键,悬浮框到边界处无法完全显示
		return composite;
	}

	
}
