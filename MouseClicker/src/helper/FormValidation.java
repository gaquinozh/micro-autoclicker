package helper;

import javafx.scene.control.TextField;
import javafx.stage.Screen;

public class FormValidation {

	public static boolean validateStart(TextField clickCount, TextField delay) {
		boolean clickCountCriteria = clickCount.getText().matches("\\d+")
				&& Integer.parseInt(clickCount.getText()) >= 1
				&& Integer.parseInt(clickCount.getText()) <= 100;
		boolean delayCreteria = delay.getText().matches("\\d+")
				&& Integer.parseInt(delay.getText()) >= 15;
		return clickCountCriteria && delayCreteria;
	}

	public static boolean validateAddPosFields(TextField x, TextField y) {
		return validateSinglePosField("x", x) && validateSinglePosField("y", y);
	}

	private static boolean validateSinglePosField(String identifier,
			TextField txt) {
		long maxValue = 0;
		switch (identifier) {
		case "x":
			maxValue = (long) Screen.getPrimary().getBounds().getMaxX();
			break;
		case "y":
			maxValue = (long) Screen.getPrimary().getBounds().getMaxY();
			break;
		}
		return txt.getText().matches("\\d+")
				&& Long.parseLong(txt.getText()) <= maxValue;
	}

}
