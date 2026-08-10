package com.shanyan.spring.boot.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlashLoginResponse} and {@link FlashLoginResponseData}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class FlashLoginResponseTest {

    @Test
    void isSuccessShouldReturnTrueForCode200000() {
        FlashLoginResponse response = new FlashLoginResponse();
        response.setCode("200000");
        assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void isSuccessShouldReturnFalseForOtherCodes() {
        FlashLoginResponse response = new FlashLoginResponse();
        response.setCode("500");
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    void isSuccessShouldReturnFalseForNullCode() {
        FlashLoginResponse response = new FlashLoginResponse();
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    void gettersAndSettersShouldWork() {
        FlashLoginResponse response = new FlashLoginResponse();
        response.setCode("200000");
        response.setMessage("success");
        response.setChargeStatus(1);

        FlashLoginResponseData data = new FlashLoginResponseData();
        data.setMobileName("encrypted");
        data.setMobile("13800138000");
        data.setTradeNo("trade123");
        response.setData(data);

        assertThat(response.getCode()).isEqualTo("200000");
        assertThat(response.getMessage()).isEqualTo("success");
        assertThat(response.getChargeStatus()).isEqualTo(1);
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getMobileName()).isEqualTo("encrypted");
        assertThat(response.getData().getMobile()).isEqualTo("13800138000");
        assertThat(response.getData().getTradeNo()).isEqualTo("trade123");
    }

    @Test
    void defaultValuesShouldBeNull() {
        FlashLoginResponse response = new FlashLoginResponse();
        assertThat(response.getCode()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getData()).isNull();
        assertThat(response.getChargeStatus()).isEqualTo(0);
    }

    @Test
    void dataDefaultValuesShouldBeNull() {
        FlashLoginResponseData data = new FlashLoginResponseData();
        assertThat(data.getMobileName()).isNull();
        assertThat(data.getMobile()).isNull();
        assertThat(data.getTradeNo()).isNull();
    }
}
