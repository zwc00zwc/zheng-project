package controller;

import annotation.Auth;
import domain.model.Job.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spi.job.JobSPI;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
}
