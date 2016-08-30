package handler;

import exception.AuthException;
import log.LogUtility;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by XR on 2016/8/29.
 */
public class AuthExceptionHandler implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //LogUtility.insertLog(e.getClass().getName(),e.getMessage());
        if (e instanceof AuthException){
            String requestheader= httpServletRequest.getHeader("X-Requested-With");
            if (requestheader!=null){
                return new ModelAndView("redirect:/nologin");
            }
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/404");
    }
}
