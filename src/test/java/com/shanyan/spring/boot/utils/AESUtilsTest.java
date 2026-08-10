package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AESUtils}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class AESUtilsTest {

    private static final String KEY = "1234567890abcdef"; // 16 chars
    private static final String IV = "abcdef1234567890";  // 16 chars

    @Test
    void decryptShouldDecryptAesEncryptedData() throws Exception {
        String plaintext = "Hello AES";
        // Encrypt first
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes("UTF-8"));
        String hexEncrypted = ByteFormat.bytesToHexString(encrypted);

        String decrypted = AESUtils.decrypt(hexEncrypted, KEY, IV);
        assertThat(decrypted).isEqualTo(plaintext);
    }

    @Test
    void decryptShouldReturnNullForEmptyInput() throws Exception {
        assertThat(AESUtils.decrypt("", KEY, IV)).isNull();
    }

    @Test
    void decryptShouldReturnNullForNullInput() throws Exception {
        assertThat(AESUtils.decrypt(null, KEY, IV)).isNull();
    }

    @Test
    void decryptShouldThrowForNullKey() {
        assertThatThrownBy(() -> AESUtils.decrypt("AABB", null, IV))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("decrypt errot");
    }

    @Test
    void decryptShouldThrowForInvalidKeyLength() {
        assertThatThrownBy(() -> AESUtils.decrypt("AABB", "short", IV))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("decrypt errot");
    }
}
