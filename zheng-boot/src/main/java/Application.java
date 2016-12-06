import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;

/**
 * Created by XR on 2016/12/5.
 */
@SpringBootApplication
public class Application {
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            MqListener.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        for (int i=0;i<10;i++){
//            try {
//                Thread.sleep(1000);
//                System.out.print("现在时间"+new Date().toString());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
