package spi.impl;

import org.springframework.stereotype.Service;
import spi.TestSPI;

/**
 * Created by alan.zheng on 2017/12/8.
 */
@Service("testSpiService")
public class TestSPIImpl implements TestSPI {
    public void test() {
        System.out.println("test SPI service");
    }

    public String test2() {
        return "test2";
    }
}
