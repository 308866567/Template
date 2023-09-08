package ui.util;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class MyLayout extends Layout {

	@Override
	// 计算布局最佳位置
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		return new Point(wHint, hHint);
	}

	@Override
	// 设置容器内组件的位置
	protected void layout(Composite composite, boolean flushCache) {
		double w = composite.getSize().x;
		double h = composite.getSize().y;
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			Control control = children[i];// 当前组件
			Object layoutData = control.getLayoutData();
			if (layoutData instanceof MyLayoutData) {
				MyLayoutData myLayoutData = (MyLayoutData) layoutData;
				// 更新组件位置
				// 更新组件大小
				if (myLayoutData.w != 0 && myLayoutData.h != 0) {
					control.setBounds((int) (w * myLayoutData.x), (int) (h * myLayoutData.y),
							(int) (w * myLayoutData.w), (int) (h * myLayoutData.h));
				} else {
					control.setBounds((int) (w * myLayoutData.x), (int) (h * myLayoutData.y), control.getSize().x,
							control.getSize().y);
				}
//				System.out.println("=========");
//				System.out.println(w+"-"+myLayoutData.x+"--"+myLayoutData.y);
//				System.out.println("=========");
			}

		}
	}

}
