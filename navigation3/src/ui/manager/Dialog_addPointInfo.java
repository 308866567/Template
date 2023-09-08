package ui.manager;

import dao.*;
import test.SetPoint2;
import ui.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * 添加地点信息 最终版
 * 
 * @author huan
 *
 */
public class Dialog_addPointInfo extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_name;
	private Text text_detail;
	private String filePath = "";// 选择路径
	// 数据库操作
	N_mapDAO mapDAO = new N_mapDAO();
	NodeInfoDAO infoDAO = new NodeInfoDAO();
	// 标点的大小
	int pointSize = 15;

	Composite last = null, now = null;
	boolean flag = true;// 判断点

	private Text text_id;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public Dialog_addPointInfo(Shell parent, int style) {
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
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(841, 680);
		shell.setText(getText());

		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
//		SwtUtils.centerWin(shell);
		shell.setMaximized(true);

		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		sashForm.setTouchEnabled(true);
		sashForm.setToolTipText("左右滑动调整大小");
		sashForm.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		sashForm.setSashWidth(10);// 中间分隔栏的大小

		Composite composite_left = new Composite(sashForm, SWT.NONE);

		Composite composite_right = new Composite(sashForm, SWT.NONE);

		sashForm.setWeights(new int[] { 3, 2 });// 设置分配比例
		shell.layout();

		// 设置左面板
		initMap(composite_left);

		Label label = new Label(composite_right, SWT.NONE);
		label.setBounds(10, 58, 76, 20);
		label.setText("地点名称:");

		text_name = new Text(composite_right, SWT.BORDER);
		text_name.setBounds(10, 94, 289, 26);

		Label label_1 = new Label(composite_right, SWT.NONE);
		label_1.setBounds(10, 126, 76, 20);
		label_1.setText("地点简介:");

		text_detail = new Text(composite_right, SWT.BORDER);
		text_detail.setBounds(10, 152, 289, 135);

		Label label_2 = new Label(composite_right, SWT.NONE);
		label_2.setBounds(10, 293, 76, 20);
		label_2.setText("实景图:");

		Label label_image = new Label(composite_right, SWT.NONE);
		// 鼠标双击选择图片
		label_image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// 文件对话框
				FileDialog fd = new FileDialog(shell, SWT.NONE);
				// 设置文件类型
				fd.setFilterExtensions(new String[] { "*.jpg", "*.png", ".gif", "*.*" });
				filePath = fd.open();
				if (StringUtils.isNull(filePath)) {
					MessageDialog.openWarning(shell, "消息提示", "未选中图片");
					return;
				}
				// 根据路径创建文件对象
				SwtUtils.autoImage2(label_image, filePath);
			}
		});
		label_image.setBounds(10, 319, 289, 177);
		SwtUtils.autoImage2(label_image, "/image/zanwu.png");

		Button btnNewButton = new Button(composite_right, SWT.NONE);
		// 添加按钮事件
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNull(filePath)) {
					MessageDialog.openWarning(shell, "消息提示", "未选中图片");
					return;
				}
				// 获取所有的数据
				String name = text_name.getText();
				String detail = text_detail.getText();

				if (StringUtils.isNull(name, detail)) {
					MessageDialog.openWarning(shell, "消息提示", "信息不能为空");
					return;
				}
				// 将图片数据存储到字节数组中,传递数组过去
				File file = new File(filePath);
				byte[] bt = null;
				try (FileInputStream in = new FileInputStream(file);) {
					if (StringUtils.isNull(filePath) || "".equals(filePath)) {// 判断文件路径存在
						MessageDialog.openWarning(shell, "消息提示", "未选中图片");
						return;
					}
					bt = new byte[(int) file.length()];
					in.read(bt);

				} catch (IOException e1) {
					e1.printStackTrace();
				}

				double lx = 0, ly = 0, rx = 0, ry = 0;
				if (last == null || now == null) {
					SwtUtils.pop_upFrame(shell, "提示", "未选择坐标");
					return;
				}
				double px = composite_left.getSize().x, py = composite_left.getSize().y, c1 = last.getLocation().x,
						c2 = now.getLocation().x;
//				System.out.println("================");
//				System.out.println(px + " " + py);
				lx = (c1 > c2) ? c2 / px : c1 / px;
				rx = (c1 > c2) ? (c1 - c2 + last.getSize().x) / px : (c2 - c1 + now.getSize().x) / px;

				c1 = last.getLocation().y;
				c2 = now.getLocation().y;
				ly = (c1 > c2) ? c2 / py : c1 / py;
				ry = (c1 > c2) ? (c1 - c2 + last.getSize().y) / py : (c2 - c1 + now.getSize().y) / py;
//				System.out.println(lx + " " + ly + " " + rx + " " + ry);
				int id = Integer.valueOf(text_id.getText());
				NodeInfoDAO infoDAO = new NodeInfoDAO();
				// 调用dao方法存储地点信息
				int r = infoDAO.setCompositeSize(bt, name, detail, lx, ly, rx, ry, id);

				if (r <= 0) {
					SwtUtils.pop_upFrame(getParent(), "消息提示", "添加失败");
					return;
				}
				SwtUtils.pop_upFrame(getParent(), "消息提示", "添加成功");
				SwtUtils.clearText(text_detail, text_name);
//				System.out.println("===============================");
				SwtUtils.autoImage2(label_image, "/image/zanwu.png");
				now.dispose();
				last.dispose();
				return;
			}
		});
		btnNewButton.setBounds(118, 541, 98, 30);
		btnNewButton.setText("添加地点");

		Label label_4 = new Label(composite_right, SWT.NONE);
		label_4.setBounds(79, 501, 140, 20);
		label_4.setText("双击图片选择实景图");

		Label lblcomposite = new Label(composite_right, SWT.NONE);
		lblcomposite.setBounds(10, 580, 303, 20);
		lblcomposite.setText("在左侧选择两个点作为composite的范围");

		Label lblid = new Label(composite_right, SWT.NONE);
		lblid.setBounds(10, 10, 61, 17);
		lblid.setText("对应的点ID");

		text_id = new Text(composite_right, SWT.BORDER);
		text_id.setEnabled(false);
		text_id.setBounds(13, 33, 230, 23);
	}

	void initMap(Composite composite_left) {
		initMapPoint(composite_left);
		// 左键点击轮流选取2个点
		composite_left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Composite t = new Composite(composite_left, SWT.NONE);
				t.setBounds(e.x, e.y, 10, 10);
				t.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				if (flag) {
					if (last != null)
						last.dispose();
					last = t;
					flag = false;
				} else {
					if (now != null)
						now.dispose();
					now = t;
					flag = true;
				}
			}
		});

		// 设置背景图片
		SwtUtils.autoImage2(composite_left, "/image/t.jpg");
		composite_left.setLayout(new MyLayout());

	}

	// 在一个面板上绘制数据库中已经有的点
	void initMapPoint(Composite composite) {
		// 数据库获取所有点
		List<Map<String, Object>> points = mapDAO.allPoint();
		double weight = composite.getSize().x;
		double height = composite.getSize().y;
		for (Map<String, Object> point : points) {
			// 获取绝对坐标
			BigDecimal X = (BigDecimal) point.get("n_x");
			BigDecimal Y = (BigDecimal) point.get("n_y");
			int x = (int) (weight * X.doubleValue());
			int y = (int) (height * Y.doubleValue());
			// 新建标点
			Label l = new Label(composite, SWT.NONE);
			// 标点初始化
			initPoint(l, x, y, X.doubleValue(), Y.doubleValue(), ((BigDecimal) point.get("n_id")).intValue());
		}
		return;
	}

	Label lastLabel = null;

	// 对添加的标记点按钮进行初始化
	void initPoint(Label control, int x, int y, double X, double Y, int id) {
		// 标点大小,位置设置
		control.setBounds(x, y, pointSize, pointSize);
		// 布局数据设置,相对位置,地点编号
		control.setLayoutData(new MyLayoutData(X, Y));
		// 设置标点编号
		MyLayoutData t = (MyLayoutData) control.getLayoutData();
		t.id = id;

		// 颜色
//		control.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		// TODO
		control.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		control.setImage(SWTResourceManager.getImage(control.getShell().getClass(), "/image/dibiao2.png"));
		SwtUtils.autoImage(control);
		control.setToolTipText("左键设置此地点");
		// 添加左键设置点事件
		control.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// 只有左键才选择点
				if (e.button != 1)
					return;
				if (lastLabel != null) {
					lastLabel.setImage(SWTResourceManager.getImage(control.getShell().getClass(), "/image/dibiao2.png"));
					SwtUtils.autoImage(lastLabel);
				}
				text_id.setText("" + t.id);
				control.setImage(SWTResourceManager.getImage(control.getShell().getClass(), "/image/dibiao.png"));
				SwtUtils.autoImage(control);
				lastLabel = control;
			}
		});
	}

}
