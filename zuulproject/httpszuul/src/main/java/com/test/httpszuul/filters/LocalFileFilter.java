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
 * @date 2019/6/3 14:40
 */
@Component
public class LocalFileFilter extends ZuulFilter {
    private final String jsString1 = "/static/js/jquery/jquery-1.12.4.min.js";
    private final String newJsString1 = "/files/jquery-1.12.4.min.js";


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
        String uri = RequestContext.getCurrentContext().getRequest().getRequestURI();
        if(uri.contains(jsString1)){
            System.out.println(uri);
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            ctx.set("requestURI", newJsString1);
            ctx.setRouteHost(new URL("http://localhost"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
