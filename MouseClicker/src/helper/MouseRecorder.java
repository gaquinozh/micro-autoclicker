package helper;

import java.util.Observable;

public class MouseRecorder extends Observable {

	private double x;
	private double y;

	public MouseRecorder() {
		this.setX(0.0);
		this.setY(0.0);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void set(double x, double y) {
		setX(x);
		setY(y);
		setChanged();
		notifyObservers();
	}

}
