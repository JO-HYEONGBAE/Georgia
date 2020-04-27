package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkDAO {
   private static WorkDAO instance = null;
   
   public static WorkDAO getInstance() {
      if (instance == null)
         instance = new WorkDAO();
      return instance;
   }
   
   private WorkDAO() { }
   
   private Connection getConnection() throws Exception {
      Connection con = DBUtil.getConnection();
      return con;
   }
   
   /*****************출퇴근 정보 전체 조회*********************/   
   public ArrayList<WorkVO> getWorkTotalList() throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT w_no, w_go, w_out, w_name FROM work where to_char(w_go,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')");
      sql.append(" ORDER BY w_no");
      
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      WorkVO wvo = null;
      ArrayList<WorkVO>list = new ArrayList<WorkVO>();
      
      try {
         con = getConnection();
         pstmt = con.prepareStatement(sql.toString());
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            wvo = new WorkVO();
            
            wvo.setW_no(rs.getInt("w_no"));
            wvo.setW_go(rs.getString("w_go"));
            wvo.setW_out(rs.getString("w_out"));
            wvo.setW_name(rs.getString("w_name"));
            
            list.add(wvo);
         }
      }catch (SQLException se) {
         System.out.println("쿼리 getWorkTotalList error = ["+ se +"]");
      }catch (Exception e) {
         System.out.println("error = ["+ e +"]");
      }finally {
         try {
            if(rs != null)rs.close();
            if(pstmt != null) pstmt.close();
            if(con != null)con.close();
         }catch(SQLException se) {
            System.out.println("디비 연동 해제 error = ["+se + "]");
         }
      }
      return list;      
   }
   
   /*****************출퇴근 정보 검색 리스트*********************/   
   public ArrayList<WorkVO> getWorkSearch(String searchObject, String searchWord) throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT w_no,w_go,w_out,w_name from work");
      
      if(searchObject.equalsIgnoreCase("w_name")) {
         sql.append(" WHERE w_name LIKE ? ");
      }else if(searchObject.equalsIgnoreCase("w_go")) {
         sql.append(" WHERE TO_CHAR(w_go, 'YYYY-MM-DD') LIKE ? ");
      }
      sql.append(" ORDER BY w_no");
      
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      WorkVO wvo =null;
      ArrayList<WorkVO> list = new ArrayList<WorkVO>();
      
      try {
         con = getConnection();
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setString(1,"%"+searchWord + "%");
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            wvo = new WorkVO();
            
            wvo.setW_no(rs.getInt("w_no"));
            wvo.setW_go(rs.getString("w_go"));
            wvo.setW_out(rs.getString("w_out"));
            wvo.setW_name(rs.getString("w_name"));
            
            list.add(wvo);
         }
      }catch(SQLException se) {
         System.out.println("쿼리 getWorkSearch error = ["+se+"]");
      }catch(Exception e) {
         System.out.println("error = ["+ e +"]");
      }finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(con != null)con.close();
         }catch (SQLException se) {
            System.out.println("디비 연동 해제 error = ["+se+"]");
         }
      }
      return list;
   }

   	public String workDay(int no) {
   		StringBuffer sql = new StringBuffer();
   		sql.append("select count(w_out) as day from work where s_no=? and w_out between  ?||'-01' and ? ");
   		Connection con=null;
   		PreparedStatement pstmt =null;
   		ResultSet rs =null;
   		SimpleDateFormat sdf ;
   		SimpleDateFormat sdf2;
   		Date date ;
   		String cnt="";
   		try {
   			date=new Date();
   			sdf = new SimpleDateFormat("YYYY-MM");
   			sdf2 = new SimpleDateFormat("YYYY-MM-dd");
   			con =getConnection();
   			pstmt = con.prepareStatement(sql.toString());
   			pstmt.setInt(1,no);
   			pstmt.setString(2, sdf.format(date));
   			pstmt.setString(3, sdf2.format(date));
   			rs=pstmt.executeQuery();
   			if(rs.next()) {
   				//rs.getInt("day");
   				cnt=rs.getString("day");
   			}
   			
   		}catch(SQLException se) {
            System.out.println("쿼리 workday error = ["+se+"]");
        }catch(Exception e) {
           System.out.println("error = ["+ e +"]");
        }finally {
           try {
              if(rs != null) rs.close();
              if(pstmt != null) pstmt.close();
              if(con != null)con.close();
           }catch (SQLException se) {
              System.out.println("디비 연동 해제 error = ["+se+"]");
           }
        }
        return cnt;
   }
    public String goTime(int no) {
 	   StringBuffer sql=new StringBuffer();
 	   sql.append("select to_char(w_go,'hh24:mi') as time from work where s_no=? and to_char(w_go,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')");
 	   
 	   Connection con =null;
 	   PreparedStatement pstmt =null;
 	   
 	   int cnt=0;
 	   String time="";
 	   ResultSet rs;
 	   boolean success =false;
 	   try {
 		  con=getConnection();
 		  pstmt=con.prepareStatement(sql.toString());
 		  pstmt.setInt(1, no);
 		  cnt=pstmt.executeUpdate();
 		  rs=pstmt.executeQuery();
 		  if(rs.next()) {
 			  time=rs.getString("time");
 			  
 		  }
 		  
 	   }catch(SQLException e) {
 			System.out.println("쿼리 outCheck"+e);
 			success=false;
 		}catch(Exception e) {
 			System.out.println("에러"+e);
 			success=false;
 		}finally {
 			try {
 				if(pstmt!=null)pstmt.close();
 				if(con!=null)con.close();
 			}catch(SQLException e) {
 				System.out.println("디비 연동 해제"+e);
 			}
 		}
    return time;
    }
    
    public String OutTime(int no) {
  	   StringBuffer sql=new StringBuffer();
  	   sql.append("select to_char(w_out,'hh24:mi') as time from work where s_no=? and to_char(w_out,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')");
  	   
  	   Connection con =null;
  	   PreparedStatement pstmt =null;
  	   ResultSet rs =null;
   	   String time="";
  	   int cnt=0;
  	   boolean success =false;
  	   try {
  		  con=getConnection();
  		  pstmt=con.prepareStatement(sql.toString());
  		  pstmt.setInt(1, no);
  		  rs=pstmt.executeQuery();
  		  if(rs.next()) {
  			  time=rs.getString("time");
  		  }
  		  
  	   }catch(SQLException e) {
  			System.out.println("쿼리 outCheck"+e);
  			success=false;
  		}catch(Exception e) {
  			System.out.println("에러"+e);
  			success=false;
  		}finally {
  			try {
  				if(pstmt!=null)pstmt.close();
  				if(con!=null)con.close();
  			}catch(SQLException e) {
  				System.out.println("디비 연동 해제"+e);
  			}
  		}
     return time;
     }
     
   
   
   public boolean workOn(WorkVO wvo) {
	StringBuffer sql =new StringBuffer();
	sql.append("insert into work(w_no,w_name,s_no) values(work_seq.nextval,?,?)");
	Connection con =null;
	PreparedStatement pstmt =null;
	
	boolean success =false;
	try {
		con=getConnection();
		pstmt=con.prepareStatement(sql.toString());
		pstmt.setString(1, wvo.getW_name());
		pstmt.setInt(2, wvo.getS_no());
		
		int insertCount = pstmt.executeUpdate();
		if(insertCount==1) {
			success = true;
		}
	}catch(SQLException e) {
		System.out.println("쿼리 workOn"+e);
		success =false;
	}catch(Exception e) {
		System.out.println("에러"+e);
		success =false;
	}finally {
		try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		}catch(SQLException e) {
			System.out.println("디비 연동 해제"+e);
		}
	}
	return success;
   }
   public boolean outCheck(int no) {
	   StringBuffer sql=new StringBuffer();
	   sql.append("select to_char(w_out,'yyyy-mm-dd') from work where s_no=? and to_char(w_out,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd')");
	   
	   Connection con =null;
	   PreparedStatement pstmt =null;
	   
	   int cnt=0;
	   boolean success =false;
	   try {
		  con=getConnection();
		  pstmt=con.prepareStatement(sql.toString());
		  pstmt.setInt(1, no);
		  cnt=pstmt.executeUpdate();
		  if(cnt==1) {
			  success=true;
		  }
		  
	   }catch(SQLException e) {
			System.out.println("쿼리 outCheck"+e);
			success=false;
		}catch(Exception e) {
			System.out.println("에러"+e);
			success=false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
   return success;
   }
   

   public boolean goCheck(int no) {
	   StringBuffer sql=new StringBuffer();
	   sql.append("select to_char(w_go,'yyyy-mm-dd') from work where s_no=? and to_char(w_go,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')");
	   
	   Connection con =null;
	   PreparedStatement pstmt =null;
	   
	   int cnt=0;
	   boolean success =false;
	   try {
		  con=getConnection();
		  pstmt=con.prepareStatement(sql.toString());
		  pstmt.setInt(1, no);
		  cnt=pstmt.executeUpdate();
		  if(cnt==1) {
			  success=true;
		  }
		  
	   }catch(SQLException e) {
			System.out.println("쿼리 goCheck"+e);
			success=false;
		}catch(Exception e) {
			System.out.println("에러"+e);
			success=false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
   return success;
   }
   
   public boolean workOut(int no) {
	   StringBuffer sql =new StringBuffer();
	   sql.append("update work set w_out=sysdate where s_no=? and to_char(w_go,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') ");
 
	   Connection con=null;
	   PreparedStatement pstmt =null;
	   boolean success =false;
	   
	   try {
		   con=getConnection();
		   pstmt=con.prepareStatement(sql.toString());
		   pstmt.setInt(1, no);
		   int outCount=pstmt.executeUpdate();
		   
		   if(outCount==1) {
			   success=false;
		   }
	   }catch(SQLException e) {
			System.out.println("쿼리 workOut"+e);
			success =false;
		}catch(Exception e) {
			System.out.println("에러"+e);
			success =false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동 해제"+e);
			}
		}
		return success;
	   }
   
  
   
}
