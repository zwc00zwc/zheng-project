package controller;

import annotation.Auth;
import common.JsonResult;
import domain.model.Job.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.job.JobSPI;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by alan.zheng on 2017/1/18.
 */
@Controller
public class JobController extends BaseController {
    @Resource
    private JobSPI jobSPIService;

    @Auth(rule = "/job/index")
    @RequestMapping(value = "/job/index")
    public String index(Model model,HttpSession httpSession){
        List<Job> jobs= jobSPIService.queryList();
        model.addAttribute("jobs",jobs);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/job/index";
    }

    @Auth(rule = "/job/add")
    @RequestMapping(value = "/job/add")
    public String add(){
        return "/job/add";
    }

    @Auth(rule ="/admin/add")
    @ResponseBody
    @RequestMapping(value = "/job/adding")
    public JsonResult adding(Job job){
        job.setCreateTime(new Date());
        try {
            jobSPIService.insertJob(job);
        } catch (Exception e) {
            return jsonResult(-1,"新增失败");
        }
        return jsonResult(1,"新增成功");
    }
}
