package common.utility;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created by Administrator on 2017/1/18.
 */
public class DateUtility {
    public static String getStrFromDate(Date date,String format){
        DateFormat dateFormat=null;
        if (StringUtils.isEmpty(format)){
            dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }else {
            dateFormat=new SimpleDateFormat(format);
        }
        return dateFormat.format(date);
    }
}
