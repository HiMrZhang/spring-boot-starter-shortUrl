package com.easysoft.shortUrl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testHexConvert() {
        Long id = Long.MAX_VALUE;
        log.info(String.valueOf(id));
        log.info(HexConvert.tenToSixTwo(id));
        log.info(String.valueOf(HexConvert.sixTwoToTen(HexConvert.tenToSixTwo(id))));
    }
}
