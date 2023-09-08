package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ui.util.SwtUtils;

public class setBarrage {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			setBarrage window = new setBarrage();
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
		CLabel cLabel = new CLabel(shell,SWT.NONE); 
		cLabel.setLocation(0,100);
		labelToBarrage(cLabel, "弹幕111");

	}
	public static void labelToBarrage(CLabel label,String s) {
		label.setData("speed",3);//设置速度
		label.setText(s);
		label.pack();
		//TODO 设个列表使控件高度尽量不重合
		//int y=label.getLocation().y;//高度不变
		//int x=label.getBounds().x;
		Thread thread = new Thread(() -> {
			while (!label.isDisposed()) {
				//使用 Display.getDefault().asyncExec() 
				//方法来确保在主线程中更新标签，以避免多线程访问的问题。
				Display.getDefault().asyncExec(() -> {
					if (!label.isDisposed()) {
						int speed=(int) label.getData("speed");
						int x=label.getBounds().x+speed;
						if(x+label.getSize().x>label.getParent().getSize().x)
							x=0;
						label.setLocation(x, label.getLocation().y);
					}
				});

				try {
					Thread.sleep(50);//一秒20帧
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();

		
	}
}
