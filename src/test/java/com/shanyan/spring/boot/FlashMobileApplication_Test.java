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


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanyan.spring.boot.dto.FlashLoginResponse;
import com.shanyan.spring.boot.dto.FlashValidateResponse;

@SpringBootApplication
public class FlashMobileApplication_Test {
	
	@Autowired
	private FlashMobileTemplate template;
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostConstruct
	public void testLogin() {

		try {
			FlashLoginResponse response = template.login("test", "127.0.0.1", "00001");
			System.out.println(objectMapper.writeValueAsString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@PostConstruct
	public void testValidate() {

		try {
			
			FlashValidateResponse response2 = template.validate("test", "", "");
			System.out.println(objectMapper.writeValueAsString(response2));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FlashMobileApplication_Test.class, args);
	}

}
