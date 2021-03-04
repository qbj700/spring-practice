package com.sbs.springPractice.dto;

import java.util.Map;

import com.sbs.springPractice.util.Util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResultData {
	@ApiModelProperty(position = 1, example = "S-1")
	private String resultCode;
	@ApiModelProperty(position = 2,example = "성공")
	private String msg;
	@ApiModelProperty(position = 3,example = "Map형식의 데이터가 리턴됩니다.")
	private Map<String, Object> body;

	public ResultData(String resultCode, String msg, Object... args) {
		this.resultCode = resultCode;
		this.msg = msg;
		this.body = Util.mapOf(args);
	}

	@ApiModelProperty(position = 4)
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}

	@ApiModelProperty(position = 5)
	public boolean isFail() {
		return isSuccess() == false;
	}
}
