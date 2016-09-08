package toolbox;

import common.PermissionUtility;
import org.springframework.stereotype.Component;
import spi.system.PermissionSPI;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by XR on 2016/9/8.
 */
public class Authority {
    public boolean isPermission(String perm){
        String strings=PermissionUtility.getPerms();
        String[] perms=strings.split(",");
        boolean isauth=false;
        for (String permstr:perms) {
            if (permstr.equals(perm)){
                isauth=true;
                break;
            }
        }
        return isauth;
    }
}
