package minproj;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
 
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create the GradMatchJobClient client
		GradMatchJobClient jobClientPane = new GradMatchJobClient(primaryStage);
		
	}
}
