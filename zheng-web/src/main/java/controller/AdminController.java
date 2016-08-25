package controller;

import common.JsonResult;
import domain.dto.MemberDto;
import domain.model.system.Perm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.system.MemberSPI;
import spi.system.PermissionSPI;
import spi.system.RoleSPI;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
public class AdminController extends BaseController {
    @Resource
    private MemberSPI memberSPIService;

    @Resource
    private PermissionSPI permissionSPIService;

    @Resource
    private RoleSPI roleSPIService;

    @RequestMapping(value = "/admin/index")
    public String index(Model model){
        List<MemberDto> list= memberSPIService.querylistPage();
        for (MemberDto dto:list) {
            List<String> str=new ArrayList<String>();
            str.add("role1");
            str.add("role2");
            str.add("role3");
            dto.setRoles(str);
        }
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

    @RequestMapping(value = "/role/add")
    public String addrole(Model model){

        return "admin/addrole";
    }

    @RequestMapping(value = "/perm/index")
    public String permission(Model model){
        List list= permissionSPIService.queryList();
        model.addAttribute("permlist",list);
        return "/admin/permission";
    }

    @RequestMapping(value = "/perm/add")
    public String addPermission(Model model){
        List list= permissionSPIService.queryList();
        model.addAttribute("permlist",list);
        return "/admin/addpermission";
    }

    @ResponseBody
    @RequestMapping(value = "/perm/adding")
    public JsonResult addPermissioning(Perm perm){
        if (perm.getId()!=null&&perm.getId()>0){    //修改
            if (perm.getParentId()==null){
                perm.setParentId((long)0);
            }
            perm.setCreateTime(new Date());
            perm.setUpdateTime(new Date());
            permissionSPIService.updatePerm(perm);
            return jsonResult(1,"修改成功");
        }
        if (perm.getParentId()==null){
            perm.setParentId((long)0);
        }
        perm.setCreateTime(new Date());
        perm.setUpdateTime(new Date());
        permissionSPIService.insertPerm(perm);
        return jsonResult(1,"新增成功");
    }


}
