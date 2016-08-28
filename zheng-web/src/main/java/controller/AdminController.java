package controller;

import common.JsonResult;
import domain.dto.MemberDto;
import domain.model.system.Perm;
import domain.model.system.Role;
import domain.model.system.RolePerm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.system.MemberSPI;
import spi.system.PermissionSPI;
import spi.system.RolePermSPI;
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

    @Resource
    private RolePermSPI rolePermSPIService;

    @RequestMapping(value = "/admin/index")
    public String index(Model model){
        List<MemberDto> list= memberSPIService.querylistPage();
        model.addAttribute("memberlist",list);
        return "/admin/index";
    }

    @RequestMapping(value = "/admin/add")
    public String addAdmin(Model model){
        List list= roleSPIService.queryList();
        model.addAttribute("rolelist",list);
        return "/admin/addadmin";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/adding")
    public JsonResult addAdmining(){
        return jsonResult(1,"新增成功");
    }

    @RequestMapping(value = "/role/index")
    public String role(Model model){
        List list=roleSPIService.queryList();
        model.addAttribute("rolelist",list);
        return "admin/role";
    }

    @RequestMapping(value = "/role/add")
    public String addrole(Model model){
        List list=permissionSPIService.queryPermByLevel();
        model.addAttribute("permlist",list);
        return "admin/addrole";
    }

    @ResponseBody
    @RequestMapping(value = "/role/adding")
    public JsonResult addroleing(Role role, @RequestParam(value = "permids") String ids){
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        if (role.getId()!=null&&role.getId()>0){   //修改
            return jsonResult(1,"修改成功");
        }
        role= roleSPIService.insertRoleAndReturnId(role);
        if (role.getId()!=null&&role.getId()>0){
            rolePermSPIService.insertRolePermByList(ids,role.getId());
        }
        return jsonResult(1,"新增成功");
    }

    @ResponseBody
    @RequestMapping(value = "/role/delete")
    public JsonResult deleteRole(@RequestParam(value = "roleid") Long id){
        if (id>0){
            if (roleSPIService.deleteRole(id)>0){
                return jsonResult(1,"删除成功");
            }
        }
        return jsonResult(-1,"删除失败");
    }

    @RequestMapping(value = "/perm/index")
    public String permission(Model model){
        List list= permissionSPIService.queryList();
        model.addAttribute("permlist",list);
        return "/admin/permission";
    }

    @RequestMapping(value = "/perm/add")
    public String addPermission(Model model){
        List<Integer> integerList=new ArrayList<Integer>();
        integerList.add(0);
        integerList.add(1);
        List list= permissionSPIService.queryByType(integerList);
        model.addAttribute("permlist",list);
        return "/admin/addpermission";
    }

    @ResponseBody
    @RequestMapping(value = "/perm/adding")
    public JsonResult addPermissioning(Perm perm){
        if (perm.getParentId()!=null&&perm.getParentId()>0){
            Perm parent= permissionSPIService.queryById(perm.getParentId());
            if (parent!=null){
                int type=parent.getType()+1;
                perm.setType(type);
            }else {
                perm.setType(0);
            }
        }else {
            perm.setParentId((long)0);
            perm.setType(0);
        }
        if (perm.getId()!=null&&perm.getId()>0){    //修改
            perm.setCreateTime(new Date());
            perm.setUpdateTime(new Date());
            permissionSPIService.updatePerm(perm);
            return jsonResult(1,"修改成功");
        }
        perm.setCreateTime(new Date());
        perm.setUpdateTime(new Date());
        permissionSPIService.insertPerm(perm);
        return jsonResult(1,"新增成功");
    }


}
