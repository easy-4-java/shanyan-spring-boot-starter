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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Data payload for the Shanyan phone number validation response.
 * <p>Contains the verification result and transaction ID.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlashValidateResponseData{

	/**
	 * Verification result: 1 if the phone number belongs to the current device, 0 otherwise.
	 */
	@JsonProperty("isVerify")
	private int isVerify;

	/**
	 * Shanyan transaction ID.
	 */
	@JsonProperty("tradeNo")
	private String tradeNo;

	/**
	 * Returns whether the phone number belongs to the current device.
	 *
	 * @return {@code true} if verified, {@code false} otherwise
	 */
	public boolean isVerify() {
		return 1 == isVerify;
	}

}
