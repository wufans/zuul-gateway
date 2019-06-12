package com.test.httpszuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER;

/**
 * @author wufan
 * @date 2019/6/5 14:40
 */
@Component
public class VideoFilter extends ZuulFilter {
    private final String requestHost = "upload.man";
    private final String rewriteHost = "http://upload.man.test.com/";


    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        //        99
        return SIMPLE_HOST_ROUTING_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        String url = RequestContext.getCurrentContext().getRequest().getRequestURL().toString();
        if(url.contains(requestHost)){
            System.err.println(url);
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
//        String uri = RequestContext.getCurrentContext().getRequest().getRequestURI();
        try {
//            ctx.set("requestURI", newJsString1);
            ctx.setRouteHost(new URL(rewriteHost));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
