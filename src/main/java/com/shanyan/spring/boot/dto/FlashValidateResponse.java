package com.shanyan.spring.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Response model for Shanyan phone number verification.
 * <p>Contains the response code, message, charge status, and validation data.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FlashValidateResponse {

	/**
	 * Response code. 200000 indicates success; other codes indicate failure.
	 */
	@JsonProperty("code")
	private String code;

	/**
	 * Response code description.
	 */
	@JsonProperty("message")
	private String message;

	/**
	 * Charge status indicator: 1 for charged, 0 for not charged.
	 */
	@JsonProperty("chargeStatus")
	private int chargeStatus;

	/**
	 * Response data containing the verification result.
	 */
	@JsonProperty("data")
	private FlashValidateResponseData data;

	/**
	 * Returns whether the validation request was successful.
	 *
	 * @return {@code true} if the response code is 200000, {@code false} otherwise
	 */
	public boolean isSuccess() {
		return "200000".equals(code);
	}
	
}
