package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataUtil;
import model.MemberDAO;
import model.MemberVO;
import model.SellVO;

public class MemberTabController implements Initializable {

   @FXML
   private TableView<MemberVO> memberTableView;
   ObservableList<MemberVO> memberDataList = FXCollections.observableArrayList();

   @FXML
   private TableView<SellVO> memberSellTableView;
   ObservableList<SellVO> sellDataList = FXCollections.observableArrayList();

   @FXML
   private TextField txtMeNo;
   @FXML
   private TextField txtMeName;
   @FXML
   private TextField txtMeHP;
   @FXML
   private Button btnMeInsert;
   @FXML
   private TextField txtSearchMeNo;
   @FXML
   private Button btnSearchMeNo;
   @FXML
   private Button btnSellTotal;
   @FXML
   private Button btnMemberSerch;
   @FXML
   private Button btnMemberTotal;
   @FXML
   private TextField txtSearchMeName;
   @FXML
   private Button btnMeUpdate;
   @FXML
   private Button btnReset;
   @FXML
   private Label txtsellTotal;
   @FXML
   private Label txtsellTotal2;

   private int selectedIndex;

   private MemberDAO mdao = MemberDAO.getInstance();

   private Stage primaryStage;

   public void setPrimaryStage(Stage primaryStage) {
      this.primaryStage = primaryStage;
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {

      try {

         disable(true);
         memberTableView.setEditable(false);

         TableColumn<MemberVO, ?> colMeNo = memberTableView.getColumns().get(0);
         colMeNo.setCellValueFactory(new PropertyValueFactory<>("me_no"));

         TableColumn<MemberVO, ?> colMeName = memberTableView.getColumns().get(1);
         colMeName.setCellValueFactory(new PropertyValueFactory<>("me_name"));

         TableColumn<MemberVO, ?> colMePhone = memberTableView.getColumns().get(2);
         colMePhone.setCellValueFactory(new PropertyValueFactory<>("me_phone"));

         TableColumn<MemberVO, ?> colMeSave = memberTableView.getColumns().get(3);
         colMeSave.setCellValueFactory(new PropertyValueFactory<>("me_save"));

         memberTableView.setItems(memberDataList);
         memberTotalList(null);

         /***************** 회원의 구매내역 *************************/
         TableColumn<SellVO, ?> colMemNo = memberSellTableView.getColumns().get(0);
         colMemNo.setCellValueFactory(new PropertyValueFactory<>("me_no"));

         TableColumn<SellVO, ?> colSeName = memberSellTableView.getColumns().get(1);
         colSeName.setCellValueFactory(new PropertyValueFactory<>("se_name"));

         TableColumn<SellVO, ?> colSePrice = memberSellTableView.getColumns().get(2);
         colSePrice.setCellValueFactory(new PropertyValueFactory<>("se_price"));

         TableColumn<SellVO, ?> colSeDate = memberSellTableView.getColumns().get(3);
         colSeDate.setCellValueFactory(new PropertyValueFactory<>("se_date"));

         memberSellTableView.setItems(sellDataList);
         sellTotalList(null);

         // 회원번호 자동 부여시
         txtMeNo.setPromptText("");
         Tooltip tooltip = new Tooltip("회원번호를 클릭하면 자동으로 번호를 부여받을 수 있습니다.");
         tooltip.setFont(new Font(12));
         txtMeNo.setTooltip(tooltip);
         txtMeNo.setEditable(false);

         txtMeName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if (selectedIndex == 0) {
                  String memberNo = "";
                  try {
                     memberNo = mdao.getMemberNo();
                  } catch (Exception e) {
                     System.out.println("e = [ " + e + "]");
                  }
                  txtMeNo.setText(memberNo);
               }
            }
         });
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /************* 테이블뷰 레코드 출력( 회원의 구매내역 전체 리스트) *********************/
   public void sellTotalList(String searchWord) {
      sellDataList.removeAll(sellDataList);
      SellVO sevo = null;
      ArrayList<SellVO> list;

      try {
         list = mdao.getMemberSellTotalList(searchWord);
         int rowCount = list.size();

         for (int index = 0; index < rowCount; index++) {
            sevo = list.get(index);
            sellDataList.add(sevo);
         }
      } catch (Exception e) {
         System.out.println("e = [" + e.getMessage() + "]");
      }
   }

   /*************
    * 테이블뷰 레코드 출력( 회원 전체 리스트)
    * 
    * @param object
    *********************/
   public void memberTotalList(String searchWord) {
      memberDataList.removeAll(memberDataList);
      MemberVO mvo = null;
      ArrayList<MemberVO> list;

      try {
         list = mdao.getMemberTotalList(searchWord);
         int rowCount = list.size();

         for (int index = 0; index < rowCount; index++) {
            mvo = list.get(index);
            memberDataList.add(mvo);
         }
      } catch (Exception e) {
         System.out.println("e = [" + e.getMessage() + "]");
      }
   }

   /************* 구매목록 검색 및 전체 버튼 *********************/
   public void btnSearchAction(ActionEvent event) {

      if (event.getSource() == btnSearchMeNo) { // 검색버튼 클릭시
         if (!DataUtil.validityCheck(txtSearchMeNo.getText(), "검색할 회원번호를"))
            return;         
         else {
            sellTotalList(txtSearchMeNo.getText());
            try {
               int list = mdao.getSellTotal(Integer.parseInt(txtSearchMeNo.getText()));
               txtsellTotal2.setText("총 구매금액 : " + list);
               txtMeNo.clear();
               txtMeName.clear();
               txtMeHP.clear();
               txtSearchMeName.clear();
               txtsellTotal.setText("");

            } catch (Exception e) {
               System.out.println("e = [" + e.getMessage() + "]");
            }
         }
      } else if (event.getSource() == btnSellTotal) {
         txtSearchMeNo.clear();
         sellTotalList(null);
         txtsellTotal2.setText("");
         txtsellTotal.setText("");
      }
   }

   /************* 회원 등록 버튼 *********************/
   public void btnMemberInsert(ActionEvent event) {
      boolean success = false;
      if (!DataUtil.validityCheck(txtMeName.getText(), "회원의 성함을")) {
         txtMeName.requestFocus();
         return;
      } else if ((!DataUtil.validityCheck(txtMeHP.getText(), "회원의 전화번호를"))) {
         txtMeHP.requestFocus();
         return;
      } else if (txtMeHP.getLength() != 11) {
         Alert ale = new Alert(AlertType.WARNING);
         ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
         ale.setHeaderText(null);
         ale.setContentText("전화번호는 11자리를 맞춰주세요.");
         ale.showAndWait();
      } else {
         MemberVO mvo = new MemberVO();

         mvo.setMe_no(selectedIndex);
         mvo.setMe_name(txtMeName.getText().trim());
         mvo.setMe_phone(txtMeHP.getText().substring(0, 3) + "-" + txtMeHP.getText().substring(3, 7) + "-"
               + txtMeHP.getText().substring(7));

         try {
            success = mdao.membertInsert(mvo);
         } catch (Exception e) {
            System.out.println("e = [ " + e + " ]");
         }
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
         alert.setTitle("회원 정보 입력");
         if (success == true) {
            alert.setHeaderText("회원 정보 등록 여부");
            alert.setContentText(" [ " + txtMeName.getText() + " ] 회원 정보가 성공적으로 등록하였습니다.");
            memberTotalList(null); // 입력후 조회
            reset();
            disable(true);
         } else {
            alert.setAlertType(AlertType.WARNING);
            alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
            alert.setHeaderText("회원 정보 등록 여부");
            alert.setContentText("회원 정보 등록에 문제가 있어 등록을 완료하지 못하였습니다.");
         }
         alert.showAndWait();
      }
   }

   public void reset() {
      selectedIndex = 0;
      memberTableView.getSelectionModel().select(null);

      sellTotalList(null);
      memberTotalList(null);

      txtMeNo.clear();
      txtMeName.clear();
      txtMeHP.clear();
      txtSearchMeNo.clear();
      txtSearchMeName.clear();
      txtsellTotal2.setText("");
      txtsellTotal.setText("");
   }

   public void disable(boolean idDisable) {
      btnMeInsert.setDisable(!idDisable);
      btnMeUpdate.setDisable(idDisable);
   }

   /**********************************************************************************************************************************************************/

   /************* 회원 테이블 더블 클릭 시 선택된 회원 구매정보 출력 **********************/
   public void memberTable(MouseEvent event) {
      if (event.getClickCount() == 2) {

         MemberVO mvo = memberTableView.getSelectionModel().getSelectedItem();
         if (mvo != null) {
            selectedIndex = mvo.getMe_no();
            String selectedMe_no = String.valueOf(mvo.getMe_no());
            String selectedMe_name = mvo.getMe_name();
            String selectedMe_phone = mvo.getMe_phone();

            txtMeNo.setText(selectedMe_no);
            txtMeName.setText(selectedMe_name);
            txtMeHP.setText(selectedMe_phone.substring(0, 3) + selectedMe_phone.substring(4, 8)
                  + selectedMe_phone.substring(9));

            sellTotalList(txtMeNo.getText());
            disable(false);
            try {
               int list = mdao.getSellTotal(Integer.parseInt(txtMeNo.getText()));
               txtsellTotal.setText("총 구매금액 : " + list);

            } catch (Exception e) {
               System.out.println("e = [" + e.getMessage() + "]");
            }
         }
      }
   }

   /************* 회원 정보 검색 및 전체 버튼 **********************/
   public void btnMemberSearch(ActionEvent event) {
      if (event.getSource() == btnMemberSerch) {
         if (!DataUtil.validityCheck(txtSearchMeName.getText(), "검색할 회원의 성함을"))
            return;
         else {
            memberTotalList(txtSearchMeName.getText());
         }
      } else if (event.getSource() == btnMemberTotal) {
         txtSearchMeName.clear();
         txtsellTotal2.setText("");
         txtsellTotal.setText("");
         memberTotalList(null);
      }
   }

   /************* 회원 정보 수정 **********************/
   public void btnMemberUpdate(ActionEvent event) {
      boolean success = false;

      if (!DataUtil.validityCheck(txtMeName.getText(), "회원의 이름을 ")) {
         txtMeName.requestFocus();
         return;
      } else if (!DataUtil.validityCheck(txtMeHP.getText(), "회원의 전화번호을 ")) {
         txtMeHP.requestFocus();
         return;
      } else if (txtMeHP.getText().length() != 11) {
         Alert ale = new Alert(AlertType.WARNING);
         ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
         ale.setHeaderText(null);
         ale.setContentText("핸드폰 번호는 11자리로 맞춰주세요.");
         ale.showAndWait();
      } else {
         MemberVO mvo = new MemberVO();

         mvo.setMe_no(Integer.parseInt(txtMeNo.getText().trim()));
         mvo.setMe_name(txtMeName.getText().trim());
         mvo.setMe_phone(txtMeHP.getText().substring(0, 3) + "-" + txtMeHP.getText().substring(3, 7) + "-"
               + txtMeHP.getText().substring(7));

         try {
            success = mdao.memberUpdate(mvo);
         } catch (Exception e) {
            System.out.println("e = [ " + e + "]");
         }
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
         alert.setTitle("회원 정보 수정");
         if (success == true) {
            alert.setContentText("[ " + txtMeName.getText() + "] 회원 정보가 성공적으로 수정하였습니다.");
            memberTotalList(null);
            reset();
           // disable(true);
         } else {
            alert.setAlertType(AlertType.WARNING);
            alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
            alert.setHeaderText("회원 정보 수정 여부");
            alert.setContentText("회원 정보 수정에 문제가 있어 수정을 완료하지 못하였습니다.");
         }
         alert.showAndWait();
      }
   }

   /************* 초기화 버튼 **********************/
   public void btnAllReset(ActionEvent event) {
      reset();
      disable(true);
      
   }

   /************* 총 구매금액 **********************/

}
