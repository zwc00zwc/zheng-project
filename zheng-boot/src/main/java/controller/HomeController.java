package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XR on 2016/12/19.
 */
@RestController
public class HomeController {
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
}
