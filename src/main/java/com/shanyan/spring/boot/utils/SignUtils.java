package com.shanyan.spring.boot.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Map;

/**
 * Signature utility for Shanyan API request signing.
 * <p>Generates HMAC-SHA256 signatures from sorted request parameters
 * and the application key.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public class SignUtils {

    /**
     * Generates an HMAC-SHA256 signature for the given request parameters.
     *
     * @param requestMap the request parameters
     * @param appKey the application key used as the HMAC secret
     * @return the hex-encoded signature
     */
    public static String getSign(Map<String, String> requestMap, String appKey) {
        return hmacSHA256Encrypt(requestMap2Str(requestMap), appKey);
    }

    /**
     * Computes an HMAC-SHA256 hash of the given text using the given key.
     *
     * @param encryptText the text to sign
     * @param encryptKey the HMAC secret key
     * @return the hex-encoded hash
     */
    private static String hmacSHA256Encrypt(String encryptText, String encryptKey) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signinKey = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "HmacSHA256");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA256");
            //用给定密钥初始化 Mac 对象
            mac.init(signinKey);
            //完成 Mac 操作
            byte[] rawHmac = mac.doFinal(encryptText.getBytes("UTF-8"));
            return ByteFormat.bytesToHexString(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Converts a request parameter map to a sorted concatenated string (excluding the "sign" key).
     *
     * @param requestMap the request parameters
     * @return the sorted concatenated string
     */
    private static String requestMap2Str(Map<String, String> requestMap) {
        String[] keys = requestMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : keys) {
            if (!str.equals("sign")) {
                stringBuilder.append(str).append(requestMap.get(str));
            }
        }
        return stringBuilder.toString();
    }

}
