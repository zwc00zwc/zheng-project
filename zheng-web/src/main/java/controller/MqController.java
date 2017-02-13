package controller;

import domain.model.PageModel;
import domain.model.mq.BusinessMq;
import domain.model.mq.query.BusinessMqQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spi.businessmq.BusinessMqSPI;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/2/12.
 */
@Controller
public class MqController extends BaseController {
    @Resource
    private BusinessMqSPI businessMqSPIService;
    @RequestMapping(value = "/mqlistener/index")
    public String index(Model model,HttpSession httpSession){
        BusinessMqQuery query=new BusinessMqQuery();
        PageModel<BusinessMq> pageModel= businessMqSPIService.queryBusinessMqPage(query);
        model.addAttribute("mqs",pageModel);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/mq/index";
    }
}
