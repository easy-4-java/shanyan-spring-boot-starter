package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SignUtils}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class SignUtilsTest {

    @Test
    void getSignShouldReturnNonNullSignature() {
        Map<String, String> params = new HashMap<>();
        params.put("appId", "testApp");
        params.put("token", "testToken");
        String sign = SignUtils.getSign(params, "testKey");
        assertThat(sign).isNotNull();
        assertThat(sign).isNotEmpty();
    }

    @Test
    void getSignShouldBeConsistent() {
        Map<String, String> params = new HashMap<>();
        params.put("appId", "testApp");
        params.put("token", "testToken");
        String sign1 = SignUtils.getSign(params, "testKey");
        String sign2 = SignUtils.getSign(params, "testKey");
        assertThat(sign1).isEqualTo(sign2);
    }

    @Test
    void getSignShouldExcludeSignKey() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("appId", "testApp");
        String sign1 = SignUtils.getSign(params1, "testKey");

        Map<String, String> params2 = new HashMap<>();
        params2.put("appId", "testApp");
        params2.put("sign", "existingSign");
        String sign2 = SignUtils.getSign(params2, "testKey");

        assertThat(sign1).isEqualTo(sign2);
    }

    @Test
    void getSignShouldReturnDifferentForDifferentKeys() {
        Map<String, String> params = new HashMap<>();
        params.put("appId", "testApp");
        String sign1 = SignUtils.getSign(params, "key1");
        String sign2 = SignUtils.getSign(params, "key2");
        assertThat(sign1).isNotEqualTo(sign2);
    }
}
