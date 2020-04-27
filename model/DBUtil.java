package model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
	
	
	public static Connection getConnection() throws Exception{
		
		InputStream fis = DBUtil.class.getResourceAsStream("/properties_file/jdbc.properties");
		Properties pro = new Properties();
		pro.load(fis);
		
		String driver = pro.getProperty("driver");
		String url = pro.getProperty("url");
		String user= pro.getProperty("user");
		String password = pro.getProperty("password");
		
		Class.forName(driver);
		Connection con =DriverManager.getConnection(url,user,password);
		
		return con;
				
	}
}
