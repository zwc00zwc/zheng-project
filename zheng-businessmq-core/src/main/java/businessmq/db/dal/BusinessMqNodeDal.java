package businessmq.db.dal;

import businessmq.db.BaseDB;
import businessmq.db.DbConfig;
import businessmq.db.model.BusinessMqNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan.zheng on 2017/2/15.
 */
public class BusinessMqNodeDal {
    public List<BusinessMqNode> queryNodeList(DbConfig dbConfig){
        List<BusinessMqNode> nodeList=new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection= BaseDB.getConnection(dbConfig);
        try {
            preparedStatement=connection.prepareStatement("SELECT `id`,`node`,`driver`,`url`,`username`,`password` FROM tb_businessmq_node");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                BusinessMqNode node=new BusinessMqNode();
                node.setId(resultSet.getLong("id"));
                node.setNode(resultSet.getInt("node"));
                node.setDriver(resultSet.getString("driver"));
                node.setUrl(resultSet.getString("url"));
                node.setUsername(resultSet.getString("username"));
                node.setPassword(resultSet.getString("password"));
                nodeList.add(node);
            }
        } catch (SQLException e) {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }finally {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }
        return nodeList;
    }
}
