package businessmq.db.dal;

import businessmq.db.BaseDB;
import businessmq.db.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by alan.zheng on 2017/2/14.
 */
public class BusinessMqDal {
    /**
     * insert并返回主键
     * @param config
     * @param msg
     * @return
     */
    public Integer insertMq(DbConfig config,String msg){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection=BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("INSERT INTO tb_message(message,status,createtime) VALUES (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,msg);
            preparedStatement.setInt(2,0);
            preparedStatement.setDate(3,new java.sql.Date(new Date().getTime()));
            preparedStatement.execute();
            resultSet=preparedStatement.getGeneratedKeys();
            if (resultSet!=null&&resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public boolean updateMqStatus(DbConfig config,Long id,Integer status){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        connection=BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("UPDATE SET tb_message status=?,updatetime=? WHERE id=?");
            int a=1;
            int b=1;
            int c=1;
            preparedStatement.setInt(a,status);
            preparedStatement.setDate(b,new java.sql.Date(new Date().getTime()));
            preparedStatement.setLong(c,id);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDB.dispose(connection,preparedStatement,null);
        }
        return false;
    }
}
