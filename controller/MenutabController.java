package controller;

import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import model.MemberVO;
import model.MenuDAO;
import model.MenuVO;
import model.SellVO;

public class MenuTabController implements Initializable {

   @FXML
   private TextField txtPayment;
   @FXML
   private TextField txtRecive;
   @FXML
   private TextField txtChange;
   @FXML
   private TextField txtSave;
   @FXML
   private Button btn9;
   @FXML
   private Button btn8;
   @FXML
   private Button btn7;
   @FXML
   private Button btn6;
   @FXML
   private Button btn5;
   @FXML
   private Button btn4;
   @FXML
   private Button btn3;
   @FXML
   private Button btn2;
   @FXML
   private Button btn1;
   @FXML
   private Button btn0;
   @FXML
   private Button btn000;
   @FXML
   private Button btnMillon;
   @FXML
   private Button btnThousand;
   @FXML
   private Button btnHundred;
   @FXML
   private Button btnRemove;
   @FXML
   private Button btnRefund;
   @FXML
   private Button btnSellTableSearch;
   @FXML
   private Button btnStockTableSearch;
   @FXML
   private Button btnMenuModify;
   @FXML
   private Button btnPayment;
   @FXML
   private Button btnReset;
   @FXML
   private TableView<MenuVO> menuTableView;
   @FXML
   private TableView<SellVO> sellTableView;
   private LoginController lc = LoginController.getInstance();
   // @FXML private TableColumn<SellVO, String> no;
   // @FXML private TableColumn<SellVO, String> name;
   // @FXML private TableColumn<SellVO, String> price;
   // @FXML private TableColumn<SellVO, String> amount;
   private static MenuTabController instance = null;

   public static MenuTabController getInstance() {
      if (instance == null) {
         instance = new MenuTabController();
      }
      return instance;
   }

   ObservableList<MenuVO> menuDataList = FXCollections.observableArrayList();
   ObservableList<SellVO> sellDataList = FXCollections.observableArrayList();

   private int payment;
   private int total;
   private MenuDAO mdao = MenuDAO.getInstance();

   private Stage primaryStage;
   public String zeroToNine;

   public void setPrimaryStage(Stage primaryStage) {
      this.primaryStage = primaryStage;
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // TODO Auto-generated method stub
      instance = this;

      // menuTableView.setEditable(false);
      TableColumn<MenuVO, ?> comNo = menuTableView.getColumns().get(0);
      comNo.setCellValueFactory(new PropertyValueFactory<>("m_no"));

      TableColumn<MenuVO, ?> comName = menuTableView.getColumns().get(1);
      comName.setCellValueFactory(new PropertyValueFactory<>("m_name"));

      TableColumn<MenuVO, ?> comPrice = menuTableView.getColumns().get(2);
      comPrice.setCellValueFactory(new PropertyValueFactory<>("m_price"));

      TableColumn<MenuVO, ?> comAmount = menuTableView.getColumns().get(3);
      comAmount.setCellValueFactory(new PropertyValueFactory<>("m_amount"));
      menuTableView.setItems(menuDataList);

      TableColumn<SellVO, ?> cosNo = sellTableView.getColumns().get(0);
      cosNo.setCellValueFactory(new PropertyValueFactory<>("se_no"));
      TableColumn<SellVO, ?> cosName = sellTableView.getColumns().get(1);
      cosName.setCellValueFactory(new PropertyValueFactory<>("se_name"));
      TableColumn<SellVO, ?> cosPrice = sellTableView.getColumns().get(2);
      cosPrice.setCellValueFactory(new PropertyValueFactory<>("se_price"));
      TableColumn<SellVO, ?> cosAmount = sellTableView.getColumns().get(3);
      cosAmount.setCellValueFactory(new PropertyValueFactory<>("se_amount"));
      sellTableView.setItems(sellDataList);

      menuTotalList(null);
      txtPayment.setEditable(false);
      txtRecive.setEditable(false);
      txtChange.setEditable(false);
      btnPayment.setDisable(true);
      if(lc.staffDataList.get(0).getS_job().equals("직원")) {
    	  btnMenuModify.setVisible(false);
    	  btnStockTableSearch.setVisible(false);
      }

   }

   /*
    * public void sellTotalList(String searchWord) {
    * sellDataList.removeAll(sellDataList);
    * 
    * SellVO sVo = null; ArrayList<SellVO> list; try { list =
    * mdao.getSellDayList(); int rowCount = list.size();
    * 
    * for (int i = 0; i < rowCount; i++) { sVo = list.get(i);
    * sellDataList.add(sVo); } } catch (Exception e) { e.printStackTrace(); } }
    */
   public void menuTotalList(String searchWord) {
      menuDataList.removeAll(menuDataList);

      MenuVO mVo = null;
      ArrayList<MenuVO> list;
      try {
         list = mdao.getMenuTotalList();
         int rowCount = list.size();

         for (int i = 0; i < rowCount; i++) {
            mVo = list.get(i);
            menuDataList.add(mVo);
         }
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("dd");
      }
   }

   public void sellTableView(MouseEvent event) {
      if (event.getClickCount() == 2) {
         SellVO selectSell = sellTableView.getSelectionModel().getSelectedItem();
         if (selectSell != null) {
            sellDataList.remove(selectSell);
            payment -= selectSell.getSe_price();
            txtPayment.setText(String.valueOf(payment));
            txtChange.setText("0");
            txtRecive.clear();
         }
      }

   }

   public void btn1(ActionEvent event) {
      zeroToNine = btn1.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn2(ActionEvent event) {
      zeroToNine = btn2.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn3(ActionEvent event) {
      zeroToNine = btn3.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn4(ActionEvent event) {
      zeroToNine = btn4.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn5(ActionEvent event) {
      zeroToNine = btn5.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn6(ActionEvent event) {
      zeroToNine = btn6.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn7(ActionEvent event) {
      zeroToNine = btn7.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn8(ActionEvent event) {
      zeroToNine = btn8.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {
         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn9(ActionEvent event) {
      zeroToNine = btn9.getText();
      txtRecive.appendText(zeroToNine);
      if (!txtPayment.getText().equals("")) {

         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn0(ActionEvent event) {
      if (txtRecive.getLength() != 0) {
         zeroToNine = btn0.getText();
         txtRecive.appendText(zeroToNine);

         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }
   }

   public void btn000(ActionEvent event) {
      if (txtRecive.getLength() != 0) {
         zeroToNine = btn000.getText();
         txtRecive.appendText(zeroToNine);

         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      }

   }

   public void btnMillon(ActionEvent event) {
      try {
         if (txtRecive.getText().equals("")) {
            txtRecive.setText("0");
         }
         int s = 0;
         s = Integer.parseInt(txtRecive.getText()) + 10000;
         zeroToNine = String.valueOf(s);
         txtRecive.setText(zeroToNine);

         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      } catch (NumberFormatException e) {
         e.printStackTrace();
      }
   }

   public void btnThousand(ActionEvent event) {

      try {
         if (txtRecive.getText().equals("")) {
            txtRecive.setText("0");
         }
         int s = 0;
         s = Integer.parseInt(txtRecive.getText()) + 1000;
         zeroToNine = String.valueOf(s);
         txtRecive.setText(zeroToNine);

         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      } catch (NumberFormatException e) {
         e.printStackTrace();
      }
   }

   public void btnHundred(ActionEvent event) {

      try {
         if (txtRecive.getText().equals("")) {
            txtRecive.setText("0");
         }
         int s = 0;
         s = Integer.parseInt(txtRecive.getText()) + 100;
         zeroToNine = String.valueOf(s);
         txtRecive.setText(zeroToNine);

         int change1 = Integer.parseInt(txtPayment.getText());
         int change2 = Integer.parseInt(txtRecive.getText());
         change1 = change2 - change1;
         txtChange.setText(String.valueOf(change1));

      } catch (NumberFormatException e) {
         e.printStackTrace();
      }
   }

   public void btnRemove(ActionEvent event) {
      try {
         if (txtRecive.getLength() != 0) {
            txtRecive.setText(txtRecive.getText(0, txtRecive.getLength() - 1));

            if (!txtRecive.getText().equals("")) {
               int change1 = Integer.parseInt(txtPayment.getText());
               int change2 = Integer.parseInt(txtRecive.getText());
               change1 = change2 - change1;
               txtChange.setText(String.valueOf(change1));
            } else
               txtChange.setText("0");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void btnRefund(ActionEvent event) {
      txtRecive.clear();
      txtChange.setText("0");
      ;

   }

   public void btnSellTableSearch() {
      try {
         Stage sellTab = new Stage(StageStyle.DECORATED);
         sellTab.initModality(Modality.WINDOW_MODAL);

         sellTab.initOwner(primaryStage);
         sellTab.setTitle("판매 목록 화면");

         FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sellList.fxml"));
         Parent parent = loader.load();
         SellController controller = loader.getController();
         controller.setDialog(sellTab);
         Scene scene = new Scene(parent);
         sellTab.setScene(scene);
         sellTab.setResizable(false);
         sellTab.show();

      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public void btnStockTableSearch() {
      try {
         Stage stockTab = new Stage(StageStyle.DECORATED);
         stockTab.initModality(Modality.WINDOW_MODAL);
         stockTab.initOwner(primaryStage);
         stockTab.setTitle("재고 현황 화면");

         FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/stockList.fxml"));
         Parent parent = loader.load();
         StockController controller = loader.getController();
         controller.setDialog(stockTab);
         Scene scene = new Scene(parent);
         stockTab.setScene(scene);
         stockTab.setResizable(false);
         stockTab.show();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public void btnMenuModify(ActionEvent event) throws Exception {
      Stage menuSet = new Stage(StageStyle.DECORATED);
      menuSet.initModality(Modality.WINDOW_MODAL);
      menuSet.initOwner(primaryStage);
      menuSet.setTitle("메뉴 수정 화면");

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuList.fxml"));
      Parent parent = loader.load();
      MenuController controller = loader.getController();
      controller.setDialog(menuSet);
      Scene scene = new Scene(parent);
      menuSet.setScene(scene);
      menuSet.setResizable(false);
      menuSet.show();

   }

   /********************** 메뉴 선택 *****************************/
   public void menuTableView(MouseEvent event) {

      SellVO se = new SellVO();
      MenuVO selectMenu = menuTableView.getSelectionModel().getSelectedItem();
      boolean success =false;
      if(selectMenu==null) {
    	  success=true;
    	
    	  	return;
      }
      try {
         if (selectMenu.getM_amount() > 0&&!success) {
            se.setSe_no(selectMenu.getM_no());
            se.setSe_name(selectMenu.getM_name());
            se.setSe_price(selectMenu.getM_price());
            se.setSe_amount(1);
            sellDataList.add(se);

            btnPayment.setDisable(false);

            payment += se.getSe_price();
            txtPayment.setText(String.valueOf(payment));
            txtChange.setText("0");
            txtRecive.clear();
            txtSave.setText("1");
         } else {
            Alert ale = new Alert(AlertType.WARNING);
            ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
            ale.setHeaderText(null);
            ale.setContentText(selectMenu.getM_name() + "의 재고가 부족합니다.");
            ale.showAndWait();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /*
    * no.setText(String.valueOf(selectMenu.getM_no()));
    * name.setText(selectMenu.getM_name());
    * price.setText(String.valueOf(selectMenu.getM_price()));
    * amount.setText(String.valueOf(selectMenu.getM_amount()));
    * 
    * menuTableView.addEventHandler(MouseEvent.MOUSE_CLICKED,new
    * EventHandler<MouseEvent>() {
    * 
    * public void handle(MouseEvent event) { sellTableView.getItems().add(new
    * SellVO (new SimpleStringProperty(no.getText()),new
    * SimpleStringProperty(name.getText()), new
    * SimpleStringProperty(price.getText()),new
    * SimpleStringProperty(amount.getText()))); } });
    * 
    * no.setCellValueFactory(cellData -> cellData.getValue().getNo());
    * name.setCellValueFactory(cellData -> cellData.getValue().getName());
    * price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
    * amount.setCellValueFactory(cellData -> cellData.getValue().getAmount());
    */

   // sellTableView.getItems().add(new (new int(colNo.getText()), new
   // SimpleStringProperty(addressField.getText()),
   // new SimpleStringProperty(genderField.getText()), new
   // SimpleIntegerProperty(Integer.parseInt(classField.getText()))));

   // editable(false);
   // disable(false);

   /*************************
    * 결제 버튼
    ************************************************/
   public void btnPayment(ActionEvent event) throws Exception {

      if (!DataUtil.validityCheck(txtPayment.getText(), "결제 금액을"));
      else if (!DataUtil.validityCheck(txtSave.getText(), "적립 번호를")); //////////////////// 적립번호
      else  if(!mdao.getMeNo(Integer.parseInt(txtSave.getText()))) { 
			Alert alert =new Alert(AlertType.WARNING);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
			alert.setHeaderText(null);
			alert.setContentText("회원 번호가없습니다.");
			alert.showAndWait();
			return;
		}
      else {
         try {
            if (!(txtRecive.getText()).equals("")) {
               int recive5 = Integer.parseInt(txtRecive.getText());
               int payment5 = Integer.parseInt(txtPayment.getText());
               if (recive5 >= payment5) {

                  Object btn = event.getSource();
                  Stage dialog = new Stage(StageStyle.TRANSPARENT);
                  dialog.initModality(Modality.APPLICATION_MODAL);
                  dialog.initOwner(primaryStage);
                  dialog.setTitle("결제 화면");

                  Parent parent = FXMLLoader.load(getClass().getResource("/view/payment.fxml"));
                  Label txtPay = (Label) parent.lookup("#txtPay");
                  Button btnOk = (Button) parent.lookup("#btnOk");
                  Button btnNo = (Button) parent.lookup("#btnNo");
                  DecimalFormat df = new DecimalFormat("###,###");
                  CheckBox cbMeSave = (CheckBox) parent.lookup("#cbMeSave");
                  TextField txtMeSave = (TextField) parent.lookup("#txtMeSave");
                  if(txtSave.getText().equals("1")) {
                	  
                	  cbMeSave.setVisible(false);
                	  txtMeSave.setVisible(false);
                  }
                  txtMeSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
                	  public void handle(MouseEvent e) {
                		  txtMeSave.clear();
                		  
                	  }
                  });
                  Label laMeSave = (Label) parent.lookup("#laMeSave");
                  txtPay.setText(df.format(Integer.parseInt(txtPayment.getText())));
                  
                  cbMeSave.setOnAction(new EventHandler<ActionEvent>() {
                      public void handle(ActionEvent event) {
                         try {
                            int save = mdao.getMemberSave(Integer.parseInt(txtSave.getText()));
                            laMeSave.setText("사용가능한 적립금액: " + String.valueOf(save) + "원");
                         } catch (Exception e) {
                            System.out.println("e = [" + e.getMessage() + "]");
                         }
                      }
                   });
                  //if (!(txtMeSave.getText().equals("0"))) {
                 /*
                  btnOk.setOnAction(new EventHandler<ActionEvent>() {
                      public void handle(ActionEvent event) {
                         SellVO sVo = new SellVO();
                         boolean success = false;
                         for (int i = 0; i < sellDataList.size(); i++) {
                            sVo.setSe_name(sellDataList.get(i).getSe_name());
                            sVo.setSe_price(sellDataList.get(i).getSe_price());
                            sVo.setSe_amount(sellDataList.get(i).getSe_amount());
                            sVo.setM_no(sellDataList.get(i).getSe_no());
                            sVo.setMe_no(Integer.parseInt(txtSave.getText()));
                            try {
                               success = mdao.sellInsert(sVo);

                            } catch (Exception e) {
                               e.printStackTrace();
                            }

                         }
                         Alert alert = new Alert(AlertType.INFORMATION);
                         alert.setTitle("판매 정보 입력");
                         if (success == true) {
                            alert.setHeaderText("판매 정보 등록 여부");
                            alert.setContentText(" 판매가 정상적으로 처리되었습니다");
                            menuTotalList(null);

                            dialog.close();
                            txtRecive.clear();
                            txtPayment.setText("0");
                            txtChange.setText("0");
                            txtSave.setText("1");
                            payment = 0;
                            txtRecive.setPromptText("0");
                            sellDataList.clear();
                            sellTableView.setItems(sellDataList);
                            btnPayment.setDisable(true);
                         } else {
                            alert.setAlertType(AlertType.WARNING);
                            alert.setHeaderText("회원 정보가 없습니다.");

                         }

                         alert.showAndWait();
                         dialog.close();

                      }
                   });
                   */
                  btnOk.setOnAction(new EventHandler<ActionEvent>() {
                     public void handle(ActionEvent event) {
                        if(!DataUtil.validityCheck(txtMeSave.getText(), "적립금액을 "))return;
                    	
                        
                        SellVO sVo = new SellVO();
                        SellVO sVo2= new SellVO();
                        MemberVO mVo = new MemberVO();
                        boolean success = false;
                        boolean success2 =false;
                        try {
                        	int save = mdao.getMemberSave(Integer.parseInt(txtSave.getText()));
                        	int use = Integer.parseInt(txtMeSave.getText());
                        	int pay= Integer.parseInt(txtPayment.getText());
                        	if(save>=use&& use!=0 && pay>=use) {
                        		int useSave = (save - use);
                        		
                        		mVo.setMe_save(useSave);
                        		mVo.setMe_no(Integer.parseInt(txtSave.getText().trim()));
                        		success2 = true;
                        		
                        	}else if(save <use &&!success2){
                        		Alert ale = new Alert(AlertType.WARNING);
                        		ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
                        		ale.setHeaderText(null);
                        		ale.setContentText("보유한 적립금액이 부족합니다. 재확인 해주세요.");
                        		ale.showAndWait();
                        		
                        		return;
                        	}else if(pay<use) {
                        		Alert ale = new Alert(AlertType.WARNING);
                        		ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
                        		ale.setHeaderText(null);
                        		ale.setContentText("사용 금액보다 적립금이 큽니다.");
                        		ale.showAndWait();
                        		
                        		return;
                        	}
                        }catch(Exception e) {
                        	e.printStackTrace();
                        }if(txtMeSave.getText().equals("0")) {
                        for (int i = 0; i < sellDataList.size(); i++) {
                           sVo.setSe_name(sellDataList.get(i).getSe_name());
                           sVo.setSe_price(sellDataList.get(i).getSe_price());
                           sVo.setSe_amount(sellDataList.get(i).getSe_amount());
                           sVo.setM_no(sellDataList.get(i).getSe_no());
                           sVo.setMe_no(Integer.parseInt(txtSave.getText()));
                         try {
                        	 success = mdao.sellInsert(sVo);
                        	
                           } catch (Exception e) {
                              e.printStackTrace();
                           } 

                        }}else {
                        for(int i=0;i<sellDataList.size();i++) {
                        	sVo2.setSe_name(sellDataList.get(i).getSe_name());
                        	sVo2.setSe_price(sellDataList.get(i).getSe_price());
                            sVo2.setSe_amount(sellDataList.get(i).getSe_amount());
                            sVo2.setM_no(sellDataList.get(i).getSe_no());
                            sVo2.setMe_no(Integer.parseInt(txtSave.getText()));
                            
                            try {
                            	success=mdao.sellSaveInsert(sVo2);
                            	 if(success2) {
                            		 success2 = mdao.getMeSaveUpdate(mVo); 
                            	 }
                            }catch(Exception e) {
                            	e.printStackTrace();
                            }
                        }}
                        
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
                        alert.setTitle("판매 정보 입력");
                         if (success == true) {
                           alert.setHeaderText("판매 정보 등록 여부");
                           alert.setContentText(" 판매가 정상적으로 처리되었습니다");
                           menuTotalList(null);

                           dialog.close();
                           txtRecive.clear();
                           txtPayment.setText("0");
                           txtChange.setText("0");
                           txtSave.setText("1");
                           payment = 0;
                           txtRecive.setPromptText("0");
                           sellDataList.clear();
                           sellTableView.setItems(sellDataList);
                           btnPayment.setDisable(true);
                        }
                        	else {
                        
                           alert.setAlertType(AlertType.WARNING);
                           alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
                           alert.setHeaderText("회원 정보가 없습니다.");

                        }

                        alert.showAndWait();
                        dialog.close();

                     }
                  });
                  btnNo.setOnAction(new EventHandler<ActionEvent>() {
                     public void handle(ActionEvent event) {
                        dialog.close();
                     }
                  });
                  Scene scene = new Scene(parent);

                  dialog.setScene(scene);
                  dialog.setResizable(false);
                  dialog.show();
               }else {
                  Alert ale = new Alert(AlertType.WARNING);
                  ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
                  ale.setHeaderText(null);
                  ale.setContentText("받은 금액이 부족하여 결제가 불가합니다.");
                  ale.showAndWait();
               }
            } else {
               Alert ale = new Alert(AlertType.WARNING);
               ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
               ale.setHeaderText(null);
               ale.setContentText("받은 금액이 부족하여 결제가 불가합니다.");
               ale.showAndWait();
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

      }
   }

   public void btnReset(ActionEvent event) {
      txtRecive.clear();
      txtPayment.setText("0");
      txtChange.setText("0");
      sellDataList.clear();
      payment = 0;
      sellTableView.setItems(sellDataList);
      btnPayment.setDisable(true);
      txtSave.setText("1");

   }
   
   public void txtSave(MouseEvent event) {
	   txtSave.clear();
   }
   public void disable(boolean idDisable) {

   }

   public void reset() {
      btn0.setDisable(true);
      btn1.setDisable(true);
      btn2.setDisable(true);
      btn3.setDisable(true);
      btn4.setDisable(true);
      btn5.setDisable(true);
      btn6.setDisable(true);
      btn7.setDisable(true);
      btn8.setDisable(true);
      btn9.setDisable(true);
      btn0.setDisable(true);
      btnHundred.setDisable(true);
      btnMillon.setDisable(true);
      btnThousand.setDisable(true);
      btn000.setDisable(true);
      txtSave.setText("1");
   }

   public void setOn() {
      btn0.setDisable(false);
      btn1.setDisable(false);
      btn2.setDisable(false);
      btn3.setDisable(false);
      btn4.setDisable(false);
      btn5.setDisable(false);
      btn6.setDisable(false);
      btn7.setDisable(false);
      btn8.setDisable(false);
      btn9.setDisable(false);
      btn0.setDisable(false);
      btnHundred.setDisable(false);
      btnMillon.setDisable(false);
      btnThousand.setDisable(false);
      btn000.setDisable(false);
   }
}
