package com.example.service_1.config;

import com.example.service_1.filter.SysContent;
import com.microservice.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hqc
 * @date 2020/3/27 11:32
 */
public class CustomFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = SysContent.getRequest();

        if (request != null){

            String authentication = request.getHeader(CommonConstants.AUTHENTICATION_INFO_HEADER);
            String trackId = request.getHeader(CommonConstants.REQUEST_TRACK_ID_HEADER);

            if (StringUtils.isNoneBlank(authentication)){
                template.header(CommonConstants.AUTHENTICATION_INFO_HEADER,authentication);
            }

            if (StringUtils.isNoneBlank(trackId)){
                template.header(CommonConstants.REQUEST_TRACK_ID_HEADER,trackId);
            }
        }

    }
}
