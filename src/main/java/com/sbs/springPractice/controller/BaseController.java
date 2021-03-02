package com.sbs.springPractice.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	protected String msgAndBack(HttpServletRequest req, String msg) {
		req.setAttribute("historyBack", true);
		req.setAttribute("msg", msg);
		return "common/redirect";
	}
}