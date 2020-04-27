package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
@FXML private TabPane mainPane;
@FXML private MenuTabController menuTabController;
@FXML private Tab menu;
@FXML private WorkTabController workTabController;
@FXML private Tab member;
@FXML private MemberTabController memberTabController;
@FXML private Tab staff;
@FXML private StaffTabController staffTabController;
@FXML private Tab work;

	private Stage primaryStage;
private  LoginController lc =LoginController.getInstance();

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage=primaryStage;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		/*
		menuTabController.setPrimaryStage(primaryStage);
		memberTabController.setPrimaryStage(primaryStage);
		staffTabController.setPrimaryStage(primaryStage);
		workTabController.setPrimaryStage(primaryStage);
	*/
		
		mainPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			public void changed(ObservableValue<? extends Tab> observable,Tab oldValue,Tab newValue) {
				
				if(newValue==menu) {				
					try {
						memberTabController.sellTotalList(null);
						menuTabController.menuTotalList(null);
						workTabController.workTotalList(null);
						memberTabController.memberTotalList(null);
						staffTabController.staffTotalList(null);
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				}else if(newValue==work) {
					try {
						menuTabController.menuTotalList(null);
						workTabController.workTotalList(null);
						memberTabController.memberTotalList(null);
						memberTabController.sellTotalList(null);
						staffTabController.staffTotalList(null);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else if(newValue==member) {
					try {
						memberTabController.sellTotalList(null);
						menuTabController.menuTotalList(null);
						workTabController.workTotalList(null);
						memberTabController.memberTotalList(null);
						staffTabController.staffTotalList(null);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else if(newValue==staff) {
					try {
						menuTabController.menuTotalList(null);
						memberTabController.sellTotalList(null);
						workTabController.workTotalList(null);
						memberTabController.memberTotalList(null);
						staffTabController.staffTotalList(null);
					}catch(Exception e) {
						e.printStackTrace();
					}
					}
			}
		});
		if(lc.staffDataList.get(0).getS_job().equals("직원")) {
			staff.setDisable(true);
			
			Tooltip tooltip = new Tooltip("직원은 들어갈 수 없습니다.");
			tooltip.setFont(new Font(12));
			staff.setTooltip(tooltip);
			
			
			
		}
	}
	public void exit() {
		Platform.exit();
		System.exit(0);
	}
	public void logout() {
		
		Stage loginDialog = new Stage(StageStyle.DECORATED);
		//loginDialog.initModality(Modality.WINDOW_MODAL);
		//loginDialog.initOwner(primaryStage);
		//loginDialog.setTitle("로그인 화면");
		
		try {
		FXMLLoader login = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		Parent parent = login.load();
		LoginController logincontroller = login.getController();
		logincontroller.setDialog(loginDialog);
		Scene scenelogin = new Scene(parent);
		loginDialog.setScene(scenelogin);
		loginDialog.setResizable(false);
		loginDialog.show();
		
	}catch(Exception e) {
		e.printStackTrace();
	}
		primaryStage.close();
}
}


	
