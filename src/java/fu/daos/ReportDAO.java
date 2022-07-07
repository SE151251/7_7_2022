/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.daos;

import fu.dbhelper.DBUtils;
import fu.entities.Article;
import fu.entities.Comment;
import fu.entities.Member;
import fu.entities.Report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ReportDAO {
    public ArrayList<Report> getAllReports() throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Report> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Report "                        
                        + "Order By ReportTime ASC";
                stm = con.prepareStatement(sql);               
                rs = stm.executeQuery();
                while (rs.next()) {
                    int rId = rs.getInt("ReportID");                                       
                    String rContent = rs.getString("ReportContent");
                    String rTime = rs.getString("ReportTime"); 
                    String cTime = rs.getString("ConfirmTime");
                    int rStatus = rs.getInt("ReportStatus");
                    String memPostCmt = rs.getString("MemberID");
                    int aR = rs.getInt("ArticleID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memPostCmt);      
                    ArticleDAO adao = new ArticleDAO();
                    Article a = adao.find(aR);
                    Report r = new Report(rId, rContent, rTime, cTime, rStatus, a, m);
                    lb.add(r);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return lb;
    }    

    public boolean addNewReport(Report r) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Report (ReportContent, ReportTime, ConfirmTime, ReportStatus, ArticleID, MemberID)"
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, r.getReportContent());
                stm.setString(2, r.getReportTime());
                stm.setString(3, r.getConfirmTime());
                stm.setInt(4, 1);
                stm.setInt(5,r.getArticle().getArticleID());
                stm.setString(6, r.getMember().getMemberID());               
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateStatusReport(int aId)
            throws Exception, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "UPDATE Report "
                        + "SET ReportStatus = 0, ConfirmTime = ? "
                        + "Where ArticleID LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, LocalDateTime.now().toString());
                stm.setInt(2, aId);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    public Report getReportById(String rId) throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Report r = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Report "
                        + "Where ReportID Like ? ";                  
                stm = con.prepareStatement(sql);
                stm.setString(1, rId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int reportId = rs.getInt("ReportID");                                       
                    String rContent = rs.getString("ReportContent");
                    String rTime = rs.getString("ReportTime"); 
                    String cTime = rs.getString("ConfirmTime");
                    int rStatus = rs.getInt("ReportStatus");
                    String memPostCmt = rs.getString("MemberID");
                    int aR = rs.getInt("ArticleID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memPostCmt);      
                    ArticleDAO adao = new ArticleDAO();
                    Article a = adao.find(aR);
                    r = new Report(reportId, rContent, rTime, cTime, rStatus, a, m);                   
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return r;
    }    
    // Hàm này kiểm tra xem user đã 
    public Report checkReport(String aId, String mId) throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Report r = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Report "
                        + "Where ArticleID Like ? and MemberID Like ? ";                  
                stm = con.prepareStatement(sql);
                stm.setString(1, aId);
                stm.setString(2, mId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int reportId = rs.getInt("ReportID");                                       
                    String rContent = rs.getString("ReportContent");
                    String rTime = rs.getString("ReportTime");
                    String cTime = rs.getString("ConfirmTime");                    
                    int rStatus = rs.getInt("ReportStatus");
                    String memPostCmt = rs.getString("MemberID");
                    int aR = rs.getInt("ArticleID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memPostCmt);      
                    ArticleDAO adao = new ArticleDAO();
                    Article a = adao.find(aR);
                    r = new Report(reportId, rContent, rTime, cTime, rStatus, a, m);                   
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return r;
    }
    // Hàm này lấy ra các report chưa xử lý
    public ArrayList<Report> getAllReportsNotComfirm() throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Report> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Report where ReportStatus = 1"                        
                        + "Order By ReportTime ASC";
                stm = con.prepareStatement(sql);               
                rs = stm.executeQuery();
                while (rs.next()) {
                    int rId = rs.getInt("ReportID");                                       
                    String rContent = rs.getString("ReportContent");
                    String rTime = rs.getString("ReportTime"); 
                    String cTime = rs.getString("ConfirmTime");
                    int rStatus = rs.getInt("ReportStatus");
                    String memPostCmt = rs.getString("MemberID");
                    int aR = rs.getInt("ArticleID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memPostCmt);      
                    ArticleDAO adao = new ArticleDAO();
                    Article a = adao.find(aR);
                    Report r = new Report(rId, rContent, rTime, cTime, rStatus, a, m);
                    lb.add(r);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return lb;
    }   
    // Hàm này lấy ra các report đã xử lý
    public ArrayList<Report> getAllReportsComfirmed() throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Report> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Report where ReportStatus = 0"                        
                        + "Order By ReportTime ASC";
                stm = con.prepareStatement(sql);               
                rs = stm.executeQuery();
                while (rs.next()) {
                    int rId = rs.getInt("ReportID");                                       
                    String rContent = rs.getString("ReportContent");
                    String rTime = rs.getString("ReportTime");
                    String cTime = rs.getString("ConfirmTime");                    
                    int rStatus = rs.getInt("ReportStatus");
                    String memPostCmt = rs.getString("MemberID");
                    int aR = rs.getInt("ArticleID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memPostCmt);      
                    ArticleDAO adao = new ArticleDAO();
                    Article a = adao.find(aR);
                    Report r = new Report(rId, rContent, rTime, cTime, rStatus, a, m);
                    lb.add(r);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return lb;
    }
}
