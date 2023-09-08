package ui.manager;

import dao.*;
import ui.util.*;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;

public class Composite_SetPoint extends Composite {
	private Table table_edge;
	private Text text_x;
	private Text text_y;
	private Text text_name;
	Label lblid;
	N_mapDAO mapDAO = new N_mapDAO();// 数据库操作
	int pointId;
	Node point = null;

	public void setNode(double x, double y) {
		point.n_x = x;
		point.n_y = y;
		text_x.setText(String.valueOf(point.n_x));
		text_y.setText(String.valueOf(point.n_y));

	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Composite_SetPoint(Control temp, Composite parent, int style, int id) {
		super(parent, style);
		setLayout(null);

		this.pointId = id;
		point = new Node(id);

		// 返回按钮
		Button Button_back = new Button(this, SWT.NONE);
		Button_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				StackLayout stackLayout = (StackLayout) parent.getLayout();
				stackLayout.topControl = null;
				parent.layout();
				if (pointId == 0) {
					// 如果是新增加的点，按钮返回就隐藏起来
					temp.setVisible(false);
					// System.out.println("释放");
				}
			}
		});
		Button_back.setBounds(10, 0, 50, 31);
		Button_back.setText("返回");
		// 地点表格
		table_edge = new Table(this, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table_edge.setHeaderVisible(true);
		table_edge.setLinesVisible(true);
		table_edge.setBounds(10, 166, 198, 278);
		initTable(table_edge);// 表格初始化

		Composite composite_PointInfo = new Composite(this, SWT.NONE);
		composite_PointInfo.setBounds(110, 0, 98, 155);
		composite_PointInfo.setLayout(null);
		initPoint(composite_PointInfo);

		// 完成修改
		Button button_finish = new Button(this, SWT.NONE);
		button_finish.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				getPoint();
				if (id == 0) {
					MyLayoutData t = (MyLayoutData) temp.getLayoutData();
					t.id = pointId;
					lblid.setText("当前点id:"+pointId);
				}
				getEdges(table_edge, point.n_id);// 边修改
				SwtUtils.pop_upFrame(parent.getShell(), "提示", "修改完成");
			}
		});
		button_finish.setBounds(10, 67, 72, 27);
		button_finish.setText("完成修改");

		// 删除操作
		Button button = new Button(this, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				temp.setVisible(false);
				mapDAO.delPoint(pointId);
				StackLayout stackLayout = (StackLayout) parent.getLayout();
				stackLayout.topControl = null;
				parent.layout();
			}
		});
		button.setBounds(10, 113, 72, 27);
		button.setText("删除该点");
		
	}

	// 处理失败情况 TODO
	void getPoint() {
		try {
			double x = Double.valueOf(text_x.getText());
			double y = Double.valueOf(text_y.getText());
			String name = text_name.getText();
			point.n_x = x;
			point.n_y = y;
			point.n_name = name;
			point.update();// 点修改
			this.pointId = point.n_id;
		} catch (Exception e) {
			text_x.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
			text_y.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
			System.out.println("设置点的信息有误");
		}
	}

	// 遍历表格,修改边的信息
	void getEdges(Table table, int pointId) {
		TableItem[] cols = table.getItems();
		for (TableItem col : cols) {
			try {
				// 勾选
				if (col.getChecked()) {
					Edge e = new Edge(col, pointId);
					e.update();
				} else if (col.getData("e_id") != null) {
					int e_id = (int) col.getData("e_id");
					Edge e = new Edge(mapDAO.findEdge(e_id), 0);
					e.del();
				}
			} catch (Exception e) {
				col.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
				SwtUtils.pop_upFrame(getShell(), "提示", col.getText()+"边的信息有误");
			}
		}
	}

	// 初始化地点信息部分
	void initPoint(Composite composite) {

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(5, 5, 18, 17);
		lblNewLabel.setText("x");

		text_x = new Text(composite, SWT.BORDER);
		text_x.addVerifyListener(new VerifyListener() {
			// 监测一个字符
			public void verifyText(VerifyEvent e) {
				if (e.text.length() == 1)
					e.doit = ".0123456789".indexOf(e.text) > 0;
			}
		});
		text_x.setBounds(5, 27, 73, 23);

		Label lblNewLabel_y = new Label(composite, SWT.NONE);
		lblNewLabel_y.setBounds(5, 55, 18, 17);
		lblNewLabel_y.setText("y");

		text_y = new Text(composite, SWT.BORDER);

		text_y.setBounds(5, 77, 73, 23);
		// 只能输入数字
		text_y.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if (e.text.length() == 1)
					e.doit = ".0123456789".indexOf(e.text) >= 0;
			}
		});

		Label lblNewLabel_name = new Label(composite, SWT.NONE);
		lblNewLabel_name.setBounds(5, 105, 73, 17);
		lblNewLabel_name.setText("地点名称");
		

		lblid = new Label(this, SWT.NONE);
		lblid.setBounds(10, 41, 94, 20);

		text_name = new Text(composite, SWT.BORDER);
		text_name.setBounds(5, 127, 73, 23);
		text_x.setToolTipText("输入1到0的小数");
		text_y.setToolTipText("输入1到0的小数");

		// 设置地点信息

		text_x.setText(String.valueOf(point.n_x));
		text_y.setText(String.valueOf(point.n_y));
		text_name.setText(point.n_name);
		lblid.setText("当前点id:"+pointId);
//		lblid.pack();
	}

	// 地点表的构建
	void initTable(Table table) {

		// 添加列
		TableColumn col0 = new TableColumn(table, SWT.NONE);
		col0.setWidth(83);
		col0.setText("终点");

		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setWidth(100);
		col1.setText("距离");

		// 循环设置tableItem,从数据库获取值,设置输入文本框
		// 放置控件
		TableEditor editor = null;

		List<Map<String, Object>> edges = mapDAO.allEdge(pointId);// 数据库获取边
		// 遍历边
		for (Map<String, Object> edge : edges) {
			// 创建编辑器
			editor = new TableEditor(table);
			editor.horizontalAlignment = SWT.CENTER;// 单元格居中对齐
			editor.grabHorizontal = true;// 自动填充
			Edge t = new Edge(edge);
			// 创建item对象
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(0, String.valueOf(t.end));// 终点编号
			// 创建输入框
			Text dist = new Text(table, SWT.BORDER);// 距离输入框
//			dist.setBounds(tableItem.getBounds(0));//设置大小/
			dist.addVerifyListener(new VerifyListener() {
				public void verifyText(VerifyEvent e) {
					if (e.text.length() == 1)
						e.doit = "0123456789.".indexOf(e.text) >= 0;
				}
			});
			if (t.len == -1) {
				dist.setText("");
			} else {
				tableItem.setChecked(true);
				dist.setText(String.valueOf(t.len));
			}
			// 放置输入框
//			editor.minimumWidth = dist.getSize().x+2;// 设置输入框大小
			editor.setEditor(dist, tableItem, 1);
			//
			tableItem.setData("距离", dist);// 行存储文本框的引用
			if (t.e_id != -1)
				tableItem.setData("e_id", t.e_id);
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
