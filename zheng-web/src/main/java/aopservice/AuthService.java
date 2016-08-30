package aopservice;

import annotation.Auth;
import common.AuthUser;
import common.Constants;
import exception.AuthException;
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

//    @Before("methodPointcut()")
//    public void before() throws AuthException {
//        System.out.println("before方法");
//        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request= requestAttributes.getRequest();
//        AuthUser authUser=(AuthUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
//        if (authUser==null){
//            throw new AuthException("未登陆");
//        }
//    }



    @Around(value = "@annotation(auth)")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint,Auth auth) throws Throwable {
        System.out.println("auth:"+auth.rule());
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= requestAttributes.getRequest();
        AuthUser authUser=(AuthUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
        String perms=(String) request.getSession().getAttribute(Constants.SESSION_USER_PERM_KEY);
        if (authUser==null){
            throw new AuthException("未登陆");
        }
        return proceedingJoinPoint.proceed();
    }
}
