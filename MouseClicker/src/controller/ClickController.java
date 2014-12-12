package controller;

import com.sun.glass.ui.Robot;

import helper.MouseRecorder;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.glass.ui.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

public class ClickController implements Initializable, Observer {

	@FXML
	private ToggleButton tglBtn;
	@FXML
	private TextField txtXPos;
	@FXML
	private TextField txtYPos;
	@FXML
	private TextField txtDelay;
	@FXML
	private TextField xPosLbl;
	@FXML
	private TextField yPosLbl;
	@FXML
	private TextField txtClickCount;

	private Robot robot = Application.GetApplication().createRobot();
	private Timer timer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// This is an empty method for now...
	}

	public void startClicker(ActionEvent event) {

		if (this.tglBtn.isSelected()) {
			tglBtn.setText("Stop Clicking!");
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							robot.mouseMove(
									Integer.parseInt(txtXPos.getText()),
									Integer.parseInt(txtYPos.getText()));
							for (int i = 0; i != Integer.parseInt(txtClickCount
									.getText()); i++) {
								robot.mousePress(Robot.MOUSE_LEFT_BTN);
								robot.mouseRelease(Robot.MOUSE_LEFT_BTN);
							}
						}
					});
				}
			}, 0, Long.parseLong(txtDelay.getText()) * 1000);
		} else {
			emergencyStop();
		}
	}

	public void emergencyStop() {
		timer.cancel();
		timer.purge();
		tglBtn.setText("Start Clicking!");
	}

	@Override
	public void update(Observable o, Object arg) {
		MouseRecorder mR = (MouseRecorder) o;
		xPosLbl.setPromptText("" + mR.getX());
		yPosLbl.setPromptText("" + mR.getY());
	}

}
