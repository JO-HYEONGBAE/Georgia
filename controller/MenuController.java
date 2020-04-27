package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataUtil;
import model.MenuDAO;
import model.MenuVO;

public class MenuController implements Initializable {
	@FXML
	private Button btnInsert;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnCheck;
	@FXML
	private Button btnReset;
	
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPrice;
	
	@FXML
	private TableView<MenuVO> menuTableView;
	
	private MenuTabController mtc = MenuTabController.getInstance();
	private int price;
	private MenuDAO mdao = MenuDAO.getInstance();
	private Stage dialog;
	
	public void setDialog(Stage dialog) {
		this.dialog=dialog;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		TableColumn<MenuVO, ?> comNo = menuTableView.getColumns().get(0);
		comNo.setCellValueFactory(new PropertyValueFactory<>("m_no"));

		TableColumn<MenuVO, ?> comName = menuTableView.getColumns().get(1);
		comName.setCellValueFactory(new PropertyValueFactory<>("m_name"));

		TableColumn<MenuVO, ?> comPrice = menuTableView.getColumns().get(2);
		comPrice.setCellValueFactory(new PropertyValueFactory<>("m_price"));
		menuTableView.setItems(mtc.menuDataList);
		btnInsert.setDisable(true);
		btnUpdate.setDisable(true);
		btnDelete.setDisable(true);
	}
	
	public void menuTableView(MouseEvent event) {
		if(event.getClickCount()==2) {
			MenuVO mvo = menuTableView.getSelectionModel().getSelectedItem();
			
			if(mvo!=null) {
				txtName.setEditable(false);
			//	txtPrice.setEditable(false);
				txtName.setText(mvo.getM_name());
				txtPrice.setText(String.valueOf(mvo.getM_price()));
				btnUpdate.setDisable(false);
				btnDelete.setDisable(false);
				btnCheck.setDisable(true);
			}
		}
	}
	
	public void btnInsert(ActionEvent event) throws Exception {
		if(!DataUtil.validityCheck(txtName.getText(), "상품명을"))return;
		else if(!DataUtil.validityCheck(txtPrice.getText(), "가격을")) return;
		else {
		price = Integer.parseInt(txtPrice.getText());
		Stage addDialog = new Stage(StageStyle.DECORATED);
		addDialog.initModality(Modality.WINDOW_MODAL);
		addDialog.initOwner(dialog);
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/menuAdd.fxml"));
		Button btnOk=(Button)parent.lookup("#btnOk");
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				boolean success =false;
				
				 if(price<0) {
					Alert ale = new Alert(AlertType.WARNING);
					ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
					ale.setContentText("가격은 0원이상으로 설정해주세요");
					ale.showAndWait();
					addDialog.close();
					txtName.clear();
					txtPrice.clear();
					txtName.setEditable(true);
					txtPrice.setEditable(true);
					btnInsert.setDisable(true);
					btnUpdate.setDisable(true);
					btnDelete.setDisable(true);
					btnCheck.setDisable(false);
				}
				
				
					else {
					MenuVO mVo=new MenuVO();
					mVo.setM_name(txtName.getText());
					mVo.setM_price(Integer.parseInt(txtPrice.getText()));
					mVo.setM_amount(0);
					try {
						success = mdao.menuInsert(mVo);
					}catch(Exception e) {
						e.printStackTrace();
					}
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
					if(success=true) {
						alert.setHeaderText("신규 등록 여부'");
						alert.setContentText("신규 등록이 완료되었습니다");
						mtc.menuTotalList(null);
						btnInsert.setDisable(true);
						btnCheck.setDisable(false);
						txtPrice.setEditable(true);
						txtName.setEditable(true);
						txtName.clear();
						txtPrice.clear();
					}else {
						alert.setHeaderText("신규 등록 여부");
						alert.setContentText("메뉴 등록에 문제가 있어 완료하지못하였습니다.");
						
					}
					alert.showAndWait();
					addDialog.close();
					
				}
			
			}
		});
		Label txtName=(Label)parent.lookup("#txtName");
		txtName.setText(this.txtName.getText());
		Label txtPrice=(Label)parent.lookup("#txtPrice");
		txtPrice.setText(this.txtPrice.getText());
		
		Button btnNo=(Button)parent.lookup("#btnNo");
		btnNo.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				addDialog.close();
			}
		});
		
		
		Scene scene = new Scene(parent);
		addDialog.setScene(scene);
		addDialog.setResizable(false);
		addDialog.show();
	
		}
	}
	
	public void btnUpdate(ActionEvent event) throws Exception {
		if(!DataUtil.validityCheck(txtName.getText(), "상품명을"))return;
		else if(!DataUtil.validityCheck(txtPrice.getText(), "가격을")) return;
		else {
		
		price = Integer.parseInt(txtPrice.getText());
		
		
		Stage updateDialog =new Stage(StageStyle.DECORATED);
		updateDialog.initModality(Modality.WINDOW_MODAL);
		updateDialog.initOwner(dialog);
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/menuUpdate.fxml"));
		//Parent parent =loader.load();
		Button btnOk=(Button)parent.lookup("#btnOk");
		
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(price<0) {
					Alert ale =new Alert(AlertType.WARNING);
					ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
					ale.setContentText("가격은 0원이상에 값을 넣어주세요.");
					ale.showAndWait();
					txtName.clear();
					txtPrice.clear();
					txtName.setEditable(true);
					txtPrice.setEditable(true);
					btnInsert.setDisable(true);
					btnUpdate.setDisable(true);
					btnDelete.setDisable(true);
					btnCheck.setDisable(false);
					updateDialog.close();
					}
				else {
					MenuVO mVo =new MenuVO();
					
					mVo.setM_price(price);
					mVo.setM_name(txtName.getText());
					boolean success =false;
			
			try {
				success = mdao.menuUpdate(mVo);
			}catch(Exception e) {
				e.printStackTrace();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
			alert.setTitle("메뉴 정보 수정");
			if(success=true) {
				alert.setHeaderText("메뉴 가격 수정 여부");
				alert.setContentText("가격 수정이 완료되었습니다.");
				mtc.menuTotalList(null);
				btnInsert.setDisable(true);
				btnUpdate.setDisable(true);
				btnDelete.setDisable(true);
				btnCheck.setDisable(false);
				txtPrice.setEditable(true);
				txtName.setEditable(true);
				txtName.clear();
				txtPrice.clear();
			}else{
				alert.setAlertType(AlertType.WARNING);
				alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
				alert.setHeaderText("메뉴 가격 수정 여부");
				alert.setContentText("가격 수정에 문제가 있어 완료되지 못하였습니다.");
				
				btnInsert.setDisable(true);
				btnCheck.setDisable(false);
				txtPrice.setEditable(true);
				txtName.setEditable(true);
				txtName.clear();
				txtPrice.clear();
			}
			alert.showAndWait();
			updateDialog.close();
				}
			
		}
		
		});
		
		Button btnNo=(Button)parent.lookup("#btnNo");
		btnNo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				updateDialog.close();
			}
		});
		
		Label txtName=(Label)parent.lookup("#txtName");
		txtName.setText(this.txtName.getText());
		Label txtPrice=(Label)parent.lookup("#txtPrice");
		txtPrice.setText(this.txtPrice.getText());
		
		Scene scene =new Scene(parent);
		updateDialog.setScene(scene);
		updateDialog.setResizable(false);
		updateDialog.show();
		}
		
		
	}
	
	public void btnDelete(ActionEvent event) throws Exception{
		if(!DataUtil.validityCheck(txtName.getText(), "상품명을"))return;
		else if(!DataUtil.validityCheck(txtPrice.getText(), "가격을")) return;
		else {
		
		Stage deleteDialog = new Stage(StageStyle.DECORATED);
		deleteDialog.initModality(Modality.WINDOW_MODAL);
		deleteDialog.initOwner(dialog);
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/menuDelete.fxml"));
		Button btnOk=(Button)parent.lookup("#btnOk");
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		boolean success =false;
		boolean result =false;
		try {
			success =mdao.menuDelete(txtName.getText());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
		alert.setTitle("메뉴 정보 삭제");
		if(success ==true) {
			alert.setHeaderText("메뉴 정보 삭제 여부");
			alert.setContentText("메뉴가 성공적으로 삭제되었습니다.");
			mtc.menuTotalList(null);
			btnInsert.setDisable(true);
			btnUpdate.setDisable(true);
			btnDelete.setDisable(true);
			btnCheck.setDisable(false);
			txtName.setEditable(true);
			//txtPrice.setEditable(true);
			txtName.clear();
			txtPrice.clear();
		}
		else {
			alert.setAlertType(AlertType.WARNING);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
			alert.setHeaderText("메뉴 정보 삭제 여부");
			alert.setContentText("메뉴 정보 삭제에 문제가 있어 삭제가 되지 못했습니다.");
			
		}
		alert.showAndWait();
		deleteDialog.close();
		}
		});
		Button btnNo =(Button)parent.lookup("#btnNo");
		btnNo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				deleteDialog.close();
			}
		});
		Label txtName=(Label)parent.lookup("#txtName");
		txtName.setText(this.txtName.getText());
		Label txtPrice=(Label)parent.lookup("#txtPrice");
		txtPrice.setText(this.txtPrice.getText());
		Scene scene =new Scene(parent);
		deleteDialog.setScene(scene);
		deleteDialog.setResizable(false);
		deleteDialog.show();
	}
	}
	public void btnClose(ActionEvent event) {
		dialog.close();
	}
	
	public void btnCheck(ActionEvent event) {
		String searchName="";
		boolean searchResult=true;
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
		alert.setTitle("메뉴명 중복검사");
		alert.setHeaderText(null);
		try {
			searchName = txtName.getText().trim();
			if(!DataUtil.validityCheck(searchName, "메뉴명을")) {
				txtName.requestFocus();
				return;
				
			}else {
				searchResult = mdao.getMenuNameOverlap(searchName);
				if(!searchResult) {
					alert.setContentText(searchName+"를 사용할수 있습니다.");
					txtName.setEditable(false);
					btnCheck.setDisable(true);
					btnInsert.setDisable(false);
				}else {
					alert.setContentText(searchName+"를 사용할 수 없습니다.");
					txtName.clear();
					txtName.requestFocus();
				}
				alert.show();
			}
		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-error.png"));
			alert.setTitle("이름 중복 검사 오류");
			alert.setContentText("이름 중복검사에 오류가 발생하였습니다.");
			alert.show();
		}
	}
	public void btnReset(ActionEvent event) {
		txtName.clear();
		txtPrice.clear();
		txtName.setEditable(true);
		txtPrice.setEditable(true);
		btnInsert.setDisable(true);
		btnUpdate.setDisable(true);
		btnDelete.setDisable(true);
		btnCheck.setDisable(false);
	}
}
