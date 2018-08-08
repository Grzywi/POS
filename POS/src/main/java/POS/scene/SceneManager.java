package POS.scene;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

	public Scene createScene(final String filePath) {
		try {
			return new Scene(FXMLLoader.load(getClass().getResource(filePath)));
		} catch (IOException e) {
			throw new RuntimeException("Scene not working");
		}

	}

	public void showStage(final ActionEvent actionEvent, final Scene scene) {
		final Stage appStage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
		appStage.setScene(scene);
		appStage.show();
	}

}
