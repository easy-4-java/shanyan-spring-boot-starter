package com.shanyan.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlashMobileProperties}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class FlashMobilePropertiesTest {

    @Test
    void prefixShouldBeCorrect() {
        assertThat(FlashMobileProperties.PREFIX).isEqualTo("shanyan");
    }

    @Test
    void gettersAndSettersShouldWork() {
        FlashMobileProperties props = new FlashMobileProperties();
        FlashMobileApp app = new FlashMobileApp();
        app.setAppId("id1");
        List<FlashMobileApp> apps = Arrays.asList(app);
        props.setApps(apps);

        assertThat(props.getApps()).hasSize(1);
        assertThat(props.getApps().get(0).getAppId()).isEqualTo("id1");
    }

    @Test
    void appsShouldDefaultToNull() {
        FlashMobileProperties props = new FlashMobileProperties();
        assertThat(props.getApps()).isNull();
    }
}
