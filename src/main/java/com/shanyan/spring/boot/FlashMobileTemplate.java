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

/*
 * https://shanyan.253.com/document/details?lid=300&cid=93&pc=28&pn=%25E9%2597%25AA%25E9%25AA%258CSDK
 * @author [@Loong Wan](https://github.com/loong10k)
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

	public FlashMobileTemplate(FlashMobileProperties properties, ObjectMapper objectMapper,
			OkHttpClient okhttp3Client) {
		this.objectMapper = objectMapper;
		this.okhttp3Client = okhttp3Client;
		this.properties = properties;
	}

	/*
	 * 1、一键登录V2（获取手机号码）
	 * @author [@Loong Wan](https://github.com/loong10k)
	 * @param appId 应用对应的闪验APPID
	 * @param clientIp 由客户服务端获取的前端APP的IP，如需要使用反欺诈核验功能则传入，否则可以不传。
	 * @param token 从SDK获取的token参数；有效期：移动2分钟、电信10分钟、联通30分钟，一次有效。
	 * @return
	 * @throws Exception 
	 */
	public FlashLoginResponse login(String appId, String clientIp, String token) throws Exception {
		return this.login(appId, null, clientIp, token);
	}

	/*
	 * 1、一键登录V2（获取手机号码）
	 * @author [@Loong Wan](https://github.com/loong10k)
	 * @param appId 应用对应的闪验APPID
	 * @param outId 客户方流水号, 可以为空
	 * @param clientIp 由客户服务端获取的前端APP的IP，如需要使用反欺诈核验功能则传入，否则可以不传。
	 * @param token 从SDK获取的token参数；有效期：移动2分钟、电信10分钟、联通30分钟，一次有效。
	 * @return
	 * @throws Exception 
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

	/*
	 * 2、本机认证V2（本机号码校验）
	 * @author [@Loong Wan](https://github.com/loong10k)
	 * @param appId 应用对应的闪验APPID
	 * @param mobile 待校验的手机号码
	 * @param token 从SDK获取的token参数；有效期：移动2分钟、电信10分钟、联通30分钟，一次有效。
	 * @return
	 */
	public FlashValidateResponse validate(String appId, String mobile, String token) {
		return this.validate(null, mobile, token);
	}
	
	/*
	 * 2、本机认证V2（本机号码校验）
	 * @author [@Loong Wan](https://github.com/loong10k)
	 * @param appId 应用对应的闪验APPID
	 * @param outId 客户方流水号, 可以为空
	 * @param mobile 待校验的手机号码
	 * @param token 从SDK获取的token参数；有效期：移动2分钟、电信10分钟、联通30分钟，一次有效。
	 * @return
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
	
	
	public <T> T request(String url, Map<String, String> params, Class<T> cls) {
		return toBean(requestInvoke(url, params), cls);
	}

	public <T> T toBean(String json, Class<T> cls) {
		try {
			return objectMapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * http 请求service
	 *
	 * @param url
	 * @param params
	 * @return
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

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public FlashMobileProperties getProperties() {
		return properties;
	}

	public OkHttpClient getOkhttp3Client() {
		return okhttp3Client;
	}

}
