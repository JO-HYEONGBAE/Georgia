package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataUtil;
import model.MenuDAO;
import model.StockVO;

public class StockController implements Initializable {
	@FXML
	private TableView<StockVO> stockTableView;
	@FXML
	private Label name;
	@FXML
	private Label job;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtAmount;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnAll;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnWearing;
	private LoginController lc = LoginController.getInstance();
	private int amount;
	private MenuTabController mtc=MenuTabController.getInstance();
	
	//StockController st = new StockController();
			
	private Stage dialog;
	
	public void setDialog(Stage dialog) {
		this.dialog=dialog;
	}
	
	private MenuDAO mdao=MenuDAO.getInstance();
	
	ObservableList<StockVO> stockDataList = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		TableColumn<StockVO,?>costNo = stockTableView.getColumns().get(0);
		costNo.setCellValueFactory(new PropertyValueFactory<>("st_no"));
		TableColumn<StockVO,?>costName = stockTableView.getColumns().get(1);
		costName.setCellValueFactory(new PropertyValueFactory<>("st_name"));
		TableColumn<StockVO,?>costPrice= stockTableView.getColumns().get(2);
		costPrice.setCellValueFactory(new PropertyValueFactory<>("st_price"));
		TableColumn<StockVO,?>costAmount =stockTableView.getColumns().get(3);
		costAmount.setCellValueFactory(new PropertyValueFactory<>("st_amount"));
		TableColumn<StockVO,?>costData = stockTableView.getColumns().get(4);
		costData.setCellValueFactory(new PropertyValueFactory<>("st_date"));
		TableColumn<StockVO,?>costStatus =stockTableView.getColumns().get(5);
		costStatus.setCellValueFactory(new PropertyValueFactory<>("st_status"));
		stockTableView.setItems(stockDataList);
		stockTotalList(null);
		txtName.setEditable(false);
		txtPrice.setEditable(false);
		btnWearing.setDisable(true);
		name.setText(lc.staffDataList.get(0).getS_name());
		job.setText(lc.staffDataList.get(0).getS_job());
		
	}

	
	public void stockTotalList(String searchWord) {
		stockDataList.removeAll(stockDataList);
		
		StockVO stVo=null;
		ArrayList<StockVO>list;
		
		try {		
			if(searchWord==null) {
				
				list=mdao.getStockTotalList();
			}else {
				String word =searchWord;
				list =mdao.getStockSearch(searchWord);
			}
			int rowCount =list.size();
			
			for(int i =0; i<rowCount;i++) {
				stVo=list.get(i);
				stockDataList.add(stVo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void btnSearch(ActionEvent event) {
		String word = null;
		if(event.getSource()==btnSearch) {
			if(!DataUtil.validityCheck(txtSearch.getText(), "검색할 대상의 이름을"));
			else word = txtSearch.getText();
			stockTotalList(word);
		}else if(event.getSource()==btnAll) {
			txtSearch.clear();
			stockTotalList(null);
		}
		
	}

	public void btnClose() {
		
		dialog.close();
	}

	public void stockTableView(MouseEvent event) {
		if(event.getClickCount()==2) {
			StockVO selectStock = stockTableView.getSelectionModel().getSelectedItem();
			if(selectStock!=null) {
				txtName.setText(selectStock.getSt_name());
				txtPrice.setText(String.valueOf(selectStock.getSt_price()));
				txtAmount.setText(String.valueOf(selectStock.getSt_amount()));
				btnWearing.setDisable(false);
				
			
			}
				
		}
	}
	public void btnWearing(ActionEvent event)  {
		amount=Integer.parseInt(txtAmount.getText());
		try {
		Stage wearingDialog= new Stage(StageStyle.DECORATED);
		wearingDialog.initModality(Modality.WINDOW_MODAL);
		wearingDialog.initOwner(dialog);
		wearingDialog.setTitle("입고 확인 화면");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/stockWearing.fxml"));
		
		Button btnOk=(Button)parent.lookup("#btnOk");
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				boolean success = false;
				if(!DataUtil.validityCheck(txtName.getText(),"품명을")) return;
				else if(!DataUtil.validityCheck(txtPrice.getText(), "가격을")) return;
				else if(!DataUtil.validityCheck(txtAmount.getText(), "수량을")) return;
				else if(amount<1) {
					Alert ale =new Alert(AlertType.WARNING);
					ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
					ale.setContentText("재고칸은 0이상에 값을 입력해주세요");
					ale.showAndWait();
					wearingDialog.close();
					txtName.clear();
					txtPrice.clear();
					txtAmount.clear();
					btnWearing.setDisable(false);
					
				}
				else {
					StockVO stVo= new StockVO();
					stVo.setSt_name(txtName.getText());
					stVo.setSt_price(Integer.parseInt(txtPrice.getText()));
					stVo.setSt_amount(Integer.parseInt(txtAmount.getText()));
					stVo.setSt_status("입고");
					try {
						success =mdao.stockInsert(stVo);
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					Alert alert = new Alert(AlertType.INFORMATION);
					 alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
					alert.setTitle("입고 정보 여부");
					if(success==true) {
						alert.setHeaderText("입고 정보 여부");
						alert.setContentText("입고 되었습니다.");
						stockTotalList(null);
						mtc.menuTotalList(null);
						txtName.clear();
						txtPrice.clear();
						txtAmount.clear();
						btnWearing.setDisable(false);
						
					}else {
						alert.setAlertType(AlertType.WARNING);
						 alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
						alert.setHeaderText("입고 정보 여부");
						alert.setContentText("입고과정에 문제가 있어 입고되지못했습니다.");
						txtName.clear();
						txtPrice.clear();
						txtAmount.clear();
						btnWearing.setDisable(false);
						
					}
					alert.showAndWait();
					wearingDialog.close();
					btnWearing.setDisable(true);;
					
					
				}
			}
		});
				Button btnNo=(Button)parent.lookup("#btnNo");
				
				btnNo.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						wearingDialog.close();
					}
				});
				
				Label txtName=(Label)parent.lookup("#txtName");
				txtName.setText(this.txtName.getText());
				
				Label txtAmount=(Label)parent.lookup("#txtAmount");
				txtAmount.setText(this.txtAmount.getText());
				Scene scene = new Scene(parent);
				wearingDialog.setScene(scene);
				wearingDialog.setResizable(false);
				wearingDialog.show();
			
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	}
	public void txtAmount(MouseEvent event) {
		txtAmount.clear();
	}
	
}
