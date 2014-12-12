package model;

import javafx.beans.property.SimpleIntegerProperty;

public class Position {

	private final SimpleIntegerProperty x;
	private final SimpleIntegerProperty y;
	
	public Position(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}

	public Integer getX() {
		return x.get();
	}

	public Integer getY() {
		return y.get();
	}
	
}
