package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login",method = {RequestMethod.GET})
    public String login(){
        return "login";
    }
}
