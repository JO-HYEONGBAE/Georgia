package application;
	
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	
	private Stage dialog;
	
	public void setDailog(Stage dialog) {
		this.dialog=dialog;
	}
	@Override
	public void start(Stage primaryStage) {
		
		Stage loginDialog = new Stage(StageStyle.DECORATED);
		loginDialog.initModality(Modality.WINDOW_MODAL);
		loginDialog.initOwner(primaryStage);
		loginDialog.setTitle("로그인 화면");
		
		try {
		FXMLLoader login = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		Parent parent = login.load();
		LoginController logincontroller = login.getController();
		logincontroller.setDialog(loginDialog);;
		Scene scenelogin = new Scene(parent);
		loginDialog.setScene(scenelogin);
		loginDialog.setResizable(false);
		loginDialog.show();
		/*
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			MainController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
			primaryStage.setTitle("GEORGIA");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
