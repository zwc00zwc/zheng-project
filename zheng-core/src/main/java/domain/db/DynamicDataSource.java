package domain.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by alan.zheng on 2017/1/3.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static ThreadLocal<String> dataSource=new ThreadLocal<String>();
    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceKey();
    }

    public static String getDataSourceKey(){
        return dataSource.get();
    }

    /**
     * 切换数据源（注意：切换数据源在数据库会话开启前操作）
     * @param datasource
     */
    public static void changeDataSourceKey(String datasource){
        dataSource.set(datasource);
    }

    /**
     * 使用默认数据源
     */
    public static void useDefaultDataSource() {
        DynamicDataSource.dataSource.remove();
    }
}
