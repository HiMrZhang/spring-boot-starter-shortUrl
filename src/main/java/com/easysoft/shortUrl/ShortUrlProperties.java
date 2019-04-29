package com.easysoft.shortUrl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@Data
@ConfigurationProperties(prefix = ShortUrlProperties.PREFIX)
public class ShortUrlProperties {

    public static final String PREFIX = "spring.shortUrl";

    public static final String SERVER_ID_ENABLED = PREFIX + ".enabled";

    private boolean enabled = true;
    /**
     * 数据源的bean name, 如果不设置获取默认数据源
     */
    private String dataSourceName;

    private String tableName = "link";

    private String domainName = "";

    public void setDomainName(String domainName) {
        if (!StringUtils.isEmpty(domainName)) {
            if (!domainName.endsWith("/")) {
                domainName += "/";
            }
            this.domainName = domainName;
        }

    }
}
