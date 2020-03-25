package com.microservice.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hqc
 * @date 2020/3/25 12:03
 */
@Getter
@Setter
@ToString
public class HttpResult {

    private String status;
    private Object data;
    private String msgInfo;

    public HttpResult ok(){
        this.status = "200";
        return this;
    }

    public HttpResult fail(){
        this.status = "500";
        return this;
    }

    public HttpResult msg(String msgInfo){
        this.msgInfo = msgInfo;
        return this;
    }

    public HttpResult data(String data){
        this.data = data;
        return this;
    }
}
