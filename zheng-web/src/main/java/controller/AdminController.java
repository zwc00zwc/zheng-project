package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/index")
    public String index(){
        return "/admin/index";
    }

    @RequestMapping(value = "/add")
    public String addAdmin(){
        return "/admin/addadmin";
    }

    @RequestMapping(value = "/role")
    public String role(){
        return "admin/role";
    }

    @RequestMapping(value = "/perm")
    public String permission(){
        return "/admin/permission";
    }
}
