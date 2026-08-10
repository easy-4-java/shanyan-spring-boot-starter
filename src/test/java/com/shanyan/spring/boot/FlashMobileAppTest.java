package com.shanyan.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlashMobileApp}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class FlashMobileAppTest {

    @Test
    void gettersAndSettersShouldWork() {
        FlashMobileApp app = new FlashMobileApp();
        app.setAppId("appId123");
        app.setAppKey("appKey456");
        app.setEncryptType("1");
        app.setPrivateKey("privateKeyValue");

        assertThat(app.getAppId()).isEqualTo("appId123");
        assertThat(app.getAppKey()).isEqualTo("appKey456");
        assertThat(app.getEncryptType()).isEqualTo("1");
        assertThat(app.getPrivateKey()).isEqualTo("privateKeyValue");
    }

    @Test
    void defaultValuesShouldBeCorrect() {
        FlashMobileApp app = new FlashMobileApp();
        assertThat(app.getAppId()).isNull();
        assertThat(app.getAppKey()).isNull();
        assertThat(app.getEncryptType()).isEqualTo("0");
        assertThat(app.getPrivateKey()).isEmpty();
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        FlashMobileApp app1 = new FlashMobileApp();
        app1.setAppId("id1");
        app1.setAppKey("key1");

        FlashMobileApp app2 = new FlashMobileApp();
        app2.setAppId("id1");
        app2.setAppKey("key1");

        assertThat(app1).isEqualTo(app2);
        assertThat(app1.hashCode()).isEqualTo(app2.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        FlashMobileApp app = new FlashMobileApp();
        app.setAppId("testId");
        String str = app.toString();
        assertThat(str).contains("testId");
    }
}
