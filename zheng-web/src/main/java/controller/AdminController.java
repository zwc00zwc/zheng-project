package controller;

import annotation.Auth;
import common.JsonResult;
import domain.dto.MemberDto;
import domain.model.PageModel;
import domain.model.system.Member;
import domain.model.system.query.MemberQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spi.system.MemberSPI;
import spi.system.RoleSPI;
import common.utility.MD5Utility;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
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
    private RoleSPI roleSPIService;

    @Auth(rule = "/admin/index")
    @RequestMapping(value = "/admin/index")
    public String index(Model model, HttpSession httpSession) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MemberQuery query=new MemberQuery();
        PageModel<MemberDto> list= memberSPIService.queryPageList(query);
        model.addAttribute("memberlist",list);
        model.addAttribute("user",getAuthUser(httpSession));
        return "/admin/index";
    }
    @Auth(rule = "/admin/add")
    @RequestMapping(value = "/admin/add")
    public String add(Model model,@RequestParam(value = "memberid",required = false,defaultValue = "0") Long id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MemberDto member=new MemberDto();
        List list= roleSPIService.queryList();
        if (id>0){
            member=memberSPIService.queryById(id);
            model.addAttribute("rolelist",list);
            model.addAttribute("member",member);
            return "/admin/edit";
        }
        model.addAttribute("rolelist",list);
        model.addAttribute("member",member);
        return "/admin/add";
    }
    @Auth(rule ="/admin/add" )
    @ResponseBody
    @RequestMapping(value = "/admin/adding")
    public JsonResult adding(Member member,@RequestParam(value = "roleids") String ids){
        member.setStatus((short)1);
        member.setCreateTime(new Date());
        member.setPassword(MD5Utility.toMD5(member.getPassword()));
        if (member.getId()>0){

            return jsonResult(1,"修改成功");
        }
        memberSPIService.insertMember(member,ids);
        return jsonResult(1,"新增成功");
    }

    @Auth(rule = "/admin/edit")
    @RequestMapping(value = "/admin/edit")
    public String edit(){
        return "/admin/edit";
    }

    @Auth(rule = "/admin/edit")
    @ResponseBody
    @RequestMapping(value = "/admin/editing")
    public JsonResult editing(){
        return jsonResult(-1,"修改失败");
    }

    @Auth(rule = "/admin/start" )
    @ResponseBody
    @RequestMapping(value = "/admin/start")
    public JsonResult start(@RequestParam(value = "memberid") Long id){
        if (memberSPIService.updateStatus(id,(short) 1)>0){
            return jsonResult(1,"启用成功");
        }
        return jsonResult(-1,"启用失败");
    }
    @Auth(rule = "/admin/stop")
    @ResponseBody
    @RequestMapping(value = "/admin/stop")
    public JsonResult stop(@RequestParam(value = "memberid") Long id){
        if (memberSPIService.updateStatus(id,(short)-1)>0){
            return jsonResult(1,"禁用成功");
        }
        return jsonResult(-1,"禁用失败");
    }

    @Auth(rule = "/admin/delete")
    @ResponseBody
    @RequestMapping(value = "/admin/delete")
    public JsonResult delete(@RequestParam(value = "memberid") Long id){
        if (id>0){
            if (memberSPIService.deleteMember(id)>0){
                return jsonResult(1,"删除成功");
            }
        }
        return jsonResult(-1,"删除失败");
    }
}
