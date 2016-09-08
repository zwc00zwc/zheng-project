package common;

import spi.system.PermissionSPI;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by XR on 2016/9/8.
 */
public class PermissionUtility {
    private static ThreadLocal<String> threadLocal=new ThreadLocal<String>();

    public static String getPerms(){
        return threadLocal.get();
    }
    public static void changePerms(String perms){
        threadLocal.set(perms);
    }
}
