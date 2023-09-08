package ui.util;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.widgets.Control;

public class MyLayoutData extends LayoutData {
	public double x;//在横坐标上的比例,设w为1
	public double y;
	public double w;//宽
	public double h;//高
	public int id=-1;//标点编号

	public MyLayoutData(double a, double b) {
		x = a;
		y = b;
	}

	public MyLayoutData() {
		x = 0.5;
		y = 0.5;
	}
	public MyLayoutData(Control parent,Control now) {
		x=(1.0*now.getBounds().x)/parent.getSize().x;
		y=(1.0*now.getBounds().y)/parent.getSize().y;
//		System.out.println(x+"--"+y);
	}
	
	/**
	 * 
	 * @param a x
	 * @param b y
	 * @param d w
	 * @param c h
	 */
	public MyLayoutData(double a, double b,double c,double d) {
		x = a;
		y = b;
		w = c;
		h = d;
	}

}