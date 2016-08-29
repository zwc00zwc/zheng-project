package controller;

import annotation.Auth;
import common.JsonResult;
import domain.model.system.Role;
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
import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/29.
 */
@Controller
public class RoleController extends BaseController {

    @Resource
    private PermissionSPI permissionSPIService;

    @Resource
    private RoleSPI roleSPIService;

    @Resource
    private RolePermSPI rolePermSPIService;
    @Auth
    @RequestMapping(value = "/role/index")
    public String index(Model model){
        List list=roleSPIService.queryList();
        model.addAttribute("rolelist",list);
        return "/role/index";
    }
    @Auth
    @RequestMapping(value = "/role/add")
    public String add(Model model){
        List list=permissionSPIService.queryPermByLevel();
        model.addAttribute("permlist",list);
        return "/role/add";
    }
    @Auth
    @ResponseBody
    @RequestMapping(value = "/role/adding")
    public JsonResult adding(Role role, @RequestParam(value = "permids") String ids){
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
    @Auth
    @ResponseBody
    @RequestMapping(value = "/role/delete")
    public JsonResult delete(@RequestParam(value = "roleid") Long id){
        if (id>0){
            if (roleSPIService.deleteRole(id)>0){
                return jsonResult(1,"删除成功");
            }
        }
        return jsonResult(-1,"删除失败");
    }
}
