package com.sbs.springPractice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.springPractice.dto.Member;

@Mapper
public interface MemberDao {

	void join(Map<String, Object> param);
	
	Member getMember(@Param("id") int id);

	Member getMemberByLoginId(@Param("loginId") String loginId);

	void modify(Map<String, Object> param);
	
	Member getMemberByAuthKey(@Param("authKey") String authKey);
	
	List<Member> getForPrintMembers(@Param("searchKeywordType") String searchKeywordType, @Param("searchKeyword") String searchKeyword,
			@Param("limitStart") int limitStart, @Param("limitTake") int limitTake);
}
