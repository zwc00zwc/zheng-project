package controller;

import ch.qos.logback.core.util.StringCollectionUtil;
import common.AuthUser;
import common.Constants;
import domain.dto.AuthMemberDto;
import domain.model.system.Member;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import spi.system.MemberSPI;
import utility.MD5Utility;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
public class LoginController extends BaseController {
    @Resource
    private MemberSPI memberSPIService;

    @RequestMapping(value = "/login",method = {RequestMethod.GET})
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/httplogin",method = {RequestMethod.POST})
    public ModelAndView httplogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
           modelAndView.addObject("msg","请输入用户名密码");
           return modelAndView;
        }
        Member member= memberSPIService.loginQuery(username);
        if (member==null){
            modelAndView.addObject("msg","用户名密码错误");
            return modelAndView;
        }
        if (!MD5Utility.toMD5(password).equals(member.getPassword())){
            modelAndView.addObject("msg","用户名密码错误");
            return modelAndView;
        }
        if (member.getStatus()==-1){
            modelAndView.addObject("msg","用户已禁用");
            return modelAndView;
        }
        if (MD5Utility.toMD5(password).equals(member.getPassword())){
//            String userinfo="";
//            userinfo+=""+member.getId()+","+member.getPhone()+","+member.getUserName()+"";
//            setCookie(response,userinfo);
            AuthUser authUser=new AuthUser();
            authUser.setId(member.getId());
            authUser.setUserName(member.getUserName());
            request.getSession().setAttribute(Constants.SESSION_USER_KEY,authUser);
            return new ModelAndView("redirect:/");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/loginout")
    public RedirectView loginout(HttpSession httpSession){
        httpSession.removeAttribute(Constants.SESSION_USER_KEY);
        return new RedirectView("/login", true);
    }
}
