package controller;

import com.sun.glass.ui.Robot;

import helper.FormValidation;
import helper.MouseHooker;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
import javafx.scene.input.KeyEvent;

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
	private Button btnAddCurrentPos;

	@FXML
	private TableView<Position> tblPositions;
	@FXML
	private TableColumn<Position, Integer> x;
	@FXML
	private TableColumn<Position, Integer> y;
	
	@FXML
	private Tooltip toolTipAmountOfClicks;
	@FXML
	private Tooltip toolTipDelay;
	@FXML
	private Tooltip toolTipAddPosition;

	ObservableList<Position> data = FXCollections.observableArrayList();

	private Robot robot = Application.GetApplication().createRobot();
	private Timer timer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		x.setCellValueFactory(new PropertyValueFactory<Position, Integer>("x"));
		y.setCellValueFactory(new PropertyValueFactory<Position, Integer>("y"));
		tblPositions.setItems(data);
	}

	@FXML
	public void validateBeforeAdd(KeyEvent e) {
		if(FormValidation.validateAddPosFields(txtXPos, txtYPos))
			btnAddPos.setDisable(false);
		else
			btnAddPos.setDisable(true);
	}

	@FXML
	public void validateBeforeStart(KeyEvent e) {
		if (FormValidation.validateStart(txtClickCount, txtDelay))
			tglBtn.setDisable(false);
		else
			tglBtn.setDisable(true);
	}

	@FXML
	public void startClicker(ActionEvent event) {

		if (this.tglBtn.isSelected()) {
			tglBtn.setText("Stop Clicking");
			setDisabledAll(true);
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
		tglBtn.setText("Start Clicking");
		setDisabledAll(false);
		if(FormValidation.validateAddPosFields(txtXPos, txtYPos))
			btnAddPos.setDisable(false);
		else
			btnAddPos.setDisable(true);
		
	}

	private void setDisabledAll(boolean disabled) {
		txtClickCount.setDisable(disabled);
		txtXPos.setDisable(disabled);
		txtYPos.setDisable(disabled);
		txtClickCount.setDisable(disabled);
		txtDelay.setDisable(disabled);
		btnAddCurrentPos.setDisable(disabled);
		btnAddPos.setDisable(disabled);
		btnRemPos.setDisable(disabled);
	}

	@FXML
	public void addPos(ActionEvent event) {
		data.add(new Position(Integer.parseInt(txtXPos.getText()), Integer
				.parseInt(txtYPos.getText())));
		txtXPos.clear();
		txtYPos.clear();
		btnAddPos.setDisable(true);
		txtXPos.requestFocus();
	}

	@FXML
	public void remPos(ActionEvent event) {
		data.remove(tblPositions.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void addCurrentPos(ActionEvent e) {
		data.add(new Position(Integer.parseInt(txtXPosLbl.getText()), Integer
				.parseInt(txtYPosLbl.getText())));
	}

	@Override
	public void update(Observable o, Object obj) {
		MouseHooker mh = (MouseHooker) o;
		txtXPosLbl.setText(String.format("%d", mh.getX()));
		txtYPosLbl.setText(String.format("%d", mh.getY()));
	}

}
