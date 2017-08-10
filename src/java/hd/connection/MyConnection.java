/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cuk3t
 */
public class MyConnection {
    public static Connection getConn() throws SQLException, ClassNotFoundException{
       
            // Nếu bạn dùng Java6, thì ko cần dòng này cũng được.
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/house_decor";    
            String userName = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        
       
    }
}
