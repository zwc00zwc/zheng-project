package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spi.system.MemberSPI;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Resource
    private MemberSPI memberSPIService;

    @RequestMapping(value = "/index")
    public String index(Model model){
        List list= memberSPIService.querylist();
        model.addAttribute("memberlist",list);
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

    @RequestMapping(value = "/addrole")
    public String addrole(){
        return "admin/addrole";
    }

    @RequestMapping(value = "/perm")
    public String permission(){
        return "/admin/permission";
    }

    @RequestMapping(value = "/addperm")
    public String addPermission(){
        return "/admin/addpermission";
    }


}
