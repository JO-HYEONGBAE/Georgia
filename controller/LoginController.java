package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataUtil;
import model.LoginDAO;
import model.StaffVO;

public class LoginController implements Initializable {
@FXML private TextField txtId;
@FXML private PasswordField txtPasswd;
@FXML private Button btnLogin;
@FXML private Label txtIdSearch;
@FXML private Label txtPwSearch;
@FXML private Label txtJoin;
private File selectedFile =null;
private String selectFileName="";
private String localUrl="";
private Image localImage;
String pathName ="/Users/johyeongbae/Documents/Image/";
private File dirSave = new File("/Users/");
public LoginDAO ldao=LoginDAO.getInstance();
private Stage dialog;
private static LoginController instance=null;
ObservableList<StaffVO> staffDataList = FXCollections.observableArrayList();
public static LoginController getInstance(){

	  final LoginDAO login = LoginDAO.getInstance();
	if(instance==null) {
		instance=new LoginController();

	}
	return instance;
	
}
public void setDialog(Stage dialog) {
	this.dialog=dialog;
}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub	
		instance=this;

	}
	public void loginInfo(String id) {
		staffDataList.removeAll(staffDataList);
		
		StaffVO sVo =null;
		ArrayList<StaffVO> list;
		try {
			list =ldao.loginInfo(id);
			int rowCount =list.size();
			
			for(int i =0;i<rowCount;i++) {
				sVo = list.get(i);
				staffDataList.add(sVo);
			}
					
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public void btnLogin(ActionEvent event) {
		String searchId="";
		boolean searchIdResult=true;
		String searchPw="";
		boolean searchPwResult=true;
		Alert alert =new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
		alert.setTitle("로그인 정보");
		alert.setHeaderText(null);
		try {
			searchId = txtId.getText().trim();
			searchPw = txtPasswd.getText().trim();
			if(!DataUtil.validityCheck(searchId, "아이디를")) {
				txtId.requestFocus();
				return;
				}
				else if(!DataUtil.validityCheck(searchPw,"비밀번호를")) {
					txtPasswd.requestFocus();
					return;
				}else {
				searchIdResult=ldao.getIdOverlap(searchId);
				searchPwResult=ldao.getpwOverlap(searchPw,searchId);
				if(searchIdResult&&searchPwResult) {
				
					loginInfo(searchId);
					
					Stage dialog =new Stage(StageStyle.DECORATED);
					
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
						Parent root = loader.load();
						Scene scene = new Scene(root);
						Label txtJob =(Label)root.lookup("#txtJob");
						txtJob.setText(staffDataList.get(0).getS_job());
						Label txtName =(Label)root.lookup("#txtName");
						txtName.setText(staffDataList.get(0).getS_name());
						MainController controller = loader.getController();
						controller.setPrimaryStage(dialog);
						if(txtJob.getText().equals("직원")) {
							
						}
						dialog.setScene(scene);
						dialog.show();
						this.dialog.close();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					alert.setContentText("아이디나 비밀번호가 틀렸습니다.");
				txtPasswd.clear();
				alert.show();
				}
			}
		}catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.getDialogPane().setGraphic(new ImageView("/image/dialog-error.png"));
			alert.setTitle("로그인 접속 오류");
			alert.setContentText("로그인에 하는중 오류가 발생하였습니다."
					+ "다시 시도해주세요");
			alert.show();
		}

		
	}

	
	public void txtIdSearch(MouseEvent event) {
		
		
		Stage idDialog = new Stage(StageStyle.DECORATED);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/idsearch.fxml"));
			Parent parent = loader.load();
			
			TextField txtName=(TextField)parent.lookup("#txtName");
			//txtName.setText(txtName.getText().trim());
			TextField txt1=(TextField)parent.lookup("#txt1");
			
			TextField txt2=(TextField)parent.lookup("#txt2");
			TextField txt3=(TextField)parent.lookup("#txt3");
			txt3.setOnKeyReleased(new EventHandler<Event>() {
				public void handle(Event handle) {
					if(txt3.getText().length()>4) {
						String str =txt3.getText().substring(0,4);
						txt3.setText(str);
					
					}
				}
			});
			
			txt2.setOnKeyReleased(new EventHandler<Event>() {
				public void handle(Event event) {
					if(txt2.getText().length()==4) {
						txt3.requestFocus();
					}else if (txt2.getText().length()>4) {
						String str =txt2.getText().substring(0,4);
						txt2.setText(str);
						txt3.requestFocus();
					
					}
					
				}
				
			});
			txt1.setOnKeyReleased(new EventHandler<Event>() {

		         @Override
		         public void handle(Event event) {
		            if(txt1.getText().length()==3) {
		               txt2.requestFocus();
		            }
		            else if(txt1.getText().length()>3) {
		               String str = txt1.getText().substring(0,3);
		               txt1.setText(str);
		               txt2.requestFocus();
		            }
		         }
		         
		      });
			
			
			//txtPhone.setText(txtPhone.getText().substring(0,2) + "-" +txtPhone.getText().substring(3,7)+"-"+txtPhone.getText().substring(8));
			//System.out.println(txtPhone.getText());
			//String phone=txtPhone.getText().substring(0,3)+"-"+txtPhone.getText().substring(3,7)+"-"+txtPhone.getText().substring(7,11);
			
			Button btnClose=(Button)parent.lookup("#btnClose");
			btnClose.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					idDialog.close();
				}
			});
			
			Button btnSearch=(Button)parent.lookup("#btnSearch");
			btnSearch.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if(!DataUtil.validityCheck(txtName.getText().trim(), "이름을 ")) {
						txtName.requestFocus();
						return;
					}	else if(!DataUtil.validityCheck(txt1.getText().trim(),"전화번호를")) {
						txt1.requestFocus();
						return;
					}	else if(!DataUtil.validityCheck(txt2.getText().trim(),"전화번호를")) {
						txt2.requestFocus();
						return;
					}	else if(!DataUtil.validityCheck(txt3.getText().trim(),"전화번호를")) {
						txt3.requestFocus();
						return;
					}else if(txt1.getText().length()!=3) {
						Alert ale =new Alert(AlertType.WARNING);
						ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
						ale.setContentText("전화번호를 자리에 맞춰 기입해주세요.");
						ale.showAndWait();
					}else if(txt2.getText().length()!=4) {
						Alert ale =new Alert(AlertType.WARNING);
						ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
						ale.setContentText("전화번호를 자리에 맞춰 기입해주세요.");
						ale.showAndWait();
					}else if(txt3.getText().length()!=4) {
						Alert ale =new Alert(AlertType.WARNING);
						ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
						ale.setContentText("전화번호를 자리에 맞춰 기입해주세요.");
						ale.showAndWait();
				
					}
					else {
						
						String phone=txt1.getText()+"-"+txt2.getText()+"-"+txt3.getText();
					
						boolean success=false;
						
						try {
							success=ldao.getIdSearch(txtName.getText(),phone);
						}catch(Exception e) {
							e.printStackTrace();
						}
						Alert alert =new Alert(AlertType.INFORMATION);
						alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
						alert.setTitle("아이디 찾기 정보");
						if(success) {
							alert.setHeaderText(null);
							
							alert.setContentText("사용자의 아이디는 "+ ldao.getId(txt1.getText()+"-"+txt2.getText()+"-"+txt3.getText())+" 입니다.");
							//System.out.println(phone);
							alert.showAndWait();
							idDialog.close();
						}else{
							alert.setAlertType(AlertType.WARNING);
							alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
							alert.setHeaderText(null);
							alert.setContentText("정보에 맞는 사용자 아이디가 없습니다.");
							alert.showAndWait();
							
						}
						
					}
				}
			
			});
			Scene scene = new Scene(parent);
			idDialog.setScene(scene);
			idDialog.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void txtPwSearch(MouseEvent event) {
		Stage pwDialog = new Stage(StageStyle.DECORATED);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pwsearch.fxml"));
			Parent parent = loader.load();
			
			Button btnClose=(Button)parent.lookup("#btnClose");
			btnClose.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					pwDialog.close();
				}
			});
			TextField txtId=(TextField)parent.lookup("#txtId");
			TextField txtName=(TextField)parent.lookup("#txtName");
			Button btnSearch=(Button)parent.lookup("#btnSearch");
			btnSearch.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent evnet) {
					if(!DataUtil.validityCheck(txtId.getText().trim(), "아이디를")) {
						txtId.requestFocus();
					return;
					}
					else if(!DataUtil.validityCheck(txtName.getText().trim(), "이름을")) {
						
						txtName.requestFocus();
					return;
					}
					else {
						boolean success= false;
						try {
							success=ldao.getPwSearch(txtId.getText(), txtName.getText());
						}catch(Exception e) {
							e.printStackTrace();
						}
						Alert alert =new Alert(AlertType.INFORMATION);
						alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
						alert.setTitle("비밀번호 찾기 정보");
						if(success) {
							alert.setHeaderText(null);
							alert.setContentText("사용자의 비밀번호는 "+ldao.getPw(txtId.getText())+" 입니다.");
							alert.showAndWait();
							pwDialog.close();
						}else {
							alert.setHeaderText(null);
							alert.setContentText("정보에 맞는 사용자 정보가 없습니다.");
							alert.showAndWait();
							
						}
					}
				}
				
			});
			Scene scene = new Scene(parent);
			pwDialog.setScene(scene);
			pwDialog.show();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void txtJoin(MouseEvent event) {
		Stage joinDialog =new Stage(StageStyle.DECORATED);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/join.fxml"));
			Parent parent = loader.load();
			
			Button btnClose=(Button)parent.lookup("#btnClose");
			btnClose.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					joinDialog.close();
				}
			});
			TextField txtName=(TextField)parent.lookup("#txtName");
			TextField txtId=(TextField)parent.lookup("#txtId");
			PasswordField txtPw=(PasswordField)parent.lookup("#txtPw");
			TextField txtPhone=(TextField)parent.lookup("#txtPhone");
			TextField txtAddress=(TextField)parent.lookup("#txtAddress");
			TextField txtBirth=(TextField)parent.lookup("#txtBirth");
			ImageView image =(ImageView)parent.lookup("#image");
			Button btnCheck=(Button)parent.lookup("#btnCheck");
			btnCheck.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String searchId="";
					boolean searchIdResult =true;
					
					Alert alert =new Alert(AlertType.INFORMATION);
					alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
					alert.setTitle("아이디 중복 검사");
					alert.setHeaderText(null);
					try {
						searchId = txtId.getText().trim();
						if(!DataUtil.validityCheck(searchId, "아이디를")) {
							txtId.requestFocus();
						return;
					}else {
						searchIdResult = ldao.getIdOverlap(searchId);
						if(searchIdResult==false) {
							alert.setContentText("아이디를 사용할 수 있습니다.");
							alert.showAndWait();
							txtId.setEditable(false);
							btnCheck.setDisable(true);
						}else {
							alert.setContentText("아이디를 사용 할 수 없습니다.");
							alert.showAndWait();
							txtId.clear();
						}
					}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
			Button btnRegister =(Button)parent.lookup("#btnRegister");
			btnRegister.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File","*.png","*.jpg","*.gif"));
					File defaultDirectory = new File("/Users/");
					fileChooser.setInitialDirectory(defaultDirectory);
					
					try {
						selectedFile =fileChooser.showOpenDialog(joinDialog);
						if(selectedFile !=null) {
							localUrl =selectedFile.toURI().toString();
					//		System.out.println(localUrl);
							localImage = new Image(localUrl);
							image.setImage(localImage);
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			Button btnJoin =(Button)parent.lookup("#btnJoin");
			btnJoin.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					boolean success =false;
					if(!DataUtil.validityCheck(txtName.getText().trim(), "이름을")) {
					txtName.requestFocus();
					return;
					}else if(!DataUtil.validityCheck(txtId.getText().trim(), "아이디를")) {
						txtId.requestFocus();
						return;
					}else if(!DataUtil.validityCheck(txtPw.getText().trim(), "비밀번호를"))
					{
						txtPw.requestFocus();
						return;
						
					}else if(!DataUtil.validityCheck(txtBirth.getText().trim(), "전화번호를 ")) {
						txtBirth.requestFocus();
						return;
						
					}else if(txtPhone.getText().length()!=11) {
						Alert ale =new Alert(AlertType.WARNING);
						ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
						ale.setHeaderText(null);
						ale.setContentText("전화번호는 11자리로 설정해주세요.");
						ale.showAndWait();
					}else if(!DataUtil.validityCheck(txtPhone.getText(), "생년월일을")) {
						txtPhone.requestFocus();
						return;
					}else if(txtBirth.getText().length()!=8) {
						Alert ale =new Alert(AlertType.WARNING);
						ale.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
						ale.setHeaderText(null);
						ale.setContentText("생년월일은 8자리로 맞춰주세요");
						ale.showAndWait();
					}
					else if (!DataUtil.validityCheck(txtAddress.getText().trim(), "주소를")) {
						txtAddress.requestFocus();
						return;
						
					}else {
						StaffVO svo = new StaffVO();
						svo.setS_name(txtName.getText().trim());
						svo.setS_id(txtId.getText().trim());
						svo.setS_pwd(txtPw.getText().trim());
						svo.setS_phone(txtPhone.getText().substring(0,3)+"-"+txtPhone.getText().substring(3,7)+"-"+txtPhone.getText().substring(7));
						svo.setS_address(txtAddress.getText().trim());
						svo.setS_birth(txtBirth.getText().substring(0,4)+"-"+txtBirth.getText().substring(4,6)+"-"+txtBirth.getText().substring(6));
								
						try {
							String fileName = imageSave(selectedFile);
							svo.setS_image(fileName);
							success = ldao.loginJoin(svo);
						}catch(Exception e) {
							e.printStackTrace();
						}
						Alert alert =new Alert(AlertType.INFORMATION);
						alert.getDialogPane().setGraphic(new ImageView("/image/dialog-info.png"));
						alert.setTitle("회원 정보 입력");
						if(success==true) {
							alert.setHeaderText("회원 정보 등록 여부");
							alert.setContentText("회원등록 되었습니다.");
							joinDialog.close();
						}else {
							alert.setAlertType(AlertType.WARNING);
							alert.getDialogPane().setGraphic(new ImageView("/image/dialog-warning.png"));
							alert.setHeaderText("회원 정보 등록 여부");
							alert.setContentText("회원 정보 등록에 문제가 있어 등록되지못했습니다.");
							
						}
						alert.showAndWait();
					}
				}
			});
			Scene scene = new Scene(parent);
			joinDialog.setScene(scene);
			joinDialog.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
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
	
}
