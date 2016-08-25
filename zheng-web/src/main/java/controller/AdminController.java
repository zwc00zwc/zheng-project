package controller;

import domain.model.system.Perm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spi.system.MemberSPI;
import spi.system.PermissionSPI;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
public class AdminController {
    @Resource
    private MemberSPI memberSPIService;

    @Resource
    private PermissionSPI permissionSPIService;

    @RequestMapping(value = "/admin/index")
    public String index(Model model){
        List list= memberSPIService.querylist();
        model.addAttribute("memberlist",list);
        return "/admin/index";
    }

    @RequestMapping(value = "/admin/add")
    public String addAdmin(){
        return "/admin/addadmin";
    }

    @RequestMapping(value = "/role/index")
    public String role(){
        return "admin/role";
    }

    @RequestMapping(value = "/role/addrole")
    public String addrole(){
        return "admin/addrole";
    }

    @RequestMapping(value = "/perm/index")
    public String permission(){
        return "/admin/permission";
    }

    @RequestMapping(value = "/perm/add")
    public String addPermission(){
        return "/admin/addpermission";
    }

    @RequestMapping(value = "/perm/adding")
    public String addPermissioning(Perm perm){
        if (perm.getId()>0){    //新增
            permissionSPIService.insertPerm(perm);
            return "";
        }
        permissionSPIService.updatePerm(perm);
        return "";
    }


}
