package test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import dao.N_mapDAO;
import ui.manager.Composite_SetPoint;
import ui.util.MyLayout;
import ui.util.MyLayoutData;
import ui.util.SwtUtils;

public class Dialog_SetPoint1 extends Dialog {

	protected Object result;
	protected Shell shell;

	// 用于记录用户当前选取的点
	Composite start = null;

	// 数据库操作
	N_mapDAO mapDAO = new N_mapDAO();

	// 右面板
	Composite composite_right = null;
	// 右面板布局
	StackLayout stackLayout_right = null;
	// 标点的大小
	int pointSize = 10;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public Dialog_SetPoint1(Shell parent, int style) {
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
		shell.setSize(537, 500);
		shell.setText("地点设置面板");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		sashForm.setTouchEnabled(true);
		sashForm.setToolTipText("左右滑动调整大小");
		sashForm.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		sashForm.setSashWidth(10);// 中间分隔栏的大小
		// 左面板-地图
		// 鼠标右键添加点
		Composite composite_left = new Composite(sashForm, SWT.NONE);
		composite_left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button != 3) {
					return;
				}
				Composite t = new Composite(composite_left, SWT.NONE);
				Double X = (double) (1.0 * e.x / composite_left.getSize().x);
				Double Y = (double) (1.0 * e.y / composite_left.getSize().y);
				// 新标点传入0
				initPoint(t, e.x, e.y, X, Y, 0);// 初始化标记点

				// 跳转到标记点的设置界面
				// t.getListeners(0)
				if (start != null)
					start.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				start = t;
				start.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
				// 左键进入该点的设置面板
				// 传入选中点的id,id=0时数据库只进行添加操作
				stackLayout_right.topControl = new Composite_SetPoint(t, composite_right, SWT.NONE, 0);
				Composite_SetPoint tt = (Composite_SetPoint) stackLayout_right.topControl;
				tt.setNode(X, Y);// 新增点需要重新设置坐标
				composite_right.layout();// 堆栈布局的面板刷新

			}
		});
		initMap(composite_left);// 初始化

		// 右面板
		composite_right = new Composite(sashForm, SWT.NONE);

		// 设置左右面板分配比例
		sashForm.setWeights(new int[] { 3, 2 });
		// 进行分配
		shell.layout();

		stackLayout_right = new StackLayout();
		composite_right.setLayout(stackLayout_right);
	}

	// 把一个容器设置为地图
	void initMap(Composite composite_left) {
		initMapPoint(composite_left);
		// 设置背景图片
		composite_left.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				SwtUtils.autoImage(composite_left, "src/image/t.jpg");
			}
		});
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
			Composite l = new Composite(composite, SWT.NONE);
			// 标点初始化
			initPoint(l, x, y, X.doubleValue(), Y.doubleValue(), ((BigDecimal) point.get("n_id")).intValue());
		}
		return;
	}

	// 对添加的标记点按钮进行初始化
	void initPoint(Control control, int x, int y, double X, double Y, int id) {
		// 颜色
		control.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		// 标点大小,位置设置
		control.setBounds(x, y, pointSize, pointSize);
		// 布局数据设置,相对位置,地点编号
		control.setLayoutData(new MyLayoutData(X, Y));
		// 设置标点编号
		MyLayoutData t = (MyLayoutData) control.getLayoutData();
		t.id = id;
		control.setToolTipText("左键设置此地点");
		// 添加左键设置点事件
		control.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// 只有左键才选择点
				if (e.button != 1)
					return;
				// 选中的点会变色
				if (start != null)
					start.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				start = (Composite) control;
				start.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
				// 左键进入该点的设置面板
				// 传入选中点的id,id=0时数据库只进行添加操作
				stackLayout_right.topControl = new Composite_SetPoint(control, composite_right, SWT.NONE, t.id);
				if (id == 0) {
					Composite_SetPoint t = (Composite_SetPoint) stackLayout_right.topControl;
					t.setNode(X, Y);// 新增点需要重新设置坐标
				}
				composite_right.layout();// 堆栈布局的面板刷新

			}
		});
	}

}
