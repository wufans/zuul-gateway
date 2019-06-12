package com.test.httpszuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * @author wufan
 * @date 2019/6/3 14:33
 */
@Component
public class ModifyDomFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 999;
    }

    @Override
    public boolean shouldFilter() {
        String uri = RequestContext.getCurrentContext().getRequest().getRequestURI();
        if(uri.contains("png") || uri.contains("js")|| uri.contains("get") || uri.contains("clockInfo")||uri.contains("isShwoClock")){
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        InputStream compressedResponseDataStream = ctx.getResponseDataStream();

        try {
            // Uncompress and transform the response
            InputStream responseDataStream = new GZIPInputStream(compressedResponseDataStream);
            String responseAsString = StreamUtils.copyToString(responseDataStream, Charset.forName("UTF-8"));
            // Do want you want with your String response
            System.out.println(responseAsString);
            // Replace the response with the modified object
//            ctx.setResponseBody(responseAsString);
        } catch (IOException e) {
        }



//        InputStream responseDataStream = ctx.getResponseDataStream();
//        try {
//            String a = StringUtils.newStringUtf8(GZIPUtil.uncompress(StreamUtils.copyToByteArray(responseDataStream)));
//            System.out.println(a);
////            InputStream is = new ByteArrayInputStream(GZIPUtil.compress(a));
////            ctx.setResponseDataStream(is);
//            ctx.setResponseBody(GZIPUtil.compress(a).toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}