/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: renjinhao
 * @date: 2018年7月2日 上午11:07:44
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.easysoft.shortUrl;

import com.easysoft.shortUrl.db.MysqlShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableConfigurationProperties(ShortUrlProperties.class)
public class ShortUrlAutoConfiguration implements BeanFactoryAware {

    @Autowired
    private ShortUrlProperties shortUrlProperties;

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean(ShortUrlGenerator.class)
    @ConditionalOnProperty(value = ShortUrlProperties.SERVER_ID_ENABLED, matchIfMissing = true)
    public ShortUrlGenerator shortUrlGenerator() {
        DataSource dataSource = null;
        String dataSourceName = shortUrlProperties.getDataSourceName();
        if (null != dataSourceName && dataSourceName.length() > 0) {
            dataSource = (DataSource) beanFactory.getBean(dataSourceName);
            if (null == dataSource) {
                log.error(dataSourceName + "数据源不存在");
                throw new RuntimeException(dataSourceName + "数据源不存在");
            }
        }
        if (null == dataSource) {
            dataSource = beanFactory.getBean(DataSource.class);
        }

        MysqlShortUrlGenerator shortUrlGenerator = new MysqlShortUrlGenerator(dataSource, shortUrlProperties);
        shortUrlGenerator.init();
        return shortUrlGenerator;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
