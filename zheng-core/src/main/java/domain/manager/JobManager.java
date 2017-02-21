package domain.manager;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import common.mongodb.MongodbManager;
import common.utility.DateUtility;
import domain.mapper.JobMapper;
import domain.model.Job.Job;
import domain.model.Job.JobLog;
import domain.model.Job.query.JobLogQuery;
import domain.model.Job.query.JobQuery;
import domain.model.PageModel;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by alan.zheng on 2017/1/18.
 */
@Component
public class JobManager {
    @Autowired
    private JobMapper jobMapper;

    public Job queryById(Long jobId){
        return jobMapper.queryById(jobId);
    }

    public List<Job> queryList(){
        return jobMapper.queryList();
    }

    public PageModel<Job> queryPageList(JobQuery query){
        List<Job> list= jobMapper.queryPageList(query);
        int count=jobMapper.queryCountPage(query);
        PageModel<Job> pageModel=new PageModel<Job>(list,query.getCurrPage(),count,query.getPageSize());
        return pageModel;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertJob(Job job){
        if (jobMapper.insertJob(job)>0){
            return true;
        }
        return false;
    }

    public PageModel<JobLog> queryJobLogList(JobLogQuery query){
        String collectionname="";
        if (query.getQueryDate()==null){
            collectionname= DateUtility.getStrFromDate(new Date(),"yyyyMMdd")+"_log";
        }else {
            collectionname= DateUtility.getStrFromDate(query.getQueryDate(),"yyyyMMdd")+"_log";
        }
        MongoCollection collection= MongodbManager.getDatabase("JobLog").getCollection(collectionname);
        List<JobLog> list=new ArrayList<JobLog>();
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
            JobLog jobLog= new JobLog();
            jobLog.setJogName(document.get("jobName").toString());
            jobLog.setLog(document.get("log").toString());
            calendar.setTime((Date) document.get("createTime"));
            calendar.add(Calendar.HOUR,-8);//时区关系加8小时
            jobLog.setCreateTime(calendar.getTime());
            list.add(jobLog);
        }
        int i=0;
        MongoCursor mongoCursor1 = collection.find(basicDBObject).sort(new BasicDBObject("createTime", -1)).iterator();
        while (mongoCursor1.hasNext()){
            i++;
            mongoCursor1.next();
        }
        PageModel<JobLog> pageModel=new PageModel<JobLog>(list,query.getCurrPage(),i,query.getPageSize());
        return pageModel;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteJob(Long jobId){
        if (jobMapper.deleteJob(jobId)>0){
            return true;
        }
        return false;
    }
}
