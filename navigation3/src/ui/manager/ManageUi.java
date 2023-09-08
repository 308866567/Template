package ui.manager;

import ui.util.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ManageUi {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ManageUi window = new ManageUi();
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
		shell.setText("管理面板");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);
		Composite composite_left = new Composite(shell, SWT.NONE);
		initMap(composite_left);
		Composite composite_right = new Composite(shell, SWT.NONE);
				composite_right.setLayout(new FillLayout(SWT.VERTICAL));
		
				Button button = new Button(composite_right, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
					}
				});
				button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						Dialog_SetPoint t = new Dialog_SetPoint(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
						t.open();
					}
				});
				button.setText("修改标点");
				//
						Button button_1 = new Button(composite_right, SWT.NONE);
						button_1.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								Dialog_addPointInfo t = new Dialog_addPointInfo(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
								t.open();
							}
						});
						button_1.setText("添加建筑物");
						
						Button button_2 = new Button(composite_right, SWT.NONE);
						button_2.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								Dialog_delBuild t = new Dialog_delBuild(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
								t.open();
							}
						});
						button_2.setText("删除建筑物");

	}
	
	
	// 把一个容器设置为地图
	void initMap(Composite composite_left) {
		// 设置背景图片
		SwtUtils.autoImage2(composite_left, "/image/t.jpg");
		composite_left.setLayout(new MyLayout());
	}
}
