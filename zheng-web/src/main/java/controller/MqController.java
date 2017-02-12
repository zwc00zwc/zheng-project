package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/2/12.
 */
@Controller
public class MqController extends BaseController {
    @RequestMapping(value = "/mqlistener/index")
    public String index(){
        return "/mq/index";
    }
}
