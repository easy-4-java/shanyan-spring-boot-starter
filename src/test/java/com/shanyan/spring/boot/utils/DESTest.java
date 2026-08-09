package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DES}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DESTest {

    private static final String DES_KEY = "12345678"; // DES key must be 8 bytes

    @Test
    void encryptAndDecryptShouldRoundTrip() throws Exception {
        String original = "Hello DES";
        String encrypted = DES.encryptDES(original, DES_KEY);
        assertThat(encrypted).isNotNull();
        assertThat(encrypted).isNotEqualTo(original);

        String decrypted = DES.decryptDES(encrypted, DES_KEY);
        assertThat(decrypted).isEqualTo(original);
    }

    @Test
    void encryptShouldReturnDifferentResultsForDifferentInputs() throws Exception {
        String e1 = DES.encryptDES("hello", DES_KEY);
        String e2 = DES.encryptDES("world", DES_KEY);
        assertThat(e1).isNotEqualTo(e2);
    }
}
