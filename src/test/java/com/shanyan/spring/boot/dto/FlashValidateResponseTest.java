package com.shanyan.spring.boot.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlashValidateResponse} and {@link FlashValidateResponseData}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class FlashValidateResponseTest {

    @Test
    void isSuccessShouldReturnTrueForCode200000() {
        FlashValidateResponse response = new FlashValidateResponse();
        response.setCode("200000");
        assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void isSuccessShouldReturnFalseForOtherCodes() {
        FlashValidateResponse response = new FlashValidateResponse();
        response.setCode("400");
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    void isSuccessShouldReturnFalseForNullCode() {
        FlashValidateResponse response = new FlashValidateResponse();
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    void gettersAndSettersShouldWork() {
        FlashValidateResponse response = new FlashValidateResponse();
        response.setCode("200000");
        response.setMessage("success");
        response.setChargeStatus(1);

        FlashValidateResponseData data = new FlashValidateResponseData();
        data.setIsVerify(1);
        data.setTradeNo("trade456");
        response.setData(data);

        assertThat(response.getCode()).isEqualTo("200000");
        assertThat(response.getMessage()).isEqualTo("success");
        assertThat(response.getChargeStatus()).isEqualTo(1);
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getIsVerify()).isEqualTo(1);
        assertThat(response.getData().getTradeNo()).isEqualTo("trade456");
    }

    @Test
    void dataIsVerifyShouldReturnTrueWhenOne() {
        FlashValidateResponseData data = new FlashValidateResponseData();
        data.setIsVerify(1);
        assertThat(data.isVerify()).isTrue();
    }

    @Test
    void dataIsVerifyShouldReturnFalseWhenZero() {
        FlashValidateResponseData data = new FlashValidateResponseData();
        data.setIsVerify(0);
        assertThat(data.isVerify()).isFalse();
    }

    @Test
    void defaultValuesShouldBeNull() {
        FlashValidateResponse response = new FlashValidateResponse();
        assertThat(response.getCode()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getData()).isNull();
    }
}
