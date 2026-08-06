/*
 * Copyright (c) 2018, hiwepy (https://github.com/easy-4-java).
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

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlashValidateResponseData{

	/**
	 * 值：1 是本机号码 0 非本机号码
	 */
	@JsonProperty("isVerify")
	private int isVerify;
	
	/**
	 * 闪验的交易流水号
	 */
	@JsonProperty("tradeNo")
	private String tradeNo;
	
	public boolean isVerify() {
		return 1 == isVerify;
	}

}
