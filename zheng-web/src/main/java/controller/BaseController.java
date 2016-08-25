package controller;

import common.JsonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XR on 2016/8/25.
 */
public abstract class BaseController {
    protected boolean isAjax(HttpServletRequest request){
        String requestheader= request.getHeader("X-Requested-With");
        if (requestheader!=null){
            return true;
        }
        return false;
    }

    protected JsonResult jsonResult(Integer _code, String _msg){
        JsonResult jsonResult=new JsonResult(_code,_msg);
        return  jsonResult;
    }

    protected JsonResult jsonResult(Integer _code,String _msg,Object _data){
        JsonResult jsonResult=new JsonResult(_code,_msg,_data);
        return jsonResult;
    }
}
