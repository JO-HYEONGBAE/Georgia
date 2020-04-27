package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.DataUtil;
import model.StaffDAO;
import model.StaffVO;

public class StaffTabController implements Initializable {
   @FXML
   private ImageView imageView;
   @FXML
   private Button imageInsert;
   @FXML
   private TextField txtName;
   @FXML
   private ComboBox<String> cbJob;
   @FXML
   private TextField txtAddress;
   @FXML
   private DatePicker dpHiredate;
   @FXML
   private TextField txtBirthday;
   @FXML
   private ComboBox<String> cbGender;
   @FXML
   private ComboBox<String> cbPTime;
   @FXML
   private TextField txtHP;
   @FXML
   private ComboBox<String> cbState;
   @FXML
   private Button btnReset;
   @FXML
   private Button btnInsert;
   @FXML
   private Button btnUpdate;
   @FXML
   private TextField txtSearchName;
   @FXML
   private Button btnSearch;
   @FXML
   private Button btnTotalList;

   @FXML
   private TextField txtID;
   @FXML
   private Button btnIDCheck;
   @FXML
   private PasswordField txtPwd;

   @FXML
   private TableView<StaffVO> staffTableView;
   ObservableList<StaffVO> staffDataList = FXCollections.observableArrayList();

   private int selectedStaffIndex;

   private StaffDAO sdao = StaffDAO.getInstance();

   private Stage primaryStage;

   public void setPrimaryStage(Stage primaryStage) {
      this.primaryStage = primaryStage;
   }
   
   private File selectedFile = null;
   private String selectS_image = "";
   private String localUrl = "";
   private Image localImage;
   String pathName ="/Users/johyeongbae/Documents/Image/";
   private File dirSave = new File(pathName);

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      disable(true);
      
      try {
    	 dpHiredate.setValue(LocalDate.now());
         TableColumn<StaffVO, ?> colSNo = staffTableView.getColumns().get(0);
         colSNo.setCellValueFactory(new PropertyValueFactory<>("s_no"));

         TableColumn<StaffVO, ?> colSName = staffTableView.getColumns().get(1);
         colSName.setCellValueFactory(new PropertyValueFactory<>("s_name"));

         TableColumn<StaffVO, ?> colSJob = staffTableView.getColumns().get(2);
         colSJob.setCellValueFactory(new PropertyValueFactory<>("s_job"));

         TableColumn<StaffVO, ?> colSPtime = staffTableView.getColumns().get(3);
         colSPtime.setCellValueFactory(new PropertyValueFactory<>("s_ptime"));

         TableColumn<StaffVO, ?> colSID = staffTableView.getColumns().get(4);
         colSID.setCellValueFactory(new PropertyValueFactory<>("s_id"));

         TableColumn<StaffVO, ?> colSPWD = staffTableView.getColumns().get(5);
         colSPWD.setCellValueFactory(new PropertyValueFactory<>("s_pwd"));

         TableColumn<StaffVO, ?> colSPhone = staffTableView.getColumns().get(6);
         colSPhone.setCellValueFactory(new PropertyValueFactory<>("s_phone"));

         TableColumn<StaffVO, ?> colSAddress = staffTableView.getColumns().get(7);
         colSAddress.setCellValueFactory(new PropertyValueFactory<>("s_address"));

         TableColumn<StaffVO, ?> colSHiredate = staffTableView.getColumns().get(8);
         colSHiredate.setCellValueFactory(new PropertyValueFactory<>("s_hiredate"));

         TableColumn<StaffVO, ?> colSBirth = staffTableView.getColumns().get(9);
         colSBirth.setCellValueFactory(new PropertyValueFactory<>("s_birth"));

         TableColumn<StaffVO, ?> colSGender = staffTableView.getColumns().get(10);
         colSGender.setCellValueFactory(new PropertyValueFactory<>("s_gender"));

         TableColumn<StaffVO, ?> colSState = staffTableView.getColumns().get(11);
         colSState.setCellValueFactory(new PropertyValueFactory<>("s_state"));
         
         TableColumn<StaffVO, ?> colSImage = staffTableView.getColumns().get(12);
         colSImage.setCellValueFactory(new PropertyValueFactory<>("s_image"));
         
         //아이디 미체크시 등록불가          
         Tooltip tooltip = new Tooltip("ID 중복체크 후 등록할 수 있습니다.");
         tooltip.setFont(new Font(12));
         btnIDCheck.setTooltip(tooltip);
         btnIDCheck.setDisable(false);

         staffTableView.setItems(staffDataList);
         staffTotalList(null);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /************* 테이블뷰 레코드 출력( 직원 전체 리스트) *********************/
   public void staffTotalList(String searchWord) {
      staffDataList.removeAll(staffDataList);
      StaffVO svo = null;
      ArrayList<StaffVO> list;

      try {
         list = sdao.getStaffTotalList(searchWord);
         int rowCount = list.size();

         for (int index = 0; index < rowCount; index++) {
            svo = list.get(index);
            staffDataList.add(svo);
         }
      } catch (Exception e) {
         System.out.println("e = [" + e.getMessage() + "]");
      }
   }

   /************* 직원 등록 *********************/
   public void btnStaffInsert(ActionEvent event) {
      
      boolean success = false;
      if (!DataUtil.validityCheck(txtName.getText(), "직원이름을 ")) {
    	  txtName.requestFocus();
         return;
         }
      else if (!DataUtil.validityCheck(cbJob.getValue(), "직급을 "))
         
    	  return;
      else if (!DataUtil.validityCheck(txtAddress.getText(), "주소를 ")) {
    	  	txtAddress.requestFocus();
    	  return;
      }
      else if (!DataUtil.validityCheck(txtBirthday.getText(), "생년월일을 ")) {
    	  txtBirthday.requestFocus();
    	  return;
    	  }
      else if(txtBirthday.getText().length()!=8) {
    	  Alert ale = new Alert(AlertType.WARNING);
    	  ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
    	  ale.setHeaderText(null);
    	  ale.setContentText("생년월일은 8자리로 맞춰주세요.");
    	  ale.showAndWait();
      }
      else if (!DataUtil.validityCheck(cbGender.getValue(), "성별을 "))
         return;
      else if (!DataUtil.validityCheck(cbPTime.getValue(), "근무파트를 "))
         return;
      else if (!DataUtil.validityCheck(txtHP.getText(), "핸드폰번호를 ")) {
    	  txtHP.requestFocus();
         return;
      }
      else if(txtHP.getText().length()!=11) {
    	  Alert ale = new Alert(AlertType.WARNING);
    	  ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
    	  ale.setHeaderText(null);
    	  ale.setContentText("핸드폰 번호는 11자리로 맞춰주세요.");
    	  ale.showAndWait();
      }
      else if (!DataUtil.validityCheck(cbState.getValue(), "근무상태를 "))
         return;
      else if (!DataUtil.validityCheck(txtID.getText(), "ID를 ")) {
    	  txtID.requestFocus();
         return;
      }
      else if (!DataUtil.validityCheck(txtPwd.getText(), "비밀번호를 ")) {
    	  txtPwd.requestFocus();
         return;
      }
      else {
         StaffVO svo = new StaffVO();

         svo.setS_name(txtName.getText().trim());
         svo.setS_job(cbJob.getValue());
         svo.setS_ptime(cbPTime.getValue());
         svo.setS_id(txtID.getText().trim());
         svo.setS_pwd(txtPwd.getText().trim());
         svo.setS_phone(txtHP.getText().substring(0,3)+"-"+txtHP.getText().substring(3,7)+"-"+txtHP.getText().substring(7));
         svo.setS_address(txtAddress.getText().trim());
         svo.setS_birth(txtBirthday.getText().substring(0,4)+"-"+txtBirthday.getText().substring(4,6)+"-"+txtBirthday.getText().substring(6));
         svo.setS_gender(cbGender.getValue());
         svo.setS_state(cbState.getValue());

         try {
            String S_image = imageSave(selectedFile);
            svo.setS_image(S_image);
            success = sdao.staffInsert(svo);

         } catch (Exception e) {
            System.out.println("e = [ " + e + " ]");
         }
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
         alert.setTitle("직원 정보 입력");
         if (success == true) {
            alert.setHeaderText("직원 정보 등록 여부");
            alert.setContentText(" [ " + txtName.getText() + " ] 직원 정보가 성공적으로 등록하였습니다.");
            staffTotalList(null);
            reset();
         } else {
            alert.setAlertType(AlertType.WARNING);
            alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
            alert.setHeaderText("직원 정보 등록 여부");
            alert.setContentText("직원 정보 등록에 문제가 있어 등록을 완료하지 못하였습니다.");
         }
         alert.showAndWait();
      }
   }

   /************* 검색, 전체 버튼 *********************/
   public void btnSearchAction(ActionEvent event) {
      if (event.getSource() == btnSearch) { // 검색버튼 클릭시
         if (!DataUtil.validityCheck(txtSearchName.getText(), "검색할 직원의 성함을"))
            return;
         else
            staffTotalList(txtSearchName.getText());
      } else if (event.getSource() == btnTotalList) { // 전체 버튼 클릭시
         txtSearchName.clear();
         staffTotalList(null);
      }
   }

   /************** 직원 테이블뷰 더블클릭 선택시 내용 보여주기 ****************/
   public void staffTableView(MouseEvent event) {
      if (event.getClickCount() == 2) {
         StaffVO selectStaff = staffTableView.getSelectionModel().getSelectedItem();
         if (selectStaff != null) {
            selectedStaffIndex = selectStaff.getS_no();
            String selectedS_name = selectStaff.getS_name();
            String selectedS_job = selectStaff.getS_job();
            String selectedS_ptime = selectStaff.getS_ptime();
            String selectedS_id = selectStaff.getS_id();
            String selectedS_pwd = selectStaff.getS_pwd();
            String selectedS_phone = selectStaff.getS_phone();
            String selectedS_address = selectStaff.getS_address();
            String selectedS_hiredate = selectStaff.getS_hiredate();
            String selectedS_birth = selectStaff.getS_birth();
            String selectedS_gender = selectStaff.getS_gender();
            String selectedS_state = selectStaff.getS_state();

            txtName.setText(selectedS_name);
            cbJob.setValue(selectedS_job);
            cbPTime.setValue(selectedS_ptime);
            txtID.setText(selectedS_id);
            txtPwd.setText(selectedS_pwd);
            txtHP.setText(selectedS_phone.substring(0,3)+selectedS_phone.substring(4,8)+selectedS_phone.substring(9));
            txtAddress.setText(selectedS_address);
            dpHiredate.setValue(LocalDate.parse(selectedS_hiredate.substring(0,10)));
            txtBirthday.setText(selectedS_birth.substring(0,4)+selectedS_birth.substring(5,7)+selectedS_birth.substring(8));
            cbGender.setValue(selectedS_gender);
            cbState.setValue(selectedS_state);

            selectS_image = selectStaff.getS_image();
            if (selectS_image != null) {
               localUrl = "file:/Users/johyeongbae/Documents/Image\\" + selectS_image;
            } else {
               localUrl = "/image/default.png";
            }
            localImage = new Image(localUrl);
            imageView.setImage(localImage);
            
            editable(false);
            disable(false);   

         }
      }
   }

   /************** 직원 이미지 선택 *********/
   public void btnImageInsert(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));

      File defalutDirectory = new File("/Users/");
      fileChooser.setInitialDirectory(defalutDirectory);

      try {
         selectedFile = fileChooser.showOpenDialog(primaryStage);
         if (selectedFile != null) {
            localUrl = selectedFile.toURI().toString();
            localImage = new Image(localUrl);
            imageView.setImage(localImage);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /************** 직원 이미지 저장 *********/
   private String imageSave(File file) {
      BufferedInputStream bis = null;
      BufferedOutputStream bos = null;
      int data = -1;
      String S_image = null;

      try {
         File dirMake = new File(dirSave.getAbsolutePath());
         if (!dirMake.exists()) {
            dirMake.mkdir();
         }
         S_image = "Staff" + System.currentTimeMillis() + "_" + file.getName();
         bis = new BufferedInputStream(new FileInputStream(file));
         bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + S_image));

         while ((data = bis.read()) != -1) {
            bos.write(data);
            bos.flush();
         }
      } catch (Exception e) {
         e.getMessage();
      } finally {
         try {
            if (bos != null) {
               bos.close();
            }
            if (bis != null) {
               bis.close();
            }
         } catch (IOException e) {
            e.getMessage();
         }
      }
      return S_image;
   }
   
   /************** 아이디 중복 체크 *********/
   public void btnIDCheck(ActionEvent event) {
      String searchId = "";
      boolean searchResult = true;
      
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
      alert.setTitle("아이디 중복 검사");
      alert.setHeaderText(null);
      
      try {
         searchId = txtID.getText().trim();
         
         if(!DataUtil.validityCheck(searchId, "아이디를 ")) {
            txtID.requestFocus();
            return;
         }else {
            searchResult = sdao.getStaffIdOverlap(searchId);
            if(!searchResult) {
               alert.setContentText(searchId + "를 사용할 수 있습니다.");
               txtID.setEditable(false);   
               btnIDCheck.setDisable(true);   
               btnInsert.setDisable(false);
            }else {
               alert.setContentText(searchId + "를 사용할 수 없습니다.");
               txtID.clear();
               txtID.requestFocus();
            }
            alert.show();
         }
      }catch(Exception e) {
         alert.setAlertType(AlertType.ERROR);
         alert.getDialogPane().setGraphic(new ImageView("/image/dialog-error.png"));
         alert.setTitle("아이디 중복 검사 오류");
         alert.setContentText("아이디 중복 검사에 오류가 발생하였습니다.");
         alert.show();
      }
   }

   /************** 직원 정보 수정 *********/
   public void btnUpdate(ActionEvent event) {
      boolean success = false;
      String date=String.valueOf(dpHiredate.getValue());

      //boolean success = false;
      if (!DataUtil.validityCheck(txtName.getText(), "직원이름을 ")) {
    	  txtName.requestFocus();
         return;
         }
      else if (!DataUtil.validityCheck(cbJob.getValue(), "직급을 "))
         
    	  return;
      else if (!DataUtil.validityCheck(txtAddress.getText(), "주소를 ")) {
    	  	txtAddress.requestFocus();
    	  return;
      }
      else if (!DataUtil.validityCheck(txtBirthday.getText(), "생년월일을 ")) {
    	  txtBirthday.requestFocus();
    	  return;
    	  }
      else if(txtBirthday.getText().length()!=8) {
    	  Alert ale = new Alert(AlertType.WARNING);
    	  ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
    	  ale.setHeaderText(null);
    	  ale.setContentText("생년월일은 8자리로 맞춰주세요.");
    	  ale.showAndWait();
      }
      else if (!DataUtil.validityCheck(cbGender.getValue(), "성별을 "))
         return;
      else if (!DataUtil.validityCheck(cbPTime.getValue(), "근무파트를 "))
         return;
      else if (!DataUtil.validityCheck(txtHP.getText(), "핸드폰번호를 ")) {
    	  txtHP.requestFocus();
         return;
      }
      else if(txtHP.getText().length()!=11) {
    	  Alert ale = new Alert(AlertType.WARNING);
    	  ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
    	  ale.setHeaderText(null);
    	  ale.setContentText("핸드폰 번호는 11자리로 맞춰주세요.");
    	  ale.showAndWait();
      }
      else if (!DataUtil.validityCheck(cbState.getValue(), "근무상태를 "))
         return;
      else if (!DataUtil.validityCheck(txtID.getText(), "ID를 ")) {
    	  txtID.requestFocus();
         return;
      }
      else if (!DataUtil.validityCheck(txtPwd.getText(), "비밀번호를 ")) {
    	  txtPwd.requestFocus();
         return;
      }
      else {
         StaffVO svo = new StaffVO();
         
         svo.setS_no(selectedStaffIndex);
         svo.setS_name(txtName.getText().trim());
         svo.setS_job(cbJob.getValue().trim());      
         svo.setS_ptime(cbPTime.getValue().trim());   
         svo.setS_pwd(txtPwd.getText().trim());
         svo.setS_phone(txtHP.getText().substring(0,3)+"-"+txtHP.getText().substring(3,7)+"-"+txtHP.getText().substring(7));
         svo.setS_address(txtAddress.getText().trim());   
         svo.setS_birth(txtBirthday.getText().substring(0,4)+"-"+txtBirthday.getText().substring(4,6)+"-"+txtBirthday.getText().substring(6));
         svo.setS_gender(cbGender.getValue().trim());   
         svo.setS_state(cbState.getValue().trim());   
         svo.setS_hiredate(dpHiredate.getValue().toString()); 
         
         try {
            if(selectedFile != null) {
               if(selectS_image != null) 
            	   imageDelete(selectS_image);
               String fileName = imageSave(selectedFile);
               svo.setS_image(fileName);
            }else {
               svo.setS_image(selectS_image);
            }
            success = sdao.staffUpdate(svo);         
         }catch (Exception e) {
            System.out.println("e = [ "+e+ "]");
         }
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
         alert.setTitle("직원 정보 수정");
         if(success == true) {
            alert.setContentText("[ "+txtName.getText()+"] 직원 정보가 성공적으로 수정하였습니다.");
            staffTotalList(null);
            reset();            
         }else {
            alert.setAlertType(AlertType.WARNING);
            alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
            alert.setHeaderText("직원 정보 수정 여부");
            alert.setContentText("직원 정보 수정에 문제가 있어 수정을 완료하지 못하였습니다.");
         }
         alert.showAndWait();
      }
   
   }
   /************** 이미지 삭제 메소드 *********/
   private boolean imageDelete(String S_image) {
      boolean result = false;
      try {
         File fileDelete = new File (dirSave.getAbsolutePath() + "\\" + S_image);
         
         if(fileDelete.exists() && fileDelete.isFile()) {
            result = fileDelete.delete();
         }
      }catch (Exception ie) {
         System.out.println("ie = ["+ie.getMessage()+"]");
         result = false;
      }
      return result;
      
   }
   
   /************** editable() 텍스트 필드 입력가능 여부를 지정 할 메서드.  *********/
   public void editable(boolean idDisable) {
      txtID.setEditable(idDisable);
   }
   
   /************** 버튼 제어 메서드 *********/
   public void disable(boolean idDisable) {
      btnIDCheck.setDisable(!idDisable);
      if(selectedStaffIndex != 0)btnInsert.setDisable(true);
      else btnInsert.setDisable(!btnIDCheck.isDisable());
      
      btnUpdate.setDisable(idDisable);
   }
   

   /************** 초기화 버튼 *********/
   public void btnResetbtn(ActionEvent event) {
      reset();
      editable(true);
      disable(true);
   }

   public void reset() {
      selectedStaffIndex = 0;

      txtName.clear();
      cbJob.getSelectionModel().clearSelection();
      cbPTime.getSelectionModel().clearSelection();
      txtID.clear();
      txtPwd.clear();
      txtHP.clear();
      txtAddress.clear();
      dpHiredate.setValue(LocalDate.now());
      //dpHiredate.getEditor().clear();
      txtBirthday.clear();
      cbGender.getSelectionModel().clearSelection();
      cbState.getSelectionModel().clearSelection();
      staffTableView.getSelectionModel().select(null);
      selectedFile =null;
      selectS_image="";
      editable(true);
      disable(true);
     
      if (selectedStaffIndex == 0) {
         // 기본 이미지
         localUrl = "/image/default.png";
         localImage = new Image(localUrl);
         imageView.setImage(localImage);
      }
      
   }

}
