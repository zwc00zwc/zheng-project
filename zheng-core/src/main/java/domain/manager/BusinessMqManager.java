package domain.manager;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import common.mongodb.MongodbManager;
import common.utility.DateUtility;
import domain.model.Job.JobLog;
import domain.model.PageModel;
import domain.model.mq.BusinessMqLog;
import domain.model.mq.query.BusinessMqLogQuery;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by alan.zheng on 2017/2/13.
 */
@Component
public class BusinessMqManager {
    public PageModel<BusinessMqLog> queryBusinessMqLogPage(BusinessMqLogQuery query){
        String collectionname="";
        if (query.getQueryDate()==null){
            collectionname= DateUtility.getStrFromDate(new Date(),"yyyyMMdd")+"_log";
        }else {
            collectionname= DateUtility.getStrFromDate(query.getQueryDate(),"yyyyMMdd")+"_log";
        }
        MongoCollection collection= MongodbManager.getDatabase("BusinessMqLog").getCollection(collectionname);
        List<BusinessMqLog> list=new ArrayList<BusinessMqLog>();
        BasicDBObject basicDBObject=new BasicDBObject();
        BasicDBObject timebasesic=new BasicDBObject();
        Calendar calendar  =  new GregorianCalendar();
        if (query.getStartTime()!=null){
            calendar.setTime(query.getStartTime());
            calendar.add(Calendar.HOUR,8);//时区关系加8小时
            timebasesic.append("$gte",calendar.getTime());
        }
        if (query.getEndTime()!=null){
            calendar.setTime(query.getEndTime());
            calendar.add(Calendar.HOUR,8);//时区关系加8小时
            timebasesic.append("$lte",calendar.getTime());
        }
        if (timebasesic.size()>0){
            basicDBObject.put("createTime",timebasesic);
        }
        MongoCursor mongoCursor = collection.find(basicDBObject).sort(new BasicDBObject("createTime", -1)).skip(query.getStartRow()).limit(query.getPageSize()).iterator();
        while (mongoCursor.hasNext()){
            Document document=(Document) mongoCursor.next();
            BusinessMqLog businessMqLog= new BusinessMqLog();
            businessMqLog.setLogLabel(document.get("logLabel").toString());
            businessMqLog.setLog(document.get("log").toString());
            calendar.setTime((Date) document.get("createTime"));
            calendar.add(Calendar.HOUR,-8);//时区关系加8小时
            businessMqLog.setCreateTime(calendar.getTime());
            list.add(businessMqLog);
        }
        int i=0;
        MongoCursor mongoCursor1 = collection.find(basicDBObject).sort(new BasicDBObject("createTime", -1)).iterator();
        while (mongoCursor1.hasNext()){
            i++;
            mongoCursor1.next();
        }
        PageModel<BusinessMqLog> pageModel=new PageModel<BusinessMqLog>(list,query.getCurrPage(),i,query.getPageSize());
        return pageModel;
    }
}
