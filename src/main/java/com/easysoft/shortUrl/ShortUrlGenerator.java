/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: qiujiayu[qiu_jy@suixingpay.com]
 * @date: 2018年7月2日 上午10:13:18
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.easysoft.shortUrl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数字类型的服务器ID生成器
 *
 * @author: qiujiayu[qiu_jy@suixingpay.com]
 * @date: 2018年7月2日 上午10:13:18
 * @version: V1.0
 * @review: qiujiayu[qiu_jy@suixingpay.com]/2018年7月2日 上午10:13:18
 */
public abstract class ShortUrlGenerator {
    protected ShortUrlProperties shortUrlProperties;

    public ShortUrlGenerator(ShortUrlProperties shortUrlProperties) {
        this.shortUrlProperties = shortUrlProperties;
    }

    /**
     * 初始化准备工作
     */
    public abstract void init();

    /**
     * 获取短网址
     *
     * @param longUrl
     * @return
     */
    public String shortUrl(String longUrl) {
        return shortUrlProperties.getDomainName() + "/" + HexConvert.tenToSixTwo(generatorId(longUrl));
    }

    public String restoreUrl(String shortUrl) {
        return getLongUrl(HexConvert.sixTwoToTen(shortUrl.replace(shortUrlProperties.getDomainName(), "")));
    }


    protected abstract Long generatorId(String longUrl);

    public abstract String getLongUrl(Long id);

    public void close(PreparedStatement ps) {
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
            }
    }

    public void close(ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
            }
    }

    public static class ShortUrlInitException extends RuntimeException {

        public ShortUrlInitException(String message, Throwable ex) {
            super(message, ex);
        }

        public ShortUrlInitException(String message) {
            super(message);
        }
    }
}
