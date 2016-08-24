package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spi.TestSPI;
import spi.system.MemberSPI;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
@Controller
public class HomeController {
    @Resource
    private MemberSPI memberSPIService;
    @Resource
    private TestSPI testSPI;
    @RequestMapping(value = "/")
    public String index(){
        testSPI.test();
        List list= memberSPIService.querylist();
        System.out.println(testSPI.test2());
        return "index";
    }

    @RequestMapping(value = "/welcome",method = {RequestMethod.GET})
    public String welcome(){
        return "welcome";
    }
}
