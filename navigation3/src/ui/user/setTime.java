package ui.user;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class setTime {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			setTime window = new setTime();
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

		Label label_time = new Label(shell, SWT.NONE);
		label_time.setBounds(51, 42, 11, 17);
		labelShowTime(label_time);
	}
	/**
	 * 把一个标签设置为动态显示时间
	 * @param label
	 */
	public void labelShowTime(Label label) {
		Thread thread = new Thread(() -> {
			while (!label.isDisposed()) {
				//使用 Display.getDefault().asyncExec() 
				//方法来确保在主线程中更新标签，以避免多线程访问的问题。
				Display.getDefault().asyncExec(() -> {
					if (!label.isDisposed()) {
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//System.out.println(format.format(d));
						label.setText("当前系统时间: " + format.format(d));
						label.pack();//更新控件大小
					}
				});

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
	}
}