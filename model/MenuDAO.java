package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MenuDAO {
	private static MenuDAO instance =null;
	
	public static MenuDAO getInstance() {
		if(instance ==null) {
			instance =new MenuDAO();
		}
		return instance;
	}
	private MenuDAO() {
		
	}
	
	private Connection getConnection()throws Exception{
		Connection con=DBUtil.getConnection();
		return con;
	}
	
	public ArrayList<MenuVO> getMenuTotalList() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select m_no,m_name,m_price,m_amount from menu order by m_no");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		MenuVO mvo =null;
		ArrayList<MenuVO> list = new ArrayList<MenuVO>();
		try {
			con= getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				mvo = new MenuVO();
				
				mvo.setM_no(rs.getInt("m_no"));
				mvo.setM_name(rs.getString("m_name"));
				mvo.setM_price(rs.getInt("m_price"));
				mvo.setM_amount(rs.getInt("m_amount"));
			
				list.add(mvo);
			}
			
		}catch(SQLException e) {
			System.out.println("쿼리 getMenuTotalList error");
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("error"+e);
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				System.out.println("디비연동 해제 "+e);
			}
		}
		return list;
	}
	
	public ArrayList<SellVO> getSellDayList() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select se_no,se_name,se_price,se_amount,to_char(se_date,'yyyy-mm-dd:hh24:mi:ss') se_date,me_no,se_check from sell where to_char(se_date,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') order by se_date desc");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		SellVO svo =null;
		ArrayList<SellVO> list = new ArrayList<SellVO>();
		try {
			con= getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				svo = new SellVO();
				
				svo.setSe_no(rs.getInt("se_no"));
				svo.setSe_name(rs.getString("se_name"));
				svo.setSe_price(rs.getInt("se_price"));
				svo.setSe_amount(rs.getInt("se_amount"));
				svo.setSe_date(rs.getString("se_date"));
				svo.setMe_no(rs.getInt("me_no"));
				list.add(svo);
			}
			
		}catch(SQLException e) {
			System.out.println("쿼리 getsellTotalList error");
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("error getSellTotalList"+e);
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				System.out.println("디비연동 해제 "+e);
			}
		}
		return list;
	}
	public ArrayList<SellVO> getSellMonthList() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select se_no,se_name,se_price,se_amount,to_char(se_date,'yyyy-mm-dd:hh24:mi:ss') se_date,me_no from sell where to_char(se_date,'yyyy-mm')=to_char(sysdate,'yyyy-mm') order by se_date");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		SellVO svo =null;
		ArrayList<SellVO> list = new ArrayList<SellVO>();
		try {
			con= getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				svo = new SellVO();
				
				svo.setSe_no(rs.getInt("se_no"));
				svo.setSe_name(rs.getString("se_name"));
				svo.setSe_price(rs.getInt("se_price"));
				svo.setSe_amount(rs.getInt("se_amount"));
				svo.setSe_date(rs.getString("se_date"));
				svo.setMe_no(rs.getInt("me_no"));
				list.add(svo);
			}
			
		}catch(SQLException e) {
			System.out.println("쿼리 getsellTotalList error");
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("error getSellTotalList"+e);
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				System.out.println("디비연동 해제 "+e);
			}
		}
		return list;
	}
	public ArrayList<SellVO> getSellYearList() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select se_no,se_name,se_price,se_amount,to_char(se_date,'yyyy-mm-dd:hh24:mi:ss') se_date,me_no from sell where to_char(se_date,'yyyy')=to_char(sysdate,'yyyy') order by se_date");
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		SellVO svo =null;
		ArrayList<SellVO> list = new ArrayList<SellVO>();
		try {
			con= getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs= pstmt.executeQuery();
			while(rs.next()) {
				svo = new SellVO();
				
				svo.setSe_no(rs.getInt("se_no"));
				svo.setSe_name(rs.getString("se_name"));
				svo.setSe_price(rs.getInt("se_price"));
				svo.setSe_amount(rs.getInt("se_amount"));
				svo.setSe_date(rs.getString("se_date"));
				svo.setMe_no(rs.getInt("me_no"));
				list.add(svo);
			}
			
		}catch(SQLException e) {
			System.out.println("쿼리 getsellTotalList error");
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("error getSellTotalList"+e);
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				System.out.println("디비연동 해제 "+e);
			}
		}
		return list;
	}
	
	public ArrayList<StockVO> getStockSearch(String searchWord){
		StringBuffer sql =new StringBuffer();
		sql.append("select st_no,st_name,st_price,st_amount,st_date,st_status from stock where st_name like ? order by st_no");
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		StockVO svo =null;
		ArrayList<StockVO> list = new ArrayList<StockVO>();
		try {
			con =getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+searchWord+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				svo = new StockVO();
				svo.setSt_no(rs.getInt("st_no"));
				svo.setSt_name(rs.getString("st_name"));
				svo.setSt_price(rs.getInt("st_price"));
				svo.setSt_amount(rs.getInt("st_amount"));
				svo.setSt_date(rs.getString("st_date"));
				svo.setSt_status(rs.getString("st_status"));
				
				list.add(svo);
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getStockSearch"+e);
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("에러 "+ e);
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}if(pstmt!=null) pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비연동 해제"+e);
			}
		}
		return list;
	}
	
	public ArrayList<StockVO> getStockTotalList(){
		StringBuffer sql =new StringBuffer();
		sql.append("select st_no,st_name,st_price,st_amount,to_char(st_date,'yyyy-mm-dd:hh24:mi:ss') st_date,st_status from stock order by st_date desc");
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockVO stvo=null;
		ArrayList<StockVO> list = new ArrayList<StockVO>();
		
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				stvo = new StockVO();
				
				stvo.setSt_no(rs.getInt("st_no"));
				stvo.setSt_name(rs.getString("st_name"));
				stvo.setSt_price(rs.getInt("st_price"));
				stvo.setSt_amount(rs.getInt("st_amount"));
				stvo.setSt_date(rs.getString("st_date"));
				stvo.setSt_status(rs.getString("st_status"));
				
				list.add(stvo);
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getStockTotalList "+e);
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("error "+ e);
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
				
			}catch(SQLException e) {
				System.out.println("디비 연동해제 "+e);
			}
		}
		return list;
	}
	public boolean getMenuNameOverlap(String nameOverlap)throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append("select m_no,m_name from menu where m_name=?");
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		boolean nameOverlapResult =false;
		try {
			con=getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, nameOverlap);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				nameOverlapResult=true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 getMenuNameOverlap"+e);
			nameOverlapResult=false;
		}catch(Exception e) {
			System.out.println("에러"+e);
			nameOverlapResult=false;
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비연동해제"+e);
				
			}
		}
		return nameOverlapResult;
	}
	
	
	public boolean MenuSellList(SellVO svo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sell values(sell_seq.nextval,?,?,?,?,?)");
		Connection con = null;
		PreparedStatement pstmt = null;
		
		boolean success = false;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getSe_name());
			pstmt.setInt(2, svo.getSe_price());
			pstmt.setInt(3, svo.getSe_amount());
			pstmt.setInt(4, svo.getM_no());
			pstmt.setInt(5, svo.getMe_no());
		
			int insertCount = pstmt.executeUpdate();
			if (insertCount == 1) {
				success = true;
			}

		} catch (SQLException e) {
			System.out.println("쿼리 MenuSellView error =[ " + e + "]");
			success = false;
		} catch (Exception e) {
			System.out.println("error MenuSellView = [" + e + "]");
			success = false;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("디비 연동해제 error = [ " + e + " ]");
			}
		}

		return success;
	}
	
	
	public boolean sellInsert(SellVO svo) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sell(se_no,se_name,se_price,se_amount,m_no,me_no,se_check)values(sell_seq.nextval,?,?,?,?,?,1)");
		Connection con = null;
		PreparedStatement pstmt =null;
		boolean success = false;
		
		try {
			con = getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getSe_name());
			pstmt.setInt(2, svo.getSe_price());
			pstmt.setInt(3, svo.getSe_amount());
			pstmt.setInt(4, svo.getM_no());
			pstmt.setInt(5, svo.getMe_no());
		
			
			int insertCount = pstmt.executeUpdate();
			if(insertCount == 1) {
				success = true;
			}
		}catch (SQLException e) {
			System.out.println("쿼리 sellInsert "+e);
			success =false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			success =false;
			
		}finally {
			try {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null) {
				con.close();
			}
		}catch(SQLException e) {
			System.out.println("디비 연동 해제"+e);
		}
		}
		return success;
	}
	public boolean menuUpdate(MenuVO mvo)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("update menu set m_price=? where m_name=?");
		Connection con =null;
		PreparedStatement pstmt = null;
		
		boolean success =false;
		try {
			con =getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, mvo.getM_price());
			pstmt.setString(2, mvo.getM_name());
			
			int updateCount = pstmt.executeUpdate();
			if(updateCount ==1) {
				success =true;
			}
		
		
		}catch(SQLException e) {
			System.out.println("쿼리 menuUpdate"+e);
			success =false;
		}catch(Exception e) {
			System.out.println("에러"+e);
			success =false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비연동해제 "+e);
			}
		}
		return success;
	}

	
	public boolean menuDelete(String name)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from menu where m_name=?");
		Connection con =null;
		PreparedStatement pstmt =null;
		boolean success =false;
		
		try {
			con=getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			
			int deleteCount=pstmt.executeUpdate();
			if(deleteCount==1) {
				success=true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 menuDelete"+e);
			success=false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			success =false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비연동해제 "+e);
				e.printStackTrace();
			}
		}
		return success;
	}
	
	
	public boolean sellDelete(int no) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from sell where se_no=?");
		
		Connection con =null;
		PreparedStatement pstmt =null;
		boolean success =false;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			
			int deleteCount = pstmt.executeUpdate();
			if(deleteCount ==1) {
				success =true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 sellDelete "+e);
			
		}catch(Exception e){
			System.out.println("에러 "+e);
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동해제 "+e);
			}
		}
		return success;
	}
	
	public boolean menuInsert(MenuVO mvo) throws Exception{
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2=new StringBuffer();
		sql.append("insert into menu(m_no,m_name,m_price,m_amount)\n" + 
				"values(menu_seq.nextval,?,?,0)");
		sql2.append("insert into stock(st_no,st_name,st_price,st_amount,st_status)"
				+ "values(stock_seq.nextval,?,?,0,'신규')");
		
		Connection con =null;
		Connection con2 =null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmt2=null;
		
		boolean success = false;
		boolean success2=false;
		try {
			con =getConnection();
			con2=getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt2=con2.prepareStatement(sql2.toString());
			pstmt.setString(1, mvo.getM_name());
			pstmt.setInt(2, mvo.getM_price());
			pstmt2.setString(1, mvo.getM_name());
			pstmt2.setInt(2, mvo.getM_price());
			
			int insertCount = pstmt.executeUpdate();
			if(insertCount==1) {
				success =true;
			}
			int insertCount2 =pstmt2.executeUpdate();
			if(insertCount2==2) {
				success2=true;
			}
		}catch(SQLException e) {
			System.out.println("쿼리 menuInsert"+e);
			e.printStackTrace();
			success=false;
			success2=false;
		}catch(Exception e) {
			System.out.println("에러 "+e);
			success=false;
			success2=false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
				return success&&success2;
	}
	public boolean stockInsert(StockVO svo) throws Exception{
		StringBuffer sql =new StringBuffer();
		sql.append("insert into stock( st_no,\n" + 
				"    st_name," + 
				"    st_price," + 
				"    st_amount," + 
				"    st_status)values(stock_seq.nextval,?,?,?,?)");
		Connection con = null;
		PreparedStatement pstmt =null;
		
		boolean success = false;
		
		try {
			con = getConnection();
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getSt_name());
			pstmt.setInt(2, svo.getSt_price());
			pstmt.setInt(3, svo.getSt_amount());
			pstmt.setString(4,svo.getSt_status());
			int insertCount = pstmt.executeUpdate();
			if(insertCount ==1) {
				success = true;
				
			}
		}catch(SQLException e) {
			System.out.println("쿼리 stockInsert "+e);
			success = false;
		}catch(Exception e) {
			System.out.println("에러"+e);
			success =false;
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
				

			}catch(SQLException e) {
				System.out.println("디비 연동해제"+e);
			}
		}
		return success;
				
	}
	
	public Map<String,Integer> getSellMonth(String year)throws Exception{
		Map<String,Integer> resultMap =new LinkedHashMap<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(m1) jan, sum(m2) feb, sum(m3) mar, sum(m4) apr,sum(m5) may,\n" + 
				"sum(m6) jun, sum(m7) jul, sum(m8) aug,sum(m9) sep, sum(m10) oct, sum(m11) nov, sum(m12) dec \n" + 
				"from(select decode(to_char(se_date, 'yyyy-mm'), ?||'-01',sum(se_price), 0) m1,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-02', sum(se_price), 0) m2,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-03', sum(se_price), 0) m3,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-04', sum(se_price), 0) m4,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-05', sum(se_price), 0) m5,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-06', sum(se_price), 0) m6,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-07', sum(se_price), 0) m7,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-08', sum(se_price), 0) m8,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-09', sum(se_price), 0) m9,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-10', sum(se_price), 0) m10,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-11', sum(se_price), 0) m11,\n" + 
				"decode(to_char(se_date, 'yyyy-mm'), ?||'-12', sum(se_price), 0) m12 \n" + 
				"from  sell group by to_char(se_date, 'yyyy-mm'))");
		
	
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		ResultSetMetaData rsmd = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, year);
			pstmt.setString(2, year);
			pstmt.setString(3, year);
			pstmt.setString(4, year);
			pstmt.setString(5, year);
			pstmt.setString(6, year);
			pstmt.setString(7, year);
			pstmt.setString(8, year);
			pstmt.setString(9, year);
			pstmt.setString(10, year);
			pstmt.setString(11, year);
			pstmt.setString(12, year);
			rs=pstmt.executeQuery();
			
			rsmd=rs.getMetaData();
			if(rs.next()) {
				for(int i =1;i<=rsmd.getColumnCount();i++) {
					resultMap.put(rsmd.getColumnName(i),rs.getInt(i));
					
				}
			}
		}catch(SQLException e) {
			System.out.println("쿼리 에러 "+e);
		}catch(Exception e) {
			System.out.println("에러 "+e);
		}finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				System.out.println("디비 연동해제 "+e);
			}
		}
		return resultMap;
	}
	

/*********************** 적립금 조회 *************************/
public int getMemberSave(int searchWord) throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append("select me_save from member where me_no=?");
      
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      int meSave = 0;
      
      try {
         con = getConnection();
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setInt(1,searchWord);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            meSave = rs.getInt("me_save");
         }
      }catch(SQLException se) {
         System.out.println("쿼리 getMemberSave error = [ "+se+" ]");
         se.printStackTrace();
      }catch(Exception e) {
         System.out.println("error = [ "+e+" ]");
      }finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(con != null) con.close();
         } catch(SQLException se) {
            System.out.println("디비 연동 해제 error = [ "+se+" ]");
         }
      }
   
      return meSave;
   }

/*********************** 적립금 사용 *************************/
public boolean getMeSaveUpdate(MemberVO mvo) throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append("UPDATE member SET me_save =? WHERE me_no=? ");
      

      Connection con = null;
      PreparedStatement pstmt = null;
      boolean success = false;
      
      try {
         con = getConnection();
         
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setInt(1, mvo.getMe_save());
         pstmt.setInt(2, mvo.getMe_no());
      
         int i = pstmt.executeUpdate();
         if(i==1) success = true;
      }catch (SQLException se) {
         System.out.println("쿼리 getMeSaveUpdate error = ["+se+" ]");
         success = false;
      }catch (Exception e) {
         System.out.println("error = ["+e+" ]");
         success = false;
      }finally {
         try {
            if(pstmt != null)pstmt.close();
            if(con != null)con.close();
         }catch(SQLException se) {
            System.out.println("디비 연동 해제 error = ["+se+" ]");
         }
      }   
      return success;
   }

public boolean getMeNo(int no) throws Exception {
    StringBuffer sql = new StringBuffer();
    sql.append("select me_no from member where me_no=? ");

    Connection con = null;
    PreparedStatement pstmt = null;
    boolean success = false;
    
    try {
       con = getConnection();
       
       pstmt = con.prepareStatement(sql.toString());
       pstmt.setInt(1, no);
     
    
       int i = pstmt.executeUpdate();
       if(i==1) success = true;
    }catch (SQLException se) {
       System.out.println("쿼리 getMeSaveUpdate error = ["+se+" ]");
       success = false;
    }catch (Exception e) {
       System.out.println("error = ["+e+" ]");
       success = false;
    }finally {
       try {
          if(pstmt != null)pstmt.close();
          if(con != null)con.close();
       }catch(SQLException se) {
          System.out.println("디비 연동 해제 error = ["+se+" ]");
       }
    }   
    return success;
 }
public boolean sellSaveInsert(SellVO svo) throws Exception{
	StringBuffer sql = new StringBuffer();
	sql.append("insert into sell(se_no,se_name,se_price,se_amount,m_no,me_no,se_check)values(sell_seq.nextval,?,?,?,?,?,2)");
	Connection con = null;
	PreparedStatement pstmt =null;
	boolean success = false;
	
	try {
		con = getConnection();
		pstmt=con.prepareStatement(sql.toString());
		pstmt.setString(1, svo.getSe_name());
		pstmt.setInt(2, svo.getSe_price());
		pstmt.setInt(3, svo.getSe_amount());
		pstmt.setInt(4, svo.getM_no());
		pstmt.setInt(5, svo.getMe_no());
		
		int insertCount = pstmt.executeUpdate();
		if(insertCount == 1) {
			success = true;
		}
	}catch (SQLException e) {
		System.out.println("쿼리 sellInsert "+e);
		success =false;
	}catch(Exception e) {
		System.out.println("에러 "+e);
		success =false;
		
	}finally {
		try {
		if(pstmt!=null) {
			pstmt.close();
		}
		if(con!=null) {
			con.close();
		}
	}catch(SQLException e) {
		System.out.println("디비 연동 해제"+e);
	}
	}
	return success;
}
}
