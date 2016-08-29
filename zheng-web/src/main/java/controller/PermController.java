package controller;

import annotation.Auth;
import common.JsonResult;
import domain.model.system.Perm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.system.PermissionSPI;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/29.
 */
@Controller
public class PermController extends BaseController {

    @Resource
    private PermissionSPI permissionSPIService;
    @Auth
    @RequestMapping(value = "/perm/index")
    public String permission(Model model){
        List list= permissionSPIService.queryList();
        model.addAttribute("permlist",list);
        return "/perm/index";
    }
    @Auth
    @RequestMapping(value = "/perm/add")
    public String addPermission(Model model){
        List<Integer> integerList=new ArrayList<Integer>();
        integerList.add(0);
        integerList.add(1);
        List list= permissionSPIService.queryByType(integerList);
        model.addAttribute("permlist",list);
        return "/perm/add";
    }
    @Auth
    @ResponseBody
    @RequestMapping(value = "/perm/adding")
    public JsonResult addPermissioning(Perm perm){
        if (perm.getParentId()!=null&&perm.getParentId()>0){
            Perm parent= permissionSPIService.queryById(perm.getParentId());
            if (parent!=null){
                int type=parent.getType()+1;
                perm.setType(type);
            }else {
                perm.setType(1);
            }
        }else {
            perm.setParentId((long)0);
            perm.setType(1);
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
