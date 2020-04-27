package model;


import java.util.List;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javafx.scene.control.Alert;

import javafx.scene.control.Alert.AlertType;

public class DataUtil {
	/**************************************************************
	 
	 **************************************************************/
	public static boolean validityCheck(String value, String printData) {
		boolean result = true;
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("입력 여부 확인");

		if (isBlank(value)) {
			alert.setHeaderText(printData + "입력 해주세요");
			alert.setContentText("값이 입력되지 않으면 정상적으로 작업을 수행 할 수 없습니다.");
			alert.showAndWait();
			result = false;
		}
		return result;
	}

	/***********************************************************
	  
	***********************************************************/
	
	public static List<String> fieldName(Object obj){
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> result = new ArrayList<>();
		for(int i =0;i<fields.length;++i) {
			try {
				result.add(fields[i].getName());
				
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
	
	private static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;

		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
