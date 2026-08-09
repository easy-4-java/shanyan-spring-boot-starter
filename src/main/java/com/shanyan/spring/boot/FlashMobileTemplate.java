package com.shanyan.spring.boot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanyan.spring.boot.dto.FlashLoginResponse;
import com.shanyan.spring.boot.dto.FlashValidateResponse;
import com.shanyan.spring.boot.utils.AESUtils;
import com.shanyan.spring.boot.utils.MD5;
import com.shanyan.spring.boot.utils.RSAUtils;
import com.shanyan.spring.boot.utils.SignUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Template for Shanyan (Flash Mobile) API operations.
 * <p>Provides methods for one-click login (phone number retrieval) and
 * phone number verification using the Shanyan SDK backend API.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 * @see <a href="https://shanyan.253.com/document/details?lid=300&cid=93&pc=28">Shanyan SDK Documentation</a>
 */
@Slf4j
public class FlashMobileTemplate {

	// 免密登录后台url
	public static final String FLASH_LOGIN_URL = "https://api.253.com/open/flashsdk/mobile-query";
	// 本机号校验url
	public static final String FLASH_VALIDATE_URL = "https://api.253.com/open/flashsdk/mobile-validate";

	private ObjectMapper objectMapper;
	private OkHttpClient okhttp3Client;
	private final FlashMobileProperties properties;

	/**
	 * Constructs a new template with the given properties, object mapper, and HTTP client.
	 *
	 * @param properties the Shanyan configuration properties
	 * @param objectMapper the Jackson object mapper for JSON serialization
	 * @param okhttp3Client the OkHttp3 client for HTTP requests
	 */
	public FlashMobileTemplate(FlashMobileProperties properties, ObjectMapper objectMapper,
			OkHttpClient okhttp3Client) {
		this.objectMapper = objectMapper;
		this.okhttp3Client = okhttp3Client;
		this.properties = properties;
	}

	/**
	 * Performs one-click login to retrieve the phone number.
	 *
	 * @param appId the Shanyan application ID
	 * @param clientIp the client IP address (optional, used for anti-fraud verification)
	 * @param token the token obtained from the SDK (valid for 2 min on China Mobile, 10 min on China Telecom, 30 min on China Unicom)
	 * @return the login response containing the encrypted phone number
	 * @throws Exception if the request fails or decryption fails
	 */
	public FlashLoginResponse login(String appId, String clientIp, String token) throws Exception {
		return this.login(appId, null, clientIp, token);
	}

	/**
	 * Performs one-click login to retrieve the phone number with an optional transaction ID.
	 *
	 * @param appId the Shanyan application ID
	 * @param outId the client-side transaction ID (optional)
	 * @param clientIp the client IP address (optional, used for anti-fraud verification)
	 * @param token the token obtained from the SDK
	 * @return the login response containing the decrypted phone number
	 * @throws Exception if the request fails or decryption fails
	 */
	public FlashLoginResponse login(String appId, String outId, String clientIp, String token) throws Exception {
		for (FlashMobileApp app : properties.getApps()) {
			// 仅执行该应用对应的逻辑
			if(StringUtils.equals(app.getAppId(), appId)) {
				
				Map<String, String> params = new HashMap<String, String>();
				params.put("appId", StringUtils.defaultString(appId));
				params.put("token", StringUtils.defaultString(token));
				params.put("clientIp", StringUtils.defaultString(clientIp));
				params.put("outId", StringUtils.defaultString(outId));
				params.put("encryptType", app.getEncryptType());// 可以不传，不传则解密直接使用AES解密
				params.put("sign", SignUtils.getSign(params, app.getAppKey())); // 签名算法：hmacSHA256(所有传入参数按字段名正序排序后拼接的字符串，应用appKey)

				FlashLoginResponse res = request(FLASH_LOGIN_URL, params, FlashLoginResponse.class);
				if (res.isSuccess()) {
					String mobile = res.getData().getMobileName();
					// 加解密方式，值包含：0（AES加密）、1（RSA加密）缺省为0，如使用RSA方式则在创建应用时必须填写RSA公钥 
		            if ("0".equals(app.getEncryptType())) {
			             String key = MD5.getMD5Code(app.getAppKey());
			             mobile = AESUtils.decrypt(mobile, key.substring(0, 16), key.substring(16));
			         } else if ("1".equals(app.getEncryptType())) {
			             mobile = RSAUtils.decryptByPrivateKeyForLongStr(mobile, app.getPrivateKey());
			         }
					 res.getData().setMobile(mobile);
					 return res;
				}
				log.error("获取手机号码失败：code: {}、Message: {}", res.getCode(), res.getMessage());
				return res;
			}
		}
		return new FlashLoginResponse();
	}

	/**
	 * Verifies if the given phone number belongs to the current device.
	 *
	 * @param appId the Shanyan application ID
	 * @param mobile the phone number to verify
	 * @param token the token obtained from the SDK
	 * @return the validation response
	 */
	public FlashValidateResponse validate(String appId, String mobile, String token) {
		return this.validate(null, mobile, token);
	}
	
	/**
	 * Verifies if the given phone number belongs to the current device with an optional transaction ID.
	 *
	 * @param appId the Shanyan application ID
	 * @param outId the client-side transaction ID (optional)
	 * @param mobile the phone number to verify
	 * @param token the token obtained from the SDK
	 * @return the validation response
	 */
	public FlashValidateResponse validate(String appId, String outId, String mobile, String token) {

		for (FlashMobileApp app : properties.getApps()) {
			// 仅执行该应用对应的逻辑
			if(StringUtils.equals(app.getAppId(), appId)) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("appId", StringUtils.defaultString(appId));
				params.put("token", StringUtils.defaultString(token));
				params.put("mobile", StringUtils.defaultString(mobile));
				params.put("outId", StringUtils.defaultString(outId));
				params.put("sign", SignUtils.getSign(params, app.getAppKey())); // 签名算法：hmacSHA256(所有传入参数按字段名正序排序后拼接的字符串，应用appKey)

				FlashValidateResponse res = request(FLASH_VALIDATE_URL, params, FlashValidateResponse.class);
				if (!res.isSuccess()) {
					log.error("本机号码校验：code: {}、Message: {}", res.getCode(), res.getMessage());
				}
				return res;
			}
		}
		return new FlashValidateResponse();
	}
	
	
	/**
	 * Sends a request and deserializes the response to the given class.
	 *
	 * @param url the request URL
	 * @param params the request parameters
	 * @param cls the target class for deserialization
	 * @param <T> the response type
	 * @return the deserialized response, or {@code null} if the request fails
	 */
	public <T> T request(String url, Map<String, String> params, Class<T> cls) {
		return toBean(requestInvoke(url, params), cls);
	}

	/**
	 * Deserializes a JSON string to the given class.
	 *
	 * @param json the JSON string
	 * @param cls the target class
	 * @param <T> the target type
	 * @return the deserialized object, or {@code null} if deserialization fails
	 */
	public <T> T toBean(String json, Class<T> cls) {
		try {
			return objectMapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Executes an HTTP POST request with form-encoded parameters.
	 *
	 * @param url the request URL
	 * @param params the form parameters
	 * @return the response body as a string, or {@code null} if the request fails
	 */
	public String requestInvoke(String url, Map<String, String> params) {
		String content = null;
		try {

			FormBody.Builder builder = new FormBody.Builder();
			for (Map.Entry<String, String> m : params.entrySet()) {
				builder.add(m.getKey(), m.getValue());
			}
			RequestBody body = builder.build();
			Request request = new Request.Builder().url(url).post(body).build();
			Response response = okhttp3Client.newCall(request).execute();
			if (response.isSuccessful()) {
				content = response.body().string();
				log.debug("response : {}", content);
				return content;
			}
		} catch (Exception e) {
			log.error("请求异常", e);
		}
		return content;
	}

	/**
	 * Returns the Jackson object mapper.
	 *
	 * @return the object mapper
	 */
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * Returns the Shanyan configuration properties.
	 *
	 * @return the properties
	 */
	public FlashMobileProperties getProperties() {
		return properties;
	}

	/**
	 * Returns the OkHttp3 client.
	 *
	 * @return the OkHttp3 client
	 */
	public OkHttpClient getOkhttp3Client() {
		return okhttp3Client;
	}

}
