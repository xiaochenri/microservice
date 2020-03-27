package com.example.gateway.router;

import com.microservice.CommonConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 给每个request 加入唯一值头部，以便于后续服务中的链路追踪
 */
@Component
public class TrackIdFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();

        String s = UUID.randomUUID().toString();

        currentContext.addZuulRequestHeader(CommonConstants.REQUEST_TRACK_ID_HEADER,s);

        return null;
    }
}
