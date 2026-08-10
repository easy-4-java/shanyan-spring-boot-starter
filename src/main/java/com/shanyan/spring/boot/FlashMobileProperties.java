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

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Top-level configuration properties for Shanyan (Flash Mobile) integration.
 * <p>Binds to the {@code shanyan} prefix and holds a list of
 * {@link FlashMobileApp} configurations for multiple applications.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(FlashMobileProperties.PREFIX)
public class FlashMobileProperties {

	public static final String PREFIX = "shanyan";

	private List<FlashMobileApp> apps;

	public List<FlashMobileApp> getApps() {
		return apps;
	}

	public void setApps(List<FlashMobileApp> apps) {
		this.apps = apps;
	}
	
}
