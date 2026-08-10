/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.shanyan.spring.boot;

import lombok.Data;

/**
 * Configuration for a single Shanyan (Flash Mobile) application.
 * <p>Contains the app ID, app key, encryption type, and private key
 * required for one-click login and phone number verification.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
public class FlashMobileApp {
	
	/**
	 * Shanyan application ID, generated when creating the application in the console.
	 */
	private String appId;
	/**
	 * Shanyan application key.
	 */
	private String appKey;
	/**
	 * Phone number encryption type: 0 for AES, 1 for RSA. Defaults to 0.
	 * When using RSA, the RSA public key must be provided when creating the application.
	 */
	private String encryptType = "0";
	/**
	 * RSA private key for phone number decryption. Required when encryptType is 1 (RSA).
	 */
	private String privateKey = "";
	
}
