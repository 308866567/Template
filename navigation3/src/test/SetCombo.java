package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ui.util.Build;
import ui.util.FuzzySearch;

public class SetCombo extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SetCombo(Shell parent, int style) {
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
		shell.setSize(819, 608);
		shell.setText(getText());

		Combo combo = new Combo(shell, SWT.DROP_DOWN);
        combo.setBounds(84, 42, 189, 28);
//        String[] items = {"北门", "北操场", "南操场", "雁鸣湖", "图书馆"};
//        combo.setItems(items);
        String[] items=build.selectName();//搜索项
        combo.setItems(items);
        combo.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
            	String key=combo.getText();
            	FuzzySearch search = new FuzzySearch(key);
            	search.search(items);
            	System.out.println(combo.getText());
            	combo.setListVisible(true);
            }
        });
	}
	
	Build build = new Build();
	/**
	 * 初始化搜索框
	 * i_landmark - n_id
	 */
	public void initCombo(Combo combo) {
		String[] items=build.selectName();//搜索项
		combo.setItems(items);
		combo.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
            	
            	
            	
            	
            }
        });
	}
}
