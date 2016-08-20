package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spi.TestSPI;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/8/20.
 */
@Controller
public class HomeController {
    //@Resource
    //private TestSPI testSPI;
    @RequestMapping(value = "/")
    public String index(){
        //testSPI.test();
        return "index";
    }
}
