package spi;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/20.
 */
public interface TestSPI extends Serializable {
    void test();

    String test2();
}
