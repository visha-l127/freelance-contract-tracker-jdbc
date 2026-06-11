package com.freelance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.freelance.bean.User;
import com.freelance.util.DBUtil;

public class UserDAO {

    public User findUser(String userID) throws ClassNotFoundException, SQLException {
        User user = null;
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM USERS_TBL WHERE USER_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setUserID(rs.getString("USER_ID"));
            user.setFullName(rs.getString("FULL_NAME"));
            user.setEmail(rs.getString("EMAIL"));
            user.setMobile(rs.getString("MOBILE"));
            user.setRole(rs.getString("ROLE"));
            user.setPrimarySkillOrCompany(rs.getString("PRIMARY_SKILL_OR_COMPANY"));
            user.setStatus(rs.getString("STATUS"));
        }

        rs.close();
        ps.close();
        con.close();
        return user;
    }

    public List<User> viewAllUsers() throws ClassNotFoundException, SQLException {
        List<User> users = new ArrayList<User>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM USERS_TBL";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User user = new User();
            user.setUserID(rs.getString("USER_ID"));
            user.setFullName(rs.getString("FULL_NAME"));
            user.setEmail(rs.getString("EMAIL"));
            user.setMobile(rs.getString("MOBILE"));
            user.setRole(rs.getString("ROLE"));
            user.setPrimarySkillOrCompany(rs.getString("PRIMARY_SKILL_OR_COMPANY"));
            user.setStatus(rs.getString("STATUS"));
            users.add(user);
        }

        rs.close();
        ps.close();
        con.close();
        return users;
    }

    public boolean insertUser(User user) throws ClassNotFoundException, SQLException {
        Connection con = DBUtil.getDBConnection();
        String sql = "INSERT INTO USERS_TBL(USER_ID,FULL_NAME,EMAIL,MOBILE,ROLE,PRIMARY_SKILL_OR_COMPANY,STATUS) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getUserID());
        ps.setString(2, user.getFullName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getMobile());
        ps.setString(5, user.getRole());
        ps.setString(6, user.getPrimarySkillOrCompany());
        ps.setString(7, user.getStatus());

        int rows = ps.executeUpdate();

        ps.close();
        con.close();

        return rows > 0;
    }

    public boolean updateUserStatus(String userID, String status) throws ClassNotFoundException, SQLException {
        Connection con = DBUtil.getDBConnection();
        String sql = "UPDATE USERS_TBL SET STATUS=? WHERE USER_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, status);
        ps.setString(2, userID);

        int rows = ps.executeUpdate();

        ps.close();
        con.close();

        return rows > 0;
    }

    public boolean deleteUser(String userID) throws ClassNotFoundException, SQLException {
        Connection con = DBUtil.getDBConnection();
        String sql = "DELETE FROM USERS_TBL WHERE USER_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, userID);

        int rows = ps.executeUpdate();

        ps.close();
        con.close();

        return rows > 0;
    }
}