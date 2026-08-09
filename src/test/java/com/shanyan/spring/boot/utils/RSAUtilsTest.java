package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.crypto.Cipher;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link RSAUtils}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class RSAUtilsTest {

    @Test
    void decryptByPrivateKeyForLongStrShouldDecrypt() throws Exception {
        // Generate RSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();

        String original = "Hello RSA";
        // Encrypt with public key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encrypted = cipher.doFinal(original.getBytes("UTF-8"));
        String hexEncrypted = ByteFormat.bytesToHexString(encrypted);

        // Get private key as Base64
        String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        // Decrypt using RSAUtils
        String decrypted = RSAUtils.decryptByPrivateKeyForLongStr(hexEncrypted, privateKeyBase64);
        assertThat(decrypted).isEqualTo(original);
    }

    @Test
    void constantValuesShouldBeCorrect() {
        assertThat(RSAUtils.KEY_ALGORITHM).isEqualTo("RSA");
    }
}
