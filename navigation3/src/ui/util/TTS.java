package ui.util;

import java.util.ArrayList;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 文字转语音
 * @author huan
 *
 */
public class TTS {

	static ActiveXComponent component;
	static Dispatch dispatch;
	public TTS() {
		component = new ActiveXComponent("Sapi.SpVoice");
		dispatch = component.getObject();//获取默认语音
		component.setProperty("Volume", new Variant(100));
		component.setProperty("Rate", new Variant(-1));
	}
	/**
	 * 播放指定文本的语音
	 * @param text
	 */
	static public void speak(String text) {
        Dispatch.call(dispatch , "Speak", new Variant(text)); // 播放指定文本的语音
    }
	
	public void speakPath(ArrayList<Integer> path) {
		//点id
	}
}
