package Communicator;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    FXMLLoader loader= new FXMLLoader();
    public Controller controller = null;
	@Override
	public void start(Stage primaryStage) {
		try {
		    loader.setLocation(getClass().getResource("Sample.fxml"));
			VBox root = loader.load();
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("聊天室小程序");
			primaryStage.show();
			controller = loader.getController();
			//controller.setMain(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

}
