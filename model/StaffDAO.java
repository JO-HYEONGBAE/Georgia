package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffDAO {
   private static StaffDAO instance = null;

   public static StaffDAO getInstance() {
      if (instance == null)
         instance = new StaffDAO();
      return instance;
   }

   private StaffDAO() {
   }

   private Connection getConnection() throws Exception {
      Connection con = DBUtil.getConnection();
      return con;
   }

   /***************** 직원 정보 전체 조회 *********************/
   public ArrayList<StaffVO> getStaffTotalList(String searchWord) throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append(
            "SELECT s_no,s_name,s_job,s_ptime,s_id,s_pwd,s_phone,s_address,to_char(s_hiredate,'yyyy-mm-dd')as s_hiredate,s_birth,s_gender,s_state,s_image FROM staff");
      if(searchWord != null) {
         sql.append(" WHERE s_name Like ? ");
      }
      sql.append(" ORDER BY s_job asc");

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      StaffVO svo = null;
      ArrayList<StaffVO> list = new ArrayList<StaffVO>();

      try {
         con = getConnection();
         pstmt = con.prepareStatement(sql.toString());
         if(searchWord != null) {
            pstmt.setString(1, "%"+searchWord+"%");
         }
         rs = pstmt.executeQuery();

         while (rs.next()) {
            svo = new StaffVO();

            svo.setS_no(rs.getInt("s_no"));
            svo.setS_name(rs.getString("s_name"));
            svo.setS_job(rs.getString("s_job"));
            svo.setS_ptime(rs.getString("s_ptime"));
            svo.setS_id(rs.getString("s_id"));
            svo.setS_pwd(rs.getString("s_pwd"));
            svo.setS_phone(rs.getString("s_phone"));
            svo.setS_address(rs.getString("s_address"));
            svo.setS_hiredate(rs.getString("s_hiredate"));
            svo.setS_birth(rs.getString("s_birth"));
            svo.setS_gender(rs.getString("s_gender"));
            svo.setS_state(rs.getString("s_state"));
            svo.setS_image(rs.getString("s_image"));

            list.add(svo);

         }
      } catch (SQLException se) {
         System.out.println("쿼리 getStaffTotalList error = [" + se + "]");
      } catch (Exception e) {
         System.out.println("error = [" + e + "]");
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (con != null)
               con.close();
         } catch (SQLException se) {
            System.out.println("디비 연동 해제 error = [" + se + "]");
         }
      }
      return list;
   }
   
   /***************** 직원 정보 등록 *********************/
   public boolean staffInsert(StaffVO svo) throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO staff(s_no,s_name,s_job,s_ptime,s_id,s_pwd,s_phone,s_address,s_birth,s_gender,s_state,s_image) VALUES (staff_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)");
      
      Connection con = null;
      PreparedStatement pstmt = null;
      boolean success = false;
      
      try {
         con = getConnection();
         
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setString(1, svo.getS_name());
         pstmt.setString(2, svo.getS_job());
         pstmt.setString(3, svo.getS_ptime());
         pstmt.setString(4, svo.getS_id());
         pstmt.setString(5, svo.getS_pwd());
         pstmt.setString(6, svo.getS_phone());
         pstmt.setString(7, svo.getS_address());
         pstmt.setString(8, svo.getS_birth());
         pstmt.setString(9, svo.getS_gender());
         pstmt.setString(10, svo.getS_state());
         pstmt.setString(11, svo.getS_image());
         
         int i = pstmt.executeUpdate();
         if(i==1) success = true;
      } catch (SQLException se) {
         System.out.println("쿼리 staffInsert11 error = [ "+se+" ]");
         success = false;
      } catch (Exception e) {
         System.out.println("error = [ "+e+" ]");
         success = false;
      } finally {
         try {
            if(pstmt != null) pstmt.close();
            if(con != null) con.close();
         }catch(SQLException se) {
            System.out.println("디비 연동 해제 error = [ "+se+" ]");
         }
      }
      return success;
   }

   /***************** 직원 정보 수정 *********************/
   public boolean staffUpdate(StaffVO svo) throws Exception{
      StringBuffer sql = new StringBuffer();
      sql.append ( "UPDATE staff SET s_name = ?,"
      		+ " s_job = ?,s_ptime =?,s_pwd =?, "
      		+ "s_phone=?, s_address =?,s_hiredate =?,"
      		+ " s_birth=?, s_gender=?, s_state =?, s_image=? "
      		+ "WHERE s_no=? ");
      
      Connection con = null;
      PreparedStatement pstmt = null;
      boolean success = false;
      
      try {
         con = getConnection();
         
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setString(1, svo.getS_name());
         pstmt.setString(2, svo.getS_job());
         pstmt.setString(3, svo.getS_ptime());
         pstmt.setString(4, svo.getS_pwd());
         pstmt.setString(5, svo.getS_phone());
         pstmt.setString(6, svo.getS_address());
         pstmt.setString(7, svo.getS_hiredate());
         pstmt.setString(8, svo.getS_birth());
         pstmt.setString(9, svo.getS_gender());
         pstmt.setString(10, svo.getS_state());
         pstmt.setString(11, svo.getS_image());
         pstmt.setInt(12, svo.getS_no());
         
         int i = pstmt.executeUpdate();
         if(i==1) success = true;
      }catch (SQLException se) {
         System.out.println("쿼리 staffUpdate error = ["+se+" ]");
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

   /***************** 직원 아이디 중복 체크 *********************/
   public boolean getStaffIdOverlap(String idOverlap) throws Exception {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT s_no,s_name,s_id,s_pwd,s_state FROM staff WHERE s_id = ?");
      
      Connection         con   = null;
      PreparedStatement pstmt = null;
      ResultSet         rs    = null;
      boolean idOverlapResult = false; 
      
      try {
         con = getConnection();
         pstmt = con.prepareStatement(sql.toString());
         pstmt.setString(1, idOverlap);
         rs = pstmt.executeQuery();
      
         if(rs.next()) {
            idOverlapResult = true; 
         }
      }catch(SQLException se) {
         System.out.println("쿼리 getStaffIdOverlap error = [ "+se+" ]");
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
      return idOverlapResult;
   }


   
   
}
