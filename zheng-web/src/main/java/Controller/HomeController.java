package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/8/20.
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
}
