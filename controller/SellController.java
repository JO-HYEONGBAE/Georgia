package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
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
import model.MenuDAO;
import model.SellVO;

public class SellController implements Initializable {
	@FXML
	private Button btnClose;
	@FXML
	private Button btnRefund;
	@FXML
	private Label txtCash;
	@FXML
	private Label txtDate;

	@FXML
	private Button btnSales;

	private Date today = new Date();

	private SimpleDateFormat smp;

	private Stage dialog;
	private MenuTabController mtc = MenuTabController.getInstance();

	public void setDialog(Stage dialog) {
		this.dialog = dialog;
	}

	ObservableList<SellVO> sellDataList = FXCollections.observableArrayList();
	ObservableList<SellVO> sellMonthDataList = FXCollections.observableArrayList();
	ObservableList<SellVO> sellYearDataList = FXCollections.observableArrayList();
	private int selectedSellIndex;
	private String selectedSellName;
	private int selectedSellPrice;
	private int selectedSellCheck;
	private LoginController lc = LoginController.getInstance();
	private int total;
	private int monthTotal;
	private int yearTotal;
	@FXML
	private TableView<SellVO> sellTableView;

	private MenuDAO mdao = MenuDAO.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		TableColumn<SellVO, ?> cosDayNo = sellTableView.getColumns().get(0);
		cosDayNo.setCellValueFactory(new PropertyValueFactory<>("se_no"));

		TableColumn<SellVO, ?> cosDayName = sellTableView.getColumns().get(1);
		cosDayName.setCellValueFactory(new PropertyValueFactory<>("se_name"));

		TableColumn<SellVO, ?> cosDayPrice = sellTableView.getColumns().get(2);
		cosDayPrice.setCellValueFactory(new PropertyValueFactory<>("se_price"));

		TableColumn<SellVO, ?> cosDayAmount = sellTableView.getColumns().get(3);
		cosDayAmount.setCellValueFactory(new PropertyValueFactory<>("se_amount"));

		TableColumn<SellVO, ?> cosDayDate = sellTableView.getColumns().get(4);
		cosDayDate.setCellValueFactory(new PropertyValueFactory<>("se_date"));

		TableColumn<SellVO, ?> cosDayMeNo = sellTableView.getColumns().get(5);
		cosDayMeNo.setCellValueFactory(new PropertyValueFactory<>("me_no"));
		TableColumn<SellVO, ?> cosDaycheck = sellTableView.getColumns().get(6);
		cosDayMeNo.setCellValueFactory(new PropertyValueFactory<>("se_check"));
		sellTableView.setItems(sellDataList);
		sellTotalList(null);
		btnRefund.setDisable(true);
		for (int i = 0; i < sellDataList.size(); i++) {
			total += sellDataList.get(i).getSe_price();
		}
		DecimalFormat dm = new DecimalFormat("###,###");
		txtCash.setText(dm.format(total));

		smp = new SimpleDateFormat("yyyy년 MM월 dd일");
		txtDate.setText(smp.format(today));
		if (lc.staffDataList.get(0).getS_job().equals("직원")) {
			btnSales.setVisible(false);
		}
	}

	public void sellTotalList(String searchWord) {
		sellDataList.removeAll(sellDataList);
		SellVO sVo = null;
		ArrayList<SellVO> list;

		try {
			list = mdao.getSellDayList();
			int rowCount = list.size();

			for (int i = 0; i < rowCount; i++) {
				sVo = list.get(i);
				sellDataList.add(sVo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sellTableView(MouseEvent event) {
		if (event.getClickCount() == 2) {
			SellVO selectSell = sellTableView.getSelectionModel().getSelectedItem();
			btnRefund.setDisable(false);
			if (selectSell != null) {
				selectedSellIndex = selectSell.getSe_no();
				selectedSellName = selectSell.getSe_name();
				selectedSellPrice = selectSell.getSe_price();
				selectedSellCheck = selectSell.getSe_check();
			}
		}
	}

	public void sellMonthList(String searchWord) {
		sellMonthDataList.removeAll(sellMonthDataList);
		SellVO sVo = null;
		ArrayList<SellVO> list;

		try {
			list = mdao.getSellMonthList();
			int rowCount = list.size();

			for (int i = 0; i < rowCount; i++) {
				sVo = list.get(i);
				sellMonthDataList.add(sVo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void sellYearList(String searchWord) {
		sellYearDataList.removeAll(sellYearDataList);
		SellVO sVo = null;
		ArrayList<SellVO> list;
		
		try {
			list = mdao.getSellYearList();
			int rowCount = list.size();
			
			for (int i = 0; i < rowCount; i++) {
				sVo = list.get(i);
				sellYearDataList.add(sVo);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void btnClose(ActionEvent event) {
		dialog.close();

	}

	public void btnRefund(ActionEvent event) throws Exception {
		int ok=selectedSellPrice;
		String result="";
		char c;
		for(int i =0;i<txtCash.getText().length();i++) {
			c=txtCash.getText().charAt(i);
			if(c==',') {
				result+="";
			}else {
				result+=c;
			}
		}
		total = Integer.parseInt(result) -ok;
		Stage refunddialog = new Stage(StageStyle.DECORATED);
		refunddialog.initModality(Modality.WINDOW_MODAL);
		refunddialog.initOwner(dialog);
		refunddialog.setTitle("환불 정보");

		Parent parent = FXMLLoader.load(getClass().getResource("/view/refund.fxml"));

		Button btnOk = (Button) parent.lookup("#btnOk");

		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(selectedSellCheck==2){
					
					Alert alert = new Alert(AlertType.WARNING);
					//alert.setAlertType(AlertType.WARNING);
					alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
					alert.setHeaderText("환불 정보 여부");
					alert.setContentText("적립금 사용한 상품은 환불 할 수없습니다.");
					alert.showAndWait();
					return;
				}else {
				boolean success = false;
				boolean result = false;

				try {
					success = mdao.sellDelete(selectedSellIndex);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
				alert.setTitle("환불 정보 여부");
				if (success == true && selectedSellPrice>0) {
					alert.setHeaderText("환불 정보 여부");
					alert.setContentText("환불 되었습니다.");
					sellTotalList(null);
					mtc.menuTotalList(null);
					btnRefund.setDisable(true);
					txtCash.setText(String.valueOf(total));
				} 
				
				
				
				else {
					alert.setAlertType(AlertType.WARNING);
					alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
					alert.setHeaderText("환불 정보 여부");
					alert.setContentText("환불과정에 문제가 있어 실패하였습니다");
				}
				alert.showAndWait();
				refunddialog.close();

				}		// me.menuTotalList(null);
			
		}
		});
		
		Button btnNo = (Button) parent.lookup("#btnNo");
		btnNo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				refunddialog.close();
			}
		});

		Label txtName = (Label) parent.lookup("#txtName");
		txtName.setText(selectedSellName);

		Label txtPay = (Label) parent.lookup("#txtPay");
		txtPay.setText(String.valueOf(selectedSellPrice));
		Scene scene = new Scene(parent);
		refunddialog.setScene(scene);
		refunddialog.setResizable(false);
		refunddialog.show();

	}

	public void btnSales(ActionEvent event) {
		
		
		smp = new SimpleDateFormat("yyyy");
		int x = Integer.parseInt(smp.format(today)) - 1;
		int x2 = Integer.parseInt(smp.format(today)) - 2;
		String date = String.valueOf(x);

		String date2 = String.valueOf(x2);
		try {
			Stage chartDialog = new Stage(StageStyle.DECORATED);
			chartDialog.initModality(Modality.WINDOW_MODAL);
			chartDialog.initOwner(dialog);

			Parent parent = FXMLLoader.load(getClass().getResource("/view/monthSales.fxml"));

			@SuppressWarnings("unchecked")
			LineChart<String, Integer> lineChart = (LineChart<String, Integer>) parent.lookup("#lineChart");

			XYChart.Series<String, Integer> dataSeries = new XYChart.Series<String, Integer>();
			dataSeries.setName(Calendar.getInstance().get(Calendar.YEAR) + "년");
			Map<String, Integer> resultMap = mdao.getSellMonth(smp.format(today));
			for (Map.Entry<String, Integer> result : resultMap.entrySet()) {
				dataSeries.getData().add(new Data<String, Integer>(result.getKey(), result.getValue()));
			}
			XYChart.Series<String, Integer> dataSeries3 = new XYChart.Series<String, Integer>();
			dataSeries3.setName(Calendar.getInstance().get(Calendar.YEAR) - 1 + "년");
			Map<String, Integer> resultMap1 = mdao.getSellMonth(date);
			for (Map.Entry<String, Integer> result : resultMap1.entrySet()) {
				dataSeries3.getData().add(new Data<String, Integer>(result.getKey(), result.getValue()));
			}

			XYChart.Series<String, Integer> dataSeries4 = new XYChart.Series<String, Integer>();
			dataSeries4.setName(Calendar.getInstance().get(Calendar.YEAR) - 2 + "년");
			Map<String, Integer> resultMap4 = mdao.getSellMonth(date2);
			for (Map.Entry<String, Integer> result : resultMap4.entrySet()) {
				dataSeries4.getData().add(new Data<String, Integer>(result.getKey(), result.getValue()));
			}
			lineChart.getData().add(dataSeries);
			lineChart.getData().add(dataSeries3);
			lineChart.getData().add(dataSeries4);

			AreaChart<String, Integer> areaChart = (AreaChart<String, Integer>) parent.lookup("#areaChart");

			XYChart.Series<String, Integer> dataSeries2 = new XYChart.Series<String, Integer>();
			dataSeries2.setName(Calendar.getInstance().get(Calendar.YEAR) + "년 월단위 매출분포");
			Map<String, Integer> resultMap3 = mdao.getSellMonth(smp.format(today));
			for (Map.Entry<String, Integer> result : resultMap3.entrySet()) {
				dataSeries2.getData().add(new Data<String, Integer>(result.getKey(), result.getValue()));
			}
			areaChart.getData().add(dataSeries2);

			PieChart pieChart = (PieChart) parent.lookup("#pieChart");
			pieChart.setTitle(Calendar.getInstance().get(Calendar.YEAR) + "년 월단위 매출 분포");
			Map<String, Integer> resultMap2 = mdao.getSellMonth(smp.format(today));
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
			for (Map.Entry<String, Integer> result : resultMap.entrySet()) {
				pieChartData.add(new PieChart.Data(result.getKey(), result.getValue()));
			}
			pieChart.setData(pieChartData);

			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					chartDialog.close();
					// System.out.println(smp.format(today));
				}
			});
			SimpleDateFormat sm = new SimpleDateFormat("MM월");
			SimpleDateFormat sy = new SimpleDateFormat("YYYY년");
			Label month =(Label)parent.lookup("#month");
			
			month.setText(sm.format(today));
			Label year =(Label)parent.lookup("#year");
			year.setText(sy.format(today));
			TextField monthSales =(TextField)parent.lookup("#monthSales");
			monthSales.setEditable(false);
			TextField yearSales =(TextField)parent.lookup("#yearSales");
			yearSales.setEditable(false);
			TableView<SellVO> sellMonthTableView=(TableView) parent.lookup("#monthTable");

			TableColumn<SellVO, ?> cosNo = sellMonthTableView.getColumns().get(0);
			cosNo.setCellValueFactory(new PropertyValueFactory<>("se_no"));

			TableColumn<SellVO, ?> cosName = sellMonthTableView.getColumns().get(1);
			cosName.setCellValueFactory(new PropertyValueFactory<>("se_name"));

			TableColumn<SellVO, ?> cosPrice = sellMonthTableView.getColumns().get(2);
			cosPrice.setCellValueFactory(new PropertyValueFactory<>("se_price"));

			TableColumn<SellVO, ?> cosAmount = sellMonthTableView.getColumns().get(3);
			cosAmount.setCellValueFactory(new PropertyValueFactory<>("se_amount"));

			TableColumn<SellVO, ?> cosDate = sellMonthTableView.getColumns().get(4);
			cosDate.setCellValueFactory(new PropertyValueFactory<>("se_date"));

			TableColumn<SellVO, ?> cosMeNo = sellMonthTableView.getColumns().get(5);
			cosMeNo.setCellValueFactory(new PropertyValueFactory<>("me_no"));
			sellMonthTableView.setItems(sellMonthDataList);
			sellMonthList(null);
			
			for (int i = 0; i < sellMonthDataList.size(); i++) {
				monthTotal += sellMonthDataList.get(i).getSe_price();
			}
			DecimalFormat formatter = new DecimalFormat("###,###");
			formatter.format(monthTotal);
			monthSales.setText(formatter.format(monthTotal));
			sellYearList(null);
			
			for (int i = 0; i < sellYearDataList.size(); i++) {
				yearTotal += sellYearDataList.get(i).getSe_price();
			}
			yearSales.setText(formatter.format(yearTotal));
			Scene scene = new Scene(parent);
			chartDialog.setScene(scene);
			chartDialog.setResizable(false);
			chartDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
