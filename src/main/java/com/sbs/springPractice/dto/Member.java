package com.sbs.springPractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbs.springPractice.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	MemberService memberService;
	
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	@JsonIgnore
	private String loginPw;
	@JsonIgnore
	private int authLevel;
	@JsonIgnore
	private String authKey;
	private String name;
	private String nickname;
	private String cellphoneNo;
	private String email;
	
	public String getAuthLevelName() {
		return "일반회원";
	}
}
