package Driver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		Content content = new Content(window);
		Scene scene = new Scene(content, 1350, 600);
		window.setScene(scene);
		window.show();
		window.setResizable(false);
	}
}
