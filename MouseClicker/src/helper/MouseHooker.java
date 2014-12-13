package helper;

import java.util.Observable;

import javafx.application.Platform;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class MouseHooker extends Observable implements
		NativeMouseMotionListener {

	private int x;
	private int y;

	public MouseHooker() {
		this.setX(0);
		this.setY(0);
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		// Not needed for this task!
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				set(e.getX(), e.getY());
			}
		});
	}

	public void set(int x, int y) {
		this.setX(x);
		this.setY(y);
		setChanged();
		notifyObservers(this);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
