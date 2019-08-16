package com.ethon.springboot.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 파라미터 필드 Validation 정보 모델
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamFieldValidModel {

	private String field = null;			// 필드명
	private String validCode = null;		// Validation 코드명
	private String message = null;			// Validation 메세지
	
}
