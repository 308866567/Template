package ui.user;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import dao.UserDao;
import ui.util.SwtUtils;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Login_user extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_uname;
	private Text text_pwd;
	UserDao dao = new UserDao();

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Login_user(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
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
		shell.setSize(626, 477);
		shell.setText(getText());
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(178, 138, 76, 20);
		lblNewLabel.setText("账号:");
		
		text_uname = new Text(shell, SWT.BORDER);
		text_uname.setBounds(260, 135, 136, 26);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(178, 197, 76, 20);
		lblNewLabel_1.setText("密码:");
		
		text_pwd = new Text(shell, SWT.BORDER);
		text_pwd.setBounds(260, 194, 136, 26);
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name=text_uname.getText();
				String pwd=text_pwd.getText();
				
				int i= dao.addUser(name, pwd);
				if (i<=0) {
					SwtUtils.pop_upFrame(getParent(), "消息提示", "账号或密码错误");
				}
			}
		});
		button.setBounds(232, 269, 98, 30);
		button.setText("登录");
		
		Label label_dlogin = new Label(shell, SWT.NONE);
		label_dlogin.setAlignment(SWT.CENTER);
		label_dlogin.setBounds(246, 316, 76, 20);
		label_dlogin.setText("跳过登录");
		
		Button button_1 = new Button(shell, SWT.CHECK);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		button_1.setBounds(241, 243, 89, 20);
		button_1.setText("记住密码");

	}
}
