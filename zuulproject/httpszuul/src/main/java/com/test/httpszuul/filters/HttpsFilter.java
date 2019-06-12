package com.test.httpszuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_FORWARD_FILTER_ORDER;

/**
 * @author wufan
 * @date 2019/6/3 14:28
 */
@Component
public class HttpsFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "route";
    }
    @Override
    public int filterOrder() {
        return 99;
    }


    @Override
    public boolean shouldFilter() {
        String url = RequestContext.getCurrentContext().getRequest().getRequestURL().toString();
        if(url.contains("https")){
            System.err.println("视频播放:"+url);
            return true;
        }
        return false;
//        return true;
    }

    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
//        String uri = RequestContext.getCurrentContext().getRequest().getRequestURI();
        try {
//            ctx.set("requestURI", newJsString1);
            ctx.setRouteHost(new URL("https://vod.300hu.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
//        获取Request内容
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//
//        String requestURL = request.getRequestURL().toString();
//        String apiName = request.getParameter("apiName");
//        String data = request.getParameter("data");
////        System.out.println("URL:"+requestURL+"  ApiName:"+apiName+" data:"+data);
//        System.out.println("URL:"+requestURL);
//        return null;
    }
}

