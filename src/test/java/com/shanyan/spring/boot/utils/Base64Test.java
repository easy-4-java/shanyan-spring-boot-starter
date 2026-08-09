package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Base64}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class Base64Test {

    @Test
    void encodeShouldEncodeBytes() {
        byte[] input = "Hello".getBytes();
        String encoded = Base64.encode(input);
        assertThat(encoded).isEqualTo("SGVsbG8=");
    }

    @Test
    void decodeShouldDecodeString() {
        byte[] decoded = Base64.decode("SGVsbG8=");
        assertThat(new String(decoded)).isEqualTo("Hello");
    }

    @Test
    void decodeShouldReturnNullForNullInput() {
        assertThat(Base64.decode(null)).isNull();
    }

    @Test
    void decodeShouldReturnEmptyArrayForEmptyString() {
        byte[] decoded = Base64.decode("");
        assertThat(decoded).isEmpty();
    }

    @Test
    void decodeShouldThrowForInvalidLength() {
        assertThatThrownBy(() -> Base64.decode("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("4*n");
    }

    @Test
    void roundTripEncoding() {
        byte[] original = "Hello, World! 你好".getBytes();
        String encoded = Base64.encode(original);
        byte[] decoded = Base64.decode(encoded);
        assertThat(decoded).isEqualTo(original);
    }

    @Test
    void encodeShouldHandleSingleByte() {
        byte[] input = {65};
        String encoded = Base64.encode(input);
        assertThat(encoded).isEqualTo("QQ==");
    }

    @Test
    void encodeShouldHandleTwoBytes() {
        byte[] input = {65, 66};
        String encoded = Base64.encode(input);
        assertThat(encoded).isEqualTo("QUI=");
    }
}
