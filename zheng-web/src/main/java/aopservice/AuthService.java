package aopservice;

import common.AuthUser;
import common.Constants;
import exception.AuthException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XR on 2016/8/29.
 */
@Aspect
@Component
public class AuthService {
    @Pointcut("@annotation(annotation.Auth)")
    public void methodPointcut(){

    }

    @Before("methodPointcut()")
    public void before() throws AuthException {
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= requestAttributes.getRequest();
        AuthUser authUser=(AuthUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
        if (authUser==null){
            throw new AuthException(1);
        }
    }
}
