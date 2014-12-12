package controller;

import com.sun.glass.ui.Robot;

import helper.MouseRecorder;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import model.Position;

import com.sun.glass.ui.Application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClickController implements Initializable, Observer {

	@FXML
	private TextField txtXPosLbl;
	@FXML
	private TextField txtYPosLbl;

	@FXML
	private TextField txtXPos;
	@FXML
	private TextField txtYPos;
	@FXML
	private TextField txtDelay;
	@FXML
	private TextField txtClickCount;

	@FXML
	private ToggleButton tglBtn;
	@FXML
	private Button btnAddPos;
	@FXML
	private Button btnRemPos;

	@FXML
	private TableView<Position> tblPositions;
	@FXML
	private TableColumn<Position, Integer> x;
	@FXML
	private TableColumn<Position, Integer> y;

	ObservableList<Position> data = FXCollections.observableArrayList();

	private Robot robot = Application.GetApplication().createRobot();
	private Timer timer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		x.setCellValueFactory(new PropertyValueFactory<Position, Integer>("x"));
		y.setCellValueFactory(new PropertyValueFactory<Position, Integer>("y"));
		tblPositions.setItems(data);
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
							for (Position p : data) {
								robot.mouseMove(p.getX(), p.getY());
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								for (int i = 0; i != Integer
										.parseInt(txtClickCount.getText()); i++) {
									robot.mousePress(Robot.MOUSE_LEFT_BTN);
									robot.mouseRelease(Robot.MOUSE_LEFT_BTN);
								}
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

	public void addPos(ActionEvent event) {
		data.add(new Position(Integer.parseInt(txtXPos.getText()), Integer
				.parseInt(txtYPos.getText())));
	}

	public void remPos(ActionEvent event) {
		data.remove(tblPositions.getSelectionModel().getSelectedItem());
	}

	@Override
	public void update(Observable o, Object arg) {
		MouseRecorder mR = (MouseRecorder) o;
		txtXPosLbl.setPromptText("" + mR.getX());
		txtYPosLbl.setPromptText("" + mR.getY());
	}

}
