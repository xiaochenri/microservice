package com.microservice.cache;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hqc
 * @date 2020/3/25 12:20
 *
 * 使用缓存存储前端访问的返回的code值
 *
 */
public class CodeCache {

    private static final Map<String,String> codeMap = new ConcurrentHashMap<>();

    public void setCode(String code,String userId){

        if (StringUtils.isNoneBlank(code,userId)){

            if (!codeMap.containsKey(code)){
                codeMap.put(code,userId);
            }
        }
    }

    public String getUserIdByCode(String code){

        if (StringUtils.isNoneBlank(code)){

            return codeMap.get(code);
        }

        return "";
    }

    public void removeCode(String code){

        if (StringUtils.isNoneBlank(code)){

            if (codeMap.containsKey(code)){

                codeMap.remove(code);
            }

        }
    }


}
