package com.shanyan.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlashMobileOkHttp3Properties}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class FlashMobileOkHttp3PropertiesTest {

    @Test
    void prefixShouldBeCorrect() {
        assertThat(FlashMobileOkHttp3Properties.PREFIX).isEqualTo("shanyan.okhttp3");
    }

    @Test
    void defaultValuesShouldBeCorrect() {
        FlashMobileOkHttp3Properties props = new FlashMobileOkHttp3Properties();
        assertThat(props.isFollowSslRedirects()).isFalse();
        assertThat(props.isFollowRedirects()).isFalse();
        assertThat(props.isRetryOnConnectionFailure()).isFalse();
        assertThat(props.getCallTimeout()).isEqualTo(0);
        assertThat(props.getConnectTimeout()).isEqualTo(10);
        assertThat(props.getReadTimeout()).isEqualTo(10);
        assertThat(props.getWriteTimeout()).isEqualTo(10);
        assertThat(props.getPingInterval()).isEqualTo(0);
        assertThat(props.getMaxIdleConnections()).isEqualTo(200);
        assertThat(props.getKeepAliveDuration()).isEqualTo(Duration.ofSeconds(30000));
    }

    @Test
    void settersAndGettersShouldWork() {
        FlashMobileOkHttp3Properties props = new FlashMobileOkHttp3Properties();
        props.setFollowSslRedirects(true);
        props.setFollowRedirects(true);
        props.setRetryOnConnectionFailure(true);
        props.setCallTimeout(30);
        props.setConnectTimeout(5);
        props.setReadTimeout(15);
        props.setWriteTimeout(20);
        props.setPingInterval(10);
        props.setMaxIdleConnections(100);
        props.setKeepAliveDuration(Duration.ofSeconds(60));

        assertThat(props.isFollowSslRedirects()).isTrue();
        assertThat(props.isFollowRedirects()).isTrue();
        assertThat(props.isRetryOnConnectionFailure()).isTrue();
        assertThat(props.getCallTimeout()).isEqualTo(30);
        assertThat(props.getConnectTimeout()).isEqualTo(5);
        assertThat(props.getReadTimeout()).isEqualTo(15);
        assertThat(props.getWriteTimeout()).isEqualTo(20);
        assertThat(props.getPingInterval()).isEqualTo(10);
        assertThat(props.getMaxIdleConnections()).isEqualTo(100);
        assertThat(props.getKeepAliveDuration()).isEqualTo(Duration.ofSeconds(60));
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        FlashMobileOkHttp3Properties p1 = new FlashMobileOkHttp3Properties();
        FlashMobileOkHttp3Properties p2 = new FlashMobileOkHttp3Properties();
        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        FlashMobileOkHttp3Properties props = new FlashMobileOkHttp3Properties();
        String str = props.toString();
        assertThat(str).contains("maxIdleConnections");
    }
}
