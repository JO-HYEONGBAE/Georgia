package controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DataUtil;
import model.WorkDAO;
import model.WorkVO;

public class WorkTabController implements Initializable {

	@FXML
	private ToggleGroup groupSearch;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtJob;
	@FXML
	private TextField txtHiredate;
	@FXML
	private Button btnGo;
	@FXML
	private Button btnOut;
	@FXML
	private TextField txtSearchName;
	@FXML
	private DatePicker dpDate;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnTotalList;

	@FXML
	private Label txtday;
	@FXML
	private Label txtMonth;
	@FXML
	private Label txtTotal;
	@FXML
	private Label txtGoTime;
	@FXML
	private Label txtOutTime;
	@FXML
	private Label txtGo;
	@FXML
	private Label txtOut;

	@FXML
	private ImageView imageView;

	@FXML
	private TableView<WorkVO> workTableView;
	ObservableList<WorkVO> workDataList = FXCollections.observableArrayList();
	
	private File selectedFiel =null;
	private String selectS_image ="";
	private String localUrl = "";
	private Image localImage;
	
	private WorkDAO wdao = WorkDAO.getInstance(); // 객체 생성
	private LoginController lc = LoginController.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat();
	private Date date = new Date();
	private SimpleDateFormat now = new SimpleDateFormat();
	
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			//wdao.goCheck(lc.staffDataList.get(0).getS_no());
			int no = lc.staffDataList.get(0).getS_no();
			//System.out.println(no);
			 if(wdao.goCheck(no)==true&&!wdao.outCheck(no)==true) {
				btnGo.setVisible(false);
				btnOut.setVisible(true);
				txtGo.setText("출근 :");
				txtGoTime.setText(wdao.goTime(no));
			}else if(wdao.goCheck(no)==true && wdao.outCheck(no)==true){
				btnGo.setVisible(false);
				btnOut.setVisible(false);
				txtGoTime.setText(wdao.goTime(no));
				txtOutTime.setText(wdao.OutTime(no));
				txtGo.setText("출근 :");
				txtOut.setText("퇴근 :");
				
			}
			else if(lc.staffDataList.get(0).getS_job().equals("사장")) {
				btnGo.setVisible(false);
				btnOut.setVisible(false);
			}else {
			btnGo.setVisible(true);
			btnOut.setVisible(false);
			}
			txtName.setEditable(false); // -------------------로그인 시 등록된 정보 수정 불가
			txtJob.setEditable(false);
			txtHiredate.setEditable(false);
			txtName.setText(lc.staffDataList.get(0).getS_name());
			txtJob.setText(lc.staffDataList.get(0).getS_job());
			txtHiredate.setText(lc.staffDataList.get(0).getS_hiredate());
			String image = lc.staffDataList.get(0).getS_image();
			
			if(image !=null) {
				localUrl = "file:/Users/johyeongbae/Documents/Image\\"+image;
			}else {
	               localUrl = "/image/default.png";
	            }
			localImage = new Image(localUrl);
			imageView.setImage(localImage);
			dpDate.setEditable(false);
			dpDate.setVisible(false);

			TableColumn<WorkVO, ?> colWNo = workTableView.getColumns().get(0);
			colWNo.setCellValueFactory(new PropertyValueFactory<>("w_no"));

			TableColumn<WorkVO, ?> colWGo = workTableView.getColumns().get(1);
			colWGo.setCellValueFactory(new PropertyValueFactory<>("w_go"));

			TableColumn<WorkVO, ?> colWOut = workTableView.getColumns().get(2);
			colWOut.setCellValueFactory(new PropertyValueFactory<>("w_out"));

			TableColumn<WorkVO, ?> colWName = workTableView.getColumns().get(3);
			colWName.setCellValueFactory(new PropertyValueFactory<>("w_name"));

			/*
			 * List<String>title = DataUtil.fieldName(new WorkVO()); for(int i =0; i
			 * <title.size(); i++) { TableColumn<WorkVO,?>columnName =
			 * workTableView.getColumns().get(i); columnName.setCellValueFactory(new
			 * PropertyValueFactory<>(title.get(i))); }
			 */

			sdf = new SimpleDateFormat("YYYY년 MM월 dd일 출퇴근부");
			txtday.setText(sdf.format(date));
			sdf = new SimpleDateFormat("MM");
			txtMonth.setText(sdf.format(date));
			workTableView.setItems(workDataList); // 테이블에 항목설정
			workTotalList(null); // 학과 전체 목록 DB에서 가져옴
			txtTotal.setText(wdao.workDay(no));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***********************
	 * 테이블뷰 레코드 출력 (출퇴근 전체 리스트)
	 ******************************/
	public void workTotalList(String searchWord) {
		workDataList.removeAll(workDataList);
		WorkVO wvo = null;
		ArrayList<WorkVO> list;

		try {

			if (searchWord == null) {
				list = wdao.getWorkTotalList();
			} else {
				String[] word = searchWord.split(",");
				list = wdao.getWorkSearch(word[0], word[1]);
			}

			int rowCount = list.size();
			for (int index = 0; index < rowCount; index++) {
				wvo = list.get(index);
				workDataList.add(wvo);
			}
		} catch (Exception e) {
			System.out.println("e = [" + e.getMessage() + "]");
		}
	}

	/************************
	 * 출근 버튼 클릭시 이벤트
	 *********************************************/
	public void btnGoAction(ActionEvent event) {
		boolean success =false;
		WorkVO wvo = new WorkVO();
		wvo.setW_name(lc.staffDataList.get(0).getS_name());
		wvo.setS_no(lc.staffDataList.get(0).getS_no());
	
		
		try {
			success = wdao.workOn(wvo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Alert alert =new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
		if(success ==true) {
			now =new SimpleDateFormat("YYYY년 MM월 dd일");
			
			alert.setHeaderText("출근 등록 여부");
			alert.setContentText(now.format(date)+" 출근완료");
			workTotalList(null);
			btnGo.setVisible(false);
			btnOut.setVisible(true);
		}else {
			alert.setAlertType(AlertType.WARNING);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
			alert.setHeaderText("출근 등록 여부");
			alert.setContentText("출근 등록에 실패하였습니다 다시 시도해주세요.");
			
		}
		alert.showAndWait();
		int no = lc.staffDataList.get(0).getS_no();
		txtGoTime.setText(wdao.goTime(no));
		txtGo.setText("출근 :");
	}

	/************************
	 * 퇴근 버튼 클릭시 이벤트
	 *********************************************/
	public void btnOutAction() {
		boolean success =false;
		
		WorkVO wvo = new WorkVO();
		try {
			success=wdao.workOut(lc.staffDataList.get(0).getS_no());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
		if(success =true) {
			now =new SimpleDateFormat("YYYY년 MM월 dd일");
			alert.setHeaderText("퇴근 등록 여부");
			alert.setContentText(now.format(date)+" 퇴근완료");
			workTotalList(null);
		
			btnOut.setVisible(false);
		}else {
			alert.setAlertType(AlertType.WARNING);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
			alert.setHeaderText("퇴근 등록 여부");
			alert.setContentText("퇴근 등록에 실패하였습니다. 다시 시도해주세요.");
			
		}
		int no = lc.staffDataList.get(0).getS_no();
		txtOutTime.setText(wdao.OutTime(no));
		txtOut.setText("퇴근 :");
		alert.showAndWait();
		
	}

	/************************
	 * 검색 대상 제어
	 ******************************************************/
	public void searchWork(ActionEvent event) {
		if (groupSearch.getSelectedToggle().getUserData().toString().equals("w_name")) {
			txtSearchName.setVisible(true);
			dpDate.setVisible(false);
		} else {
			txtSearchName.setVisible(false);
			dpDate.setVisible(true);
		}
	}

	/************************
	 * 검색 버튼
	 ***********************************************************/
	public void btnSearchAction(ActionEvent event) {
		String mode = "", word = null;

		if (event.getSource() == btnSearch) {
			mode = groupSearch.getSelectedToggle().getUserData().toString();
			if (mode.equals("w_name")) {
				if (!DataUtil.validityCheck(txtSearchName.getText(), "검색할 대상의 이름을"))
					return;
				else
					word = mode + "," + txtSearchName.getText();

			} else if (mode.equals("w_go")) {
				if (!DataUtil.validityCheck((dpDate.getValue() == null) ? "" : dpDate.getValue().toString(),
						"검색할 대상의 출근일을"))
					return;
				else
					word = mode + "," + dpDate.getValue();
			}
			workTotalList(word);
		} else if (event.getSource() == btnTotalList) {
			txtSearchName.clear();
			workTotalList(null);
		}
	}

}
