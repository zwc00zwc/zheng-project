package spiservice;

import org.springframework.stereotype.Service;
import spi.TestSPI;

/**
 * Created by Administrator on 2016/8/20.
 */
@Service("testSpiService")
public class TestSPIService extends BaseSPIService implements TestSPI {
    public void test() {
        System.out.println("test SPI service");
    }

    public String test2() {
        return "test2";
    }
}
