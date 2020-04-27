package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAO {
	private static LoginDAO instance = null;
	
	public static LoginDAO getInstance() {
		if(instance ==null) {
			instance =new LoginDAO();
		}
		return instance;
	}
	private LoginDAO() {
		
	}
	private Connection getConnection() throws Exception{
		Connection con =DBUtil.getConnection();
		return con;
	}
	public ArrayList<StaffVO> loginInfo(String id) {
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT s_no,s_name,s_job,s_ptime,s_id,s_pwd,s_phone,s_address,to_char(s_hiredate,'yyyy-mm-dd'),s_birth,s_gender,s_state,s_image FROM staff where s_id=? ");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		StaffVO sVo =null;
		ArrayList<StaffVO> list =new ArrayList<StaffVO>();
		try {
			con = getConnection();
			pstmt =con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				sVo = new StaffVO();
				
				 sVo.setS_no(rs.getInt("s_no"));
		            sVo.setS_name(rs.getString("s_name"));
		            sVo.setS_job(rs.getString("s_job"));
		            sVo.setS_ptime(rs.getString("s_ptime"));
		            sVo.setS_id(rs.getString("s_id"));
		            sVo.setS_pwd(rs.getString("s_pwd"));
		            sVo.setS_phone(rs.getString("s_phone"));
		            sVo.setS_address(rs.getString("s_address"));
		            sVo.setS_hiredate(rs.getString("to_char(s_hiredate,'yyyy-mm-dd')"));
		            sVo.setS_birth(rs.getString("s_birth"));
		            sVo.setS_gender(rs.getString("s_gender"));
		            sVo.setS_state(rs.getString("s_state"));
		            sVo.setS_image(rs.getString("s_image"));
		            list.add(sVo);

			}
		}catch(SQLException e) {
			System.out.println("쿼리 loginInfo :"+e);
		}catch(Exception e) {
			System.out.println("에러 "+e);
		}
		finally{
			try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
	
			}catch(SQLException e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
		return list;
	}
	public boolean loginJoin(StaffVO svo) {
		StringBuffer sql =new StringBuffer();
		sql.append("insert into staff(s_no,s_name,s_job,s_id,s_pwd,s_phone,s_address,s_birth,s_image)values(staff_seq.nextval,?,'직원',?,?,?,?,?,?)");
		Connection con =null;
		PreparedStatement pstmt =null;
		
		boolean success =false;
		try {
			con =getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getS_name());
			pstmt.setString(2, svo.getS_id());
			pstmt.setString(3, svo.getS_pwd());
			pstmt.setString(4, svo.getS_phone());
			pstmt.setString(5, svo.getS_address());
			pstmt.setString(6, svo.getS_birth());
			pstmt.setString(7, svo.getS_image());
			
			int insertCount =pstmt.executeUpdate();
			if(insertCount ==1) {
				success =true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 loginJoin"+e);
			success =false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			success =false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동 해제 "+e);
			}
		}
		return success;
	}
	public boolean getIdOverlap(String idOverlap) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s_id from staff where s_id=? ");
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		boolean idOverlapResult =false;
		try {
			con =getConnection();
			pstmt =con.prepareStatement(sql.toString());
			pstmt.setString(1, idOverlap);
			//pstmt.setString(2,pwOverlap);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				idOverlapResult=true;
				
			}
			
		}catch(SQLException e) {
			System.out.println("쿼리 idOverlap"+e);
			idOverlapResult=false;
			
		}catch(Exception e) {
			System.out.println("에러 "+e);
			idOverlapResult=false;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
		return idOverlapResult;
	}
	
	public boolean getpwOverlap(String pwOverlap,String idOverlap) {
		StringBuffer sql =new StringBuffer();
		sql.append("select s_pwd from staff where s_pwd=? and s_id=?");
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		boolean pwOverlapResult = false;
		try {
			con =getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,pwOverlap);
			pstmt.setString(2, idOverlap);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				pwOverlapResult=true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getpwOverlap"+e);
			pwOverlapResult=false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			pwOverlapResult=false;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비연동해제"+e);
			}
		}
		return pwOverlapResult;
	}
	public boolean getIdSearch(String name,String phone) {
		StringBuffer sql =new StringBuffer();
		sql.append("select s_name,s_phone from staff where s_name=? and s_phone=?");
		
		
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		boolean result =false;
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1,name);
			pstmt.setString(2,phone);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=true;
				
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getIdSearch"+e);
			result =false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			result=false;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
		return result;
	}
	public String getId(String name) {
		StringBuffer sql =new StringBuffer();
		sql.append("select s_id from staff where s_phone=?");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		String id="";
		try {
			con=getConnection();
			pstmt=con.prepareCall(sql.toString());
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				id=rs.getString("s_id");
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getId"+e);
			
		}catch(Exception e) {
			System.out.println("에러 "+e);
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비연동해제"+e);
			}
		}
		return id;
	}
	public boolean getPwSearch(String id,String name) {
		StringBuffer sql =new StringBuffer();
		sql.append("select s_name,s_id from staff where s_name=? and s_id=?");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		boolean result=false;
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			pstmt.setString(2,id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result =true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getPwSearch"+e);
			result=false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			result=false;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
		return result;
	}
	public String getPw(String id) {
		StringBuffer sql=new StringBuffer();
		sql.append("select s_pwd from staff where s_id=?");
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String pw="";
		
		try {
			con =getConnection();
			pstmt =con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				pw=rs.getString("s_pwd");
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getPw"+e);
			
		}catch(Exception e) {
			System.out.println("에러 "+e);
			
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
		return pw;
	}
	
	
}
