package org.apache.velocity.tools.view.context;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

/**
 * @auther a-de
 * @date 2018/10/20 14:06
 */
public class ChainedContext extends VelocityContext implements ViewContext {
    private Map toolbox;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext application;
    private VelocityEngine velocity;

    public ChainedContext(VelocityEngine velocity, HttpServletRequest request, HttpServletResponse response, ServletContext application) {
        this((Context)null, velocity, request, response, application);
    }

    public ChainedContext(Context ctx, VelocityEngine velocity, HttpServletRequest request, HttpServletResponse response, ServletContext application) {
        super((Map)null, ctx);
        this.velocity = velocity;
        this.request = request;
        this.response = response;
        this.session = request.getSession(false);
        this.application = application;
    }

    public void setToolbox(Map box) {
        this.toolbox = box;
        this.session = this.request.getSession(false);
    }

    public Map getToolbox() {
        return this.toolbox != null ? Collections.unmodifiableMap(this.toolbox) : null;
    }

    public Object internalGet(String key) {
        Object o = null;
        if (this.toolbox != null) {
            o = this.toolbox.get(key);
            if (o != null) {
                return o;
            }
        }

        if (key.equals("request")) {
            return this.request;
        } else if (key.equals("response")) {
            return this.response;
        } else if (key.equals("session")) {
            return this.session;
        } else if (key.equals("application")) {
            return this.application;
        } else {
            o = super.internalGet(key);
            return o != null ? o : this.getAttribute(key);
        }
    }

    public Object getAttribute(String key) {
        Object o = this.request.getAttribute(key);
        if (o == null) {
            if (this.session != null) {
                try {
                    o = this.session.getAttribute(key);
                } catch (IllegalStateException var4) {
                    o = null;
                }
            }

            if (o == null) {
                o = this.application.getAttribute(key);
            }
        }

        return o;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public ServletContext getServletContext() {
        return this.application;
    }

    public Context getVelocityContext() {
        return this;
    }

    public VelocityEngine getVelocityEngine() {
        return this.velocity;
    }
}
