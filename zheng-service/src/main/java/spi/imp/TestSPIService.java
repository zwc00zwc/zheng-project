package spi.imp;

import spi.TestSPI;

/**
 * Created by Administrator on 2016/8/20.
 */
public class TestSPIService implements TestSPI {
    public void test() {
        System.out.println("test SPI service");
    }
}
