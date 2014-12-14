package helper;

import javafx.scene.control.TextField;
import javafx.stage.Screen;

public class FormValidation {

	public static boolean validateAddPosFields(TextField x, TextField y) {
		return validate("x", x) && validate("y", y);
	}

	private static boolean validate(String identifier, TextField txt) {
		long maxValue = 0;
		switch (identifier) {
		case "x":
			maxValue = (long) Screen.getPrimary().getBounds().getMaxX();
			break;
		case "y":
			maxValue = (long) Screen.getPrimary().getBounds().getMaxY();
			break;
		}
		return txt.getText().matches("\\d+") && Long.parseLong(txt.getText()) <= maxValue;
	}

}
