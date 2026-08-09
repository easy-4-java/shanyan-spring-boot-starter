package com.shanyan.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanyan.spring.boot.dto.FlashLoginResponse;
import com.shanyan.spring.boot.dto.FlashValidateResponse;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlashMobileTemplate}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class FlashMobileTemplateTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient okhttp3Client = new OkHttpClient.Builder().build();
    private FlashMobileProperties properties;
    private FlashMobileTemplate template;

    @BeforeEach
    void setup() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        properties = new FlashMobileProperties();
        List<FlashMobileApp> apps = new ArrayList<>();
        FlashMobileApp app = new FlashMobileApp();
        app.setAppId("testAppId");
        app.setAppKey("testAppKey");
        app.setEncryptType("0");
        apps.add(app);
        properties.setApps(apps);
        template = new FlashMobileTemplate(properties, objectMapper, okhttp3Client);
    }

    @Test
    void loginShouldReturnEmptyResponseWhenNoMatchingApp() throws Exception {
        FlashMobileProperties emptyProps = new FlashMobileProperties();
        emptyProps.setApps(Collections.emptyList());
        FlashMobileTemplate t = new FlashMobileTemplate(emptyProps, objectMapper, okhttp3Client);

        FlashLoginResponse response = t.login("nonExistent", "127.0.0.1", "token");
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isNull();
    }

    @Test
    void loginWithOutIdShouldReturnEmptyResponseWhenNoMatchingApp() throws Exception {
        FlashMobileProperties emptyProps = new FlashMobileProperties();
        emptyProps.setApps(Collections.emptyList());
        FlashMobileTemplate t = new FlashMobileTemplate(emptyProps, objectMapper, okhttp3Client);

        FlashLoginResponse response = t.login("nonExistent", "outId", "127.0.0.1", "token");
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isNull();
    }

    @Test
    void validateShouldReturnEmptyResponseWhenNoMatchingApp() {
        FlashMobileProperties emptyProps = new FlashMobileProperties();
        emptyProps.setApps(Collections.emptyList());
        FlashMobileTemplate t = new FlashMobileTemplate(emptyProps, objectMapper, okhttp3Client);

        FlashValidateResponse response = t.validate("nonExistent", "13800138000", "token");
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isNull();
    }

    @Test
    void validateWithOutIdShouldReturnEmptyResponseWhenNoMatchingApp() {
        FlashMobileProperties emptyProps = new FlashMobileProperties();
        emptyProps.setApps(Collections.emptyList());
        FlashMobileTemplate t = new FlashMobileTemplate(emptyProps, objectMapper, okhttp3Client);

        FlashValidateResponse response = t.validate("nonExistent", "outId", "13800138000", "token");
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isNull();
    }

    @Test
    void toBeanShouldDeserializeJson() {
        String json = "{\"code\":\"200000\",\"message\":\"success\",\"chargeStatus\":1}";
        FlashLoginResponse result = template.toBean(json, FlashLoginResponse.class);
        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("200000");
        assertThat(result.getMessage()).isEqualTo("success");
        assertThat(result.getChargeStatus()).isEqualTo(1);
    }

    @Test
    void toBeanShouldReturnNullForInvalidJson() {
        FlashLoginResponse result = template.toBean("not valid json", FlashLoginResponse.class);
        assertThat(result).isNull();
    }

    @Test
    void requestInvokeShouldReturnNullForUnreachableUrl() {
        String result = template.requestInvoke("http://localhost:1/nonexistent", Collections.emptyMap());
        assertThat(result).isNull();
    }

    @Test
    void gettersShouldReturnInjectedDependencies() {
        assertThat(template.getObjectMapper()).isSameAs(objectMapper);
        assertThat(template.getOkhttp3Client()).isSameAs(okhttp3Client);
        assertThat(template.getProperties()).isSameAs(properties);
    }

    @Test
    void constantsShouldBeCorrect() {
        assertThat(FlashMobileTemplate.FLASH_LOGIN_URL).contains("mobile-query");
        assertThat(FlashMobileTemplate.FLASH_VALIDATE_URL).contains("mobile-validate");
    }

    @Test
    void loginShouldTryToMatchAppId() throws Exception {
        // App exists but network will fail, verifying code path reaches the app-matching logic
        FlashLoginResponse response = template.login("testAppId", "127.0.0.1", "token");
        // Will get null from requestInvoke since network fails, so toBean(null) returns null
        // but the app-matching branch was entered
        assertThat(response).isNotNull();
    }

    @Test
    void validateShouldTryToMatchAppId() {
        FlashValidateResponse response = template.validate("testAppId", "13800138000", "token");
        assertThat(response).isNotNull();
    }
}
