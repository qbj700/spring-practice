package com.sbs.springPractice.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.springPractice.dto.Member;
import com.sbs.springPractice.dto.ResultData;
import com.sbs.springPractice.service.MemberService;
import com.sbs.springPractice.util.Util;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class AdmMemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/adm/member/join", method = RequestMethod.GET)
	@ApiOperation(value = "회원가입 화면", notes = "성공시 join.jsp의 경로를 반환합니다.")
	public String showJoin() {
		return "adm/member/join";
	}

	@RequestMapping(value = "/adm/member/doJoin", method = RequestMethod.POST)
	@ApiOperation(value = "회원가입", notes = "성공시 msg와 redirectUrl을 반환합니다.")
	@ResponseBody
	public String doJoin(@RequestParam Map<String, Object> param) {
		if (param.get("loginId") == null) {
			return Util.msgAndBack("loginId를 입력해주세요.");
		}

		Member existingMember = memberService.getMemberByLoginId((String)param.get("loginId"));

		if (existingMember != null) {
			return Util.msgAndBack("이미 사용중인 로그인아이디 입니다.");
		}

		if (param.get("loginPw") == null) {
			return Util.msgAndBack("loginPw를 입력해주세요.");
		}

		if (param.get("name") == null) {
			return Util.msgAndBack("name을 입력해주세요.");
		}

		if (param.get("nickname") == null) {
			return Util.msgAndBack("nickname을 입력해주세요.");
		}

		if (param.get("email") == null) {
			return Util.msgAndBack("email을 입력해주세요.");
		}

		if (param.get("cellphoneNo") == null) {
			return Util.msgAndBack("cellphoneNo를 입력해주세요.");
		}

		memberService.join(param);

		String msg = String.format("%s님 환영합니다.", param.get("nickname"));

		String redirectUrl = Util.ifEmpty((String)param.get("redirectUrl"), "../member/login");

		return Util.msgAndReplace(msg, redirectUrl);
	}
	
	@RequestMapping(value = "/adm/member/login", method = RequestMethod.GET)
	@ApiOperation(value = "로그인 화면", notes = "성공시 login.jsp의 경로를 반환합니다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 400, message = "잘못된 접근"),
		@ApiResponse(code = 500, message = "서버 에러")
	})
	public String showLogin() {
		return "adm/member/login";
	}

	@RequestMapping(value = "/adm/member/doLogin", method = RequestMethod.POST)
	@ApiOperation(value = "로그인", notes = "성공시 로그인상태가 됩니다.")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "loginId", value ="로그인아이디", required = true),
		@ApiImplicitParam( name = "loginPw", value ="로그인비밀번호", required = true)
	})
	@ResponseBody
	public String doLogin(String loginId, String loginPw, String redirectUrl, HttpSession session) {
		if (loginId == null) {
			return Util.msgAndBack("loginId를 입력해주세요.");
		}

		Member existingMember = memberService.getMemberByLoginId(loginId);

		if (existingMember == null) {
			return Util.msgAndBack("존재하지 않는 로그인아이디 입니다.");
		}

		if (loginPw == null) {
			return Util.msgAndBack("loginPw를 입력해주세요.");
		}

		if (existingMember.getLoginPw().equals(loginPw) == false) {
			return Util.msgAndBack("비밀번호가 일치하지 않습니다.");
		}

		if ( memberService.isAdmin(existingMember) == false ) {
			return Util.msgAndBack("관리자만 접근할 수 있는 페이지 입니다.");
		}

		session.setAttribute("loginedMemberId", existingMember.getId());

		String msg = String.format("%s님 환영합니다.", existingMember.getNickname());

		redirectUrl = Util.ifEmpty(redirectUrl, "../home/main");

		return Util.msgAndReplace(msg, redirectUrl);
	}

	@RequestMapping(value = "/adm/member/doModify", method = RequestMethod.POST)
	@ApiOperation(value = "회원정보수정", notes = "성공시 회원정보가 수정됩니다.")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "memberId", value ="회원번호", required = true)
	})
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		if (param.isEmpty()) {
			return new ResultData("F-2", "수정할 정보를 입력해주세요.");
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);

		return memberService.modify(param);
	}
	
	@RequestMapping(value = "/adm/member/doLogout", method = RequestMethod.GET)
	@ApiOperation(value = "로그아웃", notes = "성공시 로그아웃 상태가 됩니다.")
	@ResponseBody
	public String doLogout(HttpSession session) {
		session.removeAttribute("loginedMemberId");

		return Util.msgAndReplace("로그아웃 되었습니다.", "../member/login");
	}
}