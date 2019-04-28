# 短网址功能

作者：掌少

## 1. 概述

支持短网址生成、解码等操作

## 2. 使用方法

## 2.1 导入依赖包

    compile "com.easysoft:spring-boot-starter-shortUrl:xxx"

## 2.2 配置

	spring:
      shortUrl:
        enabled:true            #是否支持短网址，默认true
        dataSourceName: XXXXX   #数据源spring容器中实例名
        tableName: xxxx         #短链存储表名
        domainName:'http://www.baidu.com/' #短网址域名,默认为空字符串

### 2.3 使用方法

    ......
    @Autowired
    ShortUrlGenerator shortUrlGenerator;
    ......
