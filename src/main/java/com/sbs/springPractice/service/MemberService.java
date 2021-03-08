package com.sbs.springPractice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.springPractice.dao.MemberDao;
import com.sbs.springPractice.dto.Member;
import com.sbs.springPractice.dto.ResultData;
import com.sbs.springPractice.util.Util;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public ResultData join(Map<String, Object> param) {
		memberDao.join(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", String.format("%s님 환영합니다.", param.get("nickname")), "id", id);
	}
	
public Member getMember(int id) {
		
		return memberDao.getMember(id);
	}

	public Member getMemberByLoginId(String loginId) {
		
		return memberDao.getMemberByLoginId(loginId);
	}

	public ResultData modify(Map<String, Object> param) {
		memberDao.modify(param);

		return new ResultData("S-1", "회원정보가 수정되었습니다.");
		
	}
	
	public boolean isAdmin(Member actor) {
		return actor.getAuthLevel() == 7;
	}
	
	public Member getMemberByAuthKey(String authKey) {
		return memberDao.getMemberByAuthKey(authKey);
	}

}
