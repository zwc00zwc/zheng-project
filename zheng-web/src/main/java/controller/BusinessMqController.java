package controller;

import annotation.Auth;
import common.JsonResult;
import common.utility.DateUtility;
import domain.model.PageModel;
import domain.model.mq.BusinessMq;
import domain.model.mq.BusinessMqLog;
import domain.model.mq.BusinessMqNode;
import domain.model.mq.query.BusinessMqLogQuery;
import domain.model.mq.query.BusinessMqNodeQuery;
import domain.model.mq.query.BusinessMqQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.businessmq.BusinessMqSPI;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/2/12.
 */
@Controller
public class BusinessMqController extends BaseController {
    @Resource
    private BusinessMqSPI businessMqSPIService;

    @Auth(rule ="/businessmq/index")
    @RequestMapping(value = "/businessmq/index")
    public String index(Model model,HttpSession httpSession){
        BusinessMqQuery query=new BusinessMqQuery();
        PageModel<BusinessMq> pageModel= businessMqSPIService.queryBusinessMqPage(query);
        model.addAttribute("mqs",pageModel);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/mq/index";
    }

    @Auth(rule ="/businessmq/node")
    @RequestMapping(value = "/businessmq/node")
    public String node(Model model,HttpSession httpSession){
        BusinessMqNodeQuery query=new BusinessMqNodeQuery();
        PageModel<BusinessMqNode> pageModel= businessMqSPIService.queryBusinessMqNodePage(query);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/mq/node";
    }

    @Auth(rule ="/businessmq/node/add")
    @RequestMapping(value = "/businessmq/node/add")
    public String nodeAdd(){
        return "/mq/nodeadd";
    }

    @Auth(rule ="/businessmq/node/add")
    @ResponseBody
    @RequestMapping(value = "/businessmq/node/adding")
    public JsonResult nodeAdding(BusinessMqNode businessMqNode){
        try {
            if (businessMqSPIService.insertNode(businessMqNode)){
                return jsonResult(1,"新增成功");
            }
        } catch (Exception e) {
            return jsonResult(-1,"新增失败");
        }
        return jsonResult(-1,"新增失败");
    }

    @Auth(rule ="/businessmq/log")
    @RequestMapping(value = "/businessmq/log")
    public String log(Model model,String queryDate,String startTime,String endTime,Integer currPage,HttpSession httpSession){
        BusinessMqLogQuery query=new BusinessMqLogQuery();
        query.setQueryDate(DateUtility.getDateFromStr(queryDate,"yyyy-MM-dd"));
        if (currPage!=null&&currPage>0){
            query.setCurrPage(currPage);
        }
        if (StringUtils.isNotEmpty(startTime)){
            query.setStartTime(DateUtility.getDateFromStr(queryDate +" "+ startTime,"yyyy-MM-dd hh:mm:ss"));
        }
        if (StringUtils.isNotEmpty(endTime)){
            query.setEndTime(DateUtility.getDateFromStr(queryDate +" "+ endTime,"yyyy-MM-dd hh:mm:ss"));
        }
        PageModel<BusinessMqLog> logPageModel= businessMqSPIService.queryBusinessMqLogPage(query);
        model.addAttribute("queryDate",queryDate);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        model.addAttribute("mqlogs",logPageModel);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/mq/log";
    }
}
