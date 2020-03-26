package com.example.gateway.router;

import com.example.gateway.util.HttpUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hqc
 * @date 2020/3/26 15:20
 * <p>
 * <p>
 * 我们可以在网关处通过对zuul filter的拓展，合理的在此处安排我们的一些处理
 * 比如请求信息收集、请求信息埋点、身份信息授予
 */
@Slf4j
@Component
public class RequestInfoFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        //后续拓展
        StringBuilder builder = new StringBuilder();
        builder.append(dateFormat.format(new Date())).append(" ")
                .append(HttpUtil.getIpAddress(request));

        log.info(builder.toString());

        return null;
    }
}
