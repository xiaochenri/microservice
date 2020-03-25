# microservice

微服务架构，涉及模块分为以下几部分：
1.web端，web与后端分离，web直访auth模块获取code，在访问网关获取token信息
2.auth模块，主要用户授权验证，并进行单点登录
3.gateway，网关模块，做请求的分发、负载均衡、请求验证、过滤等工作
4.service，服务模块，根据具体需求添加，不做服务的登录等验证，需要涉及到登录用户
信息获取时请求auth模块进行获取
5.nacos,注册中心，配置中心
6.elk，日志收集

可考虑加入seate，sentinel等，支持国产！！！ alibaba牛逼