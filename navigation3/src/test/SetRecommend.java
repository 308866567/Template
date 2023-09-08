package test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import dao.NodeInfoDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SetRecommend {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SetRecommend window = new SetRecommend();
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

	// 用于记录查询结果
	ArrayList<Integer> path = null;
	NodeInfoDAO infoDAO = new NodeInfoDAO();

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(806, 621);
		shell.setText("SWT Application");

//		List<Map<String, Object>> list = infoDAO.selectSD();
		Table table_1 = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(105, 95, 257, 200);
//		initTable(table_1, list);

	}

	// 地点表的构建
	void initTable(Table table, List<Map<String, Object>> list) {
		
		// 添加列
		TableColumn col0 = new TableColumn(table, SWT.NONE);
		col0.setWidth(123);
		col0.setText("起点");

		TableColumn col1 = new TableColumn(table, SWT.NONE);
		col1.setWidth(171);
		col1.setText("终点");

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
		}
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 TableItem item = (TableItem) e.item;
				 System.out.println(item.getText(0)+"  "+item.getText(1));
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	public int bigToInt(Object big) {
		BigDecimal decimalValue = (BigDecimal) big;
		return decimalValue.intValue();
	}
}
