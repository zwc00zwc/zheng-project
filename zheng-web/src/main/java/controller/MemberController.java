package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/8/21.
 */
@Controller
public class MemberController {
    @RequestMapping(value = "/addmember")
    public String addMember(){
        return "member/addmember";
    }
}
