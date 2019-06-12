package com.test.httpszuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wufan
 * @date 2019/6/3 14:33
 */
@Component
public class RewriteFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }


    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if(ctx.getRequest().getRequestURL().toString().contains("rewrite")){
            ctx.set("requestURI", "/static/css/default/portal/footer.css");
        }


//替换js文件url
        if(ctx.getRequest().getRequestURL().toString().contains("frontstyle.css")){
            String uri = request.getRequestURI().toString();
            String newUri = uri.replaceAll("frontstyle", "bootstrap.min");
            System.out.println("-----------------------------------------------------------");
            System.out.println(newUri);
            ctx.set("requestURI", newUri);
        }
//       替换js文件url
        if(ctx.getRequest().getRequestURL().toString().contains("layout")){
            String uri = request.getRequestURI().toString();
            String newUri = uri.replaceAll("layout", "footer");
            System.out.println("-----------------------------------------------------------");
            System.out.println(newUri);
            ctx.set("requestURI", newUri);
        }

        return null;
    }

}
