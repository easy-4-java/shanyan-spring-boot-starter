package com.shanyan.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

/**
 * Tests for {@link FlashMobileAutoConfiguration}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class FlashMobileAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(FlashMobileAutoConfiguration.class));

    @Test
    void shouldCreateTemplateBeanWhenPropertiesPresent() {
        contextRunner
                .withPropertyValues(
                        "shanyan.apps[0].appId=testApp",
                        "shanyan.apps[0].appKey=testKey"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(FlashMobileTemplate.class);
                    assertThat(context).hasSingleBean(FlashMobileProperties.class);
                    assertThat(context).hasSingleBean(FlashMobileOkHttp3Properties.class);
                    FlashMobileTemplate template = context.getBean(FlashMobileTemplate.class);
                    assertThat(template).isNotNull();
                    assertThat(template.getObjectMapper()).isNotNull();
                    assertThat(template.getOkhttp3Client()).isNotNull();
                    assertThat(template.getProperties()).isNotNull();
                });
    }

    @Test
    void shouldUseCustomObjectMapper() {
        ObjectMapper customMapper = new ObjectMapper();
        contextRunner
                .withPropertyValues(
                        "shanyan.apps[0].appId=testApp",
                        "shanyan.apps[0].appKey=testKey"
                )
                .withBean(ObjectMapper.class, () -> customMapper)
                .run(context -> {
                    FlashMobileTemplate template = context.getBean(FlashMobileTemplate.class);
                    assertThat(template.getObjectMapper()).isSameAs(customMapper);
                });
    }

    @Test
    void shouldUseCustomOkHttpClient() {
        OkHttpClient customClient = new OkHttpClient.Builder().build();
        contextRunner
                .withPropertyValues(
                        "shanyan.apps[0].appId=testApp",
                        "shanyan.apps[0].appKey=testKey"
                )
                .withBean(OkHttpClient.class, () -> customClient)
                .run(context -> {
                    FlashMobileTemplate template = context.getBean(FlashMobileTemplate.class);
                    assertThat(template.getOkhttp3Client()).isSameAs(customClient);
                });
    }
}
