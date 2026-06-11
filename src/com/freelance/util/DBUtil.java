package com.freelance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {	
    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe",
            "freelance_user",
            "freelance_pwd"
        );
        return con;
    }
}
