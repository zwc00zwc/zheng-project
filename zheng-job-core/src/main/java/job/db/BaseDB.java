package job.db;

import common.utility.PropertiesUtility;

import java.io.IOException;
import java.sql.*;

/**
 * Created by alan.zheng on 2017/2/9.
 */
public class BaseDB {
    private static Connection getConnection(){
        PropertiesUtility propertiesUtility=new PropertiesUtility("job.properties");
        String driver =propertiesUtility.getProperty("jdbc.driver");
        String url = propertiesUtility.getProperty("jdbc.url");
        String username = propertiesUtility.getProperty("jdbc.username");
        String password = propertiesUtility.getProperty("jdbc.password");
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static ResultSet query(String sqlstr,String[] args) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement preparedStatement = (PreparedStatement)conn.prepareStatement(sqlstr);
        if (args!=null&&args.length>0){
            for (int i=1;i<=args.length;i++){
                preparedStatement.setString(i,args[i-1]);
            }
        }
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }
}
