package com.freelance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.freelance.bean.Project;
import com.freelance.util.DBUtil;

public class ProjectDAO {
    public Project findProject(int projectID) throws ClassNotFoundException, SQLException {
        Project project = null;
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM PROJECT_TBL WHERE PROJECT_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, projectID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            project = new Project();
            project.setProjectID(rs.getInt("PROJECT_ID"));
            project.setClientUserID(rs.getString("CLIENT_USER_ID"));
            project.setProjectTitle(rs.getString("PROJECT_TITLE"));
            project.setProjectDescription(rs.getString("PROJECT_DESCRIPTION"));
            project.setBudgetMin(rs.getBigDecimal("BUDGET_MIN"));
            project.setBudgetMax(rs.getBigDecimal("BUDGET_MAX"));
            project.setPostedDate(rs.getDate("POSTED_DATE"));
            project.setStatus(rs.getString("STATUS"));
            int awardedBidID = rs.getInt("AWARDED_BID_ID");
            if (rs.wasNull()) {
                project.setAwardedBidID(null);
            } else {
                project.setAwardedBidID(awardedBidID);
            }
        }
        rs.close();
        ps.close();
        con.close();
        return project;
    }
    public List<Project> viewAllProjects() throws ClassNotFoundException, SQLException {
        List<Project> projects = new ArrayList<Project>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM PROJECT_TBL";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Project project = new Project();
            project.setProjectID(rs.getInt("PROJECT_ID"));
            project.setClientUserID(rs.getString("CLIENT_USER_ID"));
            project.setProjectTitle(rs.getString("PROJECT_TITLE"));
            project.setProjectDescription(rs.getString("PROJECT_DESCRIPTION"));
            project.setBudgetMin(rs.getBigDecimal("BUDGET_MIN"));
            project.setBudgetMax(rs.getBigDecimal("BUDGET_MAX"));
            project.setPostedDate(rs.getDate("POSTED_DATE"));
            project.setStatus(rs.getString("STATUS"));
            int awardedBidID = rs.getInt("AWARDED_BID_ID");
            if (rs.wasNull()) {
                project.setAwardedBidID(null);
            } else {
                project.setAwardedBidID(awardedBidID);
            }
            projects.add(project);
        }
        rs.close();
        ps.close();
        con.close();
        return projects;
    }
    public List<Project> viewOpenProjects() throws ClassNotFoundException, SQLException {
        List<Project> projects = new ArrayList<Project>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM PROJECT_TBL WHERE STATUS='OPEN'";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Project project = new Project();
            project.setProjectID(rs.getInt("PROJECT_ID"));
            project.setClientUserID(rs.getString("CLIENT_USER_ID"));
            project.setProjectTitle(rs.getString("PROJECT_TITLE"));
            project.setProjectDescription(rs.getString("PROJECT_DESCRIPTION"));
            project.setBudgetMin(rs.getBigDecimal("BUDGET_MIN"));
            project.setBudgetMax(rs.getBigDecimal("BUDGET_MAX"));
            project.setPostedDate(rs.getDate("POSTED_DATE"));
            project.setStatus(rs.getString("STATUS"));
            int awardedBidID = rs.getInt("AWARDED_BID_ID");
            if (rs.wasNull()) {
                project.setAwardedBidID(null);
            } else {
                project.setAwardedBidID(awardedBidID);
            }
            projects.add(project);
        }
        rs.close();
        ps.close();
        con.close();
        return projects;
    }
    public int generateProjectID() throws ClassNotFoundException, SQLException {
        int projectID = 50001;
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT NVL(MAX(PROJECT_ID),50000)+1 FROM PROJECT_TBL";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            projectID = rs.getInt(1);
        }
        rs.close();
        ps.close();
        con.close();
        return projectID;
    }
    public boolean insertProject(Project project) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Connection con = DBUtil.getDBConnection();
        String sql = "INSERT INTO PROJECT_TBL(PROJECT_ID,CLIENT_USER_ID,PROJECT_TITLE,PROJECT_DESCRIPTION,BUDGET_MIN,BUDGET_MAX,POSTED_DATE,STATUS,AWARDED_BID_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, project.getProjectID());
        ps.setString(2, project.getClientUserID());
        ps.setString(3, project.getProjectTitle());
        ps.setString(4, project.getProjectDescription());
        ps.setBigDecimal(5, project.getBudgetMin());
        ps.setBigDecimal(6, project.getBudgetMax());
        ps.setDate(7, project.getPostedDate());
        ps.setString(8, project.getStatus());
        if (project.getAwardedBidID() == null) {
            ps.setNull(9, java.sql.Types.INTEGER);
        } else {
            ps.setInt(9, project.getAwardedBidID());
        }
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        con.close();
        return result;
    }
    public boolean updateProjectStatus(int projectID, String status) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Connection con = DBUtil.getDBConnection();
        String sql = "UPDATE PROJECT_TBL SET STATUS=? WHERE PROJECT_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, projectID);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        con.close();
        return result;
    }
    public boolean updateProjectStatus(Connection con, int projectID, String status) throws SQLException {
        boolean result = false;
        String sql = "UPDATE PROJECT_TBL SET STATUS=? WHERE PROJECT_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, projectID);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        return result;
    }
    public boolean updateAwardedBid(int projectID, int bidID, String status) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Connection con = DBUtil.getDBConnection();
        String sql = "UPDATE PROJECT_TBL SET AWARDED_BID_ID=?,STATUS=? WHERE PROJECT_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bidID);
        ps.setString(2, status);
        ps.setInt(3, projectID);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        con.close();
        return result;
    }
    public boolean updateAwardedBid(Connection con, int projectID, int bidID, String status) throws SQLException {
        boolean result = false;
        String sql = "UPDATE PROJECT_TBL SET AWARDED_BID_ID=?,STATUS=? WHERE PROJECT_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bidID);
        ps.setString(2, status);
        ps.setInt(3, projectID);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        return result;
    }
    public List<Project> findActiveProjectsByClient(String clientUserID) throws ClassNotFoundException, SQLException {
        List<Project> projects = new ArrayList<Project>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM PROJECT_TBL WHERE CLIENT_USER_ID=? AND STATUS IN('OPEN','AWARDED')";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, clientUserID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Project project = new Project();
            project.setProjectID(rs.getInt("PROJECT_ID"));
            project.setClientUserID(rs.getString("CLIENT_USER_ID"));
            project.setProjectTitle(rs.getString("PROJECT_TITLE"));
            project.setProjectDescription(rs.getString("PROJECT_DESCRIPTION"));
            project.setBudgetMin(rs.getBigDecimal("BUDGET_MIN"));
            project.setBudgetMax(rs.getBigDecimal("BUDGET_MAX"));
            project.setPostedDate(rs.getDate("POSTED_DATE"));
            project.setStatus(rs.getString("STATUS"));
            int awardedBidID = rs.getInt("AWARDED_BID_ID");
            if (rs.wasNull()) {
                project.setAwardedBidID(null);
            } else {
                project.setAwardedBidID(awardedBidID);
            }
            projects.add(project);
        }
        rs.close();
        ps.close();
        con.close();
        return projects;
    }
}
