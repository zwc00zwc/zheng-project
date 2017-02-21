package controller;

import annotation.Auth;
import common.JsonResult;
import domain.dto.RolePermDto;
import domain.model.PageModel;
import domain.model.system.Role;
import domain.model.system.query.RoleQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.system.PermissionSPI;
import spi.system.RoleSPI;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
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
    @Auth(rule = "/role/index")
    @RequestMapping(value = "/role/index")
    public String index(Model model,Integer currPage){
        RoleQuery query=new RoleQuery();
        PageModel<Role> list=roleSPIService.queryPageList(query);
        model.addAttribute("rolelist",list);
        return "/role/index";
    }
    @Auth(rule = "/role/add")
    @RequestMapping(value = "/role/add")
    public String add(Model model){
        List list=permissionSPIService.queryPermByLevel();
        RolePermDto rolePermDto=new RolePermDto();
        model.addAttribute("permlist",list);
        model.addAttribute("role",rolePermDto);
        return "/role/add";
    }
    @Auth(rule = "/role/add")
    @ResponseBody
    @RequestMapping(value = "/role/adding")
    public JsonResult adding(Role role, @RequestParam(value = "permids") String ids){
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        if (roleSPIService.insertRole(role,ids)){
            return jsonResult(1,"新增成功");
        }
        return jsonResult(-1,"新增失败");
    }

    @Auth(rule = "/role/edit")
    @RequestMapping(value = "/role/edit")
    public String edit(Model model,@RequestParam(value = "roleid",defaultValue = "0",required = false) Long id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List list=permissionSPIService.queryPermByLevel();
        RolePermDto rolePermDto=roleSPIService.queryDtoById(id);
        model.addAttribute("permlist",list);
        model.addAttribute("role",rolePermDto);
        return "/role/edit";
    }

    @Auth(rule = "/role/edit")
    @ResponseBody
    @RequestMapping(value = "/role/editing")
    public JsonResult editing(Role role, @RequestParam(value = "permids") String ids){
        if (role.getId()!=null&&role.getId()>0){   //修改
            try {
                if (roleSPIService.updateRole(role,ids)){
                    return jsonResult(1,"修改成功");
                }
            } catch (Exception e) {
                return jsonResult(-1,"修改失败");
            }
        }
        return jsonResult(-1,"修改失败");
    }

    @Auth(rule = "/role/delete")
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

    @Auth(rule = "/role/resetadmin")
    @ResponseBody
    @RequestMapping(value = "/role/resetadmin")
    public JsonResult resetAdmin(){
        if (roleSPIService.resetadmin()){
            return jsonResult(1,"重置成功");
        }
        return jsonResult(-1,"重置失败");
    }
}
