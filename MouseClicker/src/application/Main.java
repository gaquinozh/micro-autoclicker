package application;

import java.awt.MouseInfo;
import java.awt.Point;

import helper.MouseRecorder;
import controller.ClickController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Main extends Application {

	private Scene scene;
	private ClickController cC;
	@SuppressWarnings("unused")
	private MouseRecorder mR;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Pane p = fxmlLoader.load(getClass().getResource(
					"/application/ClickerMainView.fxml").openStream());
			
			scene = new Scene(p);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());

			cC = (ClickController) fxmlLoader.getController();
			MouseRecorder mR = new MouseRecorder();
			mR.addObserver(cC);
			
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Micro Autoclicker");
			primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Point p = MouseInfo.getPointerInfo().getLocation();
					mR.set(p.getX(), p.getY());
				}
			});
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
