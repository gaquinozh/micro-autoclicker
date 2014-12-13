package application;

import helper.MouseHooker;

import org.jnativehook.GlobalScreen;

import controller.ClickController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {

	private Scene scene;
	private ClickController cC;
	private MouseHooker mH;

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
			mH = new MouseHooker();
			mH.addObserver(cC);
			GlobalScreen.registerNativeHook();
			GlobalScreen.getInstance().addNativeMouseMotionListener(mH);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Micro Autoclicker");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} finally {
			GlobalScreen.unregisterNativeHook();
			System.runFinalization();
			System.exit(0);
		}
	}
}
