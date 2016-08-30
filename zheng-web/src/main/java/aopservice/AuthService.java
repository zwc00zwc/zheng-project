package aopservice;

import annotation.Auth;
import common.AuthUser;
import common.Constants;
import exception.AuthException;
import exception.LoginException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by XR on 2016/8/29.
 */
@Aspect
@Component
public class AuthService {
    @Pointcut("@annotation(annotation.Auth)")
    public void methodPointcut(){

    }

    @Before("@annotation(auth)")
    public void before(Auth auth) throws AuthException {
        System.out.println("before方法");
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= requestAttributes.getRequest();
        AuthUser authUser=(AuthUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
        if (authUser==null){
            throw new LoginException("未登陆");
        }
        String permstr=(String) request.getSession().getAttribute(Constants.SESSION_USER_PERM_KEY);
        String[] perms=permstr.split(",");
        boolean isauth=false;
        for (String perm:perms) {
            if (auth.rule()==perm){
                isauth=true;
                break;
            }
        }
        if (!isauth){
            throw new AuthException("没有权限");
        }
    }



//    @Around(value = "@annotation(auth)")
//    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint,Auth auth) throws Throwable {
//        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request= requestAttributes.getRequest();
//        AuthUser authUser=(AuthUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
//        if (authUser==null){
//            throw new LoginException("未登陆");
//        }
//        String permstr=(String) request.getSession().getAttribute(Constants.SESSION_USER_PERM_KEY);
//        String[] perms=permstr.split(",");
//        for (String perm:perms) {
//            if (auth.rule()==perm){
//                return proceedingJoinPoint.proceed();
//            }
//        }
//        throw new AuthException("没有权限");
//    }
}
