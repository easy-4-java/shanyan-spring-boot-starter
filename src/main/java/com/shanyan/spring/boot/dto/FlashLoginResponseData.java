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
package com.shanyan.spring.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Data payload for the Shanyan login response.
 * <p>Contains the encrypted phone number, decrypted phone number, and transaction ID.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlashLoginResponseData{

	/**
	 * Encrypted phone number. Decrypt using the algorithm specified by encryptType.
	 */
	@JsonProperty("mobileName")
	private String mobileName;
	/**
	 * Decrypted phone number in plaintext.
	 */
	@JsonIgnore
	private String mobile;
	/**
	 * Shanyan transaction ID.
	 */
	@JsonProperty("tradeNo")
	private String tradeNo;

}
