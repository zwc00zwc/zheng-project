package spiservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by alan.zheng on 2017/2/5.
 */
@Controller
public class HomeController {
    @ResponseBody
    @RequestMapping(value = "/")
    public String index(){
        return "Services已启动";
    }
}
